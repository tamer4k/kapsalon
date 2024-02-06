package kapsalon.nl.services;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.RequestedOwnerRoleDTO;
import kapsalon.nl.models.entity.Category;
import kapsalon.nl.models.entity.RequestedOwnerRole;
import kapsalon.nl.models.entity.Status;
import kapsalon.nl.repo.RequestedOwnerRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestedOwnerRoleService {

    private final RequestedOwnerRoleRepository requestedOwnerRoleRepository;

    private final UserService userService;
    @Autowired
    public RequestedOwnerRoleService(RequestedOwnerRoleRepository requestedOwnerRoleRepository, UserService userService) {
        this.requestedOwnerRoleRepository = requestedOwnerRoleRepository;
        this.userService = userService;
    }

    public List<RequestedOwnerRoleDTO> getAllRequestedOwnerRoles() {
        return requestedOwnerRoleRepository.findAll()
                .stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    public RequestedOwnerRoleDTO getRequestedOwnerRoleById(Long id) {
        RequestedOwnerRole requestedOwnerRole  =  requestedOwnerRoleRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No request found with ID: " + id));

        return entityToDTO(requestedOwnerRole);
    }

    public RequestedOwnerRoleDTO createRequestedOwnerRole(RequestedOwnerRoleDTO requestedOwnerRoleDTO) {
        String loggedInUsername = getLoggedInUsername();

        if (hasOnlyOwnerRole()) {
            throw new IllegalStateException("User with 'OWNER' role cannot submit the request again.");
        }

        // Zoek de enum-waarde die overeenkomt met "OPEN"
        Status status = Status.PENDING;

        requestedOwnerRoleDTO.setStatus(status.getDisplayName()); // Gebruik de displayName van de enum-waarde
        requestedOwnerRoleDTO.setUsername(loggedInUsername);

        RequestedOwnerRole newRequestedOwnerRole = dTOToEntity(requestedOwnerRoleDTO);
        RequestedOwnerRole savedRequestedOwnerRole = requestedOwnerRoleRepository.save(newRequestedOwnerRole);
        return entityToDTO(savedRequestedOwnerRole);
    }
    private String getLoggedInUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return principal.toString();
    }
    private boolean hasOnlyOwnerRole() {
        boolean hasOwnerRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_OWNER"));

        if (hasOwnerRole) {
            throw new AccessDeniedException("you have 'OWNER' role cannot submit the request again.");
        }

        return hasOwnerRole;
    }
    public RequestedOwnerRoleDTO updateRequestedOwnerRole(Long id, RequestedOwnerRoleDTO requestedOwnerRoleDTO) {
        Optional<RequestedOwnerRole> existingRequestedOwnerRoleOptional = requestedOwnerRoleRepository.findById(id);

        if (existingRequestedOwnerRoleOptional.isPresent()) {
            RequestedOwnerRole existingRequestedOwnerRole = existingRequestedOwnerRoleOptional.get();
            Status newStatus = Status.valueOf(requestedOwnerRoleDTO.getStatus().toUpperCase()); // Converteer de status-string naar enum-waarde

            // Controleer of de nieuwe status overeenkomt met de huidige status
            if (existingRequestedOwnerRole.getStatus() != Status.PENDING && newStatus != existingRequestedOwnerRole.getStatus()) {
                throw new AccessDeniedException("Status cannot be changed once it's set to '" + existingRequestedOwnerRole.getStatus().getDisplayName() + "'");
            }

            existingRequestedOwnerRole.setStatus(newStatus);

            // Controleer of de status is gewijzigd naar 'APPROVED' en voeg automatisch de rol 'Owner' toe
            if (newStatus == Status.APPROVED) {
                // Haal de gebruikersnaam op uit de aangevraagde rol
                String username = existingRequestedOwnerRole.getUsername();
                // Voeg de rol 'Owner' toe aan de gebruiker
                userService.addAuthority(username, "ROLE_OWNER");
            }

            RequestedOwnerRole updatedRequestedOwnerRole = requestedOwnerRoleRepository.save(existingRequestedOwnerRole);
            return entityToDTO(updatedRequestedOwnerRole);
        } else {
            return null;
        }
    }

    public void deleteRequestedOwnerRole(Long id) {
      RequestedOwnerRole requestedOwnerRole  =  requestedOwnerRoleRepository.findById(id)
              .orElseThrow(() -> new RecordNotFoundException("No request found with ID: " + id));

        requestedOwnerRoleRepository.delete(requestedOwnerRole);
    }

    private RequestedOwnerRoleDTO entityToDTO(RequestedOwnerRole entity) {
        return RequestedOwnerRoleDTO.builder()
                .id(entity.getId())
                .username(entity.getUsername())
                .status(entity.getStatus().getDisplayName())
                .build();
    }

    private RequestedOwnerRole dTOToEntity(RequestedOwnerRoleDTO dto) {
        return RequestedOwnerRole.builder()
                .username(dto.getUsername())
                .status(Status.valueOf(dto.getStatus()))
                .build();
    }
}