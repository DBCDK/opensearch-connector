/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchEntity {

    public OpensearchEntity() {}

    private OpensearchSearchResponse searchResponse;

    public OpensearchSearchResponse getSearchResponse() {
        return searchResponse;
    }

    public void setSearchResponse(OpensearchSearchResponse searchResponse) {
        this.searchResponse = searchResponse;
    }

    public OpensearchEntity withSearchResponse(OpensearchSearchResponse searchResponse) {
        this.searchResponse = searchResponse;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchEntity that = (OpensearchEntity) o;
        return Objects.equals(searchResponse, that.searchResponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(searchResponse);
    }
}
