package com.example.examenuni1_rodriguez.service.impl;

import com.example.examenuni1_rodriguez.controller.exceptions.ResourceNotFoundException;
import com.example.examenuni1_rodriguez.controller.exceptions.ResourceValidationException;
import com.example.examenuni1_rodriguez.dto.DetalleDTO;
import com.example.examenuni1_rodriguez.dto.AlquilerDTO;
import com.example.examenuni1_rodriguez.entity.Empresa;
import com.example.examenuni1_rodriguez.entity.Detalle;
import com.example.examenuni1_rodriguez.entity.Equipo;
import com.example.examenuni1_rodriguez.entity.Alquiler;
import com.example.examenuni1_rodriguez.mappers.AlquilerMapper;
import com.example.examenuni1_rodriguez.repository.EmpresaRepository;
import com.example.examenuni1_rodriguez.repository.EquipoRepository;
import com.example.examenuni1_rodriguez.repository.AlquilerRepository;
import com.example.examenuni1_rodriguez.service.service.AlquilerService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlquilerServiceImpl implements AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final AlquilerMapper alquilerMapper;
    private final EmpresaRepository empresaRepository;
    private final EquipoRepository equipoRepository;

    public AlquilerServiceImpl(AlquilerRepository alquilerRepository, AlquilerMapper alquilerMapper, EmpresaRepository empresaRepository, EquipoRepository equipoRepository) {
        this.alquilerRepository = alquilerRepository;
        this.alquilerMapper = alquilerMapper;
        this.empresaRepository = empresaRepository;
        this.equipoRepository = equipoRepository;
    }

    @Transactional
    @Override
    public AlquilerDTO create(AlquilerDTO alquilerDTO) throws ServiceException {
        if (alquilerDTO == null) {
            throw new IllegalArgumentException("El alquiler no puede ser nulo");
        }
        Empresa empresa = empresaRepository.findById(alquilerDTO.getEmpresaId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con ID: " + alquilerDTO.getEmpresaId()));

        if (alquilerDTO.getDetalles()== null || alquilerDTO.getDetalles().isEmpty()) {
            throw new ResourceValidationException("El alquiler debe tener al menos un detalle");
        }
        Alquiler alquiler = new Alquiler();
        alquiler.setFechaSalida(alquilerDTO.getFechaSalida());
        alquiler.setFechaEntrada(alquilerDTO.getFechaEntrada());
        alquiler.setObservacion(alquilerDTO.getObservacion());
        alquiler.setEmpresaId(empresa);

        List<Detalle> detalles = new ArrayList<>();
        for (DetalleDTO d : alquilerDTO.getDetalles()) {
            if (d.getPrecio() == null || d.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResourceValidationException("El precio debe ser mayor que 0");
            }
            Equipo equipo = equipoRepository.findById(d.getEquipoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con ID: " + d.getEquipoId()));

            Detalle detalle = new Detalle();
            detalle.setAlquilerId(alquiler);
            detalle.setEquipoId(equipo);
            detalle.setPrecio(d.getPrecio());
            detalle.setCantidad(d.getCantidad());

            detalles.add(detalle);
        }
        alquiler.setDetalles(detalles);
        Alquiler guardada = alquilerRepository.save(alquiler);
        return alquilerMapper.toDTO(guardada);
    }

    @Transactional
    @Override
    public AlquilerDTO update(Long aLong, AlquilerDTO alquilerDTO) throws ServiceException {
        if (aLong == null || alquilerDTO == null) {
            throw new IllegalArgumentException("El ID y el alquiler no pueden ser nulos");
        }
        Alquiler alquilerExistente = alquilerRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Alquiler no encontrado con ID: " + aLong));

        Empresa empresa = empresaRepository.findById(alquilerDTO.getEmpresaId())
                .orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada con ID: " + alquilerDTO.getEmpresaId()));

        if (alquilerDTO.getDetalles() == null || alquilerDTO.getDetalles().isEmpty()) {
            throw new ResourceValidationException("El alquiler debe tener al menos un detalle");
        }
        alquilerExistente.setFechaSalida(alquilerDTO.getFechaSalida() != null ? alquilerDTO.getFechaSalida() : alquilerExistente.getFechaSalida());
        alquilerExistente.setFechaEntrada(alquilerDTO.getFechaEntrada() != null ? alquilerDTO.getFechaEntrada() : alquilerExistente.getFechaEntrada());
        alquilerExistente.setObservacion(alquilerDTO.getObservacion() != null ? alquilerDTO.getObservacion() : alquilerExistente.getObservacion());
        alquilerExistente.setEmpresaId(empresa);
        alquilerExistente.getDetalles().clear();
        List<Detalle> nuevosDetalles = new ArrayList<>();

        for (DetalleDTO d : alquilerDTO.getDetalles()) {

            if (d.getPrecio() == null || d.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
                throw new ResourceValidationException("El precio debe ser mayor que 0");
            }

            Equipo equipo = equipoRepository.findById(d.getEquipoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con ID: " + d.getEquipoId()));

            Detalle detalle = new Detalle();
            detalle.setAlquilerId(alquilerExistente);
            detalle.setEquipoId(equipo);
            detalle.setPrecio(d.getPrecio());
            detalle.setCantidad(d.getCantidad());

            nuevosDetalles.add(detalle);
        }

        alquilerExistente.setDetalles(nuevosDetalles);
        Alquiler actualizada = alquilerRepository.save(alquilerExistente);
        return alquilerMapper.toDTO(actualizada);
    }

    @Transactional(readOnly = true)
    @Override
    public AlquilerDTO findById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        Alquiler alquiler = alquilerRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Alquiler no encontrado con ID: " + aLong));

        return alquilerMapper.toDTO(alquiler);
    }

    @Transactional
    @Override
    public void deleteById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }

        if (!alquilerRepository.existsById(aLong)) {
            throw new ResourceNotFoundException("Alquiler no encontrado con ID: " + aLong);
        }

        try {
            alquilerRepository.deleteById(aLong);
        } catch (DataIntegrityViolationException ex) {
            throw new RuntimeException("No se puede eliminar el alquiler porque tiene detalles asociados", ex);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<AlquilerDTO> findAll() throws ServiceException {
        List<Alquiler> alquileres = alquilerRepository.findAll();
        return alquileres.stream()
                .map(alquilerMapper::toDTO)
                .toList();
    }
}
