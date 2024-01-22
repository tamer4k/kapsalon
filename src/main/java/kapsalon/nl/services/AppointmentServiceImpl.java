package kapsalon.nl.services;
import jakarta.persistence.EntityNotFoundException;
import kapsalon.nl.exceptions.ErrorMessages;
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
    private final CustomerRepository customerRepository;
    private final KapsalonRepository kapsalonRepository;
    private final DienstRepository dienstRepository;

    public AppointmentServiceImpl (AppointmentRepository appointmentRepository, BarberRepository barberRepository, CustomerRepository customerRepository, KapsalonRepository kapsalonRepository, DienstRepository dienstRepository){
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
        this.customerRepository = customerRepository;
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
        Optional<Appointment> entity = appointmentRepository.findById(id);
        if (entity.isPresent()) {
            return fromEntityToDto(entity.get());
        }else {
            throw new RecordNotFoundException(ErrorMessages.APPOINTMENT_NOT_FOUND + id);
        }
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO dto) {

        List<Kapsalon> kapsalonList = kapsalonRepository.findAll();
        for (Kapsalon kapsalonUitList : kapsalonList){
            if (dto.getKapsalon().getId().equals(kapsalonUitList.getId())){
                List<Barber> kapperList = barberRepository.findAll();
                for (Barber kapperUitList : kapperList) {
                    if(dto.getBarber().getId().equals(kapperUitList.getId()) && dto.getKapsalon().getId().equals(kapperUitList.getKapsalon().getId())){
                        List<Dienst> kapperDienstenList = kapperUitList.getDiensten();
                        for (Dienst kapperDienst: kapperDienstenList) {
                            if (dto.getDienst().getId().equals(kapperDienst.getId())) {

                                Appointment entity = appointmentRepository.save(fromDtoToEntity(dto));

                                Kapsalon kapsalon = kapsalonRepository.findById(dto.getKapsalon().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Kapsalon not found with id: " + dto.getKapsalon().getId()));
                                entity.setKapsalon(kapsalon);

                                Dienst dienst =dienstRepository.findById(dto.getDienst().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Dienst not found with id: " + dto.getDienst().getId()));
                                entity.setDienst(dienst);

                                Barber barber = barberRepository.findById(dto.getBarber().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Barber not found with id: " + dto.getBarber().getId()));
                                entity.setBarber(barber);

                                Customer customer = customerRepository.findById(dto.getCustomer().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getCustomer().getId()));
                                entity.setCustomer(customer);

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
            if (dto.getKapsalon().getId().equals(kapsalonUitList.getId())){
                List<Barber> kapperList = barberRepository.findAll();
                for (Barber kapperUitList : kapperList) {
                    if(dto.getBarber().getId().equals(kapperUitList.getId()) && dto.getKapsalon().getId().equals(kapperUitList.getKapsalon().getId())){
                        List<Dienst> kapperDienstenList = kapperUitList.getDiensten();
                        for (Dienst kapperDienst: kapperDienstenList) {
                            if (dto.getDienst().getId().equals(kapperDienst.getId())) {

                                Optional<Appointment> entityId = appointmentRepository.findById(id);
                                if (entityId.isPresent()) {
                                    Appointment entity = entityId.get();

                                    entity.setAppointmentDate(dto.getAppointmentDate());
                                    entity.setAppointmentTime(dto.getAppointmentTime());
                                    entity.setKapsalon(dto.getKapsalon());
                                    entity.setDienst(dto.getDienst());
                                    entity.setBarber(dto.getBarber());
                                    entity.setCustomer(dto.getCustomer());
                                    Appointment updatedEntity = appointmentRepository.save(entity);

                                    Kapsalon kapsalon = kapsalonRepository.findById(dto.getKapsalon().getId())
                                            .orElseThrow(() -> new EntityNotFoundException("Kapsalon not found with id: " + dto.getKapsalon().getId()));
                                    updatedEntity.setKapsalon(kapsalon);

                                    Dienst dienst =dienstRepository.findById(dto.getDienst().getId())
                                            .orElseThrow(() -> new EntityNotFoundException("Dienst not found with id: " + dto.getDienst().getId()));
                                    updatedEntity.setDienst(dienst);

                                    Barber barber = barberRepository.findById(dto.getBarber().getId())
                                            .orElseThrow(() -> new EntityNotFoundException("Barber not found with id: " + dto.getBarber().getId()));
                                    updatedEntity.setBarber(barber);

                                    Customer customer = customerRepository.findById(dto.getCustomer().getId())
                                            .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + dto.getCustomer().getId()));
                                    updatedEntity.setCustomer(customer);

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
    public AppointmentDTO deleteAppointment(Long id) {
        Optional<Appointment> entity = appointmentRepository.findById(id);
        if (entity.isPresent()) {
            appointmentRepository.deleteById(id);
        }else {

            throw new RecordNotFoundException(ErrorMessages.BARBER_NOT_FOUND + id);
        }
        return null;
    }

    public  AppointmentDTO fromEntityToDto(Appointment entity){

        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(entity.getId());
        dto.setKapsalon(entity.getKapsalon());
        dto.setDienst(entity.getDienst());
        dto.setBarber(entity.getBarber());
        dto.setAppointmentDate(entity.getAppointmentDate());
        dto.setAppointmentTime(entity.getAppointmentTime());
        dto.setCustomer(entity.getCustomer());

        return  dto;
    }

    public  Appointment fromDtoToEntity(AppointmentDTO dto) {

        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setKapsalon(dto.getKapsalon());
        entity.setDienst(dto.getDienst());
        entity.setBarber(dto.getBarber());
        entity.setAppointmentDate(dto.getAppointmentDate());
        entity.setAppointmentTime(dto.getAppointmentTime());
        entity.setCustomer(dto.getCustomer());

        return entity;

    }

}
