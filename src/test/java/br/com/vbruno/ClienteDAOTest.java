package br.com.vbruno;

import br.com.vbruno.dao.ClienteDAO;
import br.com.vbruno.dao.IClienteDAO;
import br.com.vbruno.domain.Cliente;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.MaisDeUmRegistroException;
import br.com.vbruno.exceptions.TableException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.Random;

import static org.junit.Assert.assertTrue;

public class ClienteDAOTest {

    private IClienteDAO clienteDAO;
    private Random rd;

    public ClienteDAOTest() {
        this.clienteDAO = new ClienteDAO();
        this.rd = new Random();
    }

    @Test
    public void cadastrarCliente() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Cliente cliente = criarCliente();
        Cliente clienteCadastrado = clienteDAO.cadastrar(cliente);
        Assert.assertNotNull(clienteCadastrado);

        Cliente clienteConsultado = clienteDAO.consultar(clienteCadastrado.getId());
        Assert.assertEquals(clienteCadastrado.getId(), clienteConsultado.getId());

        clienteDAO.excluir(clienteCadastrado);
        Cliente clienteConsultado2 = clienteDAO.consultar(clienteCadastrado.getId());
        Assert.assertNull(clienteConsultado2);
    }

    @Test
    public void consultarCliente() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Cliente cliente = criarCliente();
        Cliente clienteCadastrado = clienteDAO.cadastrar(cliente);
        Assert.assertNotNull(clienteCadastrado);

        Cliente clienteConsultado = clienteDAO.consultar(clienteCadastrado.getId());
        Assert.assertEquals(clienteCadastrado.getId(), clienteConsultado.getId());

        clienteDAO.excluir(clienteCadastrado);
        Cliente clienteConsultado2 = clienteDAO.consultar(clienteCadastrado.getId());
        Assert.assertNull(clienteConsultado2);
    }

    @Test
    public void excluirCliente() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Cliente cliente = criarCliente();
        Cliente clienteCadastrado = clienteDAO.cadastrar(cliente);
        Assert.assertNotNull(clienteCadastrado);

        Cliente clienteConsultado = clienteDAO.consultar(clienteCadastrado.getId());
        Assert.assertEquals(clienteCadastrado.getId(), clienteConsultado.getId());

        clienteDAO.excluir(clienteCadastrado);
        Cliente clienteConsultado2 = clienteDAO.consultar(clienteCadastrado.getId());
        Assert.assertNull(clienteConsultado2);
    }

    @Test
    public void alterarCliente() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Cliente cliente = criarCliente();
        Cliente clienteCadastrado = clienteDAO.cadastrar(cliente);
        Assert.assertNotNull(clienteCadastrado);

        Cliente clienteConsultado = clienteDAO.consultar(clienteCadastrado.getId());
        Assert.assertEquals(clienteCadastrado.getId(), clienteConsultado.getId());

        clienteConsultado.setNome("Nome Alterado");
        clienteDAO.alterar(clienteConsultado);

        Cliente clienteAlterado = clienteDAO.consultar(clienteConsultado.getId());
        Assert.assertNotEquals(clienteCadastrado.getNome(), clienteAlterado.getNome());
        Assert.assertEquals("Nome Alterado", clienteAlterado.getNome());

        clienteDAO.excluir(clienteAlterado);
        Cliente clienteConsultado2 = clienteDAO.consultar(clienteAlterado.getId());
        Assert.assertNull(clienteConsultado2);
    }

    @Test
    public void buscarTodosClientes() throws DAOException, TipoChaveNaoEncontradaException, MaisDeUmRegistroException, TableException {
        Cliente cliente1 = criarCliente();
        Cliente clienteCadastrado1 = clienteDAO.cadastrar(cliente1);
        Assert.assertNotNull(clienteCadastrado1);

        Cliente cliente2 = criarCliente();
        Cliente clienteCadastrado2 = clienteDAO.cadastrar(cliente2);
        Assert.assertNotNull(clienteCadastrado2);

        Collection<Cliente> listaClientes = clienteDAO.buscarTodos();
        assertTrue(listaClientes != null);
        assertTrue(listaClientes.size() >= 2);

        clienteDAO.excluir(cliente1);
        Cliente clienteConsultado1 = clienteDAO.consultar(cliente1.getId());
        Assert.assertNull(clienteConsultado1);

        clienteDAO.excluir(cliente2);
        Cliente clienteConsultado2 = clienteDAO.consultar(cliente2.getId());
        Assert.assertNull(clienteConsultado2);
    }

    private Cliente criarCliente() throws DAOException, TipoChaveNaoEncontradaException {
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
