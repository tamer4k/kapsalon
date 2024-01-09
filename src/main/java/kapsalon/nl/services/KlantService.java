package kapsalon.nl.services;

import kapsalon.nl.models.dto.KlantDTO;

import java.util.List;

public interface KlantService {
    public List<KlantDTO> getAllKlanten();
    public KlantDTO getKlantById(Long id);
    public KlantDTO createKlant(KlantDTO klantDTO);
    public KlantDTO updateKlant(Long id, KlantDTO klantDTO);
    public void deleteKlant(Long id);
}
