package com.apexfit.backend.controller;

import com.apexfit.backend.dto.AlertaDto;
import com.apexfit.backend.service.AlertaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alertas")
@RequiredArgsConstructor
public class AlertaController {

    private final AlertaService alertaService;

    @GetMapping
    public ResponseEntity<List<AlertaDto>> getAll() {
        return ResponseEntity.ok(alertaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlertaDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(alertaService.getById(id));
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<AlertaDto>> getByUsuario(@PathVariable Integer idUsuario) {
        return ResponseEntity.ok(alertaService.getByUsuario(idUsuario));
    }

    @PostMapping
    public ResponseEntity<AlertaDto> create(@Valid @RequestBody AlertaDto dto) {
        return new ResponseEntity<>(alertaService.create(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlertaDto> update(@PathVariable Integer id, @Valid @RequestBody AlertaDto dto) {
        return ResponseEntity.ok(alertaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        alertaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
