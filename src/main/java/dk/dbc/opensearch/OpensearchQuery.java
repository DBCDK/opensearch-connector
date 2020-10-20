/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class OpensearchQuery {

    /* Todo: Add query terms and builder function */
    // Search separator, currently fixed to AND, but we may later wish to make this configurable
    private static String separator = " AND ";

    // Agency - default using 100200
    private String agency = "100200";

    // Search profile - default using 'test'
    private String profile = "test";

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

    private String addToQueryString(String query, String term) {
        return (!query.isEmpty() ? separator : "") + term;
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

        return String.format("agency=%s&profile=%s&start=1&stepValue=10&query=%s",
                agency,
                profile,
                URLEncoder.encode(query, StandardCharsets.UTF_8.toString()));
    }
}
