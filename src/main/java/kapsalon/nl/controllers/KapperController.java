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
        List<KapperDTO> result = kapperService.getAllKappers();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<KapperDTO> getKapperById(@PathVariable Long id) {
        KapperDTO result = kapperService.getKapperById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<KapperDTO> createKapper(@RequestBody KapperDTO dto) {

        KapperDTO result = kapperService.createKapper(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);

    }

    @PutMapping("/{id}")
    public ResponseEntity<KapperDTO> updateKapper(@PathVariable Long id, @RequestBody KapperDTO kapperDTO) {
        KapperDTO result = kapperService.updateKapper(id, kapperDTO);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
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
