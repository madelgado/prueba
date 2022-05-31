package com.microservice.pruebatecnica.model.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.microservice.pruebatecnica.util.CollectionsDeserializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The common base for the invoices object
 * The common variable
 * -Code
 * -Name
 */
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @JsonDeserialize(using = CollectionsDeserializer.class) public class CollectionData {

    /**
     * The id
     */
    private String id;

    /**
     * The title
     */
    private String title;

    /**
     * The description
     */
    private String description;

    @JsonProperty("cover_photo_id") private String photoId;

}
