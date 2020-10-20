/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model.marcx;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class OpensearchMarcxSubfieldDeserializer extends JsonDeserializer<OpensearchMarcxSubfield[]> {

    @Override
    public OpensearchMarcxSubfield[] deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        if(JsonToken.START_OBJECT.equals(jp.getCurrentToken())) {
            return new OpensearchMarcxSubfield[] { mapper.readValue(jp, OpensearchMarcxSubfield.class) };
        } else if(JsonToken.START_ARRAY.equals(jp.getCurrentToken())) {
            return mapper.readValue(jp, OpensearchMarcxSubfield[].class);
        }
        else {
            throw new OpensearchMarcxDeserializationException("Unexpected token received when either OpensearchMarcxSubfield or OpensearchMarcxSubfield[] was expected.");
        }
    }
}
