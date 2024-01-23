package kapsalon.nl.controllers;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.KapsalonDTO;
import kapsalon.nl.services.KapsalonService;
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
@RequestMapping("/api/v1/kapsalon")
public class KapsalonController {
    private final KapsalonService kapsalonService;

    public KapsalonController(KapsalonService kapsalonService){
        this.kapsalonService = kapsalonService;
    }

    @GetMapping
    public ResponseEntity<List<KapsalonDTO>> getAllKapsalons() {
        List<KapsalonDTO> result = kapsalonService.getAllKapsalons();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getKapsalonById(@PathVariable Long id) {

            KapsalonDTO result = kapsalonService.getKapsalonById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);


    }


    @PostMapping
    public ResponseEntity<Object> createKapsalon(@Validated @RequestBody KapsalonDTO dto , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }else {
            KapsalonDTO result = kapsalonService.createKapsalon(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateKapsalon(@PathVariable Long id, @RequestBody KapsalonDTO dto) {

        KapsalonDTO result = kapsalonService.updateKapsalon(id, dto);
            return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKapsalon(@PathVariable Long id) {

        kapsalonService.deleteKapsalon(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
