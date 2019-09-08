package kz.sabyr.dddinpractice.core.atms.jpa.repository;

import kz.sabyr.dddinpractice.core.atms.jpa.AtmJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AtmJpaRepository extends CrudRepository<AtmJpa, Long> {
}
