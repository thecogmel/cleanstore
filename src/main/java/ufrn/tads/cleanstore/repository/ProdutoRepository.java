package ufrn.tads.cleanstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ufrn.tads.cleanstore.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    
}
