package com.microservice.pruebatecnica.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * The Errors Response.
 * <p>
 * This is a dto containing the errors handler in the api.
 */
@Getter @Setter @AllArgsConstructor  public class ErrorsResponse {
    /**
     * The Errors.
     */
    private List<ErrorResponse> errors;
}
