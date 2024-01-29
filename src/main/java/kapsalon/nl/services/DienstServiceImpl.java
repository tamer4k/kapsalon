package kapsalon.nl.services;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.dto.UserDto;
import kapsalon.nl.models.entity.Category;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.User;
import kapsalon.nl.repo.DienstRepository;
import org.springframework.http.ResponseEntity;
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

        Dienst dienst = dienstRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Dienst found with ID: " + id));

            return fromEntityToDto(dienst);

    }

    @Override
    public DienstDTO createDienst(DienstDTO dto) {
        Dienst entity = dienstRepository.save(fromDtoToEntity(dto));
        return fromEntityToDto(entity);
    }

    @Override
    public DienstDTO updateDienst(Long id, DienstDTO dto) {

        Dienst dienst = dienstRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Dienst found with ID: " + id));

        if (dto.getCategory() != null) {
            dienst.setCategory(Category.valueOf(dto.getCategory()));
        }

//        dienst.setCategory(Category.valueOf(dto.getCategory()));
        dienst.setTitle(dto.getTitle());
        dienst.setDescription(dto.getDescription());
        dienst.setPrice(dto.getPrice());
        dienst.setDuration(dto.getDuration());

            Dienst updatedEntity = dienstRepository.save(dienst);

            return fromEntityToDto(updatedEntity);
    }

    @Override
    public void deleteDienst(Long id) {
        Dienst dienst = dienstRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Dienst found with ID: " + id));

         dienstRepository.delete(dienst);


    }
    public static DienstDTO fromEntityToDto(Dienst entity){
        DienstDTO dto = new DienstDTO();
        dto.setId(entity.getId());
//        dto.setCategory(entity.getCategory().getDisplayName());
        Category category = entity.getCategory();
        dto.setCategory(category != null ? category.getDisplayName() : null);
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setDuration(entity.getDuration());

        return  dto;
    }

    public static Dienst fromDtoToEntity(DienstDTO dto) {
        Dienst entity = new Dienst();

        entity.setId(dto.getId());
        if (dto.getCategory() != null && !dto.getCategory().isEmpty()) {
            entity.setCategory(Category.valueOf(dto.getCategory()));
        }
//        entity.setCategory(Category.valueOf(dto.getCategory()));
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setDuration(dto.getDuration());

        return entity;
    }

}