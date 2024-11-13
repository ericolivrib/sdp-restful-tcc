package br.com.erico.sdp.service;

import br.com.erico.sdp.dto.ProjetoFromUsuarioDTO;
import br.com.erico.sdp.exception.NumeroProjetoExistenteException;
import br.com.erico.sdp.exception.UsuarioNaoAutenticadoException;
import br.com.erico.sdp.model.Projeto;
import br.com.erico.sdp.model.StatusProjeto;
import br.com.erico.sdp.model.Usuario;
import br.com.erico.sdp.repository.ProjetoRepository;
import br.com.erico.sdp.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class ProjetoService {

    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;

    public ProjetoService(ProjetoRepository projetoRepository, UsuarioRepository usuarioRepository) {
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public void adicionarProjeto(Projeto projeto) {
        Usuario usuario = usuarioRepository.findById(projeto.getUsuario().getId())
                .orElseThrow(UsuarioNaoAutenticadoException::new);

        if (isNumeroProjetoExistente(projeto.getNumero()))
            throw new NumeroProjetoExistenteException();

        log.info("Cadastrando projeto n° {}", projeto.getNumero());

        projetoRepository.save(projeto);
    }

    public List<ProjetoFromUsuarioDTO> getProjetosByUsuario(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(UsuarioNaoAutenticadoException::new);

        log.info("Buscando projetos do usuário de ID {}", usuarioId);

        return projetoRepository.findAllByUsuario(usuario)
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
