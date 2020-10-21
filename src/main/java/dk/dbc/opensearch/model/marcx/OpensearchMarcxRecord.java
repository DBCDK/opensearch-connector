/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model.marcx;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dk.dbc.opensearch.model.OpensearchFieldValue;

import java.util.Arrays;
import java.util.Comparator;
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

    /**
     * Return either the first found datafield with fieldcode. If no datafields exists with the given
     * fieldcode, an empty datafield is returned as to allow a construct such as 'getDatafield("x99").getSubfield("q")' to return
     * an empty string instead of throwing an exception. Since this makes more sense in the scenarios where this getter
     * will be used.
     *
     * @param fieldcode The fieldcode
     * @return The found data or an empty datafield
     */
    public OpensearchMarcxDatafield getDatafield(String fieldcode) {
        if( datafield == null || datafield.length == 0 ) {
            return new OpensearchMarcxDatafield().withTag(fieldcode);
        }

        return Arrays.stream(datafield)
                .sorted(Comparator.comparing(OpensearchMarcxDatafield::getTag))
                .filter(f -> f.getTag().equals(fieldcode))
                .findFirst()
                .orElse(new OpensearchMarcxDatafield().withTag(fieldcode));
    }
}
