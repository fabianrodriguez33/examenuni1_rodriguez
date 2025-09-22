package com.example.examenuni1_rodriguez.controller.general;

import com.example.examenuni1_rodriguez.dto.EquipoDTO;
import com.example.examenuni1_rodriguez.service.service.EquipoService;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/equipos")
public class EquipoController {
    private final EquipoService equipoService;

    public EquipoController(EquipoService equipoService) {
        this.equipoService = equipoService;
    }

    @GetMapping
    public ResponseEntity<List<EquipoDTO>> findAll() {
        return ResponseEntity.ok(equipoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EquipoDTO> read(@PathVariable Long id) throws ServiceException {
        EquipoDTO equipoDTO = equipoService.findById(id);
        return ResponseEntity.ok(equipoDTO);
    }

    @PostMapping
    public ResponseEntity<EquipoDTO> create(@Valid @RequestBody EquipoDTO equipoDTO) throws ServiceException {
        EquipoDTO equipoDTO1 = equipoService.create(equipoDTO);
        return new ResponseEntity<>(equipoDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EquipoDTO> update(@PathVariable Long id, @Valid @RequestBody EquipoDTO equipoDTO) throws ServiceException {
        EquipoDTO equipoDTO1 = equipoService.update(id, equipoDTO);
        return ResponseEntity.ok(equipoDTO1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        equipoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}