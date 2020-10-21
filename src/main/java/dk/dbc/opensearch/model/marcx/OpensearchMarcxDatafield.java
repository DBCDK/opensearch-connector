/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model.marcx;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import dk.dbc.opensearch.model.OpensearchFieldValue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchMarcxDatafield {

    public OpensearchMarcxDatafield() {}

    // Field number
    private String tag;

    // First indicator
    private String ind1;

    // Second indicator
    private String ind2;

    // Subfields
    private OpensearchMarcxSubfield[] subfield;

    public String getTag() {
        return tag;
    }

    @JsonAlias("@tag")
    public void setTag(OpensearchFieldValue tag) {
        this.tag = tag.toString();
    }

    public String getInd1() {
        return ind1;
    }

    @JsonAlias("@ind1")
    public void setInd1(OpensearchFieldValue ind1) {
        this.ind1 = ind1.toString();
    }

    public String getInd2() {
        return ind2;
    }

    @JsonAlias("@ind2")
    public void setInd2(OpensearchFieldValue ind2) {
        this.ind2 = ind2.toString();
    }

    public OpensearchMarcxSubfield[] getSubfield() {
        return subfield;
    }

    @JsonDeserialize(using = OpensearchMarcxSubfieldDeserializer.class)
    public void setSubfield(OpensearchMarcxSubfield[] subfield) {
        this.subfield = subfield;
    }

    public OpensearchMarcxDatafield withTag(String tag) {
        this.tag = tag;
        return this;
    }

    public OpensearchMarcxDatafield withSubfield(OpensearchMarcxSubfield[] subfield) {
        this.subfield = subfield;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchMarcxDatafield that = (OpensearchMarcxDatafield) o;
        return tag.equals(that.tag) &&
                Objects.equals(ind1, that.ind1) &&
                Objects.equals(ind2, that.ind2) &&
                subfield.equals(that.subfield);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, ind1, ind2, subfield);
    }

    /**
     * Return the first found subfield with the given subfield code
     *
     * @param subfieldCode The subfield code
     * @return Return either the first found subfield with subfieldCode. If no subfields exists with the given
     * subfieldCode, an empty subfieldfield is returned as to allow a construct such as 'getSubfield("q")' to return
     * an empty string instead of throwing an exception. Since this makes more sense in the scenarios where this getter
     * will be used.
     */
    public OpensearchMarcxSubfield getSubfield(String subfieldCode) {
        if( subfield == null || subfield.length == 0 ) {
            return new OpensearchMarcxSubfield().withCode(subfieldCode).withValue("");
        }

        return Arrays.stream(subfield)
                .sorted(Comparator.comparing(OpensearchMarcxSubfield::getCode))
                .filter(sf -> sf.getCode().equals(subfieldCode))
                .findFirst()
                .orElse(new OpensearchMarcxSubfield().withCode(subfieldCode).withValue(""));
    }
}
