package injection.configuration;

import java.util.Date;

public class TypeCast {

    public TypeCast() {
    }

    public Object castToType(Object obj){
        return castToInt(obj);
    }

    private Object castToInt(Object obj){
        try{
            return Integer.parseInt(obj.toString());
        }catch (NumberFormatException e){
            return castToLong(obj);
        }
    }

    private Object castToLong(Object obj){
        try{
            return Long.parseLong(obj.toString());
        }catch (NumberFormatException e){
            return castToFloat(obj);
        }
    }

    private Object castToFloat(Object obj){
        try{
            return Float.parseFloat(obj.toString());
        }catch (NumberFormatException e){
            return castToDouble(obj);
        }
    }

    private Object castToDouble(Object obj){
        try{
            return Double.parseDouble(obj.toString());
        }catch (NumberFormatException e){
            return castToBoolean(obj);
        }
    }

    private Object castToBoolean(Object obj){
        try{
            if(obj.toString().equals("true") || obj.toString().equals("false") || obj.toString().equals("0")
            || obj.toString().equals("1")){
                return Boolean.parseBoolean(obj.toString());
            }else{
                return castToDate(obj);
            }
        }catch (Exception e){
            return castToDate(obj);
        }
    }

    private Object castToDate(Object obj){
        try{
            return new Date(obj.toString());
        }catch (Exception e){
            return castToString(obj);
        }
    }

    private Object castToString(Object obj){
        return obj.toString();
    }
}
