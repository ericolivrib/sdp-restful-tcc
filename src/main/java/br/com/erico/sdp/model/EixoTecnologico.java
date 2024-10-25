package br.com.erico.sdp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "eixos_tecnologicos")
public class EixoTecnologico {

    @Id
    private Long id;

    @OneToMany(mappedBy = "eixoTecnologico")
    private List<Projeto> projetos;

}
