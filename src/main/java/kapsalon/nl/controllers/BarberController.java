package kapsalon.nl.controllers;

import kapsalon.nl.models.dto.BarberDTO;
import kapsalon.nl.services.BarberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/barbers")
public class BarberController {
    private final BarberService barberService;

    public BarberController(BarberService barberService){
        this.barberService = barberService;
    }

    @GetMapping
    public ResponseEntity<List<BarberDTO>> getAllBarbers() {
        List<BarberDTO> result = barberService.getAllBarbers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberDTO> getBarberById(@PathVariable Long id) {
        BarberDTO result = barberService.getBarberById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createBarber(@Validated @RequestBody BarberDTO dto , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }else {
            BarberDTO result = barberService.createBarber(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarberDTO> updateBarber(@PathVariable Long id, @RequestBody BarberDTO barberDTO) {
        BarberDTO result = barberService.updateBarber(id, barberDTO);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBarber(@PathVariable Long id) {
        barberService.deleteBarber(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
