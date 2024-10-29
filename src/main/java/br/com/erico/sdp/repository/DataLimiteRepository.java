package br.com.erico.sdp.repository;

import br.com.erico.sdp.model.DataLimite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataLimiteRepository extends JpaRepository<DataLimite, Integer> {
}
