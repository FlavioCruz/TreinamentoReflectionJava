package injection.configuration;

import injection.annotation.ApplicationContext;
import injection.annotation.Inject;
import injection.exception.ParsingException;
import org.reflections.Reflections;

import java.lang.annotation.Documented;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Request {

    private String controller;
    private String method;
    private Map<String, String> params;

    public Request(String captalizeControllerName, String method, Map<String, String> params) {
        this.controller = captalizeControllerName;
        this.method = method;
        this.params = params;
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
                try {
                    if(x.trySetAccessible()){
                        x.set(obj, instantiateDependencies(x.getType().getConstructor().newInstance()));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Request request = (Request) o;

        if (controller != null ? !controller.equals(request.controller) : request.controller != null) return false;
        if (method != null ? !method.equals(request.method) : request.method != null) return false;
        return params != null ? params.equals(request.params) : request.params == null;

    }
}
