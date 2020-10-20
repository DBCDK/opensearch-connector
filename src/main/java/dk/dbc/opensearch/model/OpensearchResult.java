/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GNU GPL v3
 *  See license text at https://opensource.dbc.dk/licenses/gpl-3.0
 */

package dk.dbc.opensearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

/**
 * Toplevel result object from an Opensearch search request
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchResult {

    public OpensearchResult() {}

    // Number of results total
    public int hitCount;

    // Number of collections returned by this object
    public int collectionCount;

    // More results than what is returned by this object
    private boolean more;

    // The results
    OpensearchSearchResult[] searchResult;

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(OpensearchFieldValue hitCount)  {
        this.hitCount = hitCount.toInteger();
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(OpensearchFieldValue collectionCount) {
        this.collectionCount = collectionCount.toInteger();
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(OpensearchFieldValue more) {
        this.more = more.toBoolean();
    }

    public OpensearchSearchResult[] getSearchResult() {
        return searchResult;
    }

    public void setSearchResult(OpensearchSearchResult[] searchResult) {
        this.searchResult = searchResult;
    }

    public OpensearchResult withHitcount(int count) {
        this.hitCount = count;
        return this;
    }

    public OpensearchResult withCollectionCount(int count) {
        this.collectionCount = count;
        return this;
    }

    public OpensearchResult withMore(boolean more) {
        this.more = more;
        return this;
    }

    public OpensearchResult withSearchResult(OpensearchSearchResult[] searchResult) {
        this.searchResult = searchResult;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchResult that = (OpensearchResult) o;
        return hitCount == that.hitCount &&
                collectionCount == that.collectionCount &&
                more == that.more &&
                searchResult.equals(that.searchResult);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hitCount, collectionCount, more, searchResult);
    }
}
