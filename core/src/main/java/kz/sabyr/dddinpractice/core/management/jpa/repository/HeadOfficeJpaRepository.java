package kz.sabyr.dddinpractice.core.management.jpa.repository;

import kz.sabyr.dddinpractice.core.management.jpa.HeadOfficeJpa;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadOfficeJpaRepository extends CrudRepository<HeadOfficeJpa, Long> {
}
