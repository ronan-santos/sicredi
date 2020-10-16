package sicredi.persistencia.entidade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "TB02_PAUTA", schema = "SICREDI")
@NamedNativeQuery (name = "Pauta.obterPautasAtivas",
				 query = " select p.* from sicredi.tb02_pauta p " + 
						 " where p.dh_inicio_votacao  < current_timestamp " + 
						 " and p.dh_fim_votacao > current_timestamp ",
				resultClass = Pauta.class)
public class Pauta implements Serializable {


	private static final long serialVersionUID = 4129616613430300966L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "NU_PAUTA")
	private Long id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "NU_ASSOCIADO")
	private Associado proponente;
	
	@NotBlank
	@Column(name = "NO_PAUTA", nullable = false)
	private String nome;
	
	@Setter(value = AccessLevel.NONE)
	@Column(name = "DH_CADASTRO")
	private LocalDateTime cadastro;
	
	@Column(name = "DH_INICIO_VOTACAO")
	private LocalDateTime dataInicioVotacao;
	
	@Column(name = "DH_FIM_VOTACAO")
	private LocalDateTime dataFimVotacao;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "NU_PAUTA")
	private List<VotoAssociado> votos;
	
	
}
