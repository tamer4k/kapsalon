package kapsalon.nl.controllers;

import kapsalon.nl.exceptions.ErrorMessages;
import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.services.DienstService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/diensten")
public class DienstController {


    private final DienstService dienstService;

    public DienstController(DienstService dienstService){
      this.dienstService = dienstService;
    }

    @GetMapping
    public ResponseEntity<List<DienstDTO>> getAllDiensten() {
        List<DienstDTO> result = dienstService.getAllDiensten();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getDienstById(@PathVariable Long id) {
        try {
            DienstDTO result = dienstService.getDienstById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RecordNotFoundException ex) {
            String errorMessage = ErrorMessages.DIENST_NOT_FOUND + id + " niet gevonden";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createDienst(@Validated @RequestBody DienstDTO dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }else {
            DienstDTO result = dienstService.createDienst(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDienst(@PathVariable Long id, @RequestBody DienstDTO dienstDTO) {

        try {
            DienstDTO result = dienstService.updateDienst(id, dienstDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (RecordNotFoundException ex) {
            String errorMessage = ErrorMessages.DIENST_NOT_FOUND + id + " niet gevonden";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDienst(@PathVariable Long id) {
        try {
            dienstService.deleteDienst(id);
        } catch (RecordNotFoundException ex) {
            String errorMessage = ErrorMessages.DIENST_NOT_FOUND + id + " niet gevonden";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        return null;
    }
}