package br.com.vbruno.mock;

import br.com.vbruno.dao.IProdutoDAO;
import br.com.vbruno.domain.Produto;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.MaisDeUmRegistroException;
import br.com.vbruno.exceptions.TableException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

public class ProdutoDAOMock implements IProdutoDAO {
    @Override
    public Produto cadastrar(Produto entity) throws TipoChaveNaoEncontradaException, DAOException {
        return entity;
    }

    @Override
    public void excluir(Produto entity) throws DAOException {

    }

    @Override
    public Produto alterar(Produto entity) throws TipoChaveNaoEncontradaException, DAOException {
        return entity;
    }

    @Override
    public Produto consultar(Long id) throws MaisDeUmRegistroException, TableException, DAOException {
        Produto produtoMock = criarProduto(id, "P1");
        return produtoMock;
    }

    @Override
    public Collection<Produto> buscarTodos() throws DAOException {
        Collection<Produto> listaProdutos = new ArrayList<>();

        Produto produtoMock1 = criarProduto(1l, "P1");
        Produto produtoMock2 = criarProduto(2l, "P2");

        listaProdutos.add(produtoMock1);
        listaProdutos.add(produtoMock2);

        return listaProdutos;
    }

    private Produto criarProduto(Long id, String codigo) {
        Produto produto = new Produto();
        produto.setId(id);
        produto.setCodigo(codigo);
        produto.setNome("Produto Mock");
        produto.setDescricao("Descrição do produto mockado");
        produto.setValor(BigDecimal.valueOf(20.5));

        return produto;
    }
}
