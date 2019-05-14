package injection.configuration;

import injection.annotation.ApplicationContext;
import injection.annotation.Entity;
import injection.annotation.Inject;
import injection.annotation.Service;
import main.exception.ParsingException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class Parser {

    private String url;

    /**
     * URL example
     * controller ok
     * controller?param=value ok
     * controller/method?param=value
     * controller/method?param1=value&param2=value
     *
     * @param url
     */
    public Parser(String url) {
        this.url = url;
    }

    public Request evaluateUrl() {
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

    private String CaptalizeControllerName(String name) {
        String firstLetter = name.substring(0, 1);
        name = name.replaceFirst(firstLetter, firstLetter.toUpperCase());
        return name;
    }
}


