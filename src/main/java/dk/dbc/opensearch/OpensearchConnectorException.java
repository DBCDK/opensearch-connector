package dk.dbc.opensearch;

public class OpensearchConnectorException extends Exception {
    public OpensearchConnectorException(String message) {
        super(message);
    }

    public OpensearchConnectorException(String message, Throwable cause) {
        super(message, cause);
    }
}
