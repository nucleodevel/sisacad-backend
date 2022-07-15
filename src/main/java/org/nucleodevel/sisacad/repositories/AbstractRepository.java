package org.nucleodevel.sisacad.repositories;

import org.nucleodevel.sisacad.domain.AbstractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public abstract interface AbstractRepository<E extends AbstractEntity<ID>, ID> extends JpaRepository<E, ID> {

}
