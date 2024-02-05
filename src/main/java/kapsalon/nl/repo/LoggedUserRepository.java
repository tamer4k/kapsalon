package kapsalon.nl.repo;

import kapsalon.nl.models.entity.LoggedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoggedUserRepository extends JpaRepository<LoggedUser, Long> {
}
