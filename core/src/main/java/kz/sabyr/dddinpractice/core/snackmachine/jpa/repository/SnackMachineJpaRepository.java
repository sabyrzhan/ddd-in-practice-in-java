package kz.sabyr.dddinpractice.core.snackmachine.jpa.repository;

import kz.sabyr.dddinpractice.core.snackmachine.jpa.SnackMachineJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnackMachineJpaRepository extends CrudRepository<SnackMachineJpa, Long> {
}
