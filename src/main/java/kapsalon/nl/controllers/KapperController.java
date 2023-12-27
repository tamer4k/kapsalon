package kapsalon.nl.controllers;

import kapsalon.nl.models.dto.KapperDTO;
import kapsalon.nl.services.KapperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kappers")
public class KapperController {
    private final KapperService kapperService;

    public KapperController(KapperService kapperService){
        this.kapperService = kapperService;
    }

    @GetMapping
    public ResponseEntity<List<KapperDTO>> getAllKappers() {
        List<KapperDTO> kappers = kapperService.getAllKappers();
        return new ResponseEntity<>(kappers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KapperDTO> getKapperById(@PathVariable Long id) {
        KapperDTO kapper = kapperService.getKapperById(id);
        if (kapper != null) {
            return new ResponseEntity<>(kapper, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<KapperDTO> createKapper(@RequestBody KapperDTO dto) {

        KapperDTO createdKapperDTO = kapperService.createKapper(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdKapperDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<KapperDTO> updateKapper(@PathVariable Long id, @RequestBody KapperDTO kapperDTO) {
        KapperDTO updatedKapperDto = kapperService.updateKapper(id, kapperDTO);
        if (updatedKapperDto != null) {
            return new ResponseEntity<>(updatedKapperDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteKapper(@PathVariable Long id) {
        kapperService.deleteKapper(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
