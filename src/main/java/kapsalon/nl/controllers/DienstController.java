package kapsalon.nl.controllers;

import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.services.DienstService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/diensten")
public class DienstController {


    private final DienstService dienstService;

    public DienstController(DienstService dienstService){
      this.dienstService = dienstService;
    }

    @GetMapping
    public ResponseEntity<List<DienstDTO>> getAllDiensten() {
        List<DienstDTO> diensten = dienstService.getAllDiensten();
        return new ResponseEntity<>(diensten, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DienstDTO> getDienstById(@PathVariable Long id) {
        DienstDTO dienst = dienstService.getDienstById(id);
        if (dienst != null) {
            return new ResponseEntity<>(dienst, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<DienstDTO> createTelevision(@RequestBody DienstDTO dto) {

        DienstDTO createdTelevisionDTO = dienstService.createDienst(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTelevisionDTO);

    }

    @PutMapping("/{id}")
    public ResponseEntity<DienstDTO> updateDienst(@PathVariable Long id, @RequestBody DienstDTO dienstDTO) {
        DienstDTO updatedDienstDto = dienstService.updateDienst(id, dienstDTO);
        if (updatedDienstDto != null) {
            return new ResponseEntity<>(updatedDienstDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDienst(@PathVariable Long id) {
        dienstService.deleteDienst(id);

            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}