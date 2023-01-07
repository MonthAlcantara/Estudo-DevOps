package io.github.monthalcantara.proposta.repository;

import io.github.monthalcantara.proposta.domain.Proposta;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long> {
}
