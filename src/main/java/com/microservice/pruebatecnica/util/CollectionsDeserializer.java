package com.microservice.pruebatecnica.util;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
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
        collectionData.setId(collectionNode.get("id").asText());
        collectionData.setDescription(collectionNode.get("description").textValue());
        collectionData.setTitle(collectionNode.get("title").textValue());
        collectionData.setPhotoId(collectionNode.get("cover_photo").get("id").textValue());
        return collectionData;
    }
}