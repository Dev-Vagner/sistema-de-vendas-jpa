package br.com.vbruno.services;

import br.com.vbruno.dao.IProdutoDAO;
import br.com.vbruno.domain.Produto;
import br.com.vbruno.services.generics.GenericService;

public class ProdutoService extends GenericService<Produto, Long> implements IProdutoService{
    public ProdutoService(IProdutoDAO dao) {
        super(dao);
    }
}
