package com.microservice.pruebatecnica.controller;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.MOCK;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class) @AutoConfigureMockMvc @SpringBootTest(webEnvironment = MOCK) @ActiveProfiles("test") class CollectionFilterControllerTest {

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

    @Test @DisplayName("Test the collection/all controller correct") void collectionsFiltered () throws Exception {
        mvc.perform(get(COLLECTION_ALL).param(FILTER, ID_FILTER).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}