package ufrn.tads.cleanstore.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Carrinho {
    List<Produto> qtdCarrinho = new ArrayList<Produto>(); 
}
