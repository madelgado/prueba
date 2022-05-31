package com.microservice.pruebatecnica.service.implementation;
import com.microservice.pruebatecnica.model.dtos.CollectionData;
import com.microservice.pruebatecnica.service.IFilterService;
import com.microservice.pruebatecnica.util.RestApiCallUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import reactor.core.publisher.Flux;

import javax.net.ssl.SSLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {FilterService.class}) @ActiveProfiles("test") class FilterServiceTest {

    @MockBean private RestApiCallUtils restApiCallUtils;

    @Autowired private IFilterService iFilterService;

    private final CollectionData collectionData = new CollectionData("1", "blabla", "desc", "c-wef");

    @Test @DisplayName("Test empty filter") void collectionFilterEmpty () throws SSLException {
        Flux<CollectionData> data = Flux.just(collectionData);
        when(restApiCallUtils.unplashAPICall()).thenReturn(data);
        List<CollectionData> result = iFilterService.collectionFilter("");
        assertTrue(result.size() > 0);
    }

    @Test @DisplayName("Test multiple filters not match") void collectionFilterMultiple () throws SSLException {
        Flux<CollectionData> data = Flux.just(collectionData);
        when(restApiCallUtils.unplashAPICall()).thenReturn(data);
        List<CollectionData> result = iFilterService.collectionFilter("id=3,title=blabla");
        assertFalse(result.size() > 0);

    }

    @Test @DisplayName("Test error filter") void collectionFilterError () throws SSLException {
        Flux<CollectionData> data = Flux.just(collectionData);
        when(restApiCallUtils.unplashAPICall()).thenReturn(data);
        List<CollectionData> result = iFilterService.collectionFilter("3");
        assertTrue(result.size() > 0);
    }

    @Test @DisplayName("Test correct filters") void collectionFilterMultipleCorrect () throws SSLException {
        Flux<CollectionData> data = Flux.just(collectionData);
        when(restApiCallUtils.unplashAPICall()).thenReturn(data);
        List<CollectionData> result = iFilterService.collectionFilter("id=1,title=blabla");
        assertTrue(result.size() > 0);
    }
}