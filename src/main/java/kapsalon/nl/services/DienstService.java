package kapsalon.nl.services;

import kapsalon.nl.models.dto.DienstDTO;
import kapsalon.nl.models.entity.Dienst;

import java.util.List;

public interface DienstService {
   public List<DienstDTO> getAllDiensten();
    public DienstDTO getDienstById(Long id);
    public DienstDTO createDienst(DienstDTO dienstDTO);
    public DienstDTO updateDienst(Long id, DienstDTO dienstDTO);
    public void deleteDienst(Long id);
}