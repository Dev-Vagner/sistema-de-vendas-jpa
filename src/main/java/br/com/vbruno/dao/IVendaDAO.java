package br.com.vbruno.dao;

import br.com.vbruno.dao.generics.IGenericDAO;
import br.com.vbruno.domain.Venda;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;

public interface IVendaDAO extends IGenericDAO<Venda, Long> {
    void finalizarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;
    void cancelarVenda(Venda venda) throws TipoChaveNaoEncontradaException, DAOException;
    Venda consultarComCollection(Long id);
}
