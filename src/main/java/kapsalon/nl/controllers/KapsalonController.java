package kapsalon.nl.controllers;

import kapsalon.nl.models.dto.KapperDTO;
import kapsalon.nl.models.dto.KapsalonDTO;
import kapsalon.nl.services.KapperService;
import kapsalon.nl.services.KapsalonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<KapsalonDTO> getKapsalonById(@PathVariable Long id) {
        KapsalonDTO result = kapsalonService.getKapsalonById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<KapsalonDTO> createKapsalon(@RequestBody KapsalonDTO dto) {

        KapsalonDTO result = kapsalonService.createKapsalon(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @PutMapping("/{id}")
    public ResponseEntity<KapsalonDTO> updateKapsalon(@PathVariable Long id, @RequestBody KapsalonDTO dto) {
        KapsalonDTO result = kapsalonService.updateKapsalon(id, dto);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKapsalon(@PathVariable Long id) {
        kapsalonService.deleteKapsalon(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
