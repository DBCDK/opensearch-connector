package dk.dbc.opensearch;

import dk.dbc.httpclient.HttpClient;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.ws.rs.client.Client;

/**
 * OpensearchConnector factory
 * <p>
 * Synopsis:
 * </p>
 * <pre>
 *    // New instance
 *    OpensearchConnector connector = OpensearchConnectorFactory.create("http://opensearch-service");
 *
 *    // Singleton instance in CDI enabled environment
 *    {@literal @}Inject
 *    OpensearchConnectorFactory factory;
 *    ...
 *    OpensearchConnector connector = factory.getInstance();
 *
 *    // or simply
 *    {@literal @}Inject
 *    OpensearchConnector connector;
 * </pre>
 * <p>
 * The CDI case depends on the Opensearch service base-url being defined as
 * the value of either a system property or environment variable
 * named OPENSEARCH_SERVICE_URL.
 * </p>
 */
@ApplicationScoped
public class OpensearchConnectorFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpensearchConnectorFactory.class);

    public static OpensearchConnector create(String baseUrl, String profile, String agency, String repository) throws OpensearchConnectorException {
        final Client client = HttpClient.newClient(new ClientConfig()
                .register(new JacksonFeature()));
        LOGGER.info("Creating OpensearchConnector for: {}", baseUrl);
        return new OpensearchConnector(client, baseUrl, profile, agency, repository);
    }

    @Inject
    @ConfigProperty(name = "OPENSEARCH_SERVICE_URL")
    private String baseUrl;

    @Inject
    @ConfigProperty(name = "OPENSEARCH_PROFILE", defaultValue = "test")
    private String profile;

    @Inject
    @ConfigProperty(name = "OPENSEARCH_AGENCY", defaultValue = "100200")
    private String agency;

    @Inject
    @ConfigProperty(name = "OPENSEARCH_REPOSITORY", defaultValue = "rawrepo_basis")
    private String repository;

    OpensearchConnector opensearchConnector;

    @PostConstruct
    public void initializeConnector() {
        try {
            opensearchConnector = dk.dbc.opensearch.OpensearchConnectorFactory.create(baseUrl, profile, agency, repository);
        } catch (OpensearchConnectorException e) {
            throw new IllegalStateException(e);
        }
    }

    @Produces
    public OpensearchConnector getInstance() {
        return opensearchConnector;
    }

    @PreDestroy
    public void tearDownConnector() {
        opensearchConnector.close();
    }
}
