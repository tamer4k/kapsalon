package kapsalon.nl.services;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.KapsalonDTO;
import kapsalon.nl.models.entity.Kapsalon;
import kapsalon.nl.repo.KapsalonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class KapsalonServiceImpl implements KapsalonService {
    private final KapsalonRepository kapsalonRepository;

    public KapsalonServiceImpl (KapsalonRepository kapsalonRepository){
        this.kapsalonRepository = kapsalonRepository;
    }
    @Override
    public List<KapsalonDTO> getAllKapsalons() {

        List<Kapsalon> entityList = kapsalonRepository.findAll();
        List<KapsalonDTO> dtoList = new ArrayList<>();
        for (Kapsalon entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public KapsalonDTO getKapsalonById(Long id) {

        Kapsalon kapsalon  = kapsalonRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Kapsalon not found with id: " + id));

            return fromEntityToDto(kapsalon);

    }


    @Override
    public KapsalonDTO createKapsalon(KapsalonDTO dto) {
        Kapsalon entity = kapsalonRepository.save(fromDtoToEntity(dto));
        return fromEntityToDto(entity);
    }

    @Override
    public KapsalonDTO updateKapsalon(Long id, KapsalonDTO dto) {
        Kapsalon kapsalon = kapsalonRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Kapsalon not found with id: " + id));



        kapsalon.setName(dto.getName());
        kapsalon.setAvailability(dto.isAvailability());
        kapsalon.setLocation(dto.getLocation());
        kapsalon.setPostalCode(dto.getPostalCode());
        kapsalon.setOpeningHours(dto.getOpeningHours());


            Kapsalon updatedEntity = kapsalonRepository.save(kapsalon);
            return fromEntityToDto(updatedEntity);

    }

    @Override
    public void deleteKapsalon(Long id) {
        Kapsalon kapsalon = kapsalonRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Kapsalon not found with id: " + id));

            kapsalonRepository.delete(kapsalon);

    }

    public static KapsalonDTO fromEntityToDto(Kapsalon entity){
        KapsalonDTO dto = new KapsalonDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAvailability(entity.isAvailability());
        dto.setLocation(entity.getLocation());
        dto.setPostalCode(entity.getPostalCode());
        dto.setOpeningHours(entity.getOpeningHours());

        return  dto;
    }

    public static Kapsalon fromDtoToEntity(KapsalonDTO dto) {
        Kapsalon entity = new Kapsalon();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAvailability(dto.isAvailability());
        entity.setLocation(dto.getLocation());
        entity.setPostalCode(dto.getPostalCode());
        entity.setOpeningHours(dto.getOpeningHours());

        return entity;
    }
}
