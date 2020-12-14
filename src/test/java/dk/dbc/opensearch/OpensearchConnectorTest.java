/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */
package dk.dbc.opensearch;

import com.github.tomakehurst.wiremock.WireMockServer;
import dk.dbc.httpclient.HttpClient;

import dk.dbc.opensearch.model.OpensearchResult;
import dk.dbc.opensearch.model.OpensearchSearchResponse;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.ws.rs.client.Client;

import java.util.Arrays;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class OpensearchConnectorTest {
    private static WireMockServer wireMockServer;
    private static String wireMockHost;

    final static Client CLIENT = HttpClient.newClient(new ClientConfig()
            .register(new JacksonFeature()));
    static OpensearchConnector connector;

    @BeforeAll
    static void startWireMockServer() {

        wireMockServer = new WireMockServer(options().dynamicPort()
                .dynamicHttpsPort());
        wireMockServer.start();
        wireMockHost = "http://localhost:" + wireMockServer.port();
        configureFor("localhost", wireMockServer.port());
    }

    @BeforeAll
    static void setConnector() throws OpensearchConnectorException {
        connector = new OpensearchConnector(CLIENT, wireMockHost, "test", "100200", "rawrepo_basis");
    }

    @AfterAll
    static void stopWireMockServer() {
        wireMockServer.stop();
    }

    @Test
    public void testOpensearchSearchResponse() throws OpensearchConnectorException {

        try {
            OpensearchSearchResponse response = connector.search(new OpensearchQuery().withFreetext("zebra"));
            assertThat(response.getError().isEmpty(), is(true));
        }
        catch(OpensearchConnectorException connectorException) {
            throw connectorException;
        }
    }

    @Test
    public void testOpensearchHitCount() throws OpensearchConnectorException {

        try {
            OpensearchSearchResponse response = connector.search(new OpensearchQuery().withFreetext("zebra"));
            OpensearchResult result = response.getResult();
            assertThat(result.getHitCount(), is(572));
        }
        catch(OpensearchConnectorException connectorException) {
            throw connectorException;
        }
    }

    @Test
    public void testOpensearchRandomSampleValues() throws OpensearchConnectorException {

        try {
            OpensearchSearchResponse response = connector.search(new OpensearchQuery().withFreetext("zebra"));
            OpensearchResult result = response.getResult();
            assertThat(result.collectionCount, is(10));

            // First object, first record and data
            assertThat(result.getSearchResult()[0].getCollection().getNumberOfObjects(), is(1));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getLeader(), is("00000n    2200000   4500"));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield().length, is(27));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getTag(), is("001"));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield().length, is(5));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[0].getCode(), is("a"));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[0].getValue(), is("49224842"));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[2].getCode(), is("c"));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[2].getValue(), is("20200612105613"));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[4].getCode(), is("f"));
            assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[4].getValue(), is("a"));

            // Last object, last record and data
            assertThat(result.getSearchResult()[9].getCollection().getNumberOfObjects(), is(1));
            assertThat(result.getSearchResult()[9].getCollection().getObject()[0].getCollection().getRecord().getLeader(), is("00000n    2200000   4500"));
            assertThat(result.getSearchResult()[9].getCollection().getObject()[0].getCollection().getRecord().getDatafield().length, is(15));
            assertThat(result.getSearchResult()[9].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[14].getTag(), is("996"));
            assertThat(result.getSearchResult()[9].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[14].getSubfield().length, is(1));
            assertThat(result.getSearchResult()[9].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[14].getSubfield()[0].getCode(), is("a"));
            assertThat(result.getSearchResult()[9].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[14].getSubfield()[0].getValue(), is("DBC"));
        }
        catch(OpensearchConnectorException connectorException) {
            throw connectorException;
        }
    }

    @Test
    public void testOpensearchIdSearchresult() throws OpensearchConnectorException {
        OpensearchSearchResponse response = connector.search(new OpensearchQuery().withId("24699773"));
        OpensearchResult result = response.getResult();
        assertThat(result.collectionCount, is(1));

        assertThat(result.getSearchResult()[0].getCollection().getNumberOfObjects(), is(1));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getLeader(), is("00000n    2200000   4500"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield().length, is(34));

        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getTag(), is("001"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield().length, is(5));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[0].getCode(), is("a"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[0].getValue(), is("24699773"));

        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[5].getTag(), is("021"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[5].getSubfield().length, is(3));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[5].getSubfield()[0].getCode(), is("e"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[5].getSubfield()[0].getValue(), is("9788764432589"));
    }

    @Test
    public void testOpensearchIsSearchresult() throws OpensearchConnectorException {
        OpensearchSearchResponse response = connector.search(new OpensearchQuery().withIs("9788764432589"));
        OpensearchResult result = response.getResult();
        assertThat(result.hitCount, is(1));
        assertThat(result.collectionCount, is(1));

        assertThat(result.getSearchResult()[0].getCollection().getNumberOfObjects(), is(1));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getLeader(), is("00000n    2200000   4500"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield().length, is(34));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getTag(), is("001"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield().length, is(5));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[0].getCode(), is("a"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[0].getSubfield()[0].getValue(), is("24699773"));

        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[5].getTag(), is("021"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[5].getSubfield().length, is(3));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[5].getSubfield()[0].getCode(), is("e"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[5].getSubfield()[0].getValue(), is("9788764432589"));
    }

    @Test
    public void testOpensearchGetNonexistingSubfieldValueFromResult() throws OpensearchConnectorException {
        OpensearchSearchResponse response = connector.search(new OpensearchQuery().withIs("9788764432589"));
        OpensearchResult result = response.getResult();
        assertThat(result.hitCount, is(1));
        assertThat(result.collectionCount, is(1));
        assertThat(result.getSearchResult()[0].getCollection().getNumberOfObjects(), is(1));

        // Unknown field with fieldcode = 999
        String value = result.getSearchResult()[0]
                .getCollection()
                .getObject()[0]
                .getCollection()
                .getRecord()
                .getDatafield("999")
                .getSubfield("q")
                .getValue();
        assertThat(value, is(""));

        // Unknown subfield with subfieldcode = q in known field with fieldcode = 001
        value = result.getSearchResult()[0]
                .getCollection()
                .getObject()[0]
                .getCollection()
                .getRecord()
                .getDatafield("001")
                .getSubfield("q")
                .getValue();
        assertThat(value, is(""));
    }

    @Test
    public void testOpensearchGetFaustFromResult() throws OpensearchConnectorException {
        OpensearchSearchResponse response = connector.search(new OpensearchQuery().withIs("9788764432589"));
        OpensearchResult result = response.getResult();
        assertThat(result.hitCount, is(1));
        assertThat(result.collectionCount, is(1));
        assertThat(result.getSearchResult()[0].getCollection().getNumberOfObjects(), is(1));

        // Long cumberson way with precise indexing
        String faust = result.getSearchResult()[0]
                .getCollection()
                .getObject()[0]
                .getCollection()
                .getRecord()
                .getDatafield("001")
                .getSubfield("a")
                .getValue();
        assertThat(faust, is("24699773"));
    }

    @Test
    public void testOpensearchCombinedSearch() throws OpensearchConnectorException {
        OpensearchSearchResponse response = connector.search(new OpensearchQuery().withCombiner(OpensearchQueryCombiner.AND).withId("24699773").withIs("9788764432589"));
        OpensearchResult result = response.getResult();
        assertThat(result.hitCount, is(1));

        response = connector.search(new OpensearchQuery().withCombiner(OpensearchQueryCombiner.OR).withId("24699773").withIs("9788779730014"));
        result = response.getResult();
        assertThat(result.hitCount, is(2));

        response = connector.search(new OpensearchQuery().withCombiner(OpensearchQueryCombiner.AND).withId("24699773").withIs("9788779730014"));
        result = response.getResult();
        assertThat(result.hitCount, is(0));
    }

    @Test
    public void testOpensearchBcSearchresult() throws OpensearchConnectorException {
        OpensearchSearchResponse response = connector.search(new OpensearchQuery().withBc("5053083221386"));
        OpensearchResult result = response.getResult();
        assertThat(result.hitCount, is(1));
        assertThat(result.collectionCount, is(1));

        assertThat(result.getSearchResult()[0].getCollection().getNumberOfObjects(), is(1));
        assertThat(Arrays.stream(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()).count(), is(43L));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[6].getTag(), is("023"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[6].getSubfield().length, is(1));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[6].getSubfield()[0].getCode(), is("b"));
        assertThat(result.getSearchResult()[0].getCollection().getObject()[0].getCollection().getRecord().getDatafield()[6].getSubfield()[0].getValue(), is("5053083221386"));
    }
}