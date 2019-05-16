package injection.configuration;

import injection.exception.ParsingException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Parser {

    private String url;

    /**
     * URL example
     * controller ok
     * controller?param=value ok
     * controller/method?param=value
     * controller/method?param1=value&param2=value
     * @param url {@link String}
     */
    public Parser(String url) {
        this.url = url;
    }

    /**
     * Evaluates the URL and return a new instance of Request with all fields fullfilled
     * @return {@link Request}
     */
    Request evaluateUrl() {
        String controller, method;
        String[] urlTokenized = url.split("/"), queryParameter;
        if (urlTokenized.length < 2) {
            queryParameter = urlTokenized[0].split("\\?");
            controller = queryParameter[0];
            method = null;
        } else {
            controller = urlTokenized[0];
            queryParameter = urlTokenized[1].split("\\?");
            method = queryParameter[0];
        }
        return new Request(CaptalizeControllerName(controller), method, createParams(queryParameter));
    }

    /**
     * Returns a dictionary with the name of the parameter as key and the value as value.
     * @param queryParameter {@link String}
     * @return {@link Map<String, String>}
     */
    private Map<String, String> createParams(String[] queryParameter) {
        Map<String, String> params;
        if (queryParameter.length > 1) {
            String[] paramsTokenized = queryParameter[1].split("&");
            params = new HashMap<>();
            Arrays.stream(paramsTokenized).forEach(x -> {
                String[] paramsLine = x.split("=");
                params.put(paramsLine[0], paramsLine[1]);
            });
        } else {
            params = null;
        }
        return params;
    }

    /**
     * @param name {@link String}
     * @return {@link String}
     */
    private String CaptalizeControllerName(String name) {
        String firstLetter = name.substring(0, 1);
        name = name.replaceFirst(firstLetter, firstLetter.toUpperCase());
        return name;
    }
}


