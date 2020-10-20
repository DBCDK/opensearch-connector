/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchCollection {

    public OpensearchCollection() {}

    // Collection position in entire search result
    private int resultPosition;

    // Number of objects contained in this collection
    private int numberOfObjects;

    // A list of objects returned by the search
    private OpensearchObject[] object;

    public int getResultPosition() {
        return resultPosition;
    }

    public void setResultPosition(OpensearchFieldValue resultPosition) {
        this.resultPosition = resultPosition.toInteger();
    }

    public int getNumberOfObjects() {
        return numberOfObjects;
    }

    public void setNumberOfObjects(OpensearchFieldValue numberOfObjects) {
        this.numberOfObjects = numberOfObjects.toInteger();
    }

    public OpensearchObject[] getObject() {
        return object;
    }

    public void setObject(OpensearchObject[] object) {
        this.object = object;
    }

    public OpensearchCollection withResultPosition(int position) {
        this.resultPosition = position;
        return this;
    }

    public OpensearchCollection withNumberOfObjects(int objects) {
        this.numberOfObjects = objects;
        return this;
    }

    public OpensearchCollection withObject(OpensearchObject[] object) {
        this.object = object;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchCollection that = (OpensearchCollection) o;
        return resultPosition == that.resultPosition &&
                numberOfObjects == that.numberOfObjects &&
                object.equals(that.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultPosition, numberOfObjects, object);
    }
}
