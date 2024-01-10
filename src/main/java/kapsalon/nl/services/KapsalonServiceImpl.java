package kapsalon.nl.services;

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

        Optional<Kapsalon> entity = kapsalonRepository.findById(id);
        KapsalonDTO dto;
        if (entity.isPresent()) {
        dto = fromEntityToDto(entity.get());

        return dto;
        }
        return  null;
    }


    @Override
    public KapsalonDTO createKapsalon(KapsalonDTO dto) {
        Kapsalon entity = kapsalonRepository.save(fromDtoToEntity(dto));
        return fromEntityToDto(entity);
    }

    @Override
    public KapsalonDTO updateKapsalon(Long id, KapsalonDTO dto) {
        Optional<Kapsalon> entityId = kapsalonRepository.findById(id);
        if (entityId.isPresent()) {
            Kapsalon entity = entityId.get();

            entity.setName(dto.getName());
            entity.setAvailability(dto.isAvailability());
            entity.setLocation(dto.getLocation());
            entity.setPostalCode(dto.getPostalCode());
            entity.setOpeningHours(dto.getOpeningHours());


            Kapsalon updatedEntity = kapsalonRepository.save(entity);
            return fromEntityToDto(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteKapsalon(Long id) {
        kapsalonRepository.deleteById(id);
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
