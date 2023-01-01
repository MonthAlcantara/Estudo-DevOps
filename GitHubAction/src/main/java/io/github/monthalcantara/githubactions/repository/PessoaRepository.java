package io.github.monthalcantara.githubactions.repository;

import io.github.monthalcantara.githubactions.domain.Pessoa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends CrudRepository<Pessoa,Long> {
}
