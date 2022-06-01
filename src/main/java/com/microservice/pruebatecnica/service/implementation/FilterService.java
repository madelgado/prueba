package com.microservice.pruebatecnica.service.implementation;

import com.microservice.pruebatecnica.model.constants.CollectionConstants;
import com.microservice.pruebatecnica.model.dtos.CollectionData;
import com.microservice.pruebatecnica.service.IFilterService;
import com.microservice.pruebatecnica.util.RestApiCallUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.net.ssl.SSLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The type Filter service.
 */
@Service public class FilterService implements IFilterService {

    @Autowired private RestApiCallUtils restApiCallUtils;

    @Override public List<CollectionData> collectionFilter (String filters) throws SSLException {
        Flux<CollectionData> collectionDataFlux = restApiCallUtils.unplashAPICall();
        return filteringData(collectionDataFlux, mapFilters(filters));
    }

    private List<CollectionData> filteringData (Flux<CollectionData> collectionDataFlux, Map<String, String> mapFilters) {
        collectionDataFlux = collectionDataFlux.filterWhen(d -> check(d.getTitle(), mapFilters.getOrDefault(CollectionConstants.TITLE, null)));
        collectionDataFlux = collectionDataFlux.filterWhen(d -> check(d.getId(), mapFilters.getOrDefault(CollectionConstants.ID, null)));
        collectionDataFlux = collectionDataFlux.filterWhen(
                d -> check(d.getDescription(), mapFilters.getOrDefault(CollectionConstants.DESCRIPTION, null)));
        collectionDataFlux = collectionDataFlux.filterWhen(
                d -> check(d.getPhotoId(), mapFilters.getOrDefault("cover_photo.id", null)));
        return collectionDataFlux.collectList().block();
    }

    private Map<String, String> mapFilters (String filters) {
        return Stream.ofNullable(filters.split(","))
                .flatMap(Arrays::stream).map(s -> s.split("=")).collect(Collectors.filtering(a->a.length>1,Collectors.toMap(s -> s[0], s -> s[1])));
    }

    private Mono<Boolean> check (String s, String request) {
        return Mono.fromCallable(() -> (request == null) || (s != null && s.contains(request)));
    }
}
