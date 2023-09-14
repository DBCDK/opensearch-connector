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
