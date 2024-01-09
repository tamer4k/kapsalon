package kapsalon.nl.controllers;

import kapsalon.nl.models.dto.AppointmentDTO;
import kapsalon.nl.services.AppointmentService;
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
@RequestMapping("/api/v1/afspraken")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> result = appointmentService.getAllAppointment();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentDTO result = appointmentService.getAppointmentById(id);
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<?> createAppointment(@Validated @RequestBody AppointmentDTO dto , BindingResult bindingResult) {
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
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AppointmentDTO> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
