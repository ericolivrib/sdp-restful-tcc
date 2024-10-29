package br.com.erico.sdp.service;

import br.com.erico.sdp.model.Periodo;
import br.com.erico.sdp.repository.DataLimiteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DataLimiteService {

    private final DataLimiteRepository dataLimiteRepository;

    public DataLimiteService(DataLimiteRepository dataLimiteRepository) {
        this.dataLimiteRepository = dataLimiteRepository;
    }

    public boolean isPeriodoVigente(Periodo periodo) {
        var dataLimite = dataLimiteRepository.getReferenceById(periodo.getId());

        LocalDate diaAtual = LocalDate.now();

        return (diaAtual.isAfter(dataLimite.getDataInicio()) || diaAtual.equals(dataLimite.getDataInicio()))
                && (diaAtual.isBefore(dataLimite.getDataFim()) || diaAtual.equals(dataLimite.getDataFim()));
    }

}
