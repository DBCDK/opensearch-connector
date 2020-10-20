/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch;

import dk.dbc.httpclient.FailSafeHttpClient;
import dk.dbc.httpclient.HttpGet;
import dk.dbc.invariant.InvariantUtil;

import dk.dbc.opensearch.model.OpensearchEntity;
import net.jodah.failsafe.RetryPolicy;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;
import java.util.Collections;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(dk.dbc.opensearch.OpensearchConnector.class);

    private static final RetryPolicy RETRY_POLICY = new RetryPolicy()
            .retryOn(Collections.singletonList(ProcessingException.class))
            .retryIf((Response response) ->
                    response.getStatus() == 404
                            || response.getStatus() == 500
                            || response.getStatus() == 502)
            .withDelay(5, TimeUnit.SECONDS)
            .withMaxRetries(3);

    private FailSafeHttpClient failSafeHttpClient;
    private String baseUrl;

    public OpensearchConnector(Client httpClient, String baseUrl) {
        this(FailSafeHttpClient.create(httpClient, RETRY_POLICY), baseUrl);
    }

    /**
     * Returns new instance with custom retry policy
     *
     * @param failSafeHttpClient web resources client with custom retry policy
     * @param baseUrl            base URL for record service endpoint
     * @throws OpensearchConnectorException on failure to create {@link dk.dbc.opensearch.OpensearchConnector}
     */
    public OpensearchConnector(FailSafeHttpClient failSafeHttpClient, String baseUrl) {
        this.failSafeHttpClient = InvariantUtil.checkNotNullOrThrow(
                failSafeHttpClient, "failSafeHttpClient");
        this.baseUrl = InvariantUtil.checkNotNullNotEmptyOrThrow(
                baseUrl, "baseUrl");
    }

    public OpensearchEntity search(OpensearchQuery query) throws OpensearchConnectorException {
        final Stopwatch stopwatch = new Stopwatch();

        // Todo: Add query terms
        OpensearchEntity entity = sendRequest(String.format("?action=search&outputType=json&collectionType=work&outputFormat=marcxchange&"));
        LOGGER.info("search took {} ms", stopwatch.getElapsedTime(TimeUnit.MILLISECONDS));

        return entity;
    }

    public void close() {
        failSafeHttpClient.getClient().close();
    }

    private OpensearchEntity sendRequest(String query) throws OpensearchConnectorException {
        LOGGER.info("Search request with query: {}", query);

        final HttpGet httpGet = new HttpGet(failSafeHttpClient).withBaseUrl(String.format("%s/%s", baseUrl, query));
        final Response response = httpGet.execute();
        assertResponseStatus(response, Response.Status.OK);

        return readResponseEntity(response);
    }

    private OpensearchEntity readResponseEntity(Response response) throws OpensearchConnectorException {

        final OpensearchEntity entity = response.readEntity(OpensearchEntity.class);
        if (entity == null) {
            throw new OpensearchConnectorException("Opensearch returned with null-valued %s entity");
        }
        return entity;
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