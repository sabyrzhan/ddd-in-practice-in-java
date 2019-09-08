package kz.sabyr.dddinpractice.core.snackmachine.jpa.repository;

import kz.sabyr.dddinpractice.core.snackmachine.jpa.SnackJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnackJpaRepository extends CrudRepository<SnackJpa, Long> {

}
