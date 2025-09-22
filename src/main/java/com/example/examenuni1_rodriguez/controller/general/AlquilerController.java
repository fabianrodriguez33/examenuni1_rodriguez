package com.example.examenuni1_rodriguez.controller.general;

import com.example.examenuni1_rodriguez.dto.AlquilerDTO;
import com.example.examenuni1_rodriguez.service.service.AlquilerService;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alquiler")
public class AlquilerController {
    private final AlquilerService alquilerService;

    public AlquilerController(AlquilerService alquilerService) {
        this.alquilerService = alquilerService;
    }

    @GetMapping
    public ResponseEntity<List<AlquilerDTO>> listarAlquileres() {
        List<AlquilerDTO> alquileres = alquilerService.findAll();
        return ResponseEntity.ok(alquileres);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlquilerDTO> obtenerAlquiler(@PathVariable Long id) {
        AlquilerDTO alquiler = alquilerService.findById(id);
        return ResponseEntity.ok(alquiler);
    }

    @PostMapping
    public ResponseEntity<AlquilerDTO> crearAlquiler(@Valid @RequestBody AlquilerDTO alquilerDTO) {
        AlquilerDTO creada = alquilerService.create(alquilerDTO);
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlquilerDTO> actualizarAlquiler(@PathVariable Long id,
                                                          @Valid @RequestBody AlquilerDTO alquilerDTO) {
        AlquilerDTO actualizada = alquilerService.update(id, alquilerDTO);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlquiler(@PathVariable Long id) {
        alquilerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}