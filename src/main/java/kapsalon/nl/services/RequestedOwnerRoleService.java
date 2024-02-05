package kapsalon.nl.services;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.RequestedOwnerRoleDTO;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.RequestedOwnerRole;
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

    @Autowired
    public RequestedOwnerRoleService(RequestedOwnerRoleRepository requestedOwnerRoleRepository) {
        this.requestedOwnerRoleRepository = requestedOwnerRoleRepository;
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

        requestedOwnerRoleDTO.setStatus("open");

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
            throw new AccessDeniedException("User with 'OWNER' role cannot submit the request again.");
        }

        return hasOwnerRole;
    }

    public RequestedOwnerRoleDTO updateRequestedOwnerRole(Long id, RequestedOwnerRoleDTO requestedOwnerRoleDTO) {

        Optional<RequestedOwnerRole> existingRequestedOwnerRoleOptional = requestedOwnerRoleRepository.findById(id);

        if (existingRequestedOwnerRoleOptional.isPresent()) {
            RequestedOwnerRole existingRequestedOwnerRole = existingRequestedOwnerRoleOptional.get();
            existingRequestedOwnerRole.setUsername(requestedOwnerRoleDTO.getUsername());
            existingRequestedOwnerRole.setStatus(requestedOwnerRoleDTO.getStatus());
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
                .status(entity.getStatus())
                .build();
    }

    private RequestedOwnerRole dTOToEntity(RequestedOwnerRoleDTO dto) {
        return RequestedOwnerRole.builder()
                .username(dto.getUsername())
                .status(dto.getStatus())
                .build();
    }
}