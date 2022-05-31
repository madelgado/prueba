package com.microservice.pruebatecnica.config;

import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import javax.net.ssl.SSLException;

/**
 * The type Ssl config.
 */
public class SSLConfig {

    /**
     * Create web client web client.
     *
     * @param url     the url
     * @param enabled the enabled
     * @return the web client
     * @throws SSLException the ssl exception
     */
    public static WebClient createWebClient (String url, boolean enabled) throws SSLException {
        ConnectionProvider connectionProvider = ConnectionProvider.builder("aod-http-client").build();
        if (!enabled) {
            SslContext sslContext = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
            HttpClient httpConnector = HttpClient.create(connectionProvider).secure(t -> t.sslContext(sslContext));
            return WebClient.builder().baseUrl(url).clientConnector(new ReactorClientHttpConnector(httpConnector)).build();
        } else {
            return WebClient.builder().baseUrl(url).build();
        }
    }
}
