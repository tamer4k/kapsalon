package kapsalon.nl.controllers;

import kapsalon.nl.models.dto.AppointmentDTO;
import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.services.DienstService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
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
    public ResponseEntity<DienstDTO> getDienstById(@PathVariable Long id) {
        DienstDTO result = dienstService.getDienstById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
    public ResponseEntity<DienstDTO> updateDienst(@PathVariable Long id, @RequestBody DienstDTO dienstDTO) {
        DienstDTO result = dienstService.updateDienst(id, dienstDTO);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<DienstDTO> deleteDienst(@PathVariable Long id) {
        dienstService.deleteDienst(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}