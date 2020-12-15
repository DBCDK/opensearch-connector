/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch;

import dk.dbc.httpclient.FailSafeHttpClient;
import dk.dbc.httpclient.HttpGet;
import dk.dbc.invariant.InvariantUtil;

import dk.dbc.opensearch.model.OpensearchEntity;
import dk.dbc.opensearch.model.OpensearchSearchResponse;
import net.jodah.failsafe.RetryPolicy;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import dk.dbc.util.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * OpensearchConnector - opensearch client
 * <p>
 * To use this class, you construct an instance, specifying a web resources client as well as
 * a base URL for the opensearch service endpoint you will be communicating with.
 * </p>
 * <p>
 * This class is thread safe, as long as the given web resources client remains thread safe.
 * </p>
 */
public class OpensearchConnector {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpensearchConnector.class);

    private static final RetryPolicy<Response> RETRY_POLICY = new RetryPolicy<Response>()
            .handle(ProcessingException.class)
            .handleResultIf(response ->
                    response.getStatus() == 404
                            || response.getStatus() == 500
                            || response.getStatus() == 502)
            .withDelay(Duration.ofSeconds(5))
            .withMaxRetries(3);

    private FailSafeHttpClient failSafeHttpClient;
    private String baseUrl;

    private String profile;
    private String agency;
    private String repository;

    public OpensearchConnector(Client httpClient, String baseUrl, String profile, String agency, String repository) {
        this(FailSafeHttpClient.create(httpClient, RETRY_POLICY), baseUrl, profile, agency, repository);
    }

    /**
     * Returns new instance with custom retry policy
     *
     * @param failSafeHttpClient web resources client with custom retry policy
     * @param baseUrl            base URL for record service endpoint
     * @throws OpensearchConnectorException on failure to create {@link dk.dbc.opensearch.OpensearchConnector}
     */
    public OpensearchConnector(FailSafeHttpClient failSafeHttpClient, String baseUrl, String profile, String agency, String repository) {
        this.failSafeHttpClient = InvariantUtil.checkNotNullOrThrow(
                failSafeHttpClient, "failSafeHttpClient");
        this.baseUrl = InvariantUtil.checkNotNullNotEmptyOrThrow(
                baseUrl, "baseUrl");
        this.profile = InvariantUtil.checkNotNullNotEmptyOrThrow(
                profile, "profile");
        this.agency = InvariantUtil.checkNotNullNotEmptyOrThrow(
                agency, "agency");
        this.repository = InvariantUtil.checkNotNullNotEmptyOrThrow(
                repository, "repository");
    }

    public OpensearchSearchResponse search(OpensearchQuery query) throws OpensearchConnectorException {
        final Stopwatch stopwatch = new Stopwatch();

        try {
            LOGGER.info("Search request with action=search and query {}", query.build());

            final HttpGet httpGet = new HttpGet(failSafeHttpClient)
                    .withBaseUrl(baseUrl)
                    .withQueryParameter("action", "search")
                    .withQueryParameter("outputType", "json")
                    .withQueryParameter("collectionType", "work")
                    .withQueryParameter("objectFormat", "marcxchange")
                    .withQueryParameter("agency", agency)
                    .withQueryParameter("profile", profile)
                    .withQueryParameter("repository", repository)
                    .withQueryParameter("start", query.getStart())
                    .withQueryParameter("stepValue", query.getStepValue())
                    .withQueryParameter("query", query.build());

            return sendGetRequest(httpGet);
        } catch(java.io.UnsupportedEncodingException exception) {
            LOGGER.error("Query contains characters that can not be url encoded: {}", exception);
            throw new OpensearchConnectorException("Query contains characters that can not be url encoded", exception);
        } finally {
            LOGGER.info("search took {} ms", stopwatch.getElapsedTime(TimeUnit.MILLISECONDS));
        }
    }

    public void close() {
        failSafeHttpClient.getClient().close();
    }

    private OpensearchSearchResponse sendGetRequest(HttpGet httpGet) throws OpensearchConnectorException {
        LOGGER.info("Search request with query: {}", httpGet.toString());

        final Response response = httpGet.execute();
        assertResponseStatus(response, Response.Status.OK);

        return readResponseEntity(response);
    }

    private OpensearchSearchResponse readResponseEntity(Response response) throws OpensearchConnectorException {

        final OpensearchEntity entity = response.readEntity(OpensearchEntity.class);
        if (entity == null) {
            throw new OpensearchConnectorException("Opensearch returned with null-valued %s entity");
        }

        if(!entity.getSearchResponse().getError().isEmpty()) {
            LOGGER.error("Error from Opensearch: {}", entity.getSearchResponse().getError());
        } else {
            LOGGER.info("Got response with {} results", entity.getSearchResponse().getResult().getHitCount());
        }

        return entity.getSearchResponse();
    }

    private void assertResponseStatus(Response response, Response.Status expectedStatus)
            throws OpensearchUnexpectedStatusCodeException {
        final Response.Status actualStatus =
                Response.Status.fromStatusCode(response.getStatus());
        if (actualStatus != expectedStatus) {
            throw new OpensearchUnexpectedStatusCodeException(
                    String.format("Opensearch returned with unexpected status code: %s",
                            actualStatus), actualStatus.getStatusCode());
        }
    }
}