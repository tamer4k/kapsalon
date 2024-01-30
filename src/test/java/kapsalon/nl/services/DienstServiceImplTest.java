package kapsalon.nl.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.entity.Category;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.repo.DienstRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class DienstServiceImplTest {

    @Mock
    private DienstRepository dienstRepository;

    @InjectMocks
    private DienstServiceImpl dienstService;


    @AfterEach
    void tearDown() {
    }
    private Dienst createSampleDienst() {
        Dienst dienst = new Dienst();
        dienst.setId(1L);
        dienst.setCategory(Category.Men);
        dienst.setTitle("Kniipbeurt");
        dienst.setDescription("Standaard knipbeurt");
        dienst.setPrice(20.0);
        dienst.setDuration(30);
        return dienst;
    }
    private DienstDTO createSampleDienstDTO() {
        DienstDTO dto = new DienstDTO();
        dto.setId(1L);
        dto.setCategory("Men");
        dto.setTitle("Kniipbeurt");
        dto.setDescription("Standaard knipbeurt");
        dto.setPrice(20.0);
        dto.setDuration(30);
        return dto;
    }
    @Test
    void getAllDiensten() {
        // Arrange
        List<Dienst> entityList = new ArrayList<>();
        Dienst dienst = createSampleDienst();
        entityList.add(dienst);
        when(dienstRepository.findAll()).thenReturn(entityList);

        // Act
        List<DienstDTO> result = dienstService.getAllDiensten();

        // Assert
        assertFalse(result.isEmpty());
        assertEquals(1, entityList.size());
    }

    @Test
    void getDienstById() {

        // Arrange
        Dienst dienst = createSampleDienst();
        when(dienstRepository.findById(createSampleDienst().getId())).thenReturn(Optional.of(dienst));

        // Act
        DienstDTO dienstDTO = dienstService.getDienstById(createSampleDienst().getId());

        // Assert
        assertNotNull(dienstDTO);
        assertEquals(1L,dienstDTO.getId());
        assertEquals("Men",dienstDTO.getCategory());
        assertEquals("Kniipbeurt",dienstDTO.getTitle());
        assertEquals("Standaard knipbeurt",dienstDTO.getDescription());
        assertEquals(20.0,dienstDTO.getPrice());
        assertEquals(30,dienstDTO.getDuration());
    }

    @Test
    void createDienst() {
        // Arrange
        DienstDTO dto = new DienstDTO();
        Dienst entity = new Dienst();
        when(dienstRepository.save(any(Dienst.class))).thenReturn(entity);

        // Act
        DienstDTO result = dienstService.createDienst(dto);

        // Assert
        assertNotNull(result);
    }

    @Test
    void updateDienst() {

        // Arrange
        Dienst existingDienst = createSampleDienst();
        Mockito.when(dienstRepository.findById(1L)).thenReturn(Optional.of(existingDienst));
        Mockito.when(dienstRepository.save(any())).thenReturn(existingDienst);

        DienstDTO updatedDienstDTO = createSampleDienstDTO();
        updatedDienstDTO.setCategory("Men");
        updatedDienstDTO.setTitle("Nieuwe titel");
        updatedDienstDTO.setDescription("Nieuwe beschrijving");
        updatedDienstDTO.setPrice(25.0);
        updatedDienstDTO.setDuration(45);

        // Act
        DienstDTO result = dienstService.updateDienst(1L, updatedDienstDTO);
        // Assert
        assertEquals(updatedDienstDTO.getCategory(), result.getCategory());
        assertEquals(updatedDienstDTO.getTitle(), result.getTitle());
        assertEquals(updatedDienstDTO.getDescription(), result.getDescription());
        assertEquals(updatedDienstDTO.getPrice(), result.getPrice());
        assertEquals(updatedDienstDTO.getDuration(), result.getDuration());
    }

    @Test
    void deleteDienst() {

        // Arrange
        Dienst dienst = createSampleDienst();
        Mockito.when(dienstRepository.findById(1L)).thenReturn(Optional.of(dienst));

        // Act
        dienstService.deleteDienst(1L);

        // Assert
        Mockito.verify(dienstRepository, Mockito.times(1)).delete(dienst);
    }

    @Test
    void fromEntityToDto() {
        // Arrange
        Dienst dienst = createSampleDienst();

        // Act
        DienstDTO dto = DienstServiceImpl.fromEntityToDto(dienst);

        // Assert
        assertEquals(dienst.getId(), dto.getId());
        assertEquals(dienst.getCategory().getDisplayName(), dto.getCategory());
        assertEquals(dienst.getTitle(), dto.getTitle());
        assertEquals(dienst.getDescription(), dto.getDescription());
        assertEquals(dienst.getPrice(), dto.getPrice());
        assertEquals(dienst.getDuration(), dto.getDuration());
    }

    @Test
    void fromDtoToEntity() {
        // Arrange
        DienstDTO dto = createSampleDienstDTO();

        // Act
        Dienst dienst = DienstServiceImpl.fromDtoToEntity(dto);

        // Assert
        assertEquals(dto.getId(), dienst.getId());
        assertEquals(Category.valueOf(dto.getCategory()), dienst.getCategory());
        assertEquals(dto.getTitle(), dienst.getTitle());
        assertEquals(dto.getDescription(), dienst.getDescription());
        assertEquals(dto.getPrice(), dienst.getPrice());
        assertEquals(dto.getDuration(), dienst.getDuration());
    }

}