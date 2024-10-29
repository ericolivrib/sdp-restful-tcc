package br.com.erico.sdp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "datas_limite")
public class DataLimite {

    @Id
    private Integer id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;

}
