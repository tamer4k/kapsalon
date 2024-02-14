package kapsalon.nl.services;

import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.BarberDTO;
import kapsalon.nl.models.dto.KapsalonDTO;
import kapsalon.nl.models.entity.*;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.access.AccessDeniedException;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import kapsalon.nl.repo.BarberRepository;
import kapsalon.nl.repo.KapsalonRepository;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.security.core.context.SecurityContextHolder;
@ExtendWith(MockitoExtension.class)
class BarberServiceImplTest {
    @Mock
    private BarberRepository barberRepository;

    @Mock
    private KapsalonRepository kapsalonRepository;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private BarberServiceImpl barberService;


    @AfterEach
    void tearDown() {

    }

    @BeforeEach
    void setUp() {

    }

    private Barber createSampleBarber() {
        Barber barber = new Barber();
        barber.setId(1L);
        barber.setName("Ashraf");
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
        dto.setName("Ashraf");
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
        kapsalon.setName("AlkmaarKapsalon");
        kapsalon.setLocation("Sample Location");
        kapsalon.setPostalCode("12345");
        kapsalon.setAvailability(true);
        kapsalon.setOwner("Eddard");

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
        List<Barber> mockBarberList = new ArrayList<>();
        mockBarberList.add(createSampleBarber());

        when(barberRepository.findAll()).thenReturn(mockBarberList);

        // Act
        List<BarberDTO> result = barberService.getAllBarbers();

        // Assert
        assertEquals(mockBarberList.size(), result.size());
        assertEquals(mockBarberList.get(0).getName(), result.get(0).getName());
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
        assertEquals("Ashraf", result.getName());
        assertEquals("123ABC", result.getLicense());
        assertEquals(true, result.getKapsalon().isAvailability());
        assertEquals(2, result.getDiensten().size());
        assertEquals(1L, result.getKapsalon().getId());

    }


@Test
void createBarber() {
    // Arrange
    String loggedInUsername = "Eddard";
    Kapsalon kapsalon = createSampleKapsalon();
    when(kapsalonRepository.findAllByOwner(loggedInUsername)).thenReturn(Collections.singletonList(kapsalon));
    BarberDTO barberDTO = createSampleBarberDTO();
    barberDTO.getKapsalon().setId(kapsalon.getId());
    when(kapsalonRepository.findById(kapsalon.getId())).thenReturn(Optional.of(kapsalon));
    when(barberRepository.save(any())).thenReturn(new Barber());
    Authentication authentication = mock(Authentication.class);
    when(authentication.getName()).thenReturn(loggedInUsername);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // Act
    BarberDTO result = barberService.createBarber(barberDTO);

    // Assert
    assertNotNull(result);

}

    @Test
    void createBarber_WithoutKapsalon() {
        // Arrange
        String loggedInUsername = "Eddard";
        when(kapsalonRepository.findAllByOwner(loggedInUsername)).thenReturn(Collections.emptyList());

        BarberDTO barberDTO = createSampleBarberDTO();
        Kapsalon kapsalon = createSampleKapsalon();
        barberDTO.setKapsalon(kapsalon);

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(loggedInUsername);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act & Assert
        org.springframework.security.access.AccessDeniedException exception = assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> barberService.createBarber(barberDTO));
        assertEquals("Only owners with kapsalons can add barbers. If you don't have a kapsalon yet, please create one.", exception.getMessage());
    }

    @Test
    void createBarber_WithDifferentOwnerKapsalon() {
        // Arrange
        String loggedInUsername = "Eddard";


        List<Kapsalon> ownerKapsalons = new ArrayList<>();
        Kapsalon otherOwnerKapsalon = new Kapsalon();
        otherOwnerKapsalon.setId(2L);
        ownerKapsalons.add(otherOwnerKapsalon);
        when(kapsalonRepository.findAllByOwner(loggedInUsername)).thenReturn(ownerKapsalons);


        BarberDTO barberDTO = createSampleBarberDTO();
        Kapsalon kapsalon = createSampleKapsalon();
        barberDTO.setKapsalon(kapsalon);


        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(loggedInUsername);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act & Assert
        org.springframework.security.access.AccessDeniedException exception = assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> barberService.createBarber(barberDTO));
        assertEquals("You can only add barbers to your own kapsalon. check what your kapsalon ID is.", exception.getMessage());
    }
    @Test
    void updateBarber() {


        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("Eddard");
        SecurityContextHolder.getContext().setAuthentication(authentication);


        BarberDTO updatedBarberDTO = createSampleBarberDTO();
        Barber barber = createSampleBarber();
        when(barberRepository.findById(anyLong())).thenReturn(Optional.of(barber));
        when(kapsalonRepository.findAllByOwner("Eddard")).thenReturn(Collections.singletonList(barber.getKapsalon()));
        when(barberRepository.save(any())).thenReturn(createSampleBarber());

        // Act
        BarberDTO result = barberService.updateBarber(1L, updatedBarberDTO);

        // Assert
        assertNotNull(result);
        assertEquals(updatedBarberDTO.getName(), result.getName());
    }
    @Test
    void updateBarber_WithDifferentKapsalon() {
        // Arrange
        String loggedInUsername = "Eddard";
        BarberDTO updatedBarberDTO = createSampleBarberDTO();
        Barber barber = createSampleBarber();

        when(barberRepository.findById(anyLong())).thenReturn(Optional.of(barber));
        when(kapsalonRepository.findAllByOwner(loggedInUsername)).thenReturn(Collections.emptyList());

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(loggedInUsername);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Act & Assert
        org.springframework.security.access.AccessDeniedException exception = assertThrows(org.springframework.security.access.AccessDeniedException.class, () -> barberService.updateBarber(1L, updatedBarberDTO));
        assertEquals("You can only update barbers in your own kapsalons. check the Barber ID", exception.getMessage());
    }
    @Test
    void updateBarber_KapsalonNotFound() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("loggedInUser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String loggedInUsername = "loggedInUser";
        Long barberId = 1L;
        BarberDTO updatedBarberDTO = createSampleBarberDTO();
        Barber barber = new Barber();
        barber.setKapsalon(new Kapsalon());
        when(authentication.getName()).thenReturn(loggedInUsername);
        when(kapsalonRepository.findAllByOwner(loggedInUsername)).thenReturn(Collections.emptyList()); // Geen kapsalons van de eigenaar
        when(barberRepository.findById(barberId)).thenReturn(Optional.of(barber));

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> barberService.updateBarber(barberId, updatedBarberDTO));
    }


    @Test
    void updateBarber_BarberNotBelongToOwnerKapsalon() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("Eddard");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String loggedInUsername = "Eddard";
        Long barberId = 1L;
        BarberDTO updatedBarberDTO = createSampleBarberDTO();
        Barber barber = createSampleBarber();
        Kapsalon kapsalon = new Kapsalon();
        kapsalon.setId(999L);
        barber.setKapsalon(kapsalon);
        when(authentication.getName()).thenReturn(loggedInUsername);
        when(kapsalonRepository.findAllByOwner(loggedInUsername)).thenReturn(Collections.singletonList(createSampleKapsalon()));
        when(barberRepository.findById(barberId)).thenReturn(Optional.of(barber));

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> barberService.updateBarber(barberId, updatedBarberDTO));
    }

    @Test
    void deleteBarber() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("Eddard");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        long id = 1L;
        Barber barber = createSampleBarber();
        when(barberRepository.findById(id)).thenReturn(Optional.of(barber));
        when(kapsalonRepository.findAllByOwner("Eddard")).thenReturn(Collections.singletonList(barber.getKapsalon()));

        // Act & Assert
        assertDoesNotThrow(() -> barberService.deleteBarber(id));
        verify(barberRepository, times(1)).delete(barber);
    }

    @Test
    void deleteBarber_BarberDoesNotExist() {
        // Arrange
        Long nonExistingBarberId = 999L;
        when(barberRepository.findById(nonExistingBarberId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RecordNotFoundException.class, () -> barberService.deleteBarber(nonExistingBarberId));
        verify(barberRepository, never()).delete(any(Barber.class));
    }

    @Test
    void deleteBarber_BarberNotBelongsToOwnerKapsalon() {
        // Arrange
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("loggedInUser");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String loggedInUsername = "loggedInUser";
        Long barberId = 1L;
        Barber barber = new Barber();
        barber.setKapsalon(new Kapsalon());
        when(authentication.getName()).thenReturn(loggedInUsername);
        when(kapsalonRepository.findAllByOwner(loggedInUsername)).thenReturn(Collections.emptyList());
        when(barberRepository.findById(barberId)).thenReturn(Optional.of(barber));

        // Act & Assert
        assertThrows(AccessDeniedException.class, () -> barberService.deleteBarber(barberId));
        verify(barberRepository, never()).delete(any(Barber.class));
    }
    @Test
    void testFindAvailableBarbers() {
        // Arrange
        List<Barber> availableBarbers = new ArrayList<>();
        Barber barber1 = createSampleBarber();
        availableBarbers.add(barber1);

        when(barberRepository.findByAvailableTrue()).thenReturn(availableBarbers);

        // Act
        List<BarberDTO> result = barberService.findAvailableBarbers();

        // Assert
        assertNotNull(result);
        assertEquals(availableBarbers.size(), result.size());

    }
}