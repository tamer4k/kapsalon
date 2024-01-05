package kapsalon.nl.controllers;
import kapsalon.nl.models.dto.KlantDTO;
import kapsalon.nl.services.KlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<KlantDTO> createKlant(@RequestBody KlantDTO dto) {

        KlantDTO result = klantService.createKlant(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

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
