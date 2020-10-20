/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GNU GPL v3
 *  See license text at https://opensource.dbc.dk/licenses/gpl-3.0
 */

package dk.dbc.opensearch.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Toplevel result object from an Opensearch search request
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpensearchResult {

    public OpensearchResult() {}
}
