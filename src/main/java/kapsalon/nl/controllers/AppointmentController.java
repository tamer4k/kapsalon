package kapsalon.nl.controllers;

import kapsalon.nl.exceptions.ErrorMessages;
import kapsalon.nl.exceptions.RecordNotFoundException;
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
    public ResponseEntity<Object> getAppointmentById(@PathVariable Long id) {
        try {
            AppointmentDTO result = appointmentService.getAppointmentById(id);
            return new ResponseEntity<>(result, HttpStatus.OK);

        }catch (RecordNotFoundException ex) {
            String errorMessage = ErrorMessages.APPOINTMENT_NOT_FOUND + id + " niet gevonden";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }
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
        try {
            appointmentService.deleteAppointment(id);
        }catch (RecordNotFoundException ex) {
            String errorMessage = ErrorMessages.APPOINTMENT_NOT_FOUND + id + " niet gevonden";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

        return null;
    }
}
