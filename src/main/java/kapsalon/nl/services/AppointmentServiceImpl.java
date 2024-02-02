package kapsalon.nl.services;
import jakarta.persistence.EntityNotFoundException;
import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.AppointmentDTO;
import kapsalon.nl.models.entity.*;
import kapsalon.nl.repo.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final UserRepository userRepository;
    private final KapsalonRepository kapsalonRepository;
    private final DienstRepository dienstRepository;

    public AppointmentServiceImpl (AppointmentRepository appointmentRepository, BarberRepository barberRepository, UserRepository userRepository, KapsalonRepository kapsalonRepository, DienstRepository dienstRepository){
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
        this.userRepository = userRepository;
        this.kapsalonRepository = kapsalonRepository;
        this.dienstRepository =dienstRepository;
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
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Appointment not found with id: " + id));
            return fromEntityToDto(appointment);

    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO dto) {

        List<Kapsalon> kapsalonList = kapsalonRepository.findAll();
        for (Kapsalon kapsalonUitList : kapsalonList){
            if (dto.getSelectedKapsalon().getId().equals(kapsalonUitList.getId())){
                List<Barber> kapperList = barberRepository.findAll();
                for (Barber kapperUitList : kapperList) {
                    if(dto.getSelectedBarber().getId().equals(kapperUitList.getId()) && dto.getSelectedKapsalon().getId().equals(kapperUitList.getKapsalon().getId())){
                        List<Dienst> kapperDienstenList = kapperUitList.getDiensten();
                        for (Dienst kapperDienst: kapperDienstenList) {
                            if (dto.getSelectedDienst().getId().equals(kapperDienst.getId())) {

                                Appointment entity = appointmentRepository.save(fromDtoToEntity(dto));

                                Kapsalon kapsalon = kapsalonRepository.findById(dto.getSelectedKapsalon().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Kapsalon not found with id: " + dto.getSelectedKapsalon().getId()));
                                entity.setSelectedKapsalon(kapsalon);

                                Dienst dienst =dienstRepository.findById(dto.getSelectedDienst().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Dienst not found with id: " + dto.getSelectedDienst().getId()));
                                entity.setSelectedDienst(dienst);

                                Barber barber = barberRepository.findById(dto.getSelectedBarber().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Barber not found with id: " + dto.getSelectedBarber().getId()));
                                entity.setSelectedBarber(barber);

                                User user = userRepository.findById(dto.getUser().getUsername())
                                        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUser().getUsername()));
                                entity.setUser(user);

                                return fromEntityToDto(entity);
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {

        List<Kapsalon> kapsalonList = kapsalonRepository.findAll();
        for (Kapsalon kapsalonUitList : kapsalonList){
            if (dto.getSelectedKapsalon().getId().equals(kapsalonUitList.getId())){
                List<Barber> kapperList = barberRepository.findAll();
                for (Barber kapperUitList : kapperList) {
                    if(dto.getSelectedBarber().getId().equals(kapperUitList.getId()) && dto.getSelectedKapsalon().getId().equals(kapperUitList.getKapsalon().getId())){
                        List<Dienst> kapperDienstenList = kapperUitList.getDiensten();
                        for (Dienst kapperDienst: kapperDienstenList) {
                            if (dto.getSelectedDienst().getId().equals(kapperDienst.getId())) {

                                Optional<Appointment> entityId = appointmentRepository.findById(id);
                                if (entityId.isPresent()) {
                                    Appointment entity = entityId.get();

                                    entity.setAppointmentDate(dto.getAppointmentDate());
                                    entity.setAppointmentTime(dto.getAppointmentTime());
                                    entity.setSelectedKapsalon(dto.getSelectedKapsalon());
                                    entity.setSelectedDienst(dto.getSelectedDienst());
                                    entity.setSelectedBarber(dto.getSelectedBarber());
                                    entity.setUser(dto.getUser());
                                    Appointment updatedEntity = appointmentRepository.save(entity);

                                    Kapsalon kapsalon = kapsalonRepository.findById(dto.getSelectedKapsalon().getId())
                                            .orElseThrow(() -> new EntityNotFoundException("Kapsalon not found with id: " + dto.getSelectedKapsalon().getId()));
                                    updatedEntity.setSelectedKapsalon(kapsalon);

                                    Dienst dienst =dienstRepository.findById(dto.getSelectedDienst().getId())
                                            .orElseThrow(() -> new EntityNotFoundException("Dienst not found with id: " + dto.getSelectedDienst().getId()));
                                    updatedEntity.setSelectedDienst(dienst);

                                    Barber barber = barberRepository.findById(dto.getSelectedBarber().getId())
                                            .orElseThrow(() -> new EntityNotFoundException("Barber not found with id: " + dto.getSelectedBarber().getId()));
                                    updatedEntity.setSelectedBarber(barber);

                                    User user = userRepository.findById(dto.getUser().getUsername())
                                            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getUser().getUsername()));
                                    updatedEntity.setUser(user);

                                    return fromEntityToDto(updatedEntity);


                                }
                            }
                        }
                    }
                }
            }
        }

        return null;
    }

    @Override
    public void deleteAppointment(Long id) {
       Appointment appointment= appointmentRepository.findById(id)
               .orElseThrow(() -> new RecordNotFoundException("Appointment not found with id: " + id));

            appointmentRepository.delete(appointment);

    }

    public  AppointmentDTO fromEntityToDto(Appointment entity){

        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(entity.getId());
        dto.setSelectedKapsalon(entity.getSelectedKapsalon());
        dto.setSelectedDienst(entity.getSelectedDienst());
        dto.setSelectedBarber(entity.getSelectedBarber());
        dto.setAppointmentDate(entity.getAppointmentDate());
        dto.setAppointmentTime(entity.getAppointmentTime());
        dto.setUser(entity.getUser());
        dto.setPaid(entity.isPaid());

        return  dto;
    }

    public static   Appointment fromDtoToEntity(AppointmentDTO dto) {

        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setSelectedKapsalon(dto.getSelectedKapsalon());
        entity.setSelectedDienst(dto.getSelectedDienst());
        entity.setSelectedBarber(dto.getSelectedBarber());
        entity.setAppointmentDate(dto.getAppointmentDate());
        entity.setAppointmentTime(dto.getAppointmentTime());
        entity.setUser(dto.getUser());
        entity.setPaid(dto.isPaid());
        return entity;

    }

}
