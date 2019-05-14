package injection.configuration;

import main.exception.ExecutionException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Executioner {

    private Object obj;
    private String methodName;
    private Map<String, String> params;

    public Executioner(Object obj, String methodName, Map<String, String> params) {
        this.obj = obj;
        this.methodName = methodName;
        this.params = params;
    }

    /**
     * Invokes the given method of the given class with the given parameters
     * @return {@link Object}
     */
    public Object execute(){
        MethodMatcher metMatch = new MethodMatcher(obj, methodName, params);
        try {
            Method method = metMatch.macth();
            if(params == null){
                return method.invoke(obj);
            }
            TypeCast caster = new TypeCast();
            List<Object> objs = new ArrayList<>();
            params.values().stream().forEach( x -> objs.add(caster.castToType(x)));
            return method.invoke(obj, objs.toArray());
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ExecutionException(e);
        }
    }
}