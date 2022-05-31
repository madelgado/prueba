package com.microservice.pruebatecnica.controller;

import com.microservice.pruebatecnica.handler.GlobalErrorWebExceptionHandler;
import com.microservice.pruebatecnica.service.IFilterService;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class) @AutoConfigureMockMvc @ContextConfiguration(classes = {CollectionFilterController.class,
        GlobalErrorWebExceptionHandler.class}) @SpringBootTest(webEnvironment = MOCK) @EnableWebMvc @ActiveProfiles("test") public class CollectionFilterControllerHandlerTest {

    private static final String COLLECTION_ALL = "/collection/all";
    private static final String FILTER = "filter";
    private static final String ID_FILTER = "id=2";

    private static MockWebServer server;
    @Autowired private MockMvc mvc;

    @MockBean private IFilterService service;

    @BeforeAll static void setUp () throws IOException {
        server = new MockWebServer();
        server.start();

    }

    @AfterAll static void tearDown () throws IOException {
        server.shutdown();
    }

    @Test @DisplayName("Test the collection/all controller bad request message not readable") public void getControllerBadRequestNotReadableException ()
            throws Exception {

        when(service.collectionFilter(nullable(String.class))).thenThrow(HttpMessageNotReadableException.class);

        mvc.perform(get(COLLECTION_ALL).param(FILTER, ID_FILTER).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

    @Test @DisplayName("Test the collection/all controller not elements") public void getControllerNotFoundCollectionException ()
            throws Exception {

        when(service.collectionFilter(nullable(String.class))).thenThrow(NoSuchElementException.class);
        mvc.perform(get(COLLECTION_ALL).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isInternalServerError());

    }

    @Test @DisplayName("Test the collection/all controller ssl error") public void getControllerSSLException () throws Exception {

        when(service.collectionFilter(nullable(String.class))).thenThrow(SSLException.class);

        mvc.perform(get(COLLECTION_ALL).param(FILTER, ID_FILTER).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

    }

}
