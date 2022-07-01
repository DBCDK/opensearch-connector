package dk.dbc.opensearch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class OpensearchQuery {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpensearchQuery.class);

    // Search combiner
    private OpensearchQueryCombiner combiner = OpensearchQueryCombiner.AND;

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

    // Index key 'bc'
    private String bc;

    // Index key 'marc.001b'
    private List<String> marc001b;

    // Index key 'term.021ex'
    private String term021ex;

    // Index key 'ld'
    private String ld;

    /* Place future needed index key fields here */

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

    public String getBc() {
        return bc;
    }

    public void setBc(String bc) {
        this.bc = bc;
    }

    public List<String> getMarc001b() {
        return this.marc001b;
    }

    public void setMarc001b(List<String> marc001b) {
        this.marc001b = marc001b;
    }

    public void setMarc001b(String marc001b) {
        this.setMarc001b(List.of(marc001b));
    }

    public String getTerm021ex() {
        return this.term021ex;
    }

    public void setTerm021ex(String term021ex) {
        this.term021ex = term021ex;
    }

    public String getLd() {
        return this.ld;
    }

    public void setLd(String ld) {
        this.ld = ld;
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

    public OpensearchQuery withId(String id) {
        this.id = id;
        return this;
    }

    public OpensearchQuery withIs(String is) {
        this.is = is;
        return this;
    }

    public OpensearchQuery withBc(String bc) {
        this.bc = bc;
        return this;
    }

    public OpensearchQuery withMarc001b(List<String> marc001b) {
        this.marc001b = marc001b;
        return this;
    }

    public OpensearchQuery withMarc001b(String marc001b) {
        return this.withMarc001b(List.of(marc001b));
    }

    public OpensearchQuery withTerm021ex(String term021ex) {
        this.term021ex = term021ex;
        return this;
    }

    public OpensearchQuery withLd(String ld) {
        this.ld = ld;
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
        if(this.bc != null && !this.bc.isBlank()) {
            query = addToQueryString(query, "bc=" + bc);
        }
        if(this.term021ex != null && !this.term021ex.isBlank()) {
            query = addToQueryString(query, "term.021ex=" + term021ex);
        }
        if(this.ld != null && !this.ld.isBlank()) {
            query = addToQueryString(query, "ld=" + ld);
        }
        if(this.marc001b != null && !this.marc001b.isEmpty() && !query.isEmpty()) {
            String joinedMarc001b = this.marc001b.stream().collect(Collectors.joining(" OR marc.001b=", "(marc.001b=", ")"));
            query = "(" + query + ") AND " + joinedMarc001b;
        }

        // Encode the query, but convert encoded blankspace from %2B (+) to %20 (real blank)
        // since Opensearch do not understand the separator character '+'
        String encoded= URLEncoder.encode(query, StandardCharsets.UTF_8.toString()).replace("+", "%20");

        return encoded;
    }
}
