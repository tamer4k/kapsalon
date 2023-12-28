package kapsalon.nl.services;

import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.entity.Category;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.repo.DienstRepository;
import kapsalon.nl.repo.KapsalonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DienstServiceImpl implements DienstService {

    private final DienstRepository dienstRepository;

    public DienstServiceImpl (DienstRepository dienstRepository){
        this.dienstRepository = dienstRepository;
    }
    @Override
    public List<DienstDTO> getAllDiensten() {

        List<Dienst> entityList = dienstRepository.findAll();
        List<DienstDTO> dtoList = new ArrayList<>();
        for (Dienst entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public DienstDTO getDienstById(Long id) {

        Optional<Dienst> entity = dienstRepository.findById(id);
        DienstDTO dto;

        dto = fromEntityToDto(entity.get());

        return dto;
    }


    @Override
    public DienstDTO createDienst(DienstDTO dto) {
        Dienst entity = dienstRepository.save(fromDtoToEntity(dto));
        return fromEntityToDto(entity);
    }

    @Override
    public DienstDTO updateDienst(Long id, DienstDTO dto) {
        Optional<Dienst> entityId = dienstRepository.findById(id);
        if (entityId.isPresent()) {
            Dienst entity = entityId.get();

            entity.setCategory(Category.valueOf(dto.getCategory()));
            entity.setTitle(dto.getTitle());
            entity.setDescription(dto.getDescription());
            entity.setPrice(dto.getPrice());
            entity.setDuration(dto.getDuration());
            entity.setKapper(dto.getKapper());
            Dienst updatedEntity = dienstRepository.save(entity);
            return fromEntityToDto(updatedEntity);
        }
        return null;
    }

    @Override
    public void deleteDienst(Long id) {
        dienstRepository.deleteById(id);
    }

    public static DienstDTO fromEntityToDto(Dienst entity){
        DienstDTO dto = new DienstDTO();
        dto.setId(entity.getId());
        dto.setCategory(entity.getCategory().getDisplayName());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());
        dto.setKapper(entity.getKapper());

        return  dto;
    }

    public static Dienst fromDtoToEntity(DienstDTO dto) {
        Dienst entity = new Dienst();

        entity.setId(dto.getId());
        entity.setCategory(Category.valueOf(dto.getCategory()));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());
        entity.setKapper(dto.getKapper());

        return entity;
    }

}