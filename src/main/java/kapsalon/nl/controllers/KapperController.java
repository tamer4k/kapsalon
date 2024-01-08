package kapsalon.nl.controllers;

import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.dto.KapperDTO;
import kapsalon.nl.services.KapperService;
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
@RequestMapping("/api/v1/kappers")
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
    public ResponseEntity<Object> createKapper(@Validated @RequestBody KapperDTO  dto , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }else {
            KapperDTO result = kapperService.createKapper(dto);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        }
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
