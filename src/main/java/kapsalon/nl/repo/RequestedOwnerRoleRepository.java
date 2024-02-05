package kapsalon.nl.repo;

import kapsalon.nl.models.entity.RequestedOwnerRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestedOwnerRoleRepository extends JpaRepository<RequestedOwnerRole, Long> {
}