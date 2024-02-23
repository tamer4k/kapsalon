package kapsalon.nl.controllers;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.AppointmentDTO;
import kapsalon.nl.models.dto.UpdateAppointmentByOwnerDTO;
import kapsalon.nl.services.AppointmentServiceImpl;
import kapsalon.nl.services.PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
@RestController
@RequestMapping("/api/v1/appointment")
public class AppointmentController {
    private final AppointmentServiceImpl appointmentService;
    private final PdfService pdfService;
    public AppointmentController(AppointmentServiceImpl appointmentService, PdfService pdfService){
        this.appointmentService = appointmentService;

        this.pdfService = pdfService;
    }


    @GetMapping("/{id}/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) throws IOException {

        byte[] pdfBytes = appointmentService.generatePdfForAppointment(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "appointment.pdf");
        if (pdfBytes != null) {
            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } else {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> result = appointmentService.getAllAppointment();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {

            AppointmentDTO result = appointmentService.getAppointmentById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);


    }


    @PostMapping
    public ResponseEntity<Object> createAppointment(@Validated @RequestBody AppointmentDTO dto , BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.put(error.getField(), error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errors);
        }else {
            AppointmentDTO result = appointmentService.createAppointment(dto);
            if (result != null) {

                URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                        .path("")
                        .buildAndExpand(result)
                        .toUri();
                return ResponseEntity.created(location).body(result);
            } else {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);

            }
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        AppointmentDTO result = appointmentService.updateAppointment(id, dto);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/owner/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointmentByOwner(@PathVariable Long id,@RequestBody UpdateAppointmentByOwnerDTO dto) {

            AppointmentDTO result = appointmentService.updateAppointmentByOwner(id, dto);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentDTO> deleteAppointment(@PathVariable Long id) {

            appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
