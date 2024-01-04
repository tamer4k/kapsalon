package kapsalon.nl.services;

import jakarta.persistence.EntityNotFoundException;
import kapsalon.nl.models.dto.KapperDTO;
import kapsalon.nl.models.entity.Kapper;
import kapsalon.nl.models.entity.Kapsalon;
import kapsalon.nl.repo.KapperRepository;
import kapsalon.nl.repo.KapsalonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KapperServiceImpl implements KapperService {
    private final KapperRepository kapperRepository;
    private final KapsalonRepository kapsalonRepository;
    public KapperServiceImpl (KapperRepository kapperRepository,KapsalonRepository kapsalonRepository){
        this.kapperRepository = kapperRepository;
        this.kapsalonRepository =kapsalonRepository;
    }
    @Override
    public List<KapperDTO> getAllKappers() {

        List<Kapper> entityList = kapperRepository.findAll();
        List<KapperDTO> dtoList = new ArrayList<>();
        for (Kapper entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public KapperDTO getKapperById(Long id) {

        Optional<Kapper> entity = kapperRepository.findById(id);
        KapperDTO dto;

        dto = fromEntityToDto(entity.get());

        return dto;
    }


    @Override
    public KapperDTO createKapper(KapperDTO dto) {
        Kapper entity = kapperRepository.save(fromDtoToEntity(dto));

        Kapsalon kapsalon = kapsalonRepository.findById(dto.getKapsalon().getId())
                .orElseThrow(() -> new EntityNotFoundException("Kapsalon not found with id: " + dto.getKapsalon().getId()));
        entity.setKapsalon(kapsalon);

        return fromEntityToDto(entity);
    }

    @Override
    public KapperDTO updateKapper(Long id, KapperDTO dto) {
        Optional<Kapper> entityId = kapperRepository.findById(id);
        if (entityId.isPresent()) {
            Kapper entity = entityId.get();

            entity.setName(dto.getName());
            entity.setAvailable(dto.isAvailable());
            entity.setLicense(dto.getLicense());
            entity.setKapsalon(dto.getKapsalon());

            Kapper updatedEntity = kapperRepository.save(entity);

            Kapsalon kapsalon = kapsalonRepository.findById(dto.getKapsalon().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Kapsalon not found with id: " + dto.getKapsalon().getId()));
            entity.setKapsalon(kapsalon);

            return fromEntityToDto(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteKapper(Long id) {
        kapperRepository.deleteById(id);
    }

    public static KapperDTO fromEntityToDto(Kapper entity){
        KapperDTO dto = new KapperDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAvailable(entity.isAvailable());
        dto.setLicense(entity.getLicense());
        dto.setKapsalon(entity.getKapsalon());
        dto.setDiensten(entity.getDiensten());
        return  dto;
    }

    public static Kapper fromDtoToEntity(KapperDTO dto) {
        Kapper entity = new Kapper();

        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAvailable(dto.isAvailable());
        entity.setLicense(dto.getLicense());
        entity.setKapsalon(dto.getKapsalon());
        entity.setDiensten(dto.getDiensten());
        return entity;
    }
}
