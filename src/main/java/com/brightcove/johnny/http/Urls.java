package com.brightcove.johnny.http;

import java.net.MalformedURLException;


/**
 * Main entrance point for http(s) URL parsing and manipulation.
 */
public class Urls {

    public static final UrlParser STANDARD_URL_PARSER = new JNUriParser();
    public static final UrlEncoder STANDARD_URL_ENCODER = new UrlEncoder();
    public static final QueryParser STANDARD_QUERY_PARSER = new NullIsEmptyQueryParser();
    public static final QueryEncoder STANDARD_QUERY_ENCODER = new BasicQueryEncoder();

    private Urls() { }

    /**
     * Parse a URL string to the default piecewise HTTP URL representation
     * and using the default parser.
     */
    public static Url parse(String url) throws MalformedURLException {
        return ImmutableUrl.from(url, STANDARD_URL_PARSER);
    }

}
