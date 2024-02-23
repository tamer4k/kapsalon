package kapsalon.nl.services;

import jakarta.persistence.EntityNotFoundException;
import kapsalon.nl.exceptions.RecordNotFoundException;
import kapsalon.nl.models.dto.BarberDTO;
import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.entity.Barber;
import kapsalon.nl.models.entity.Dienst;
import kapsalon.nl.models.entity.Kapsalon;
import kapsalon.nl.repo.BarberRepository;
import kapsalon.nl.repo.DienstRepository;
import kapsalon.nl.repo.KapsalonRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BarberServiceImpl implements BarberService {
    private final BarberRepository barberRepository;
    private final KapsalonRepository kapsalonRepository;
    private final DienstRepository dienstRepository;
    public BarberServiceImpl(BarberRepository kapperRepository, KapsalonRepository kapsalonRepository, DienstRepository dienstRepository){
        this.barberRepository = kapperRepository;
        this.kapsalonRepository =kapsalonRepository;
        this.dienstRepository = dienstRepository;
    }

    @Override
    public List<BarberDTO> findAvailableBarbers() {
        List<Barber> availableBarbers = barberRepository.findByAvailableTrue();
        return availableBarbers.stream().map(this::fromEntityToDto).collect(Collectors.toList());
    }

    @Override
    public List<BarberDTO> getAllBarbers() {

        List<Barber> entityList = barberRepository.findAll();
        List<BarberDTO> dtoList = new ArrayList<>();
        for (Barber entity : entityList) {
            dtoList.add(fromEntityToDto(entity));
        }
        return dtoList;
    }

    @Override
    public BarberDTO getBarberById(Long id) {

        Barber entity = barberRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Barber found with ID: " + id));

        return fromEntityToDto(entity);
    }

    @Override
    public BarberDTO createBarber(BarberDTO dto) {
        // Haal de ingelogde gebruikersnaam op
        String loggedInUsername = getLoggedInUsername();

        // Zoek alle kapsalons van de ingelogde gebruiker op
        List<Kapsalon> ownerKapsalons = kapsalonRepository.findAllByOwner(loggedInUsername);
        // Controleer of de lijst leeg is
        if (ownerKapsalons.isEmpty()) {
            throw new AccessDeniedException("Only owners with kapsalons can add barbers. If you don't have a kapsalon yet, please create one.");
        }

        // Controleer of de kapsalon in het DTO overeenkomt met een van de kapsalons van de eigenaar
        if (ownerKapsalons.stream().noneMatch(kapsalon -> kapsalon.getId().equals(dto.getKapsalon().getId()))) {
            throw new AccessDeniedException("You can only add barbers to your own kapsalon. check what your kapsalon ID is.");
        }

        // Controleer of alle diensten in het DTO bestaan
        for (Dienst dienst : dto.getDiensten()) {
            dienstRepository.findById(dienst.getId())
                    .orElseThrow(() -> new RecordNotFoundException("Dienst not found with id: " + dienst.getId()));
        }

        Barber entity = barberRepository.save(fromDtoToEntity(dto));

        Kapsalon kapsalon = kapsalonRepository.findById(dto.getKapsalon().getId())
                .orElseThrow(() -> new RecordNotFoundException("Kapsalon not found with id: " + dto.getKapsalon().getId()));
        entity.setKapsalon(kapsalon);

        return fromEntityToDto(entity);
    }


    @Override
    public BarberDTO updateBarber(Long id, BarberDTO dto) {

        // Haal de originele barber op
        Barber entity = barberRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Barber found with ID: " + id));

        // Haal de ingelogde gebruikersnaam op
        String loggedInUsername = getLoggedInUsername();

        // Zoek alle kapsalons van de ingelogde gebruiker op
        List<Kapsalon> ownerKapsalons = kapsalonRepository.findAllByOwner(loggedInUsername);

        // Controleer of de barber behoort tot een van de kapsalons van de eigenaar
        if (ownerKapsalons.stream().noneMatch(kapsalon -> kapsalon.getId().equals(entity.getKapsalon().getId()))) {
            throw new AccessDeniedException("You can only update barbers in your own kapsalons. check the Barber ID");
        }

        // Update de eigenschappen van de barber
        entity.setName(dto.getName());
        entity.setAvailable(dto.isAvailable());
        entity.setLicense(dto.getLicense());

        // Controleer of de kapsalon in het DTO overeenkomt met een van de kapsalons van de eigenaar
        Optional<Kapsalon> optionalKapsalon = ownerKapsalons.stream()
                .filter(kapsalon -> kapsalon.getId().equals(dto.getKapsalon().getId()))
                .findFirst();

        // Als de kapsalon niet wordt gevonden, gooi een AccessDeniedException
        if (optionalKapsalon.isEmpty()) {
            throw new AccessDeniedException("You can only update barbers in your own kapsalons. check the Kapsalon ID");
        }

        // Stel de nieuwe kapsalon in
        entity.setKapsalon(optionalKapsalon.get());
        entity.setDiensten(dto.getDiensten());

        // Sla de bijgewerkte barber op
        Barber updatedEntity = barberRepository.save(entity);

        // Geef het DTO-object terug
        return fromEntityToDto(updatedEntity);
    }


    @Override
    public void deleteBarber(Long id) {

        // Haal de originele barber op
        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No Barber found with ID: " + id));

        // Haal de ingelogde gebruikersnaam op
        String loggedInUsername = getLoggedInUsername();

        // Zoek alle kapsalons van de ingelogde gebruiker op
        List<Kapsalon> ownerKapsalons = kapsalonRepository.findAllByOwner(loggedInUsername);

        // Controleer of de barber behoort tot een van de kapsalons van de eigenaar
        if (ownerKapsalons.stream().noneMatch(kapsalon -> kapsalon.getId().equals(barber.getKapsalon().getId()))) {
            throw new AccessDeniedException("You can only delete barbers in your own kapsalons. check the Barber ID");
        }

        // Verwijder de barber
        barberRepository.delete(barber);
    }

    private String getLoggedInUsername() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    public  BarberDTO fromEntityToDto(Barber entity){

        BarberDTO dto = new BarberDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setAvailable(entity.isAvailable());
        dto.setLicense(entity.getLicense());
        dto.setKapsalon(entity.getKapsalon());
        dto.setDiensten(entity.getDiensten());
        return  dto;
    }

    public  Barber fromDtoToEntity(BarberDTO dto) {

        Barber entity = new Barber();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setAvailable(dto.isAvailable());
        entity.setLicense(dto.getLicense());
        entity.setKapsalon(dto.getKapsalon());
        entity.setDiensten(dto.getDiensten());
        return entity;
    }
}
