/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model;

import java.util.Objects;

public class OpensearchSearchResponse {

    public OpensearchSearchResponse() {}

    private OpensearchResult result;

    private String error = "";

    public OpensearchResult getResult() {
        return result;
    }

    public void setResult(OpensearchResult result) {
        this.result = result;
    }

    public String getError() {
        return error;
    }

    public void setError(OpensearchResult error) {
        this.error = error.toString();
    }

    public OpensearchSearchResponse withResult(OpensearchResult result) {
        this.result = result;
        return this;
    }

    public OpensearchSearchResponse withError(String error) {
        this.error = error;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchSearchResponse that = (OpensearchSearchResponse) o;
        return Objects.equals(result, that.result) &&
                Objects.equals(error, that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, error);
    }
}
