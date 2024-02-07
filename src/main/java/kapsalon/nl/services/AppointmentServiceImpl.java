package kapsalon.nl.services;
import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.AppointmentDTO;
import kapsalon.nl.models.dto.UpdateAppointmentByOwnerDTO;
import kapsalon.nl.models.entity.*;
import kapsalon.nl.repo.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final UserRepository userRepository;
    private final KapsalonRepository kapsalonRepository;
    private final DienstRepository dienstRepository;
    private final PdfService pdfService;
    public AppointmentServiceImpl (AppointmentRepository appointmentRepository, BarberRepository barberRepository, UserRepository userRepository, KapsalonRepository kapsalonRepository, DienstRepository dienstRepository, PdfService pdfService){
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
        this.userRepository = userRepository;
        this.kapsalonRepository = kapsalonRepository;
        this.dienstRepository =dienstRepository;
        this.pdfService = pdfService;
    }


//    @Override
//    public byte[] generatePdfForAppointment(Long id) throws IOException {
//        // Haal de ingelogde gebruikersnaam op
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String loggedInUsername = authentication.getName();
//
//        // Zoek de ingelogde gebruiker op
//        User loggedInUser = userRepository.findByUsername(loggedInUsername)
//                .orElseThrow(() -> new RecordNotFoundException("Ingelogde gebruiker niet gevonden: " + loggedInUsername));
//
//        // Zoek de afspraak op basis van het opgegeven ID
//        AppointmentDTO appointmentDTO = getAppointmentById(id);
//
//        // Controleer of de afspraak bestaat
//        if (appointmentDTO != null) {
//            // Controleer of de ingelogde gebruiker de klant is van de afspraak
//            if (!appointmentDTO.getUser().getUsername().equals(loggedInUsername)) {
//                throw new AccessDeniedException("U heeft geen toestemming om deze afspraak te bekijken.");
//            }else {
//                // Controleer of de ingelogde gebruiker de eigenaar is van de afspraak
//                if(!appointmentDTO.getSelectedKapsalon().getOwner().equals(loggedInUsername)) {
//                    throw new AccessDeniedException("U heeft geen toestemming om deze afspraak te bekijken.");
//                }
//                // Genereer de PDF voor de afspraak en retourneer deze
//                return pdfService.generatePdf(appointmentDTO);
//            }
//
//
//        } else {
//            throw new RecordNotFoundException("Appointment not found with id: " + id);
//        }
//    }

    @Override
    public byte[] generatePdfForAppointment(Long id) throws IOException {
        // Haal de ingelogde gebruikersnaam op
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        // Zoek de afspraak op basis van het opgegeven ID
        AppointmentDTO appointmentDTO = getAppointmentById(id);

        // Controleer of de afspraak bestaat
        if (appointmentDTO != null) {
            // Controleer of de ingelogde gebruiker de eigenaar is van de afspraak
            if (!appointmentDTO.getUser().getUsername().equals(loggedInUsername)) {
                throw new AccessDeniedException("U heeft geen toestemming om deze afspraak te bekijken.");
            }

            // Genereer de PDF voor de afspraak en retourneer deze
            return pdfService.generatePdf(appointmentDTO);
        } else {
            throw new RecordNotFoundException("Appointment not found with id: " + id);
        }
    }
    public void savePdfToFileSystem(byte[] pdfBytes, String filePath) throws IOException {
        Files.write(Paths.get(filePath), pdfBytes);
    }
    @Override
    public List<AppointmentDTO> getAllAppointment() {

        List<Appointment> entityList = appointmentRepository.findAll();
        List<AppointmentDTO> dtoList = new ArrayList<>();
        for (Appointment entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public AppointmentDTO getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Appointment not found with id: " + id));
            return fromEntityToDto(appointment);

    }

    @Override
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        // Zoek de kapsalon op basis van de geselecteerde kapsalon-ID
        Kapsalon kapsalon = kapsalonRepository.findById(dto.getSelectedKapsalon().getId())
                .orElseThrow(() -> new RecordNotFoundException("Kapsalon not found with id: " + dto.getSelectedKapsalon().getId()));

        // Zoek de kapper op basis van de geselecteerde kapper-ID
        Barber kapper = barberRepository.findById(dto.getSelectedBarber().getId())
                .orElseThrow(() -> new RecordNotFoundException("Barber not found with id: " + dto.getSelectedBarber().getId()));

        // Controleer of de kapper beschikbaar is
        if (!kapper.isAvailable()) {
            throw new AccessDeniedException("De geselecteerde kapper is niet beschikbaar.");
        }

        // Controleer of de kapper bij de geselecteerde kapsalon werkt
        if (!kapper.getKapsalon().getId().equals(kapsalon.getId())) {
            throw new AccessDeniedException("De geselecteerde kapper werkt niet bij de geselecteerde kapsalon.");
        }

        // Zoek de dienst op basis van de geselecteerde dienst-ID
        Dienst dienst = dienstRepository.findById(dto.getSelectedDienst().getId())
                .orElseThrow(() -> new RecordNotFoundException("Dienst not found with id: " + dto.getSelectedDienst().getId()));

        // Controleer of de geselecteerde kapper de geselecteerde dienst aanbiedt
        if (!kapper.getDiensten().contains(dienst)) {
            throw new AccessDeniedException("De geselecteerde kapper biedt de geselecteerde dienst niet aan.");
        }

        // Haal de ingelogde gebruikersnaam op
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        // Zoek de ingelogde gebruiker op
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RecordNotFoundException("Ingelogde gebruiker niet gevonden: " + loggedInUsername));

        // Maak een nieuwe afspraakentiteit
        Appointment entity = fromDtoToEntity(dto);
        entity.setSelectedKapsalon(kapsalon);
        entity.setSelectedBarber(kapper);
        entity.setSelectedDienst(dienst);
        entity.setUser(loggedInUser);

        // Stel de standaardstatus in op "PENDING"
        entity.setStatus(Status.PENDING);

        // Stel de standaar isPaid in op false
        entity.setPaid(false);

        // Sla de afspraak op
        Appointment savedAppointment = appointmentRepository.save(entity);

        // Converteer de opgeslagen afspraak terug naar een DTO en retourneer deze
        return fromEntityToDto(savedAppointment);
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        // Zoek de afspraak op basis van het opgegeven ID
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Appointment not found with id: " + id));

        // Haal de huidige ingelogde gebruiker op
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        // Zoek de ingelogde gebruiker op
        User loggedInUser = userRepository.findByUsername(loggedInUsername)
                .orElseThrow(() -> new RecordNotFoundException("Ingelogde gebruiker niet gevonden: " + loggedInUsername));

        // Controleer of de ingelogde gebruiker overeenkomt met de klant van de bestaande afspraak
        if (!existingAppointment.getUser().getUsername().equals(loggedInUser.getUsername())) {
            throw new AccessDeniedException("U heeft geen toestemming om deze afspraak bij te werken. check je afspraak ID");
        }

        // Zoek de kapsalon op basis van de geselecteerde kapsalon-ID
        Kapsalon kapsalon = kapsalonRepository.findById(dto.getSelectedKapsalon().getId())
                .orElseThrow(() -> new RecordNotFoundException("Kapsalon not found with id: " + dto.getSelectedKapsalon().getId()));

        // Zoek de kapper op basis van de geselecteerde kapper-ID
        Barber kapper = barberRepository.findById(dto.getSelectedBarber().getId())
                .orElseThrow(() -> new RecordNotFoundException("Barber not found with id: " + dto.getSelectedBarber().getId()));

        // Controleer of de kapper beschikbaar is
        if (!kapper.isAvailable()) {
            throw new AccessDeniedException("De geselecteerde kapper is niet beschikbaar.");
        }

        // Controleer of de kapper bij de geselecteerde kapsalon werkt
        if (!kapper.getKapsalon().getId().equals(kapsalon.getId())) {
            throw new AccessDeniedException("De geselecteerde kapper werkt niet bij de geselecteerde kapsalon.");
        }

        // Zoek de dienst op basis van de geselecteerde dienst-ID
        Dienst dienst = dienstRepository.findById(dto.getSelectedDienst().getId())
                .orElseThrow(() -> new RecordNotFoundException("Dienst not found with id: " + dto.getSelectedDienst().getId()));

        // Controleer of de geselecteerde kapper de geselecteerde dienst aanbiedt
        if (!kapper.getDiensten().contains(dienst)) {
            throw new AccessDeniedException("De geselecteerde kapper biedt de geselecteerde dienst niet aan.");
        }

        // Update de gegevens van de bestaande afspraak met de nieuwe DTO-gegevens
        existingAppointment.setAppointmentDate(dto.getAppointmentDate());
        existingAppointment.setAppointmentTime(dto.getAppointmentTime());
        existingAppointment.setSelectedKapsalon(kapsalon);
        existingAppointment.setSelectedBarber(kapper);
        existingAppointment.setSelectedDienst(dienst);
        existingAppointment.setUser(loggedInUser);
        existingAppointment.setPaid(dto.isPaid()); // afhankelijk van je DTO

        // Sla de bijgewerkte afspraak op
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);

        // Converteer de bijgewerkte afspraak terug naar een DTO en retourneer deze
        return fromEntityToDto(updatedAppointment);
    }

    @Override
    public AppointmentDTO updateAppointmentByOwner(Long id, UpdateAppointmentByOwnerDTO dto) {
        // Haal de ingelogde gebruikersnaam op
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInUsername = authentication.getName();

        // Zoek de kapsalons van de ingelogde gebruiker (eigenaar)
        Optional<Kapsalon> ownerKapsalons = kapsalonRepository.findByOwner(loggedInUsername);

        // Zoek de afspraak op basis van het opgegeven ID
        Appointment existingAppointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Appointment not found with id: " + id));

        // Controleer of de ingelogde gebruiker de eigenaar is van de kapsalon waar de afspraak toe behoort
        boolean isOwnerAppointment = ownerKapsalons.stream()
                .anyMatch(kapsalon -> kapsalon.getId().equals(existingAppointment.getSelectedKapsalon().getId()));

        if (!isOwnerAppointment) {
            throw new AccessDeniedException("U heeft geen toestemming om deze afspraak bij te werken. deze afspraak hoort niet bij je Kapsalon");
        }

        // Update de status en isPaid van de afspraak
        existingAppointment.setStatus(Status.valueOf(dto.getStatus()));
        existingAppointment.setPaid(dto.isPaid());

        // Sla de bijgewerkte afspraak op
        Appointment updatedAppointment = appointmentRepository.save(existingAppointment);

        // Converteer de bijgewerkte afspraak terug naar een DTO en retourneer deze
        return fromEntityToDto(updatedAppointment);
    }

    @Override
    public void deleteAppointment(Long id) {
       Appointment appointment= appointmentRepository.findById(id)
               .orElseThrow(() -> new RecordNotFoundException("Appointment not found with id: " + id));

            appointmentRepository.delete(appointment);
    }

    public  AppointmentDTO fromEntityToDto(Appointment entity){

        AppointmentDTO dto = new AppointmentDTO();

        dto.setId(entity.getId());
        dto.setSelectedKapsalon(entity.getSelectedKapsalon());
        dto.setSelectedDienst(entity.getSelectedDienst());
        dto.setSelectedBarber(entity.getSelectedBarber());
        dto.setAppointmentDate(entity.getAppointmentDate());
        dto.setAppointmentTime(entity.getAppointmentTime());
        dto.setUser(entity.getUser());
        dto.setStatus(entity.getStatus().getDisplayName());

        dto.setPaid(entity.isPaid());

        return  dto;
    }

    public static   Appointment fromDtoToEntity(AppointmentDTO dto) {

        Appointment entity = new Appointment();
        entity.setId(dto.getId());
        entity.setSelectedKapsalon(dto.getSelectedKapsalon());
        entity.setSelectedDienst(dto.getSelectedDienst());
        entity.setSelectedBarber(dto.getSelectedBarber());
        entity.setAppointmentDate(dto.getAppointmentDate());
        entity.setAppointmentTime(dto.getAppointmentTime());
//        entity.setUser(dto.getUser());
//        entity.setStatus(Status.valueOf(dto.getStatus()));
//        entity.setPaid(dto.isPaid());
        return entity;

    }

}
