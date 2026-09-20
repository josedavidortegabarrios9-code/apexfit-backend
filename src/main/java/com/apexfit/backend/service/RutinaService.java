package com.apexfit.backend.service;

import com.apexfit.backend.dto.RutinaDto;
import com.apexfit.backend.entity.Rutina;
import com.apexfit.backend.entity.Usuario;
import com.apexfit.backend.repository.RutinaRepository;
import com.apexfit.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RutinaService {

    private final RutinaRepository rutinaRepository;
    private final UsuarioRepository usuarioRepository;

    public Page<RutinaDto> getAllRutinas(Pageable pageable) {
        return rutinaRepository.findAll(pageable).map(this::mapToDto);
    }

    public Page<RutinaDto> getRutinasByCliente(Integer idCliente, Pageable pageable) {
        return rutinaRepository.findByCliente_IdUsuario(idCliente, pageable).map(this::mapToDto);
    }

    public RutinaDto getRutinaById(Integer id) {
        Rutina rutina = rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        return mapToDto(rutina);
    }

    public RutinaDto createRutina(RutinaDto dto) {
        Rutina rutina = new Rutina();
        mapToEntity(dto, rutina);
        Rutina savedRutina = rutinaRepository.save(rutina);
        return mapToDto(savedRutina);
    }

    public RutinaDto updateRutina(Integer id, RutinaDto dto) {
        Rutina rutina = rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));

        mapToEntity(dto, rutina);
        Rutina updatedRutina = rutinaRepository.save(rutina);
        return mapToDto(updatedRutina);
    }

    public void deleteRutina(Integer id) {
        Rutina rutina = rutinaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rutina no encontrada"));
        rutina.setEstado("inactiva");
        rutinaRepository.save(rutina);
    }

    private RutinaDto mapToDto(Rutina rutina) {
        RutinaDto dto = new RutinaDto();
        dto.setIdRutina(rutina.getIdRutina());
        dto.setNombre(rutina.getNombre());
        dto.setDescripcion(rutina.getDescripcion());
        dto.setIdEntrenador(rutina.getEntrenador().getIdUsuario());
        dto.setIdCliente(rutina.getCliente().getIdUsuario());
        dto.setFechaCreacion(rutina.getFechaCreacion());
        dto.setEstado(rutina.getEstado());
        return dto;
    }

    private void mapToEntity(RutinaDto dto, Rutina rutina) {
        rutina.setNombre(dto.getNombre());
        rutina.setDescripcion(dto.getDescripcion());
        
        if (dto.getEstado() != null) {
            rutina.setEstado(dto.getEstado());
        }

        Usuario entrenador = usuarioRepository.findById(dto.getIdEntrenador())
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado"));
        rutina.setEntrenador(entrenador);

        Usuario cliente = usuarioRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        rutina.setCliente(cliente);
    }
}
