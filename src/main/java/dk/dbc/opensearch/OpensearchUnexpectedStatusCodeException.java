/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch;

public class OpensearchUnexpectedStatusCodeException extends OpensearchConnectorException {
    private final int statusCode;

    public OpensearchUnexpectedStatusCodeException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
