package injection.configuration;

public class ParserTest {

    private final String URL_COMPLETA = "aluno/list?matricula=1234";
    private final String URL_SEM_METODO_NEM_PARAMETROS = "aluno";
    private final String URL_SEM_METODO_COM_PARAMETROS = "aluno?matricula=1234";
//
//    @Test
//    public void verifica_se_atribuiu_corretamente_nome_controller(){
//        Parser parser = new Parser(URL_COMPLETA);
//        Assert.assertEquals(parser.getController(), "aluno");
//    }
//
//    @Test
//    public void verifica_se_atribuiu_corretamente_nome_metodo_quando_existe(){
//        Parser parser = new Parser(URL_COMPLETA);
//        Assert.assertEquals(parser.getMethod(), "list");
//    }
//
//    @Test
//    public void verifica_se_atribuiu_corretamente_nome_metodo_quando_nao_existe(){
//        Parser parser = new Parser(URL_SEM_METODO_COM_PARAMETROS);
//        Assert.assertEquals(parser.getMethod(), null);
//    }
//
//    @Test
//    public void verifica_se_atribuiu_corretamente_parametros(){
//        Parser parser = new Parser(URL_SEM_METODO_COM_PARAMETROS);
//        Map<String, String> map = new HashMap<>();
//        map.put("matricula", "1234");
//        Assert.assertEquals(parser.getParams(), map);
//    }
//
//    @Test
//    public void verifica_se_atribuiu_corretamente_parametros_quando_nao_existir(){
//        Parser parser = new Parser(URL_SEM_METODO_NEM_PARAMETROS);
//        Assert.assertEquals(parser.getParams(), null);
//    }


}
