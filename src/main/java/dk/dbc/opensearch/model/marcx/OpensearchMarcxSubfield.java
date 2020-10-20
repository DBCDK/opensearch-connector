/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model.marcx;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dk.dbc.opensearch.model.OpensearchFieldValue;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchMarcxSubfield {

    public OpensearchMarcxSubfield() {}

    // Subfield code
    private String code;

    // Subfield value
    private String value;

    public String getCode() {
        return code;
    }

    @JsonAlias("@code")
    public void setCode(OpensearchFieldValue code) {
        this.code = code.toString();
    }

    public String getValue() {
        return value;
    }

    @JsonAlias("$")
    public void setValue(String value) {
        this.value = value;
    }

    public OpensearchMarcxSubfield withCode(String code) {
        this.code = code;
        return this;
    }

    public OpensearchMarcxSubfield withValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        OpensearchMarcxSubfield that = (OpensearchMarcxSubfield) o;
        return code.equals(that.code) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, value);
    }
}
