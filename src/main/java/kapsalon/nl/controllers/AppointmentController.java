package kapsalon.nl.controllers;

import kapsalon.nl.models.dto.AppointmentDTO;
import kapsalon.nl.services.AppointmentService;
import kapsalon.nl.services.PdfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

import java.io.IOException;
@RestController
@RequestMapping("/api/v1/afspraken")
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final PdfService pdfService;
    public AppointmentController(AppointmentService appointmentService, PdfService pdfService){
        this.appointmentService = appointmentService;

        this.pdfService = pdfService;
    }


    @GetMapping("/{id}/download-pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {
        try {
            AppointmentDTO appointmentDTO = appointmentService.getAppointmentById(id);

            if (appointmentDTO != null) {
                byte[] pdfBytes = pdfService.generatePdf(appointmentDTO);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "appointment.pdf");

                String fileName = "appointment_" + id + ".pdf";
                String filePath = "src/main/resources/invoices/" + fileName;

                // Opslaan als bestand in het project
                savePdfToFileSystem(pdfBytes, filePath);

                return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            // Handle exceptions appropriately, e.g., log the error
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void savePdfToFileSystem(byte[] pdfBytes, String filePath) throws IOException {
        Files.write(Paths.get(filePath), pdfBytes);
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> result = appointmentService.getAllAppointment();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAppointmentById(@PathVariable Long id) {

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
                return ResponseEntity.status(HttpStatus.CREATED).body(result);
            } else {

                String errorMessage = "de kapsalon moet wel een juiste kapper bevatten en de kapper moet wel een juiste dienst hebben, kijk naar kapper table om te kijken in welke kapsalon werkt hij of zij en welke diensten hij of zij biedt ";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomErrorResponse(errorMessage));

            }
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO dto) {
        AppointmentDTO result = appointmentService.updateAppointment(id, dto);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage.);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id) {

            appointmentService.deleteAppointment(id);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
