package com.microservice.pruebatecnica.service;

import com.microservice.pruebatecnica.model.dtos.CollectionData;

import javax.net.ssl.SSLException;
import java.util.List;

/**
 * ICurrenciesService
 * <p>
 * Interface of Currency Service
 */
public interface IFilterService {

    /**
     * Collection filter list.
     *
     * @param filters the filters
     * @return the list
     * @throws SSLException the ssl exception
     */
    List<CollectionData> collectionFilter (String filters) throws SSLException;

}
