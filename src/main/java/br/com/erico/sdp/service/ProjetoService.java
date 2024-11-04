package br.com.erico.sdp.service;

import br.com.erico.sdp.dto.ProjetoFromUsuarioDTO;
import br.com.erico.sdp.exception.NumeroProjetoExistenteException;
import br.com.erico.sdp.model.Projeto;
import br.com.erico.sdp.model.StatusProjeto;
import br.com.erico.sdp.repository.ProjetoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public void adicionarProjeto(Projeto projeto) {
        log.info("Cadastrando projeto n° {}", projeto.getNumero());

        if (isNumeroProjetoExistente(projeto.getNumero())) {
            log.error("Número de projeto existente");
            throw new NumeroProjetoExistenteException(projeto.getNumero());
        }

        projetoRepository.save(Projeto.builder()
                .nome(projeto.getNome())
                .numero(projeto.getNumero())
                .modalidade(projeto.getModalidade())
                .justificativa(projeto.getJustificativa())
                .impactosAmbientais(projeto.getImpactosAmbientais())
                .dataCriacao(LocalDate.now())
                .ano(LocalDate.now().getYear())
                .usuario(projeto.getUsuario())
                .eixoTecnologico(projeto.getEixoTecnologico())
                .status(projeto.getStatus())
                .build());
    }

    public List<ProjetoFromUsuarioDTO> getProjetosByUsuario(Long usuarioId) {
        log.info("Buscando projetos do usuário de ID {}", usuarioId);

        return projetoRepository.findAllByUsuarioId(usuarioId)
                .stream()
                .map(ProjetoFromUsuarioDTO::new)
                .toList();
    }

    public ProjetoFromUsuarioDTO getProjetoById(Long id) {
        log.info("Buscando projeto de ID {}", id);

        Projeto projeto = projetoRepository.findById(id).orElseThrow();
        return new ProjetoFromUsuarioDTO(projeto);
    }

    public boolean isNumeroProjetoExistente(String numeroProjeto) {
        return projetoRepository.existsByNumero(numeroProjeto);
    }

    public void finalizarProjeto(Long id) {
        Projeto projeto = projetoRepository.findById(id).orElseThrow();
        projeto.setStatus(StatusProjeto.NAO_AVALIADO);
        projeto.setDataFinalizacao(LocalDate.now());
        projetoRepository.save(projeto);
    }

}
