/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OpensearchQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpensearchQuery.class);

    // Search combiner
    private OpensearchQueryCombiner combiner = OpensearchQueryCombiner.AND;

    // Agency - default using 100200
    private String agency = "100200";

    // Search profile - default using 'test'
    private String profile = "test";

    // Starting result
    private int start = 1;

    // Max. Number of results per 'page'
    private int stepValue = 10;

    // Freetext query string
    private String freetext;

    // Index key 'id'
    private String id;

    // Index key 'is'
    private String is;

    /* Place future needed index key fields here */

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFreetext() {
        return freetext;
    }

    public void setFreetext(String freetext) {
        this.freetext = freetext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs() {
        return is;
    }

    public void setIs(String is) {
        this.is = is;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getStepValue() {
        return stepValue;
    }

    public void setStepValue(int stepValue) {
        this.stepValue = stepValue;
    }

    public void setCombiner(OpensearchQueryCombiner combiner) {
        this.combiner = combiner;
    }

    public OpensearchQueryCombiner getCombiner() {
        return this.combiner;
    }

    public OpensearchQuery withFreetext(String text) {
        this.freetext = text;
        return this;
    }

    public OpensearchQuery withAgency(String agency) {
        this.agency = agency;
        return this;
    }

    public OpensearchQuery withProfile(String profile) {
        this.profile = profile;
        return this;
    }

    public OpensearchQuery withId(String id) {
        this.id = id;
        return this;
    }

    public OpensearchQuery withIs(String is) {
        this.is = is;
        return this;
    }

    public OpensearchQuery withCombiner(OpensearchQueryCombiner combiner) {
        this.combiner = combiner;
        return this;
    }

    private String addToQueryString(String query, String term) {
        final String separator = combiner == OpensearchQueryCombiner.AND
                ? " AND "
                : " OR ";

        return (!query.isEmpty() ? query + separator : "") + term;
    }

    public String build() throws java.io.UnsupportedEncodingException {
        String query = "";

        if(this.freetext != null && !this.freetext.isBlank()) {
            query = addToQueryString(query, String.format("\"%s\"", freetext));
        }
        if(this.id != null && !this.id.isBlank()) {
            query = addToQueryString(query, "id=" + id);
        }
        if(this.is != null && !this.is.isBlank()) {
            query = addToQueryString(query, "is=" + is);
        }

        // Encode the query, but convert encoded blankspace from %2B (+) to %20 (real blank)
        // since Opensearch do not understand the separator character '+'
        return URLEncoder.encode(query, StandardCharsets.UTF_8.toString()).replace("+", "%20");
    }
}
