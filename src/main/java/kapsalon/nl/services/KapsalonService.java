package kapsalon.nl.services;

import kapsalon.nl.models.dto.KapperDTO;
import kapsalon.nl.models.dto.KapsalonDTO;

import java.util.List;

public interface KapsalonService {
    public List<KapsalonDTO> getAllKapsalons();
    public KapsalonDTO getKapsalonById(Long id);
    public KapsalonDTO createKapsalon(KapsalonDTO kapsalonDTO);
    public KapsalonDTO updateKapsalon(Long id, KapsalonDTO kapsalonDTO);
    public void deleteKapsalon(Long id);
}
