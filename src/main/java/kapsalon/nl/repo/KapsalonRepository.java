package kapsalon.nl.repo;

import kapsalon.nl.models.entity.Kapsalon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KapsalonRepository extends JpaRepository<Kapsalon, Long> {
    Optional<Kapsalon> findByOwner(String ownerUsername);
    List<Kapsalon> findAllByOwner(String owner);
}
