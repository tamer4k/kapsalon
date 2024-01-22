package kapsalon.nl.repo;

import kapsalon.nl.models.entity.Category;
import kapsalon.nl.models.entity.Dienst;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DienstRepository extends JpaRepository<Dienst, Long> {

}