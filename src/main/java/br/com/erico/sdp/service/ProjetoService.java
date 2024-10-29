package br.com.erico.sdp.service;

import br.com.erico.sdp.dto.ProjetoRequest;
import br.com.erico.sdp.dto.ProjetoResponse;
import br.com.erico.sdp.exception.NumeroProjetoExistenteException;
import br.com.erico.sdp.repository.ProjetoRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Long adicionarProjeto(ProjetoRequest projeto, Long usuarioId) {
        if (isNumeroProjetoExistente(projeto.numero())) {
            throw new NumeroProjetoExistenteException(projeto.numero());
        }

        var p = projetoRepository.save(projeto.toEntity(usuarioId));
        return p.getId();
    }

    public List<ProjetoResponse> getProjetosByUsuario(Long usuarioId) {
        return projetoRepository.findAllByUsuarioId(usuarioId)
                .stream()
                .map(ProjetoResponse::fromEntity)
                .toList();
    }

    public boolean isNumeroProjetoExistente(String numeroProjeto) {
        return projetoRepository.existsByNumero(numeroProjeto);
    }

}
