package br.com.vbruno;

import br.com.vbruno.dao.IClienteDAO;
import br.com.vbruno.domain.Cliente;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.MaisDeUmRegistroException;
import br.com.vbruno.exceptions.TableException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;
import br.com.vbruno.mock.ClienteDAOMock;
import br.com.vbruno.services.ClienteService;
import br.com.vbruno.services.IClienteService;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Random;

public class ClienteServiceTest {
    private IClienteService clienteService;
    private IClienteDAO dao;
    private Random rd;

    public ClienteServiceTest() {
        this.dao = new ClienteDAOMock();
        this.clienteService = new ClienteService(dao);
        this.rd = new Random();
    }

    @Test
    public void cadastrarCliente() throws DAOException, TipoChaveNaoEncontradaException {
        Cliente cliente = criarCliente();
        Cliente clienteCadastrado = clienteService.cadastrar(cliente);
        Assert.assertNotNull(clienteCadastrado);
    }

    @Test
    public void consultarCliente() throws DAOException, MaisDeUmRegistroException, TableException {
        Cliente clienteConsultado = clienteService.consultar(10l);
        Assert.assertNotNull(clienteConsultado);
    }

    @Test
    public void excluirCliente() throws DAOException {
        Cliente cliente = criarCliente();
        clienteService.excluir(cliente);
    }

    @Test
    public void alterarCliente() throws TipoChaveNaoEncontradaException, DAOException {
        Cliente cliente = criarCliente();
        cliente.setNome("Vagner Bruno");
        clienteService.alterar(cliente);

        Assert.assertEquals("Vagner Bruno", cliente.getNome());
    }

    @Test
    public void buscarTodosClientes() throws DAOException {
        Collection<Cliente> listaClientes = clienteService.buscarTodos();
        Assert.assertNotNull(listaClientes);
    }

    private Cliente criarCliente() {
        Cliente cliente = new Cliente();

        cliente.setNome("Cliente Teste");
        cliente.setCpf(rd.nextLong());
        cliente.setTel(432543543l);
        cliente.setEnd("Rua dos bobos, nº0");
        cliente.setNumero(354);
        cliente.setCidade("Cidade Teste");
        cliente.setEstado("Estado Teste");

        return cliente;
    }
}
