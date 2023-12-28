package kapsalon.nl.repo;

import kapsalon.nl.models.entity.Kapsalon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KapsalonRepository extends JpaRepository<Kapsalon, Long> {
}
