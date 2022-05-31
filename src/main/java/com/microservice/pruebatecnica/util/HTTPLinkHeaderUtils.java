package com.microservice.pruebatecnica.util;

/**
 * The type Http link header utils.
 */
public final class HTTPLinkHeaderUtils {

    private HTTPLinkHeaderUtils () {
        throw new AssertionError();
    }

    //

    /**
     * ex. <br>
     * https://api.github.com/users/steveklabnik/gists?page=2>; rel="next", <https://api.github.com/users/steveklabnik/gists?page=3>; rel="last"
     *
     * @param linkHeader the link header
     * @param rel        the rel
     * @return the string
     */
    public static String extractURIByRel (final String linkHeader, final String rel) {
        if (linkHeader == null) {
            return null;
        }

        String uriWithSpecifiedRel = null;
        final String[] links = linkHeader.split(", ");
        String linkRelation = null;
        for (final String link : links) {
            final int positionOfSeparator = link.indexOf(';');
            linkRelation = link.substring(positionOfSeparator + 1).trim();
            if (extractTypeOfRelation(linkRelation).equals(rel)) {
                uriWithSpecifiedRel = link.substring(1, positionOfSeparator - 1);
                break;
            }
        }

        return uriWithSpecifiedRel;
    }

    private static Object extractTypeOfRelation (final String linkRelation) {
        final int positionOfEquals = linkRelation.indexOf('=');
        return linkRelation.substring(positionOfEquals + 2, linkRelation.length() - 1).trim();
    }

}