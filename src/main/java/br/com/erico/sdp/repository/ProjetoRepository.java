package br.com.erico.sdp.repository;

import br.com.erico.sdp.model.Projeto;
import br.com.erico.sdp.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    List<Projeto> findAllByUsuario(Usuario usuario);
    boolean existsByNumero(String numero);

}
