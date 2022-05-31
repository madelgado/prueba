package com.microservice.pruebatecnica.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.pruebatecnica.config.SSLConfig;
import com.microservice.pruebatecnica.model.dtos.CollectionData;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import javax.net.ssl.SSLException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class) @EnableConfigurationProperties @SpringBootTest @ActiveProfiles("test") class RestApiCallUtilsTest {

    private static final String JSON = "  {\n" + "    \"id\": 296,\n" + "    \"title\": \"I like a man with a beard.\",\n" + "    \"description\": \"Yeah even Santa...\",\n" + "    \"published_at\": \"2016-01-27T18:47:13-05:00\",\n" + "    \"last_collected_at\": \"2016-06-02T13:10:03-04:00\",\n" + "    \"updated_at\": \"2016-07-10T11:00:01-05:00\",\n" + "    \"total_photos\": 12,\n" + "    \"private\": false,\n" + "    \"share_key\": \"312d188df257b957f8b86d2ce20e4766\",\n" + "    \"cover_photo\": {\n" + "      \"id\": \"C-mxLOk6ANs\",\n" + "      \"width\": 5616,\n" + "      \"height\": 3744,\n" + "      \"color\": \"#E4C6A2\",\n" + "      \"blur_hash\": \"L57Uhwni00t7EeRkagj@s+kBxvoe\",\n" + "      \"likes\": 12,\n" + "      \"liked_by_user\": false,\n" + "      \"description\": \"A man drinking a coffee.\",\n" + "      \"user\": {\n" + "        \"id\": \"xlt1-UPW7FE\",\n" + "        \"username\": \"lionsdenpro\",\n" + "        \"name\": \"Greg Raines\",\n" + "        \"portfolio_url\": \"https://example.com/\",\n" + "        \"bio\": \"Just an everyday Greg\",\n" + "        \"location\": \"Montreal\",\n" + "        \"total_likes\": 5,\n" + "        \"total_photos\": 10,\n" + "        \"total_collections\": 13,\n" + "        \"profile_image\": {\n" + "          \"small\": \"https://images.unsplash.com/profile-1449546653256-0faea3006d34?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32\",\n" + "          \"medium\": \"https://images.unsplash.com/profile-1449546653256-0faea3006d34?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64\",\n" + "          \"large\": \"https://images.unsplash.com/profile-1449546653256-0faea3006d34?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128\"\n" + "        },\n" + "        \"links\": {\n" + "          \"self\": \"https://api.unsplash.com/users/lionsdenpro\",\n" + "          \"html\": \"https://unsplash.com/lionsdenpro\",\n" + "          \"photos\": \"https://api.unsplash.com/users/lionsdenpro/photos\",\n" + "          \"likes\": \"https://api.unsplash.com/users/lionsdenpro/likes\",\n" + "          \"portfolio\": \"https://api.unsplash.com/users/lionsdenpro/portfolio\"\n" + "        }\n" + "      },\n" + "      \"urls\": {\n" + "        \"raw\": \"https://images.unsplash.com/photo-1449614115178-cb924f730780\",\n" + "        \"full\": \"https://images.unsplash.com/photo-1449614115178-cb924f730780?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy\",\n" + "        \"regular\": \"https://images.unsplash.com/photo-1449614115178-cb924f730780?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=1080&fit=max\",\n" + "        \"small\": \"https://images.unsplash.com/photo-1449614115178-cb924f730780?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=400&fit=max\",\n" + "        \"thumb\": \"https://images.unsplash.com/photo-1449614115178-cb924f730780?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&w=200&fit=max\"\n" + "      },\n" + "      \"links\": {\n" + "        \"self\": \"https://api.unsplash.com/photos/C-mxLOk6ANs\",\n" + "        \"html\": \"https://unsplash.com/photos/C-mxLOk6ANs\",\n" + "        \"download\": \"https://unsplash.com/photos/C-mxLOk6ANs/download\"\n" + "      }\n" + "    },\n" + "    \"user\": {\n" + "      \"id\": \"IFcEhJqem0Q\",\n" + "      \"updated_at\": \"2016-07-10T11:00:01-05:00\",\n" + "      \"username\": \"fableandfolk\",\n" + "      \"name\": \"Annie Spratt\",\n" + "      \"portfolio_url\": \"http://mammasaurus.co.uk\",\n" + "      \"bio\": \"Follow me on Twitter &amp; Instagram @anniespratt\\r\\nEmail me at hello@fableandfolk.com\",\n" + "      \"location\": \"New Forest National Park, UK\",\n" + "      \"total_likes\": 0,\n" + "      \"total_photos\": 273,\n" + "      \"total_collections\": 36,\n" + "      \"profile_image\": {\n" + "        \"small\": \"https://images.unsplash.com/profile-1450003783594-db47c765cea3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=32&w=32\",\n" + "        \"medium\": \"https://images.unsplash.com/profile-1450003783594-db47c765cea3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=64&w=64\",\n" + "        \"large\": \"https://images.unsplash.com/profile-1450003783594-db47c765cea3?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&fit=crop&h=128&w=128\"\n" + "      },\n" + "      \"links\": {\n" + "        \"self\": \"https://api.unsplash.com/users/fableandfolk\",\n" + "        \"html\": \"https://unsplash.com/fableandfolk\",\n" + "        \"photos\": \"https://api.unsplash.com/users/fableandfolk/photos\",\n" + "        \"likes\": \"https://api.unsplash.com/users/fableandfolk/likes\",\n" + "        \"portfolio\": \"https://api.unsplash.com/users/fableandfolk/portfolio\"\n" + "      }\n" + "    },\n" + "    \"links\": {\n" + "      \"self\": \"https://api.unsplash.com/collections/296\",\n" + "      \"html\": \"https://unsplash.com/collections/296\",\n" + "      \"photos\": \"https://api.unsplash.com/collections/296/photos\",\n" + "      \"related\": \"https://api.unsplash.com/collections/296/related\"\n" + "    }\n" + "  }";
    private static MockWebServer server;
    private static ObjectMapper mapper;
    @Autowired private RestApiCallUtils restApiCallUtils;

    @BeforeAll static void setUp () throws IOException {

        server = new MockWebServer();
        server.start();
        mapper = new ObjectMapper();
    }

    @AfterAll static void tearDown () throws IOException {

        server.shutdown();
    }

    @Test @DisplayName("Test a mocked API call and its response") void testApiCall () throws SSLException {

        try (MockedStatic<SSLConfig> mockedStatic = Mockito.mockStatic(SSLConfig.class)) {

            String uri = "https://api.unsplash.com/collections?per_page=50";

            server.enqueue(new MockResponse().setBody(JSON).addHeader("Content-Type", "application/json"));

            String url = server.url(uri).toString();

            mockedStatic.when(() -> SSLConfig.createWebClient(Mockito.anyString(), Mockito.anyBoolean()))
                    .thenReturn(WebClient.create(url));

            Flux<CollectionData> serviceResponse = restApiCallUtils.unplashAPICall();

            assertNotNull(serviceResponse);
        }
    }
}