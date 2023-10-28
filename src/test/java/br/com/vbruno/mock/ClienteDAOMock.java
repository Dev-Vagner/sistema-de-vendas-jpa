package br.com.vbruno.mock;

import br.com.vbruno.dao.IClienteDAO;
import br.com.vbruno.domain.Cliente;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.MaisDeUmRegistroException;
import br.com.vbruno.exceptions.TableException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

public class ClienteDAOMock implements IClienteDAO {
    private Random rd;

    public ClienteDAOMock() {
        this.rd = new Random();
    }

    @Override
    public Cliente cadastrar(Cliente entity) throws TipoChaveNaoEncontradaException, DAOException {
        return entity;
    }

    @Override
    public void excluir(Cliente entity) throws DAOException {

    }

    @Override
    public Cliente alterar(Cliente entity) throws TipoChaveNaoEncontradaException, DAOException {
        return entity;
    }

    @Override
    public Cliente consultar(Long id) throws MaisDeUmRegistroException, TableException, DAOException {
        Cliente clienteMock = criarClienteMock(id);
        return clienteMock;
    }

    @Override
    public Collection<Cliente> buscarTodos() throws DAOException {
        Collection<Cliente> listaClientes = new ArrayList<>();

        Cliente clienteMock1 = criarClienteMock(1l);
        Cliente clienteMock2 = criarClienteMock(2l);

        listaClientes.add(clienteMock1);
        listaClientes.add(clienteMock2);

        return listaClientes;
    }

    private Cliente criarClienteMock(Long id) {
        Cliente clienteMock = new Cliente();
        clienteMock.setId(id);
        clienteMock.setNome("Cliente Mock");
        clienteMock.setCpf(rd.nextLong());
        clienteMock.setTel(10l);
        clienteMock.setEnd("Rua dos mock, nº10");
        clienteMock.setNumero(354);
        clienteMock.setCidade("Cidade Mock");
        clienteMock.setEstado("Estado Mock");

        return clienteMock;
    }
}
