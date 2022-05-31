package com.microservice.pruebatecnica.controller;

import com.microservice.pruebatecnica.model.dtos.CollectionData;
import com.microservice.pruebatecnica.model.dtos.ErrorsResponse;
import com.microservice.pruebatecnica.service.IFilterService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.net.ssl.SSLException;

/**
 * CollectionFilterController
 * Expose the method to obtain collections by filter
 */
@RestController @Log4j2 public class CollectionFilterController {

    @Autowired private IFilterService filterService;

    /**
     *
     * @param filter the filter
     * @return the ResponseLogin
     * @throws SSLException the ssl exception
     */
    @Operation(summary = "Collection by filters")
    @GetMapping(value = "/collection/all", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Collection encontrada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CollectionData.class)) }),
            @ApiResponse(responseCode = "400", description = "Filtros invalidos",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class)) }),
            @ApiResponse(responseCode = "500", description = "Error inesperado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorsResponse.class)) })})
    public ResponseEntity collectionsFiltered (@Parameter(description = "filtros a aplicar a la colleccion, si se ponen con formato incorrecto o se deja vacío, no se aplica filtro"
    ,example = "id=3,title=blabla,description=description,cover_photo.id=c-w45")
    @RequestParam(name = "filter", required = false) String filter) throws SSLException {

        return ResponseEntity.ok(filterService.collectionFilter(filter));
    }

}
