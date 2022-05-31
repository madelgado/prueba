package com.microservice.pruebatecnica.util;

import com.microservice.pruebatecnica.config.SSLConfig;
import com.microservice.pruebatecnica.model.dtos.CollectionData;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The type Rest api call utils.
 */
@Component @Log4j2 public class RestApiCallUtils {

    @Value("${unsplash.endPoint}") private String endpoint;

    @Value("${unsplash.client-id}") private String accessKey;

    /**
     * Unplash api call flux.
     *
     * @return the flux
     * @throws SSLException the ssl exception
     */
    public Flux<CollectionData> unplashAPICall () throws SSLException {
        return getData(endpoint);
    }

    private Mono<ResponseEntity<Flux<CollectionData>>> getPage (String url) throws SSLException {
        return SSLConfig.createWebClient(url, false).get().header(HttpHeaders.AUTHORIZATION, "Client-ID " + accessKey).retrieve()
                .toEntityFlux(CollectionData.class)
                .doOnError(throwable -> log.error("Failed for some reason", throwable));
    }

    private Flux<CollectionData> getData (String url) throws SSLException {
        return getPage(url).expand(response -> {
            String urlLink = HTTPLinkHeaderUtils.extractURIByRel(response.getHeaders().getFirst("Link"), "next");
            if (urlLink == null) {
                // stop
                return Mono.empty();
            } else {
                // fetch next page
                try {
                    log.info("New API call to URL: {}", urlLink);
                    return getPage(urlLink);
                } catch (SSLException e) {
                    log.error("SSLException", e.getMessage());
                    throw new RuntimeException(e);
                }
            }

        }).flatMap(response -> response.getBody());

    }

}
