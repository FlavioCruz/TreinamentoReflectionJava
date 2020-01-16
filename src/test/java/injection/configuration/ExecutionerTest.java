package injection.configuration;

import injection.exception.ExecutionException;
import injection.exception.ParsingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExecutionerTest {

    @Test
    public void testExecute(){
        Executioner exec = new Executioner(new Parser("aluno/list?matricula=1234"));
        Assertions.assertEquals("list method with parameter: 1234", exec.execute());
    }

    @Test
    public void testExecuteExecutionException(){
        Executioner exec = new Executioner(new Parser("aluno/lista?matricula=1234"));
        Assertions.assertThrows(ExecutionException.class, exec::execute);
    }

    @Test
    public void verifica_se_retorna_erro_ao_nao_encontrar_classe(){
        Assertions.assertThrows(ParsingException.class, () -> new Executioner(new Parser("alunos/lista?matricula=1234")));
    }
}
