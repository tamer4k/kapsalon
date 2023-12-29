package kapsalon.nl.services;

import kapsalon.nl.models.dto.KapsalonDTO;
import kapsalon.nl.models.dto.KlantDTO;
import kapsalon.nl.models.entity.Kapsalon;
import kapsalon.nl.models.entity.Klant;
import kapsalon.nl.repo.KapsalonRepository;
import kapsalon.nl.repo.KlantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KlantServiceImpl implements KlantService{
    private final KlantRepository klantRepository;

    public KlantServiceImpl (KlantRepository klantRepository){
        this.klantRepository = klantRepository;
    }
    @Override
    public List<KlantDTO> getAllKlanten() {

        List<Klant> entityList = klantRepository.findAll();
        List<KlantDTO> dtoList = new ArrayList<>();
        for (Klant entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public KlantDTO getKlantById(Long id) {
        Optional<Klant> entity = klantRepository.findById(id);
        KlantDTO dto;
        if (entity.isPresent()) {
            dto = fromEntityToDto(entity.get());

            return dto;
        }
        return  null;
    }


    @Override
    public KlantDTO createKlant(KlantDTO dto) {
        Klant entity = klantRepository.save(fromDtoToEntity(dto));
        return fromEntityToDto(entity);
    }

    @Override
    public KlantDTO updateKlant(Long id, KlantDTO dto) {
        Optional<Klant> entityId = klantRepository.findById(id);
        if (entityId.isPresent()) {
            Klant entity = entityId.get();
            entity.setFirstName(dto.getFirstName());
            entity.setSecondName(dto.getSecondName());
            entity.setEmail(dto.getEmail());
            entity.setPhoneNumber(dto.getPhoneNumber());

        }
        return null;
    }

    @Override
    public void deleteKlant(Long id) {
        klantRepository.deleteById(id);
    }

    public static KlantDTO fromEntityToDto(Klant entity){
        KlantDTO dto = new KlantDTO();
        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        return  dto;
    }

    public static Klant fromDtoToEntity(KlantDTO dto) {
        Klant entity = new Klant();

        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setSecondName(dto.getSecondName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        return entity;
    }
}
