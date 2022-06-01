package com.microservice.pruebatecnica.util;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.microservice.pruebatecnica.model.constants.CollectionConstants;
import com.microservice.pruebatecnica.model.dtos.CollectionData;

import java.io.IOException;
/**
 * The type Collections deserializer.
 */
public class CollectionsDeserializer extends JsonDeserializer<CollectionData> {

    @Override public CollectionData deserialize (JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        JsonNode collectionNode = jsonParser.getCodec().readTree(jsonParser);
        CollectionData collectionData = new CollectionData();
        collectionData.setId(collectionNode.get(CollectionConstants.ID).asText());
        collectionData.setDescription(collectionNode.get(CollectionConstants.DESCRIPTION).textValue());
        collectionData.setTitle(collectionNode.get(CollectionConstants.TITLE).textValue());
        collectionData.setPhotoId(collectionNode.get(CollectionConstants.COVER_PHOTO).get(CollectionConstants.ID).textValue());
        return collectionData;
    }
}