package pl.edu.uj.ii.smartdom.utils;

import java.util.regex.Pattern;

/**
 * Created by Mohru on 20.08.2017.
 */

public class StringPatterns {
    public static final Pattern IP_ADDRESS_WITH_PORT
            = Pattern.compile(
            "((25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9])\\.(25[0-5]|2[0-4]"
                    + "[0-9]|[0-1][0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1]"
                    + "[0-9]{2}|[1-9][0-9]|[1-9]|0)\\.(25[0-5]|2[0-4][0-9]|[0-1][0-9]{2}"
                    + "|[1-9][0-9]|[0-9]))" + "\\:\\d{1,5}");
}
