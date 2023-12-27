package kapsalon.nl.repo;

import kapsalon.nl.models.entity.Dienst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DienstRepository extends JpaRepository<Dienst, Long> {
    // Je kunt aanvullende query-methoden toevoegen indien nodig
}