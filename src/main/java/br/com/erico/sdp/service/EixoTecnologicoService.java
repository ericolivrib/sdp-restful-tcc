package br.com.erico.sdp.service;

import br.com.erico.sdp.model.EixoTecnologico;
import br.com.erico.sdp.repository.EixoTecnologicoRepository;
import org.springframework.stereotype.Service;

@Service
public class EixoTecnologicoService {

    private final EixoTecnologicoRepository eixoTecnologicoRepository;

    public EixoTecnologicoService(EixoTecnologicoRepository eixoTecnologicoRepository) {
        this.eixoTecnologicoRepository = eixoTecnologicoRepository;
    }

    public void adicionarEixoTecnologico(EixoTecnologico eixoTecnologico) {
        eixoTecnologicoRepository.save(eixoTecnologico);
    }

}
