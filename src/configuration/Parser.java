package configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {

    private String controller;
    private String method;
    private Map<String, String> params;

    public Parser(String url) {
        evaluateUrl(url);
    }

    /**
     * URL example
     * controller ok
     * controller?param=value ok
     * controller/method?param=value
     * controller/method?param1=value&param2=value
     * @param url
     */
    private void evaluateUrl(String url){
        String[] urlTokenized = url.split("/"), queryParameter;
        if(urlTokenized.length < 2){
            queryParameter = urlTokenized[0].split("\\?");
            this.controller = queryParameter[0];
            this.method = null;
        }else{
            this.controller = urlTokenized[0];
            queryParameter = urlTokenized[1].split("\\?");
            this.method = queryParameter[0];
        }
        createParams(queryParameter);
    }

    private void createParams(String[] queryParameter){
        if(queryParameter.length > 1){
            String[] paramsTokenized = queryParameter[1].split("&");
            this.params = new HashMap<>();
            for (String s : paramsTokenized) {
                String[] paramsLine = s.split("=");
                this.params.put(paramsLine[0], paramsLine[1]);
            }
        }else{
            this.params = null;
        }
    }

    private String CaptalizeControllerName(String name){
        String firstLetter = name.substring(0, 1);
        name = name.replaceFirst(firstLetter, firstLetter.toUpperCase());
        return name;
    }

    private Object instantiate(){
        try{
            return Class.forName("clazz." + CaptalizeControllerName(controller)).getConstructor().newInstance();
        } catch(ClassNotFoundException
                | NoSuchMethodException
                |IllegalAccessException
                |InvocationTargetException
                |InstantiationException e){
            System.err.println("Ocorreu um erro: " + e.getMessage());
            return null;
        }
    }

    public Object execute(){
        Object obj = instantiate();
        MethodMatcher metMatch = new MethodMatcher(obj, method, params);
        try {
            Method method = metMatch.macth();
            if(this.params == null){
                return method.invoke(obj);
            }else{
                TypeCast caster = new TypeCast();
                List<Object> objs = new ArrayList<>();
                for(String s : params.values()){
                    caster.setObj(s);
                    objs.add(caster.castToType());
                }
                return method.invoke(obj, objs.toArray());
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
}
