/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchSearchResult {

    public OpensearchSearchResult() {}

    private OpensearchCollection collection;

    public OpensearchCollection getCollection() {
        return collection;
    }

    public void setCollection(OpensearchCollection collection) {
        this.collection = collection;
    }

    public OpensearchSearchResult withCollection(OpensearchCollection collection) {
        this.collection = collection;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchSearchResult that = (OpensearchSearchResult) o;
        return collection.equals(that.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection);
    }
}
