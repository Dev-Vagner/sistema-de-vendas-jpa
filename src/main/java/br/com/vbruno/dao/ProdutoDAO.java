package br.com.vbruno.dao;

import br.com.vbruno.dao.generics.GenericDAO;
import br.com.vbruno.domain.Produto;

public class ProdutoDAO extends GenericDAO<Produto, Long> implements IProdutoDAO {
    public ProdutoDAO() {
        super(Produto.class);
    }
}
