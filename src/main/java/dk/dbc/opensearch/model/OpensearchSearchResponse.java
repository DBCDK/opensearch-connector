/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model;

import java.util.Objects;

public class OpensearchSearchResponse {

    public OpensearchSearchResponse() {}

    private OpensearchResult result;

    public OpensearchResult getResult() {
        return result;
    }

    public void setResult(OpensearchResult result) {
        this.result = result;
    }

    public OpensearchSearchResponse withResult(OpensearchResult result) {
        this.result = result;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchSearchResponse that = (OpensearchSearchResponse) o;
        return result.equals(that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result);
    }
}
