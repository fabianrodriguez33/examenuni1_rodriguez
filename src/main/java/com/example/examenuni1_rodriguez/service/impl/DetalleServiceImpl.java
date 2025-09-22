package com.example.examenuni1_rodriguez.service.impl;

import com.example.examenuni1_rodriguez.controller.exceptions.ResourceNotFoundException;
import com.example.examenuni1_rodriguez.controller.exceptions.ResourceValidationException;
import com.example.examenuni1_rodriguez.dto.DetalleDTO;
import com.example.examenuni1_rodriguez.entity.Alquiler;
import com.example.examenuni1_rodriguez.entity.Detalle;
import com.example.examenuni1_rodriguez.entity.Equipo;
import com.example.examenuni1_rodriguez.mappers.DetalleMapper;
import com.example.examenuni1_rodriguez.repository.AlquilerRepository;
import com.example.examenuni1_rodriguez.repository.DetalleRepository;
import com.example.examenuni1_rodriguez.repository.EquipoRepository;
import com.example.examenuni1_rodriguez.service.service.DetalleService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class DetalleServiceImpl implements DetalleService {
    private final DetalleRepository detalleRepository;
    private final DetalleMapper detalleMapper;
    private final EquipoRepository equipoRepository;
    private final AlquilerRepository alquilerRepository; // <-- nuevo

    public DetalleServiceImpl(DetalleRepository detalleRepository, DetalleMapper detalleMapper, EquipoRepository equipoRepository, AlquilerRepository alquilerRepository) {
        this.detalleRepository = detalleRepository;
        this.detalleMapper = detalleMapper;
        this.equipoRepository = equipoRepository;
        this.alquilerRepository = alquilerRepository;
    }

    @Override
    public DetalleDTO create(DetalleDTO detalleDTO) throws ServiceException {
        if(detalleDTO==null){
            throw new IllegalArgumentException("El detalle no puede ser nulo.");
        }
        if(detalleDTO.getPrecio()==null || detalleDTO.getPrecio().compareTo(BigDecimal.ZERO)<=0){
            throw new ResourceValidationException("El precio unitario debe ser mayor que 0");
        }
        if(detalleDTO.getCantidad()==null || detalleDTO.getCantidad()<=0){
            throw new ResourceValidationException("La cantidad debe ser mayor que 0");
        }
        // validar existencia de equipo
        Equipo equipo = equipoRepository.findById(detalleDTO.getEquipoId())
                .orElseThrow(() -> new ResourceNotFoundException("Equipo no encontrado con ID: " + detalleDTO.getEquipoId()));

        // validar existencia de alquiler (IMPORTANTE)
        Alquiler alquiler = alquilerRepository.findById(detalleDTO.getAlquilerId())
                .orElseThrow(() -> new ResourceNotFoundException("Alquiler no encontrado con ID: " + detalleDTO.getAlquilerId()));

        try {
            Detalle detalle = detalleMapper.toEntity(detalleDTO);
            // asignar las entidades relacionadas correctamente
            detalle.setEquipoId(equipo);
            detalle.setAlquilerId(alquiler);

            Detalle saved = detalleRepository.save(detalle);
            return detalleMapper.toDTO(saved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DetalleDTO update(Long aLong, DetalleDTO detalleDTO) throws ServiceException {

        if (aLong == null || detalleDTO == null) {
            throw new IllegalArgumentException("El ID y el detalle no pueden ser nulos");
        }
        Detalle existente = detalleRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + aLong));

        if (detalleDTO.getPrecio() == null || detalleDTO.getPrecio().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ResourceValidationException("El precio debe ser mayor que 0");
        }
        if (detalleDTO.getCantidad() == null || detalleDTO.getCantidad() <= 0) {
            throw new ResourceValidationException("La cantidad debe ser mayor que 0");
        }
        try {
            existente.setPrecio(detalleDTO.getPrecio());
            existente.setCantidad(detalleDTO.getCantidad());
            if(detalleDTO.getEquipoId()!=null){
                Equipo equipo = equipoRepository.findById(detalleDTO.getEquipoId())
                        .orElseThrow(()-> new ResourceNotFoundException("Equipo no encontrado con ID "+aLong));
                existente.setEquipoId(equipo);
            }
            Detalle actualizado = detalleRepository.save(existente);
            return detalleMapper.toDTO(actualizado);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DetalleDTO findById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulos");
        }
        Detalle existente = detalleRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + aLong));
        try {
            return detalleMapper.toDTO(existente);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(Long aLong) throws ServiceException {
        if (aLong == null) {
            throw new IllegalArgumentException("El ID no puede ser nulo");
        }
        Detalle existente = detalleRepository.findById(aLong)
                .orElseThrow(() -> new ResourceNotFoundException("Detalle no encontrado con ID: " + aLong));
        try{
            detalleRepository.delete(existente);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<DetalleDTO> findAll() throws ServiceException {
        List<Detalle> detalles = detalleRepository.findAll();
        if (detalles.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron detalles registrados");
        }

        return detalles.stream()
                .map(detalleMapper::toDTO)
                .toList();
    }
}