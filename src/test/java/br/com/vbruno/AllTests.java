package br.com.vbruno;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ClienteDAOTest.class, ProdutoDAOTest.class,
        ClienteServiceTest.class, ProdutoServiceTest.class, VendaDAOTest.class})
public class AllTests {

}

