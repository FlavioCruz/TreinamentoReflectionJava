package configuration;

import java.util.Date;

public class TypeCast {

    private Object obj;

    public TypeCast() {
    }

    public TypeCast(Object obj) {
        this.obj = obj;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public Object castToType(){
        return castToInt();
    }

    private Object castToInt(){
        try{
            return Integer.parseInt(this.obj.toString());
        }catch (NumberFormatException e){
            return castToLong();
        }
    }

    private Object castToLong(){
        try{
            return Long.getLong(this.obj.toString());
        }catch (NumberFormatException e){
            return castToBoolean();
        }
    }

    private Object castToBoolean(){
        try{
            return Boolean.parseBoolean(obj.toString());
        }catch (Exception e){
            return castToDate();
        }
    }

    private Object castToDate(){
        try{
            return new Date(obj.toString());
        }catch (Exception e){
            return castToString();
        }
    }

    private Object castToString(){
        return obj.toString();
    }

}
