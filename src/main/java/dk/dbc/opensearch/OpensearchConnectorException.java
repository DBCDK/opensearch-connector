/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch;

public class OpensearchConnectorException extends Exception {
    public OpensearchConnectorException(String message) {
        super(message);
    }

    public OpensearchConnectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
