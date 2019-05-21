package injection.configuration;

import injection.exception.ParsingException;
import main.controller.AlunoController;
import main.service.AlunoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestTest {

    public RequestTest(){

    }

    @Test
    public void testInstantiateController() {
        Map<String, String> map = new HashMap<>();
        map.put("matricula", "1234");
        Request req = new Request("Aluno", "list", map);
        Object result = req.instantiateController();
        assertTrue(result instanceof AlunoController);
    }

    @Test
    public void testInstantiateControllerParsingException() {

        Map<String, String> map = new HashMap<>();
        map.put("matricula", "1234");
        Request req = new Request("Errado", "list", map);
        Assertions.assertThrows(ParsingException.class, req::instantiateController);
    }

    @Test
    public void testInstantiateService() {

        Map<String, String> map = new HashMap<>();
        map.put("matricula", "1234");
        Request req = new Request("Aluno", "list", map);
        Object result = req.instantiateService();
        assertTrue(result instanceof AlunoService);
    }

    @Test
    public void testInstantiateServiceParsingException() {
        Map<String, String> map = new HashMap<>();
        map.put("matricula", "1234");
        Request req = new Request("Errado", "list", map);
        Assertions.assertThrows(ParsingException.class, req::instantiateService);
    }
}
