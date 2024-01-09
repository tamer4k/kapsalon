package kapsalon.nl.services;
import jakarta.persistence.EntityNotFoundException;
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
    private final KapperRepository kapperRepository;
    private final KlantRepository klantRepository;
    private final KapsalonRepository kapsalonRepository;
    private final DienstRepository dienstRepository;

    public AppointmentServiceImpl (AppointmentRepository appointmentRepository,KapperRepository kapperRepository,KlantRepository klantRepository,KapsalonRepository kapsalonRepository,DienstRepository dienstRepository){
        this.appointmentRepository = appointmentRepository;
        this.kapperRepository = kapperRepository;
        this.klantRepository = klantRepository;
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
        AppointmentDTO dto;
        dto = fromEntityToDto(entity.get());

        return dto;
    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO dto) {

        List<Kapsalon> kapsalonList = kapsalonRepository.findAll();
        for (Kapsalon kapsalonUitList : kapsalonList){
            if (dto.getKapsalon().getId().equals(kapsalonUitList.getId())){
                List<Kapper> kapperList = kapperRepository.findAll();
                for (Kapper kapperUitList : kapperList) {
                    if(dto.getKapper().getId().equals(kapperUitList.getId()) && dto.getKapsalon().getId().equals(kapperUitList.getKapsalon().getId())){
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

                                Kapper kapper = kapperRepository.findById(dto.getKapper().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Kapper not found with id: " + dto.getKapper().getId()));
                                entity.setKapper(kapper);

                                Klant klant = klantRepository.findById(dto.getKlant().getId())
                                        .orElseThrow(() -> new EntityNotFoundException("Klant not found with id: " + dto.getKlant().getId()));
                                entity.setKlant(klant);

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
    public AppointmentDTO deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
        return null;
    }

    public  AppointmentDTO fromEntityToDto(Appointment entity){

        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(entity.getId());
        dto.setKapsalon(entity.getKapsalon());
        dto.setDienst(entity.getDienst());
        dto.setKapper(entity.getKapper());
        dto.setAppointmentDate(entity.getAppointmentDate());
        dto.setAppointmentTime(entity.getAppointmentTime());
        dto.setKlant(entity.getKlant());

        return  dto;
    }

    public  Appointment fromDtoToEntity(AppointmentDTO dto) {

        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setKapsalon(dto.getKapsalon());
        entity.setDienst(dto.getDienst());
        entity.setKapper(dto.getKapper());
        entity.setAppointmentDate(dto.getAppointmentDate());
        entity.setAppointmentTime(dto.getAppointmentTime());
        entity.setKlant(dto.getKlant());

        return entity;

    }

}
