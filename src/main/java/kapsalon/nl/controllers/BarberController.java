package kapsalon.nl.controllers;

import kapsalon.nl.exceptions.RecordNotFoundException;
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

    @GetMapping("/available")
    public ResponseEntity<List<BarberDTO>> getAvailableBarbers() {
        List<BarberDTO> availableBarbers = barberService.findAvailableBarbers();
        return new ResponseEntity<>(availableBarbers, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<BarberDTO>> getAllBarbers() {
        List<BarberDTO> result = barberService.getAllBarbers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberDTO> getBarberById(@PathVariable Long id) {

        BarberDTO result = barberService.getBarberById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<BarberDTO> createBarber(@Validated @RequestBody BarberDTO dto , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body((BarberDTO) errors);
        }else {
            BarberDTO result = barberService.createBarber(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarberDTO> updateBarber(@PathVariable Long id, @RequestBody BarberDTO barberDTO) {

            BarberDTO result = barberService.updateBarber(id, barberDTO);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BarberDTO> deleteBarber(@PathVariable Long id) {

        barberService.deleteBarber(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
