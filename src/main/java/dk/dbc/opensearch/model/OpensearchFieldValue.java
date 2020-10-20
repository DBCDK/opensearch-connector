/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * In the json output from Opensearch, almost every but not all field values is an object with
 * a single field '$' which contains the actual field value.
 *
 * Why?..  Properbly some archaic convention that can not be changed now opensearch is in wide public use
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchFieldValue {

    private String $;

    public OpensearchFieldValue() {}

    public void set$(String $) {
        this.$ = $;
    }

    public Integer toInteger() {
        return Integer.valueOf($);
    }

    public String toString() {
        return $;
    }

    public  Boolean toBoolean() {
        return Boolean.valueOf($);
    }
}
