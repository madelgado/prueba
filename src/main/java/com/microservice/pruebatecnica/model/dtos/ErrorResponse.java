package com.microservice.pruebatecnica.model.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Error Response.
 * <p>
 * This is a dto containing the error detail.
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @JsonInclude(JsonInclude.Include.NON_NULL) public class ErrorResponse {
    /**
     * The Code.
     */
    private String code;
    /**
     * The Message.
     */
    private String message;
    /**
     * The Level.
     */
    private String level;
    /**
     * The Description.
     */
    private String description;

}
