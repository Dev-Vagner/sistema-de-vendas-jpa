package br.com.vbruno;

import br.com.vbruno.dao.*;
import br.com.vbruno.domain.Cliente;
import br.com.vbruno.domain.Produto;
import br.com.vbruno.domain.Venda;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.MaisDeUmRegistroException;
import br.com.vbruno.exceptions.TableException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Random;

public class VendaDAOTest {
    private IVendaDAO vendaDAO;
    private IVendaDAO vendaExclusaoDAO;
    private IClienteDAO clienteDAO;
    private IProdutoDAO produtoDAO;
    private Random rd;
    private Cliente cliente;
    private Produto produto;

    public VendaDAOTest() {
        this.vendaDAO = new VendaDAO();
        this.vendaExclusaoDAO = new VendaExclusaoDAO();
        this.clienteDAO = new ClienteDAO();
        this.produtoDAO = new ProdutoDAO();
        this.rd = new Random();
    }

    @Before
    public void init() throws DAOException, TipoChaveNaoEncontradaException {
        this.cliente = cadastrarCliente();
        this.produto = cadastrarProduto("P1", BigDecimal.TEN);
    }

    @After
    public void end() throws DAOException {
        excluirVendas();
        excluirProdutos();
        clienteDAO.excluir(this.cliente);
    }

    @Test
    public void cadastrarVenda() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Venda venda = criarVenda("V1");
        Venda vendaCadastrada = vendaDAO.cadastrar(venda);
        Assert.assertNotNull(vendaCadastrada);

        Assert.assertTrue(vendaCadastrada.getValorTotal().equals(BigDecimal.valueOf(20)));
        Assert.assertTrue(vendaCadastrada.getStatus().equals(Venda.Status.INICIADA));

        Venda vendaConsultada = vendaDAO.consultar(venda.getId());
        Assert.assertTrue(vendaConsultada.getId() != null);
        Assert.assertEquals(venda.getCodigo(), vendaConsultada.getCodigo());
    }

    @Test
    public void consultarVenda() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Venda venda = criarVenda("V2");
        Venda vendaCadastrada = vendaDAO.cadastrar(venda);
        Assert.assertNotNull(vendaCadastrada);

        Venda vendaConsultada = vendaDAO.consultar(venda.getId());
        Assert.assertNotNull(vendaConsultada);
        Assert.assertEquals(vendaCadastrada.getCodigo(), vendaConsultada.getCodigo());
    }

    @Test
    public void cancelarVenda() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Venda venda = criarVenda("V3");
        Venda vendaCadastrada = vendaDAO.cadastrar(venda);
        Assert.assertNotNull(vendaCadastrada);
        Assert.assertNotNull(vendaCadastrada.getId());
        Assert.assertEquals("V3", vendaCadastrada.getCodigo());

        vendaCadastrada.setStatus(Venda.Status.CANCELADA);
        vendaDAO.cancelarVenda(vendaCadastrada);

        Venda vendaConsultada = vendaDAO.consultar(venda.getId());
        Assert.assertEquals("V3", vendaConsultada.getCodigo());
        Assert.assertEquals(Venda.Status.CANCELADA, vendaConsultada.getStatus());
    }

    @Test
    public void adicionarMaisProdutosDoMesmo() throws DAOException, TipoChaveNaoEncontradaException {
        Venda venda = criarVenda("V4");
        Venda vendaCadastrada = vendaDAO.cadastrar(venda);
        Assert.assertNotNull(vendaCadastrada);
        Assert.assertNotNull(vendaCadastrada.getId());
        Assert.assertEquals("V4", vendaCadastrada.getCodigo());
        Assert.assertTrue(vendaCadastrada.getQuantidadeTotalProdutos().equals(2));
        Assert.assertTrue(vendaCadastrada.getValorTotal().equals(BigDecimal.valueOf(20)));
        Assert.assertTrue(vendaCadastrada.getProdutos().size() == 1);

        vendaCadastrada.adicionarProduto(this.produto, 2);
        Venda vendaAlterada = vendaDAO.alterar(vendaCadastrada);
        Assert.assertNotNull(vendaAlterada);
        Assert.assertEquals(vendaCadastrada.getId(), vendaAlterada.getId());
        Assert.assertEquals(vendaCadastrada.getCodigo(), vendaAlterada.getCodigo());
        Assert.assertTrue(vendaAlterada.getQuantidadeTotalProdutos().equals(4));
        Assert.assertTrue(vendaAlterada.getValorTotal().equals(BigDecimal.valueOf(40)));
        Assert.assertTrue(vendaAlterada.getProdutos().size() == 1);
    }

    @Test
    public void adicionarMaisProdutosDiferentes() throws DAOException, TipoChaveNaoEncontradaException {
        String codigoVenda = "A5";
        Venda venda = criarVenda(codigoVenda);
        Venda vendaCadastrada = vendaDAO.cadastrar(venda);
        Assert.assertNotNull(vendaCadastrada);
        Assert.assertNotNull(vendaCadastrada.getId());
        Assert.assertEquals(codigoVenda, vendaCadastrada.getCodigo());
        Assert.assertTrue(vendaCadastrada.getQuantidadeTotalProdutos().equals(2));
        Assert.assertTrue(vendaCadastrada.getValorTotal().equals(BigDecimal.valueOf(20)));
        Assert.assertTrue(vendaCadastrada.getProdutos().size() == 1);

        Produto novoProduto = cadastrarProduto("P2", BigDecimal.valueOf(20));
        Assert.assertNotNull(novoProduto);

        vendaCadastrada.adicionarProduto(novoProduto, 3);
        Venda vendaAlterada = vendaDAO.alterar(vendaCadastrada);
        Assert.assertNotNull(vendaAlterada);
        Assert.assertEquals(vendaCadastrada.getId(), vendaAlterada.getId());
        Assert.assertEquals(vendaCadastrada.getCodigo(), vendaAlterada.getCodigo());
        Assert.assertTrue(vendaAlterada.getQuantidadeTotalProdutos().equals(5));
        Assert.assertTrue(vendaAlterada.getValorTotal().equals(BigDecimal.valueOf(80)));
        Assert.assertTrue(vendaAlterada.getProdutos().size() == 2);
    }

    @Test(expected = DAOException.class)
    public void salvarVendaMesmoCodigoExistente() throws DAOException, TipoChaveNaoEncontradaException {
        String codigoVenda = "A6";
        Venda venda1 = criarVenda(codigoVenda);
        Venda vendaCadastrada1 = vendaDAO.cadastrar(venda1);
        Assert.assertNotNull(vendaCadastrada1);
        Assert.assertNotNull(vendaCadastrada1.getId());
        Assert.assertEquals(codigoVenda, vendaCadastrada1.getCodigo());

        Venda venda2 = criarVenda(codigoVenda);
        Venda vendaCadastrada2 = vendaDAO.cadastrar(venda2);
        Assert.assertNull(vendaCadastrada2);
    }

    @Test
    public void removerProduto() throws DAOException, TipoChaveNaoEncontradaException {
        String codigoVenda = "A7";
        Venda venda = criarVenda(codigoVenda);
        Venda vendaCadastrada = vendaDAO.cadastrar(venda);
        Assert.assertNotNull(vendaCadastrada.getId());
        Assert.assertEquals(codigoVenda, vendaCadastrada.getCodigo());
        Assert.assertNotNull(vendaCadastrada.getProdutos());
        Assert.assertTrue(vendaCadastrada.getProdutos().size() == 1);
        Assert.assertTrue(vendaCadastrada.getValorTotal().equals(BigDecimal.valueOf(20)));
        Assert.assertTrue(vendaCadastrada.getStatus().equals(Venda.Status.INICIADA));

        vendaCadastrada.removerProduto(this.produto, 1);
        Venda vendaAlterada = vendaDAO.alterar(vendaCadastrada);
        Assert.assertNotNull(vendaAlterada.getId());
        Assert.assertEquals(codigoVenda, vendaAlterada.getCodigo());
        Assert.assertNotNull(vendaAlterada.getProdutos());
        Assert.assertTrue(vendaAlterada.getProdutos().size() == 1);
        Assert.assertTrue(vendaAlterada.getValorTotal().equals(BigDecimal.valueOf(10)));
        Assert.assertTrue(vendaAlterada.getStatus().equals(Venda.Status.INICIADA));
    }

    @Test
    public void removerTodosProdutos() throws DAOException, TipoChaveNaoEncontradaException {
        String codigoVenda = "A8";
        Venda venda = criarVenda(codigoVenda);
        Venda vendaCadastrada = vendaDAO.cadastrar(venda);
        Assert.assertNotNull(vendaCadastrada.getId());
        Assert.assertEquals(codigoVenda, vendaCadastrada.getCodigo());
        Assert.assertNotNull(vendaCadastrada.getProdutos());
        Assert.assertTrue(vendaCadastrada.getProdutos().size() == 1);
        Assert.assertTrue(vendaCadastrada.getValorTotal().equals(BigDecimal.valueOf(20)));
        Assert.assertTrue(vendaCadastrada.getStatus().equals(Venda.Status.INICIADA));

        Produto produto2 = cadastrarProduto("P3", BigDecimal.valueOf(25));
        vendaCadastrada.adicionarProduto(produto2, 2);
        Venda vendaAlterada = vendaDAO.alterar(vendaCadastrada);
        Assert.assertNotNull(vendaAlterada.getId());
        Assert.assertEquals(codigoVenda, vendaAlterada.getCodigo());
        Assert.assertNotNull(vendaAlterada.getProdutos());
        Assert.assertTrue(vendaAlterada.getProdutos().size() == 2);
        Assert.assertTrue(vendaAlterada.getValorTotal().equals(BigDecimal.valueOf(70)));
        Assert.assertTrue(vendaAlterada.getStatus().equals(Venda.Status.INICIADA));

        vendaAlterada.removerTodosProdutos();
        Venda vendaSemProdutos = vendaDAO.alterar(vendaAlterada);
        Assert.assertNotNull(vendaSemProdutos.getId());
        Assert.assertEquals(codigoVenda, vendaSemProdutos.getCodigo());
        Assert.assertTrue(vendaSemProdutos.getProdutos().size() == 0);
        Assert.assertTrue(vendaAlterada.getValorTotal().equals(BigDecimal.valueOf(0)));
        Assert.assertTrue(vendaAlterada.getStatus().equals(Venda.Status.INICIADA));
    }

    @Test
    public void finalizarVenda() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        String codigoVenda = "A9";
        Venda venda = criarVenda(codigoVenda);
        Venda vendaCadastrada = vendaDAO.cadastrar(venda);
        Assert.assertNotNull(vendaCadastrada.getId());
        Assert.assertEquals(codigoVenda, vendaCadastrada.getCodigo());
        Assert.assertEquals(Venda.Status.INICIADA, vendaCadastrada.getStatus());

        vendaCadastrada.setStatus(Venda.Status.CONCLUIDA);
        vendaDAO.finalizarVenda(vendaCadastrada);

        Venda vendaFinalizada = vendaDAO.consultar(vendaCadastrada.getId());
        Assert.assertNotNull(vendaFinalizada.getId());
        Assert.assertEquals(codigoVenda, vendaFinalizada.getCodigo());
        Assert.assertEquals(Venda.Status.CONCLUIDA, vendaFinalizada.getStatus());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void tentarAdicionarProdutosVendaFinalizada() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        String codigoVenda = "A10";
        Venda venda = criarVenda(codigoVenda);
        Venda vendaCadastrada = vendaDAO.cadastrar(venda);
        Assert.assertNotNull(vendaCadastrada.getId());
        Assert.assertEquals(codigoVenda, vendaCadastrada.getCodigo());
        Assert.assertEquals(Venda.Status.INICIADA, vendaCadastrada.getStatus());

        vendaCadastrada.setStatus(Venda.Status.CONCLUIDA);
        vendaDAO.finalizarVenda(vendaCadastrada);

        Venda vendaFinalizada = vendaDAO.consultar(vendaCadastrada.getId());
        Assert.assertNotNull(vendaFinalizada.getId());
        Assert.assertEquals(codigoVenda, vendaFinalizada.getCodigo());
        Assert.assertEquals(Venda.Status.CONCLUIDA, vendaFinalizada.getStatus());

        Produto novoProduto = cadastrarProduto("P4", BigDecimal.valueOf(50));
        vendaFinalizada.adicionarProduto(novoProduto, 2);
    }

    private Cliente cadastrarCliente() throws DAOException, TipoChaveNaoEncontradaException {
        Cliente cliente = new Cliente();

        cliente.setNome("Cliente Teste - Venda");
        cliente.setCpf(rd.nextLong());
        cliente.setTel(432543543l);
        cliente.setEnd("Rua dos bobos, nº0");
        cliente.setNumero(354);
        cliente.setCidade("Cidade Teste");
        cliente.setEstado("Estado Teste");

        clienteDAO.cadastrar(cliente);

        return cliente;
    }

    private Produto cadastrarProduto(String codigo, BigDecimal valor) throws DAOException, TipoChaveNaoEncontradaException {
        Produto produto = new Produto();

        produto.setCodigo(codigo);
        produto.setNome("Produto Teste - Venda");
        produto.setDescricao("Descrição do produto teste");
        produto.setValor(valor);

        produtoDAO.cadastrar(produto);

        return produto;
    }

    private Venda criarVenda(String codigo) {
        Venda venda = new Venda();

        venda.setCodigo(codigo);
        venda.setCliente(this.cliente);
        venda.setDataVenda(Instant.now());
        venda.setStatus(Venda.Status.INICIADA);
        venda.adicionarProduto(this.produto, 2);

        return venda;
    }

    private void excluirVendas() throws DAOException {
        Collection<Venda> list = this.vendaExclusaoDAO.buscarTodos();
        list.forEach(prod -> {
            try {
                this.vendaExclusaoDAO.excluir(prod);
            } catch (DAOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

    private void excluirProdutos() throws DAOException {
        Collection<Produto> list = this.produtoDAO.buscarTodos();
        list.forEach(prod -> {
            try {
                this.produtoDAO.excluir(prod);
            } catch (DAOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

}
