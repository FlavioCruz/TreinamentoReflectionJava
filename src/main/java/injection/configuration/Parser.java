package injection.configuration;

import injection.annotation.ApplicationContext;
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

    private Class<?> clazz;
    private String controller;
    private String method;
    private Map<String, String> params;

    public Parser(String url, Class<?> clazz) {
        this.clazz = clazz;
        evaluateUrl(url);
    }

    public String getController() {
        return controller;
    }

    public String getMethod() {
        return method;
    }

    public Map<String, String> getParams() {
        return params;
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
            Arrays.stream(paramsTokenized).forEach( x -> {
                String[] paramsLine = x.split("=");
                this.params.put(paramsLine[0], paramsLine[1]);
            });
        }else{
            this.params = null;
        }
    }

    private String CaptalizeControllerName(String name){
        String firstLetter = name.substring(0, 1);
        name = name.replaceFirst(firstLetter, firstLetter.toUpperCase());
        return name;
    }

    public Object instantiateController(){
        return instantiate("Controller");
    }

    public Object instantiateService(){
        return instantiate("Service");
    }

    private Object instantiate(String sufix){
        try{
            Package[] packages = Package.getPackages();
            AtomicReference<ApplicationContext> annotation = null;
            Arrays.stream(packages).forEach( x -> {
                annotation.set(x.getAnnotation(ApplicationContext.class));
            });
            Object obj = Class.forName(annotation.get().value().getPackageName()+ "." + sufix.toLowerCase() + "."
                    + CaptalizeControllerName(controller) + sufix).getConstructor().newInstance();
            instantiateDependencies(obj);
            return obj;
        } catch(ClassNotFoundException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | InstantiationException e){
            throw new ParsingException(e);
        }
    }

    private List<Object> instantiateDependencies(Object obj){
        Field[] dependencies =  obj.getClass().getDeclaredFields();
        List<Object> objDependencies = new ArrayList<>();
        if(dependencies.length == 0){
            return objDependencies;
        }
        Arrays.stream(dependencies).forEach( x -> {
            if(x.getClass().isInterface()){
                List<Object> xDependencies = new ArrayList<>();
                Arrays.stream(x.getClass().getDeclaredFields()).forEach( f -> {
                    try{
                        Object fDependency = f.getClass().getConstructor().newInstance();
                        xDependencies.add(instantiateDependencies(fDependency));
                    } catch (InstantiationException
                            | IllegalAccessException
                            | InvocationTargetException
                            | NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                });
            }
        });
        return objDependencies;
    }
}
