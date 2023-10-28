package br.com.vbruno;

import br.com.vbruno.dao.IProdutoDAO;
import br.com.vbruno.domain.Cliente;
import br.com.vbruno.domain.Produto;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.MaisDeUmRegistroException;
import br.com.vbruno.exceptions.TableException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;
import br.com.vbruno.mock.ProdutoDAOMock;
import br.com.vbruno.services.IProdutoService;
import br.com.vbruno.services.ProdutoService;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Random;

public class ProdutoServiceTest {
    private IProdutoService produtoService;
    private IProdutoDAO dao;

    public ProdutoServiceTest() {
        this.dao = new ProdutoDAOMock();
        this.produtoService = new ProdutoService(dao);
    }

    @Test
    public void cadastrarProduto() throws DAOException, TipoChaveNaoEncontradaException {
        Produto produto = criarProduto("P1");
        Produto produtoCadastrado = produtoService.cadastrar(produto);
        Assert.assertNotNull(produtoCadastrado);
    }

    @Test
    public void consultarProduto() throws DAOException, MaisDeUmRegistroException, TableException {
        Produto produtoConsultado = produtoService.consultar(10l);
        Assert.assertNotNull(produtoConsultado);
    }

    @Test
    public void excluirProduto() throws DAOException {
        Produto produto = criarProduto("P1");
        produtoService.excluir(produto);
    }

    @Test
    public void alterarProduto() throws TipoChaveNaoEncontradaException, DAOException {
        Produto produto = criarProduto("P1");
        produto.setNome("Produto Alterado");
        produtoService.alterar(produto);

        Assert.assertEquals("Produto Alterado", produto.getNome());
    }

    @Test
    public void buscarTodosProdutos() throws DAOException {
        Collection<Produto> listaProdutos = produtoService.buscarTodos();
        Assert.assertNotNull(listaProdutos);
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
