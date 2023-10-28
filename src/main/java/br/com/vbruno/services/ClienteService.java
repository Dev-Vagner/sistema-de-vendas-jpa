package br.com.vbruno.services;

import br.com.vbruno.dao.IClienteDAO;
import br.com.vbruno.domain.Cliente;
import br.com.vbruno.services.generics.GenericService;

public class ClienteService extends GenericService<Cliente, Long> implements IClienteService {
    public ClienteService(IClienteDAO dao) {
        super(dao);
    }
}
