package com.example.examenuni1_rodriguez.controller.general;

import com.example.examenuni1_rodriguez.dto.DetalleDTO;
import com.example.examenuni1_rodriguez.service.service.DetalleService;
import jakarta.validation.Valid;
import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/detalles")
public class DetalleController {
    private final DetalleService detalleService;

    public DetalleController(DetalleService detalleService) {
        this.detalleService = detalleService;
    }

    @GetMapping
    public ResponseEntity<List<DetalleDTO>> findAll() {
        return ResponseEntity.ok(detalleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetalleDTO> read(@PathVariable Long id) throws ServiceException {
        DetalleDTO detalleDTO = detalleService.findById(id);
        return ResponseEntity.ok(detalleDTO);
    }

    @PostMapping
    public ResponseEntity<DetalleDTO> create(@Valid @RequestBody DetalleDTO detalleDTO) throws ServiceException {
        DetalleDTO detalleDTO1 = detalleService.create(detalleDTO);
        return new ResponseEntity<>(detalleDTO1, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DetalleDTO> update(@PathVariable Long id, @Valid @RequestBody DetalleDTO detalleDTO) throws ServiceException {
        DetalleDTO detalleDTO1 = detalleService.update(id, detalleDTO);
        return ResponseEntity.ok(detalleDTO1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws ServiceException {
        detalleService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}