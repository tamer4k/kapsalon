package kapsalon.nl.repo;

import kapsalon.nl.models.entity.Klant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KlantRepository extends JpaRepository<Klant,Long> {
}
