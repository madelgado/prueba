package com.microservice.pruebatecnica.util;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
@ActiveProfiles("test") class HTTPLinkHeaderUtilsTest {

    private static final String LINK = "Link: <https://api.unsplash.com/collections?page=8>; rel=\"last\", <https://api.unsplash.com/collections?page=2>; rel=\"next\"";

    @Test @DisplayName("Test link utils correct urls") void extractURIByRel () {
        String next = HTTPLinkHeaderUtils.extractURIByRel(LINK, "next");
        String last = HTTPLinkHeaderUtils.extractURIByRel(LINK, "last");
        assertTrue(next.contains("page=2"));
        assertTrue(last.contains("page=8"));
    }

    @Test @DisplayName("Test link header extractor empty values") void extractURIByRelNotValues () {
        String other = HTTPLinkHeaderUtils.extractURIByRel(LINK, "other");
        String noUrl = HTTPLinkHeaderUtils.extractURIByRel(null, "last");
        assertNull(other);
        assertNull(noUrl);
    }

}