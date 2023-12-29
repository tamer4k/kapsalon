package kapsalon.nl.controllers;
import kapsalon.nl.models.dto.KlantDTO;
import kapsalon.nl.services.KlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Klanten")
public class KlantController {
    private final KlantService klantService;

    public KlantController(KlantService klantService){
        this.klantService = klantService;
    }

    @GetMapping
    public ResponseEntity<List<KlantDTO>> getAllKlanten() {
        List<KlantDTO> klanten = klantService.getAllKlanten();
        return new ResponseEntity<>(klanten, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KlantDTO> getKlantById(@PathVariable Long id) {
        KlantDTO klantDTO = klantService.getKlantById(id);
        if (klantDTO != null) {
            return new ResponseEntity<>(klantDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<KlantDTO> createKlant(@RequestBody KlantDTO dto) {

        KlantDTO createdKlanttDTO = klantService.createKlant(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdKlanttDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<KlantDTO> updateKlant(@PathVariable Long id, @RequestBody KlantDTO dto) {
        KlantDTO updateKlantDto = klantService.updateKlant(id, dto);
        if (updateKlantDto != null) {
            return new ResponseEntity<>(updateKlantDto, HttpStatus.OK);
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
