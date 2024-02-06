package kapsalon.nl.repo;

import kapsalon.nl.models.entity.Barber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BarberRepository extends JpaRepository<Barber, Long> {

    List<Barber> findByAvailableTrue();
    Optional<Barber> findByIdAndKapsalonId(Long barberId, Long kapsalonId);

}
