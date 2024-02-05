package kapsalon.nl.controllers;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.RequestedOwnerRoleDTO;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.services.RequestedOwnerRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/request-owner-role")
public class RequestedOwnerRoleController {

    private final RequestedOwnerRoleService requestedOwnerRoleService;

    @Autowired
    public RequestedOwnerRoleController(RequestedOwnerRoleService requestedOwnerRoleService) {
        this.requestedOwnerRoleService = requestedOwnerRoleService;
    }

    @GetMapping
    public ResponseEntity<List<RequestedOwnerRoleDTO>> getAllRequestedOwnerRoles() {
        List<RequestedOwnerRoleDTO> requestedOwnerRoles = requestedOwnerRoleService.getAllRequestedOwnerRoles();
        return new ResponseEntity<>(requestedOwnerRoles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequestedOwnerRoleDTO> getRequestedOwnerRoleById(@PathVariable Long id) {
        RequestedOwnerRoleDTO requestedOwnerRole = requestedOwnerRoleService.getRequestedOwnerRoleById(id);
        if (requestedOwnerRole != null) {
            return new ResponseEntity<>(requestedOwnerRole, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<RequestedOwnerRoleDTO> createRequestedOwnerRole(@RequestBody RequestedOwnerRoleDTO requestedOwnerRoleDTO) {
        RequestedOwnerRoleDTO createdRequestedOwnerRole = requestedOwnerRoleService.createRequestedOwnerRole(requestedOwnerRoleDTO);
        return new ResponseEntity<>(createdRequestedOwnerRole, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RequestedOwnerRoleDTO> updateRequestedOwnerRole(@PathVariable Long id, @RequestBody RequestedOwnerRoleDTO requestedOwnerRoleDTO) {
        RequestedOwnerRoleDTO updatedRequestedOwnerRole = requestedOwnerRoleService.updateRequestedOwnerRole(id, requestedOwnerRoleDTO);
        if (updatedRequestedOwnerRole != null) {
            return new ResponseEntity<>(updatedRequestedOwnerRole, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequestedOwnerRole(@PathVariable Long id) {
        requestedOwnerRoleService.deleteRequestedOwnerRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}