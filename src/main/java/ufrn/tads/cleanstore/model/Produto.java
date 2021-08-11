package ufrn.tads.cleanstore.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ufrn.tads.cleanstore.message.Mensagem;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Produto {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	String nome;

	@Size(min = 2, max = 25, message = Mensagem.ERRO_TAMANHO_STRING)
	String marca;
	
    @Min(value = 1900, message = Mensagem.ERRO_ANO_MINIMO)
	Date fabricacao;
	Float preco;
	String tipo;
	String imagemUri;
}
