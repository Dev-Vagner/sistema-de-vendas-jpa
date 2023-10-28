package br.com.vbruno.dao;

import br.com.vbruno.dao.generics.GenericDAO;
import br.com.vbruno.domain.Cliente;

public class ClienteDAO extends GenericDAO<Cliente, Long> implements IClienteDAO {
    public ClienteDAO(){
        super(Cliente.class);
    }
}
