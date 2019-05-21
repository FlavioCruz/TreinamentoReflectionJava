package injection.configuration;

import main.controller.AlunoController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MethodMatcherTest {

    @Test
    public void verifica_se_metodo_foi_encontrado() throws NoSuchMethodException{
        Map<String, String> map = new HashMap<>();
        map.put("matricula", "1234");
        MethodMatcher metmat = new MethodMatcher(new AlunoController(), "list", map);
        Method method = metmat.macth();
        Assertions.assertEquals("list", method.getName());
    }

    @Test
    public void verifica_erro_ao_nao_encontrar_metodo(){
        Map<String, String> map = new HashMap<>();
        map.put("matricula", "1234");
        MethodMatcher metmat = new MethodMatcher(new AlunoController(), "erro", map);
        Assertions.assertThrows(NoSuchMethodException.class, metmat::macth);
    }
}
