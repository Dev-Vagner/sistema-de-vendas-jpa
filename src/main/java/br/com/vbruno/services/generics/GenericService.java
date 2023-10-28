package br.com.vbruno.services.generics;

import br.com.vbruno.dao.generics.IGenericDAO;
import br.com.vbruno.exceptions.DAOException;
import br.com.vbruno.exceptions.MaisDeUmRegistroException;
import br.com.vbruno.exceptions.TableException;
import br.com.vbruno.exceptions.TipoChaveNaoEncontradaException;

import javax.persistence.Persistence;
import java.io.Serializable;
import java.util.Collection;

public class GenericService<T extends Persistence, E extends Serializable> implements IGenericService<T, E> {

    protected IGenericDAO<T, E> dao;

    public GenericService(IGenericDAO<T, E> dao) {
        this.dao = dao;
    }

    @Override
    public T cadastrar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
        return this.dao.cadastrar(entity);
    }

    @Override
    public void excluir(T entity) throws DAOException {
        this.dao.excluir(entity);
    }

    @Override
    public T alterar(T entity) throws TipoChaveNaoEncontradaException, DAOException {
        return this.dao.alterar(entity);
    }

    @Override
    public T consultar(E id) throws MaisDeUmRegistroException, TableException, DAOException {
        return this.dao.consultar(id);
    }

    @Override
    public Collection<T> buscarTodos() throws DAOException {
        return this.dao.buscarTodos();
    }
}
