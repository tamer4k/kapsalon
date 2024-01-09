package kapsalon.nl.services;

import kapsalon.nl.models.dto.KapperDTO;

import java.util.List;

public interface KapperService {
    public List<KapperDTO> getAllKappers();
    public KapperDTO getKapperById(Long id);
    public KapperDTO createKapper(KapperDTO kapperDTO);
    public KapperDTO updateKapper(Long id, KapperDTO kapperDTO);
    public void deleteKapper(Long id);

}
