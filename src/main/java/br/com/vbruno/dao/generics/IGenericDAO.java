package br.com.vbruno.dao.generics;

import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.MaisDeUmRegistroException;
import br.com.vbruno.exceptions.TableException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;

import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.Collection;

public interface IGenericDAO<T extends Persistence, E extends Serializable> {

    T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    void excluir(T entity) throws DAOException;

    T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException;

    T consultar(E id) throws MaisDeUmRegistroException, TableException, DAOException;

    Collection<T> buscarTodos() throws DAOException;
}
