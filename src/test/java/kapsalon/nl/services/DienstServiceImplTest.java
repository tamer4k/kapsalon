package kapsalon.nl.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.entity.Category;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.repo.DienstRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DienstServiceImplTest {

    @Mock
    private DienstRepository dienstRepository;

    @InjectMocks
    private DienstServiceImpl dienstService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllDiensten() {
        // Arrange
        List<Dienst> entityList = new ArrayList<>();
        when(dienstRepository.findAll()).thenReturn(entityList);

        // Act
        List<DienstDTO> result = dienstService.getAllDiensten();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getDienstById() {
        // Arrange
        Long id = 1L;
        Dienst dienst = new Dienst();
        when(dienstRepository.findById(id)).thenReturn(Optional.of(dienst));

        // Act
        DienstDTO result = dienstService.getDienstById(id);

        // Assert
        assertNotNull(result);
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
    @Test
    void updateDienst() {
        Dienst existingDienst = createSampleDienst();
        Mockito.when(dienstRepository.findById(1L)).thenReturn(Optional.of(existingDienst));
        Mockito.when(dienstRepository.save(any())).thenReturn(existingDienst);

        DienstDTO updatedDienstDTO = new DienstDTO();
        updatedDienstDTO.setCategory("Men");
        updatedDienstDTO.setTitle("Nieuwe titel");
        updatedDienstDTO.setDescription("Nieuwe beschrijving");
        updatedDienstDTO.setPrice(25.0);
        updatedDienstDTO.setDuration(45);

        DienstDTO result = dienstService.updateDienst(1L, updatedDienstDTO);

        assertEquals(updatedDienstDTO.getCategory(), result.getCategory());
        assertEquals(updatedDienstDTO.getTitle(), result.getTitle());
        assertEquals(updatedDienstDTO.getDescription(), result.getDescription());
        assertEquals(updatedDienstDTO.getPrice(), result.getPrice());
        assertEquals(updatedDienstDTO.getDuration(), result.getDuration());
    }

    @Test
    void deleteDienst() {
        Mockito.when(dienstRepository.findById(1L)).thenReturn(Optional.of(createSampleDienst()));

        dienstService.deleteDienst(1L);

        Mockito.verify(dienstRepository, Mockito.times(1)).delete(any());
    }

    @Test
    public void testDeleteDienstNotFound() {

        Mockito.when(dienstRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> dienstService.deleteDienst(1L));

        Mockito.verify(dienstRepository, Mockito.never()).delete(any());
    }
    @Test
    void fromEntityToDto() {
        // Maak een voorbeeld Dienst entity aan
        Dienst dienst = createSampleDienst();

        // Roep de methode aan om een DTO te krijgen
        DienstDTO dto = DienstServiceImpl.fromEntityToDto(dienst);

        // Voer asserties uit om te controleren of de DTO de verwachte waarden heeft
        assertEquals(dienst.getId(), dto.getId());
        assertEquals(dienst.getCategory().getDisplayName(), dto.getCategory());
        assertEquals(dienst.getTitle(), dto.getTitle());
        assertEquals(dienst.getDescription(), dto.getDescription());
        assertEquals(dienst.getPrice(), dto.getPrice());
        assertEquals(dienst.getDuration(), dto.getDuration());
    }

    @Test
    void fromDtoToEntity() {
        // Maak een voorbeeld DienstDTO aan
        DienstDTO dto = createSampleDienstDTO();

        // Roep de methode aan om een Dienst entity te krijgen
        Dienst dienst = DienstServiceImpl.fromDtoToEntity(dto);

        // Voer asserties uit om te controleren of het entity de verwachte waarden heeft
        assertEquals(dto.getId(), dienst.getId());
        assertEquals(Category.valueOf(dto.getCategory()), dienst.getCategory());
        assertEquals(dto.getTitle(), dienst.getTitle());
        assertEquals(dto.getDescription(), dienst.getDescription());
        assertEquals(dto.getPrice(), dienst.getPrice());
        assertEquals(dto.getDuration(), dienst.getDuration());
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
}