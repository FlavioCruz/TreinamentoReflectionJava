package injection.configuration;

import injection.annotation.Inject;
import injection.exception.ParsingException;

import java.lang.annotation.Documented;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

public class Request {

    private String controller;
    private String method;
    private Map<String, String> params;

    public Request(String captalizeControllerName, String method, Map<String, String> params) {
        this.controller = captalizeControllerName;
        this.method = method;
        this.params = params;
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

    public Object instantiateController(){
        return instantiate("Controller");
    }

    public Object instantiateService(){
        return instantiate("Service");
    }

    /**
     * Creates a new instance of the object, given the class sufix
     * @param sufix {@link String}
     * @return {@link Object}
     */
    private Object instantiate(String sufix){
        try{
            Object obj = Class.forName("main." + sufix.toLowerCase() + "."
                    + controller + sufix).getConstructor().newInstance();
            return instantiateDependencies(obj);
        } catch(ClassNotFoundException
                | NoSuchMethodException
                | IllegalAccessException
                | InvocationTargetException
                | InstantiationException e){
            throw new ParsingException(e);
        }
    }

    /**
     * Instantiates all dependencies of the given class and goes on recursively
     * @param obj {@link Object}
     * @return {@link Object}
     */
    private Object instantiateDependencies(Object obj){
        Field[] dependencies =  obj.getClass().getDeclaredFields();
        if(dependencies.length == 0 || obj.equals("")){
            return obj;
        }
        Arrays.stream(dependencies).forEach(x -> {
            if(!x.getType().isInterface() || isFieldAnnotated(x)){
                Object xDependency = null;
                try {
                    if(x.trySetAccessible()){
                        xDependency = x.getType().getConstructor().newInstance();
                        x.set(obj, instantiateDependencies(xDependency));
                    }

                }  catch (InstantiationException
                        | IllegalAccessException
                        | InvocationTargetException
                        | NoSuchMethodException e) {
                    throw new ParsingException(e);
                }
            }
        });
        return obj;
    }

    private boolean isFieldAnnotated(Field f){
        return f.getAnnotationsByType(Inject.class).length != 0;
    }
}
