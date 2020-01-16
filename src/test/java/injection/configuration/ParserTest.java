package injection.configuration;

import injection.exception.ParsingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ParserTest {

    @Test
    public void verifica_se_cria_corretamente_requisicao(){
        Parser parser = new Parser("aluno/list?matricula=1234");
        Map<String, String> map = new HashMap<>();
        map.put("matricula", "1234");
        Request req = new Request("Aluno", "list", map);
        Assertions.assertEquals(req, parser.evaluateUrl());
    }

    @Test
    public void verifica_falha_curl_mal_formada(){
        Parser parser = new Parser("??&&url_mal_formada??&&");
        Assertions.assertThrows(ParsingException.class, parser::evaluateUrl);
    }

    @Test
    public void verifica_se_falha_caso_nulo_seja_passado(){
        Parser parser = new Parser(null);
        Assertions.assertThrows(ParsingException.class, parser::evaluateUrl);
    }
}
