package configuration;

import java.lang.reflect.Method;
import java.util.Map;

public class MethodMatcher {

    private Object clazz;
    private String invokedMethod;
    private Map<String, String> params;

    public MethodMatcher(Object clazz, String invokedMethod, Map<String, String> params) {
        this.clazz = clazz;
        this.invokedMethod = invokedMethod;
        this.params = params;
    }

    public Method macth() throws NoSuchMethodException {
        Method[] methods = clazz.getClass().getDeclaredMethods();
        int paramsSize;
        if(this.params == null){
            paramsSize = 0;
        }
        else{
            paramsSize = this.params.size();
        }
        for (int i = 0; i < methods.length; i++){

            if(invokedMethod == null){
                if(methods[i].getParameterCount() == paramsSize){
                    return methods[i];
                }
            } else if(methods[i].getParameterCount() == paramsSize && methods[i].getName().equals(this.invokedMethod)){
                return methods[i];
            }
        }
        throw new NoSuchMethodException();
    }
}
