package com.apexfit.backend.controller;

import com.apexfit.backend.dto.EntrenadorClienteDto;
import com.apexfit.backend.service.EntrenadorClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/entrenador-cliente")
@RequiredArgsConstructor
public class EntrenadorClienteController {

    private final EntrenadorClienteService service;

    @GetMapping
    public ResponseEntity<List<EntrenadorClienteDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntrenadorClienteDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/entrenador/{idEntrenador}")
    public ResponseEntity<List<EntrenadorClienteDto>> getByEntrenador(@PathVariable Integer idEntrenador) {
        return ResponseEntity.ok(service.getByEntrenador(idEntrenador));
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<EntrenadorClienteDto>> getByCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(service.getByCliente(idCliente));
    }

    @PostMapping
    public ResponseEntity<EntrenadorClienteDto> create(@Valid @RequestBody EntrenadorClienteDto dto) {
        return new ResponseEntity<>(service.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntrenadorClienteDto> update(@PathVariable Integer id, @Valid @RequestBody EntrenadorClienteDto dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
