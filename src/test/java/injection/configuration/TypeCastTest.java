package injection.configuration;

import injection.exception.CastingException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class TypeCastTest {

    private class TestClass{

    }

    TypeCast tc = new TypeCast();

    String varString = "a";
    Integer varInt = 1;
    TestClass varClass = new TestClass();


    @Test
    public void verifica_se_tipo_passado_eh_string(){
        String result = (String) tc.castToType(varString);
        Assert.assertEquals("a", result);
    }

    @Test
    public void verifica_se_tipo_passado_eh_inteiro(){
        Integer result = (Integer) tc.castToType(varInt);
        Assert.assertEquals( Integer.valueOf(1), result);
    }

    @Test
    public void verifica_erro_ao_passar_objeto_complexo(){
        Assertions.assertThrows(CastingException.class, () -> { tc.castToType(varClass); });
    }

}
