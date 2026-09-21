package com.apexfit.backend.service;

import com.apexfit.backend.dto.UsuarioDto;
import com.apexfit.backend.entity.Rol;
import com.apexfit.backend.entity.Usuario;
import com.apexfit.backend.repository.RolRepository;
import com.apexfit.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public Page<UsuarioDto> getAllUsuarios(Pageable pageable) {
        return usuarioRepository.findAll(pageable).map(this::mapToDto);
    }

    public UsuarioDto getUsuarioById(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return mapToDto(usuario);
    }

    public UsuarioDto getUsuarioByCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
        return mapToDto(usuario);
    }

    public UsuarioDto createUsuario(UsuarioDto dto) {
        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está en uso");
        }
        Usuario usuario = new Usuario();
        mapToEntity(dto, usuario);
        // For MVP, just putting a dummy hash if not handling security yet
        usuario.setContrasenaHash("dummy_hash_for_now");
        
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return mapToDto(savedUsuario);
    }

    public UsuarioDto updateUsuario(Integer id, UsuarioDto dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                
        if (!usuario.getCorreo().equals(dto.getCorreo()) && 
            usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new RuntimeException("El correo ya está en uso");
        }

        mapToEntity(dto, usuario);
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return mapToDto(updatedUsuario);
    }

    public void deleteUsuario(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        // Soft delete
        usuario.setEstado("inactivo");
        usuarioRepository.save(usuario);
    }

    private UsuarioDto mapToDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setIdUsuario(usuario.getIdUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setCorreo(usuario.getCorreo());
        dto.setIdRol(usuario.getRol().getIdRol());
        dto.setTelefono(usuario.getTelefono());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setEstado(usuario.getEstado());
        return dto;
    }

    private void mapToEntity(UsuarioDto dto, Usuario usuario) {
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setFechaNacimiento(dto.getFechaNacimiento());
        
        if (dto.getEstado() != null) {
            usuario.setEstado(dto.getEstado());
        }

        Rol rol = rolRepository.findById(dto.getIdRol())
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        usuario.setRol(rol);
    }
}
