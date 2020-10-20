/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dk.dbc.opensearch.model.marcx.OpensearchMarcxCollection;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchObject {

    public OpensearchObject() {}

    private OpensearchMarcxCollection collection;

    public OpensearchMarcxCollection getCollection() {
        return collection;
    }

    public void setCollection(OpensearchMarcxCollection marcxCollection) {
        this.collection = marcxCollection;
    }

    public OpensearchObject withCollection(OpensearchMarcxCollection collection) {
        this.collection = collection;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchObject that = (OpensearchObject) o;
        return collection.equals(that.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collection);
    }
}
