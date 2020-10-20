/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model.marcx;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchMarcxCollection {

    public OpensearchMarcxCollection() {}

    private OpensearchMarcxRecord record;

    public OpensearchMarcxRecord getRecord() {
        return record;
    }

    public void setRecord(OpensearchMarcxRecord marcxRecord) {
        this.record = marcxRecord;
    }

    public OpensearchMarcxCollection withRecord(OpensearchMarcxRecord record) {
        this.record = record;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchMarcxCollection that = (OpensearchMarcxCollection) o;
        return Objects.equals(record, that.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(record);
    }
}
