package kapsalon.nl.repo;

import kapsalon.nl.models.entity.Kapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KapperRepository extends JpaRepository<Kapper, Long> {
}
