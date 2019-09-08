package kz.sabyr.dddinpractice.core.snackmachine.jpa.repository;

import kz.sabyr.dddinpractice.core.snackmachine.jpa.SlotJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlotJpaRepository extends CrudRepository<SlotJpa, Long> {
    List<SlotJpa> findBySnackMachineId(long snackMachineId);
}
