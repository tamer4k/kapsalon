package kapsalon.nl.services;

import kapsalon.nl.models.dto.BarberDTO;
import kapsalon.nl.models.entity.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import kapsalon.nl.repo.BarberRepository;
import kapsalon.nl.repo.KapsalonRepository;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class BarberServiceImplTest {
    @Mock
    private BarberRepository barberRepository;

    @Mock
    private KapsalonRepository kapsalonRepository;

    @InjectMocks
    private BarberServiceImpl barberService;


    @AfterEach
    void tearDown() {
    }
    private Barber createSampleBarber() {
        Barber barber = new Barber();
        barber.setId(1L);
        barber.setName("John Doe");
        barber.setLicense("123ABC");
        barber.setAvailable(true);


        List<Dienst> diensten = new ArrayList<>();
        Dienst dienst1 = new Dienst();
        dienst1.setId(1L);
        dienst1.setCategory(Category.Men);
        dienst1.setTitle("Haircut");
        dienst1.setDescription("Standard haircut");
        dienst1.setPrice(20.0);
        dienst1.setDuration(30);

        Dienst dienst2 = new Dienst();
        dienst2.setId(2L);
        dienst2.setCategory(Category.Women);
        dienst2.setTitle("Hair Styling");
        dienst2.setDescription("Professional hair styling");
        dienst2.setPrice(30.0);
        dienst2.setDuration(45);

        diensten.add(dienst1);
        diensten.add(dienst2);

        barber.setDiensten(diensten);


        Kapsalon kapsalon = createSampleKapsalon();
        barber.setKapsalon(kapsalon);

        return barber;
    }

    private BarberDTO createSampleBarberDTO() {
        BarberDTO dto = new BarberDTO();
        dto.setId(1L);
        dto.setName("John Doe");
        dto.setAvailable(true);
        dto.setLicense("123ABC");


        List<Dienst> diensten = new ArrayList<>();
        Dienst dienst1 = new Dienst();
        dienst1.setId(1L);
        dienst1.setCategory(Category.Men);
        dienst1.setTitle("Haircut");
        dienst1.setDescription("Standard haircut");
        dienst1.setPrice(20.0);
        dienst1.setDuration(30);

        Dienst dienst2 = new Dienst();
        dienst2.setId(2L);
        dienst2.setCategory(Category.Women);
        dienst2.setTitle("Hair Styling");
        dienst2.setDescription("Professional hair styling");
        dienst2.setPrice(30.0);
        dienst2.setDuration(45);


        diensten.add(dienst1);
        diensten.add(dienst2);

        dto.setDiensten(diensten);

        Kapsalon kapsalon = createSampleKapsalon();
        dto.setKapsalon(kapsalon);

        return dto;
    }

    private Kapsalon createSampleKapsalon() {
        Kapsalon kapsalon = new Kapsalon();
        kapsalon.setId(1L);
        kapsalon.setName("Sample Kapsalon");
        kapsalon.setLocation("Sample Location");
        kapsalon.setPostalCode("12345");
        kapsalon.setAvailability(true);
        kapsalon.setOwner("John Doe");

        OpeningHours openingHours = new OpeningHours();
        openingHours.setMonday("9:00 - 18:00");
        openingHours.setTuesday("9:00 - 18:00");
        openingHours.setWednesday("9:00 - 18:00");
        openingHours.setThursday("9:00 - 18:00");
        openingHours.setFriday("9:00 - 18:00");
        openingHours.setSunday("close");
        openingHours.setSunday("close");

        kapsalon.setOpeningHours(openingHours);

        return kapsalon;
    }
    @Test
    void getAllBarbers() {
        // Arrange
        List<Barber> entityList = new ArrayList<>();
        Barber barber = createSampleBarber();
        entityList.add(barber);
        when(barberRepository.findAll()).thenReturn(entityList);

        // Act
        List<BarberDTO> result = barberService.getAllBarbers();

        // Assert
        assertEquals(1, entityList.size());
        assertEquals(1, entityList.size());
    }

    @Test
    void getBarberById() {
        // Arrange
        Barber barber = createSampleBarber();
        when(barberRepository.findById(anyLong())).thenReturn(Optional.of(barber));

        // Act
        BarberDTO result = barberService.getBarberById(createSampleBarber().getId());

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("John Doe", result.getName());
        assertEquals("123ABC", result.getLicense());
        assertEquals(true, result.getKapsalon().isAvailability());
        assertEquals(2, result.getDiensten().size());
        assertEquals(1L, result.getKapsalon().getId());

    }

    @Test
    void createBarber() {
        // Arrange
        BarberDTO barberDTO = createSampleBarberDTO();
        when(barberRepository.save(any())).thenReturn(createSampleBarber());
        when(kapsalonRepository.findById(anyLong())).thenReturn(Optional.of(createSampleKapsalon()));

        // Act
        BarberDTO result = barberService.createBarber(barberDTO);

        // Assert
        assertNotNull(result);
    }

    @Test
    void updateBarber() {
        // Arrange
        BarberDTO updatedBarberDTO = createSampleBarberDTO();
        when(barberRepository.findById(anyLong())).thenReturn(Optional.of(createSampleBarber()));
        when(barberRepository.save(any())).thenReturn(createSampleBarber());
        when(kapsalonRepository.findById(anyLong())).thenReturn(Optional.of(createSampleKapsalon()));

        // Act
        BarberDTO result = barberService.updateBarber(1L, updatedBarberDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedBarberDTO.getName(), result.getName());
    }

    @Test
    void deleteBarber() {
        // Arrange
        Barber barber = createSampleBarber();
        Mockito.when(barberRepository.findById(1L)).thenReturn(Optional.of(barber));

        // Act
        barberService.deleteBarber(1L);

        // Assert
        Mockito.verify(barberRepository, Mockito.times(1)).delete(barber);
    }

}