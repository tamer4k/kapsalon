package kapsalon.nl.controllers;
import kapsalon.nl.models.dto.KapperDTO;
import kapsalon.nl.models.dto.KlantDTO;
import kapsalon.nl.services.KlantService;
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
@RequestMapping("/api/v1/klanten")
public class KlantController {
    private final KlantService klantService;

    public KlantController(KlantService klantService){
        this.klantService = klantService;
    }

    @GetMapping
    public ResponseEntity<List<KlantDTO>> getAllKlanten() {
        List<KlantDTO> result = klantService.getAllKlanten();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KlantDTO> getKlantById(@PathVariable Long id) {
        KlantDTO result = klantService.getKlantById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createKlant(@Validated  @RequestBody KlantDTO dto , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }else {
            KlantDTO result = klantService.createKlant(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<KlantDTO> updateKlant(@PathVariable Long id, @RequestBody KlantDTO dto) {
        KlantDTO result = klantService.updateKlant(id, dto);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKlant(@PathVariable Long id) {
        klantService.deleteKlant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
