package org.timmc.johnny.parts;

import org.timmc.johnny.Ascii7Oracle;
import org.timmc.johnny.ByCharPercentEncoder;
import org.timmc.johnny.Constants;
import org.timmc.johnny.Paths;
import org.timmc.johnny.StringEncoder;

/**
 * Formatter for paths.
 */
public class TextPathFormatter {
    /** Default encoder for path segments. */
    public static final StringEncoder DEFAULT_SEGMENT_ENCODER =
            new ByCharPercentEncoder(new Ascii7Oracle(Constants.RFC3986_UNENCODED_PATH_SEGMENT));

    /**
     * Format a sequence of segments into a normalized raw path string.
     * @param segments Non-null sequence of non-null parsed path segments,
     *  probably from a {@link TextPath}.
     * @return Raw path string, either empty or starting with "/".
     */
    public String format(Iterable<String> segments) {
        segments = Paths.isNormalized(segments) ? segments : Paths.normalize(segments);
        StringBuilder ret = new StringBuilder();
        for (String seg : segments) {
            ret.append('/').append(DEFAULT_SEGMENT_ENCODER.encode(seg));
        }
        return ret.toString();
    }
}
