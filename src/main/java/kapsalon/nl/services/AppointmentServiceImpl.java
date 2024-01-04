package kapsalon.nl.services;
import kapsalon.nl.models.dto.AppointmentDTO;
import kapsalon.nl.models.entity.Appointment;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.Kapper;
import kapsalon.nl.repo.AppointmentRepository;
import kapsalon.nl.repo.KapperRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final KapperRepository kapperRepository;


    public AppointmentServiceImpl (AppointmentRepository appointmentRepository,KapperRepository kapperRepository){
        this.appointmentRepository = appointmentRepository;
        this.kapperRepository = kapperRepository;
    }
    @Override
    public List<AppointmentDTO> getAllAppointment() {

        List<Appointment> entityList = appointmentRepository.findAll();
        List<AppointmentDTO> dtoList = new ArrayList<>();
        for (Appointment entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {

        Optional<Appointment> entity = appointmentRepository.findById(id);
        AppointmentDTO dto;
        dto = fromEntityToDto(entity.get());

        return dto;
    }


    @Override
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        List<Kapper> kapperList = kapperRepository.findAll();

        for (Kapper k : kapperList) {
            if(dto.getKapper().getId().equals(k.getId())){
                List<Dienst> kapperDienstenList = k.getDiensten();
                for (Dienst kapperDienst: kapperDienstenList) {
                    if (dto.getDienst().getId().equals(kapperDienst.getId())) {

                        System.out.println("Kapper ID: " + k.getId() + ", Dienst ID: " + dto.getDienst().getId());
                        Appointment entity = appointmentRepository.save(fromDtoToEntity(dto));
                        return fromEntityToDto(entity);

                    }
                }
            }
        }
        return null;
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        Optional<Appointment> entityId = appointmentRepository.findById(id);
        if (entityId.isPresent()) {
            Appointment entity = entityId.get();

            entity.setAppointmentDate(dto.getAppointmentDate());
            entity.setAppointmentTime(dto.getAppointmentTime());
            entity.setKapper(dto.getKapper());
            Appointment updatedEntity = appointmentRepository.save(entity);

            return fromEntityToDto(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }

    public  AppointmentDTO fromEntityToDto(Appointment entity){

        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(entity.getId());
        dto.setAppointmentDate(entity.getAppointmentDate());
        dto.setAppointmentTime(entity.getAppointmentTime());
        dto.setKapper(entity.getKapper());
        dto.setDienst(entity.getDienst());

        return  dto;
    }

    public  Appointment fromDtoToEntity(@org.jetbrains.annotations.NotNull AppointmentDTO dto) {

        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setAppointmentDate(dto.getAppointmentDate());
        entity.setAppointmentTime(dto.getAppointmentTime());
        entity.setKapper(dto.getKapper());
        entity.setDienst(dto.getDienst());


        return entity;

    }


}
