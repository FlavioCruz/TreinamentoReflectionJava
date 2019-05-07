package configuration;

import java.lang.reflect.Method;
import java.util.Map;

class MethodMatcher {

    private Object clazz;
    private String invokedMethod;
    private Map<String, String> params;

    MethodMatcher(Object clazz, String invokedMethod, Map<String, String> params) {
        this.clazz = clazz;
        this.invokedMethod = invokedMethod;
        this.params = params;
    }

    Method macth() throws NoSuchMethodException {
        Method[] methods = clazz.getClass().getDeclaredMethods();
        int paramsSize;
        if(this.params == null){
            paramsSize = 0;
        }
        else{
            paramsSize = this.params.size();
        }
        for (Method method : methods) {

            if(params != null && checkParamsType(method, params.values().toArray())){
                if (invokedMethod == null) {
                    if (method.getParameterCount() == paramsSize) {
                        return method;
                    }
                } else if (method.getParameterCount() == paramsSize && method.getName().equals(this.invokedMethod)) {
                    return method;
                }
            }
        }
        throw new NoSuchMethodException();
    }

    private boolean checkParamsType(Method method, Object[] params){
        if(params.length != 0 && method.getParameters().length != 0){
            TypeCast caster = new TypeCast();
            for(int i = 0; i < params.length; i++){
                caster.setObj(params[i]);
                if(!method.getParameters()[i].getType().isInstance(caster.castToType())){
                    return false;
                }
            }
        }
        return true;
    }
}