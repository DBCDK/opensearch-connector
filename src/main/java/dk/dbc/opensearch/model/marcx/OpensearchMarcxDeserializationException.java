/*
 * Copyright Dansk Bibliotekscenter a/s. Licensed under GPLv3
 * See license text in LICENSE.txt or at https://opensource.dbc.dk/licenses/gpl-3.0/
 */

package dk.dbc.opensearch.model.marcx;

import com.fasterxml.jackson.core.JsonProcessingException;

public class OpensearchMarcxDeserializationException extends JsonProcessingException {

    public OpensearchMarcxDeserializationException(String message) {
        super(message);
    }
}
