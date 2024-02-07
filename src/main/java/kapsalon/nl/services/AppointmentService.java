package kapsalon.nl.services;

import kapsalon.nl.models.dto.AppointmentDTO;
import kapsalon.nl.models.dto.UpdateAppointmentByOwnerDTO;

import java.io.IOException;
import java.util.List;

public interface AppointmentService {
    byte[] generatePdfForAppointment(Long id) throws IOException;

    public List<AppointmentDTO> getAllAppointment();
    public AppointmentDTO getAppointmentById(Long id);
    public AppointmentDTO createAppointment(AppointmentDTO dto);
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto);
    public AppointmentDTO updateAppointmentByOwner(Long id, UpdateAppointmentByOwnerDTO dto);
    public void deleteAppointment(Long id);
}
