package dk.dbc.opensearch;

public class OpensearchConnectorTestWireMockRecorder {
        /*
        Steps to reproduce wiremock recording:

        * Start standalone runner in src/test/resources (That contains __files and mappings)
            java -jar wiremock-standalone-{WIRE_MOCK_VERSION}.jar --proxy-all="{Opensearch_SERVICE_HOST}" --record-mappings --verbose

        * Run the main method of this class
            mvn exec:java -Dexec.mainClass="dk.dbc.opensearch.OpensearchConnectorTestWireMockRecorder"

        * Replace content of src/test/resources/{__files|mappings} with that produced by the standalone runner
     */
    public static void main(String[] args) throws Exception {
        OpensearchConnectorTest.connector = new OpensearchConnector(
                OpensearchConnectorTest.CLIENT, "http://localhost:8080", "test", "100200", "rawrepo_basis");
        final OpensearchConnectorTest OpensearchConnectorTest = new OpensearchConnectorTest();
        recordGetApplicantRequests(OpensearchConnectorTest);
    }

    private static void recordGetApplicantRequests(OpensearchConnectorTest OpensearchConnectorTest)
            throws OpensearchConnectorException {
        OpensearchConnectorTest.testOpensearchSearchResponse();
        OpensearchConnectorTest.testOpensearchHitCount();
        OpensearchConnectorTest.testOpensearchRandomSampleValues();
        OpensearchConnectorTest.testOpensearchIdSearchresult();
        OpensearchConnectorTest.testOpensearchIsSearchresult();
        OpensearchConnectorTest.testOpensearchGetNonexistingSubfieldValueFromResult();
        OpensearchConnectorTest.testOpensearchGetFaustFromResult();
        OpensearchConnectorTest.testOpensearchCombinedSearch();
        OpensearchConnectorTest.testOpensearchBcSearchresult();
        OpensearchConnectorTest.testOpensearchTerm021exSearchresult();
        OpensearchConnectorTest.testOpensearchLdSearchResult();
        OpensearchConnectorTest.testOpensearchSeveralMarc001bSearchResult();
    }

}
