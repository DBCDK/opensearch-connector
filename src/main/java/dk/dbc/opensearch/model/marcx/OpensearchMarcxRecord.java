/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model.marcx;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dk.dbc.opensearch.model.OpensearchFieldValue;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchMarcxRecord {

    public OpensearchMarcxRecord() {}

    private String leader;

    private OpensearchMarcxDatafield[] datafield;

    public String getLeader() {
        return leader;
    }

    public void setLeader(OpensearchFieldValue leader) {
        this.leader = leader.toString();
    }

    public OpensearchMarcxDatafield[] getDatafield() {
        return datafield;
    }

    public void setDatafield(OpensearchMarcxDatafield[] datafield) {
        this.datafield = datafield;
    }

    public OpensearchMarcxRecord withLeader(String leader) {
        this.leader = leader;
        return this;
    }

    public OpensearchMarcxRecord withDatafield(OpensearchMarcxDatafield[] datafield) {
        this.datafield = datafield;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchMarcxRecord that = (OpensearchMarcxRecord) o;
        return leader.equals(that.leader) &&
                datafield.equals(that.datafield);
    }

    @Override
    public int hashCode() {
        return Objects.hash(leader, datafield);
    }
}
