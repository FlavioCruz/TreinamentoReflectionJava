package injection.configuration;

import injection.exception.CastingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeCastImplTest {

    private class TestClass{

    }

    TypeCast tc = new TypeCastImpl();

    String varString = "a";
    Integer varInt = 1;
    TestClass varClass = new TestClass();


    @Test
    public void verifica_se_tipo_passado_eh_string(){
        String result = (String) tc.castToType(varString);
        Assertions.assertEquals("a", result);
    }

    @Test
    public void verifica_se_tipo_passado_eh_inteiro(){
        Integer result = (Integer) tc.castToType(varInt);
        Assertions.assertEquals( Integer.valueOf(1), result);
    }

    @Test
    public void verifica_erro_ao_passar_objeto_complexo(){
        Assertions.assertThrows(CastingException.class, () -> tc.castToType(varClass) );
    }
}
