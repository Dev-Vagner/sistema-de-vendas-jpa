package br.com.vbruno;

import br.com.vbruno.dao.IClienteDAO;
import br.com.vbruno.dao.IProdutoDAO;
import br.com.vbruno.dao.ProdutoDAO;
import br.com.vbruno.domain.Cliente;
import br.com.vbruno.domain.Produto;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.MaisDeUmRegistroException;
import br.com.vbruno.exceptions.TableException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class ProdutoDAOTest {
    private IProdutoDAO produtoDAO;

    public ProdutoDAOTest() {
        this.produtoDAO = new ProdutoDAO();
    }

    @Test
    public void cadastrarProduto() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Produto produto = criarProduto("P1");
        Produto produtoCadastrado = produtoDAO.cadastrar(produto);
        Assert.assertNotNull(produtoCadastrado);

        Produto produtoConsultado = produtoDAO.consultar(produtoCadastrado.getId());
        Assert.assertEquals(produtoCadastrado.getId(), produtoConsultado.getId());

        produtoDAO.excluir(produtoCadastrado);
        Produto produtoConsultado2 = produtoDAO.consultar(produtoCadastrado.getId());
        Assert.assertNull(produtoConsultado2);
    }

    @Test
    public void consultarProduto() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Produto produto = criarProduto("P1");
        Produto produtoCadastrado = produtoDAO.cadastrar(produto);
        Assert.assertNotNull(produtoCadastrado);

        Produto produtoConsultado = produtoDAO.consultar(produtoCadastrado.getId());
        Assert.assertEquals(produtoCadastrado.getId(), produtoConsultado.getId());

        produtoDAO.excluir(produtoCadastrado);
        Produto produtoConsultado2 = produtoDAO.consultar(produtoCadastrado.getId());
        Assert.assertNull(produtoConsultado2);
    }

    @Test
    public void excluirProduto() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Produto produto = criarProduto("P1");
        Produto produtoCadastrado = produtoDAO.cadastrar(produto);
        Assert.assertNotNull(produtoCadastrado);

        Produto produtoConsultado = produtoDAO.consultar(produtoCadastrado.getId());
        Assert.assertEquals(produtoCadastrado.getId(), produtoConsultado.getId());

        produtoDAO.excluir(produtoCadastrado);
        Produto produtoConsultado2 = produtoDAO.consultar(produtoCadastrado.getId());
        Assert.assertNull(produtoConsultado2);
    }

    @Test
    public void alterarProduto() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Produto produto = criarProduto("P1");
        Produto produtoCadastrado = produtoDAO.cadastrar(produto);
        Assert.assertNotNull(produtoCadastrado);

        Produto produtoConsultado = produtoDAO.consultar(produtoCadastrado.getId());
        Assert.assertEquals(produtoCadastrado.getId(), produtoConsultado.getId());

        produtoConsultado.setNome("Nome Alterado");
        Produto produtoAlterado = produtoDAO.alterar(produtoConsultado);
        Assert.assertNotEquals(produtoCadastrado.getNome(), produtoAlterado.getNome());
        Assert.assertEquals("Nome Alterado", produtoAlterado.getNome());

        produtoDAO.excluir(produtoAlterado);
        Produto produtoConsultado2 = produtoDAO.consultar(produtoAlterado.getId());
        Assert.assertNull(produtoConsultado2);
    }

    @Test
    public void buscarTodosProdutos() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Produto produto1 = criarProduto("P1");
        Produto produtoCadastrado1 = produtoDAO.cadastrar(produto1);
        Assert.assertNotNull(produtoCadastrado1);

        Produto produto2 = criarProduto("P2");
        Produto produtoCadastrado2 = produtoDAO.cadastrar(produto2);
        Assert.assertNotNull(produtoCadastrado2);

        Collection<Produto> listaProdutos = produtoDAO.buscarTodos();
        assertTrue(listaProdutos != null);
        assertTrue(listaProdutos.size() >= 2);

        produtoDAO.excluir(produto1);
        Produto produtoConsultado1 = produtoDAO.consultar(produto1.getId());
        Assert.assertNull(produtoConsultado1);

        produtoDAO.excluir(produto2);
        Produto produtoConsultado2 = produtoDAO.consultar(produto2.getId());
        Assert.assertNull(produtoConsultado2);
    }

    private Produto criarProduto(String codigo) {
        Produto produto = new Produto();

        produto.setCodigo(codigo);
        produto.setNome("Produto Teste");
        produto.setDescricao("Descrição do produto teste");
        produto.setValor(BigDecimal.valueOf(20.5));

        return produto;
    }
}
