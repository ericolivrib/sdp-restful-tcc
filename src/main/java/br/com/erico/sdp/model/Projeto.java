package br.com.erico.sdp.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "projetos")
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String numero;
    private String modalidade;
    private String justificativa;
    private String impactosAmbientais;
    private Integer ano;
    @Enumerated(EnumType.STRING)
    private StatusProjeto status;
    private LocalDate dataCriacao;
    private LocalDate dataFinalizacao;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "eixo_tecnologico_id")
    private EixoTecnologico eixoTecnologico;

}
