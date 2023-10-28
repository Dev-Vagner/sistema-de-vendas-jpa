package br.com.vbruno.dao;

import br.com.vbruno.dao.generics.GenericDAO;
import br.com.vbruno.domain.Venda;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;

public class VendaExclusaoDAO extends GenericDAO<Venda, Long> implements IVendaDAO {
    public VendaExclusaoDAO() {
        super(Venda.class);
    }

    @Override
    public void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    public void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }

    @Override
    public Venda consultarComCollection(Long id) {
        throw new UnsupportedOperationException("OPERAÇÃO NÃO PERMITIDA");
    }
}
