package ufrn.tads.cleanstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ufrn.tads.cleanstore.model.Produto;
import ufrn.tads.cleanstore.repository.ProdutoRepository;

@Service
public class ProdutoService {
    ProdutoRepository repository;

	@Autowired
	public void setRepository(ProdutoRepository repository) {
		this.repository = repository;
	}

	public List<Produto> findAll(){
		return repository.findAll();
	}

	public void save(Produto a){
		 repository.save(a);
	}

	public void delete(Long id){
		repository.deleteById(id);
	}

	public Produto findById(Long id){
		return repository.getById(id);
	}
}
