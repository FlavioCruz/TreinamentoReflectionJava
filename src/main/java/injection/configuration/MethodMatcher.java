package injection.configuration;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MethodMatcher {

    private Object clazz;
    private String invokedMethod;
    private Map<String, String> params;

    MethodMatcher(Object clazz, String invokedMethod, Map<String, String> params) {
        this.clazz = clazz;
        this.invokedMethod = invokedMethod;
        this.params = params == null ? new HashMap<>() : params;
    }

    /**
     * find the right method to be invoked, by its params
     * @return {@link Method}
     * @throws {@link NoSuchMethodException}
     */
    Method macth() throws NoSuchMethodException {
        Method[] methods = clazz.getClass().getDeclaredMethods();
        for (Method method : methods) {

            if(checkParamsType(method, params.values().toArray())){
                if (invokedMethod == null) {
                    if (method.getParameterCount() == params.size()) {
                        return method;
                    }
                } else if (method.getParameterCount() == params.size()) {
                    return method;
                }
            }
        }
        throw new NoSuchMethodException();
    }

    /**
     * try to cast the given parameter to its real type and evaluates if this parameter fits the method description and
     * order.
     * @param method {@link Method}
     * @param params {@link Object[]}
     * @return {@link Boolean}
     */
    private boolean checkParamsType(Method method, Object[] params){
        if(params.length != 0 && method.getParameters().length != 0){
            TypeCast caster = new TypeCast();
            for(int i = 0; i < params.length; i++){
                if(!method.getParameters()[i].getType().isInstance(caster.castToType(params[i])) ){
                    return false;
                }
            }
        }
        return true;
    }

//    private boolean checkParamsName(Method method, Object[] params){
//        List<String> names = getParameterNames(method);
//        if(params.length != 0 && names.size() != 0){
//            for(int i = 0; i < params.length; i++){
//                if(!names.get(i).equals(params[i])){
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    private List<String> getParameterNames(Method method) {
//        Parameter[] parameters = method.getParameters();
//        List<String> parameterNames = new ArrayList<>();
//
//        for (Parameter parameter : parameters) {
//            if(!parameter.isNamePresent()) {
//                throw new IllegalArgumentException("Parameter names are not present!");
//            }
//
//            String parameterName = parameter.getName();
//            parameterNames.add(parameterName);
//        }
//
//        return parameterNames;
//    }
}