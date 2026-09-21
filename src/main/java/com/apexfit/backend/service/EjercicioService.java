package com.apexfit.backend.service;

import com.apexfit.backend.dto.EjercicioDto;
import com.apexfit.backend.entity.Ejercicio;
import com.apexfit.backend.repository.EjercicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EjercicioService {

    private final EjercicioRepository ejercicioRepository;

    

    public Page<EjercicioDto> getAllEjercicios(Pageable pageable) {
        return ejercicioRepository.findAll(pageable).map(this::mapToDto);
    } 
    
    

    public EjercicioDto getEjercicioById(Integer id) {
        Ejercicio ejercicio = ejercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));
        return mapToDto(ejercicio);
    }
    

    

    public EjercicioDto createEjercicio(EjercicioDto dto) {
        if (ejercicioRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("El nombre del ejercicio ya existe");
        }
        Ejercicio ejercicio = new Ejercicio();
        mapToEntity(dto, ejercicio);
        Ejercicio savedEjercicio = ejercicioRepository.save(ejercicio);
        return mapToDto(savedEjercicio);
    }

    public EjercicioDto updateEjercicio(Integer id, EjercicioDto dto) {
        Ejercicio ejercicio = ejercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));

        if (!ejercicio.getNombre().equals(dto.getNombre()) && 
            ejercicioRepository.findByNombre(dto.getNombre()).isPresent()) {
            throw new RuntimeException("El nombre del ejercicio ya existe");
        }

        mapToEntity(dto, ejercicio);
        Ejercicio updatedEjercicio = ejercicioRepository.save(ejercicio);
        return mapToDto(updatedEjercicio);
    }

    public void deleteEjercicio(Integer id) {
        Ejercicio ejercicio = ejercicioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejercicio no encontrado"));
        ejercicioRepository.delete(ejercicio);
    }

    


    private EjercicioDto mapToDto(Ejercicio ejercicio) {
        EjercicioDto dto = new EjercicioDto();
        dto.setIdEjercicio(ejercicio.getIdEjercicio());
        dto.setNombre(ejercicio.getNombre());
        dto.setGrupoMuscular(ejercicio.getGrupoMuscular());
        dto.setVideoUrl(ejercicio.getVideoUrl());
        dto.setNivelDificultad(ejercicio.getNivelDificultad());
        return dto;
    }

    private void mapToEntity(EjercicioDto dto, Ejercicio ejercicio) {
        ejercicio.setNombre(dto.getNombre());
        ejercicio.setGrupoMuscular(dto.getGrupoMuscular());
        ejercicio.setVideoUrl(dto.getVideoUrl());
        ejercicio.setNivelDificultad(dto.getNivelDificultad());
    }

    
    public Page<EjercicioDto> getByGrupoMuscular(String grupoMuscular, Pageable pageable) {
        log.info("Buscando ejercicios por grupo muscular: {}", grupoMuscular);
        return ejercicioRepository
                .findByGrupoMuscularContainingIgnoreCase(grupoMuscular, pageable)
                .map(this::mapToDto);
    }

    public Page<EjercicioDto> getByNivelDificultad(String nivelDificultad, Pageable pageable) {
        log.info("Buscando ejercicios por nivel de dificultad: {}", nivelDificultad);
        return ejercicioRepository
                .findByNivelDificultadIgnoreCase(nivelDificultad, pageable)
                .map(this::mapToDto);
    }
    
}


