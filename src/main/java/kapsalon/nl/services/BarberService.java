package kapsalon.nl.services;

import kapsalon.nl.models.dto.BarberDTO;

import java.util.List;

public interface BarberService {
    public List<BarberDTO> getAllBarbers();
    public BarberDTO getBarberById(Long id);
    public BarberDTO createBarber(BarberDTO kapperDTO);
    public BarberDTO updateBarber(Long id, BarberDTO barberDTO);
    public void deleteBarber(Long id);

}
