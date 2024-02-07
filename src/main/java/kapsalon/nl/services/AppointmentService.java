package kapsalon.nl.services;

import kapsalon.nl.models.dto.AppointmentDTO;
import kapsalon.nl.models.dto.UpdateAppointmentByOwnerDTO;

import java.util.List;

public interface AppointmentService {
    public List<AppointmentDTO> getAllAppointment();
    public AppointmentDTO getAppointmentById(Long id);
    public AppointmentDTO createAppointment(AppointmentDTO dto);
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto);
    public AppointmentDTO updateAppointmentByOwner(Long id, UpdateAppointmentByOwnerDTO dto);
    public void deleteAppointment(Long id);
}
