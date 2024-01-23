package kapsalon.nl.services;

import jakarta.persistence.EntityNotFoundException;
import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.BarberDTO;
import kapsalon.nl.models.entity.Barber;
import kapsalon.nl.models.entity.Kapsalon;
import kapsalon.nl.repo.BarberRepository;
import kapsalon.nl.repo.KapsalonRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BarberServiceImpl implements BarberService {
    private final BarberRepository barberRepository;
    private final KapsalonRepository kapsalonRepository;
    public BarberServiceImpl(BarberRepository kapperRepository, KapsalonRepository kapsalonRepository){
        this.barberRepository = kapperRepository;
        this.kapsalonRepository =kapsalonRepository;
    }
    @Override
    public List<BarberDTO> getAllBarbers() {

        List<Barber> entityList = barberRepository.findAll();
        List<BarberDTO> dtoList = new ArrayList<>();
        for (Barber entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public BarberDTO getBarberById(Long id) {

        Barber entity = barberRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Barber found with ID: " + id));

        return fromEntityToDto(entity);


    }


    @Override
    public BarberDTO createBarber(BarberDTO dto) {
        Barber entity = barberRepository.save(fromDtoToEntity(dto));

        Kapsalon kapsalon = kapsalonRepository.findById(dto.getKapsalon().getId())
                .orElseThrow(() -> new EntityNotFoundException("Kapsalon not found with id: " + dto.getKapsalon().getId()));


        entity.setKapsalon(kapsalon);

        return fromEntityToDto(entity);
    }

    @Override
    public BarberDTO updateBarber(Long id, BarberDTO dto) {

        Barber entity = barberRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Barber found with ID: " + id));



            entity.setName(dto.getName());
            entity.setAvailable(dto.isAvailable());
            entity.setLicense(dto.getLicense());
            entity.setKapsalon(dto.getKapsalon());
            entity.setDiensten(dto.getDiensten());
            Barber updatedEntity = barberRepository.save(entity);

            Kapsalon kapsalon = kapsalonRepository.findById(dto.getKapsalon().getId())
                    .orElseThrow(() -> new RecordNotFoundException("Kapsalon not found with id: " + dto.getKapsalon().getId()));
            entity.setKapsalon(kapsalon);

            return fromEntityToDto(updatedEntity);
    }

    @Override
    public void deleteBarber(Long id) {

        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Barber found with ID: " + id));

            barberRepository.delete(barber);
    }

    public static BarberDTO fromEntityToDto(Barber entity){
        BarberDTO dto = new BarberDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAvailable(entity.isAvailable());
        dto.setLicense(entity.getLicense());
        dto.setKapsalon(entity.getKapsalon());
        dto.setDiensten(entity.getDiensten());
        return  dto;
    }

    public static Barber fromDtoToEntity(BarberDTO dto) {
        Barber entity = new Barber();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAvailable(dto.isAvailable());
        entity.setLicense(dto.getLicense());
        entity.setKapsalon(dto.getKapsalon());
        entity.setDiensten(dto.getDiensten());
        return entity;
    }
}
