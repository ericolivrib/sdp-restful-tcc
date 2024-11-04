package br.com.erico.sdp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Year;

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

    @NotBlank(message = "Nome do projeto é obrigatório")
    private String nome;

    @NotBlank(message = "Número de projeto é obrigatório")
    @Size(min = 6, max = 6, message = "Número de projeto deve conter 6 caracteres")
    private String numero;

    @NotBlank(message = "Modalidade do projeto é obrigatória")
    @Pattern(regexp = "Ensino|Pesquisa|Extensão", message = "Modalidade do projeto deve ser Ensino, Pesquisa ou Extensão")
    private String modalidade;

    @NotBlank(message = "Justificativa de criação do projeto é obrigatória")
    @Size(min = 20, max = 100, message = "Justificativa de criação do projeto deve conter de 20 a 100 caracteres")
    private String justificativa;

    @NotBlank(message = "Impactos ambientais do projeto são obrigatórios")
    @Size(min = 10, max = 100, message = "Impactos ambientais do projeto devem conter de 10 a 100 caracteres")
    private String impactosAmbientais;

    private Integer ano;

    @Enumerated(EnumType.STRING)
    private StatusProjeto status;

    private LocalDate dataCriacao;

    private LocalDate dataFinalizacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    @NotNull(message = "Usuário responsável pelo projeto é obrigatório")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "eixo_tecnologico_id")
    @NotNull(message = "Eixo tecnológico do projeto é obrigatório")
    private EixoTecnologico eixoTecnologico;

}
