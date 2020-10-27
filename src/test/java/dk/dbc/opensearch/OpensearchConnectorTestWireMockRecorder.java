/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch;

public class OpensearchConnectorTestWireMockRecorder {
        /*
        Steps to reproduce wiremock recording:

        * Start standalone runner
            java -jar wiremock-standalone-{WIRE_MOCK_VERSION}.jar --proxy-all="{Opensearch_SERVICE_HOST}" --record-mappings --verbose

        * Run the main method of this class

        * Replace content of src/test/resources/{__files|mappings} with that produced by the standalone runner
     */
    public static void main(String[] args) throws Exception {
        OpensearchConnectorTest.connector = new OpensearchConnector(
                OpensearchConnectorTest.CLIENT, "http://localhost:8080");
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
    }

}
