package com.example.examenuni1_rodriguez.service.impl;

import com.example.examenuni1_rodriguez.controller.exceptions.ResourceNotFoundException;
import com.example.examenuni1_rodriguez.dto.EquipoDTO;
import com.example.examenuni1_rodriguez.entity.Equipo;
import com.example.examenuni1_rodriguez.mappers.EquipoMapper;
import com.example.examenuni1_rodriguez.repository.EquipoRepository;
import com.example.examenuni1_rodriguez.service.service.EquipoService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipoServiceImpl implements EquipoService {
    private final EquipoRepository equipoRepository;
    private final EquipoMapper equipoMapper;

    public EquipoServiceImpl(EquipoRepository equipoRepository, EquipoMapper equipoMapper) {
        this.equipoRepository = equipoRepository;
        this.equipoMapper = equipoMapper;
    }

    @Override
    public EquipoDTO create(EquipoDTO equipoDTO) throws ServiceException {
        try {
            return equipoMapper.toDTO(equipoRepository.save( equipoMapper.toEntity(equipoDTO)));
        } catch (Exception e) {
            throw new RuntimeException("Error al crear el equipo: "+e);
        }
    }

    @Override
    public EquipoDTO update(Long aLong, EquipoDTO equipoDTO) throws ServiceException {
        try {
            Equipo equipo1 = equipoRepository.findById(aLong).orElseThrow(() -> new ServiceException("No existe el equipo"));
            equipo1.setDescripcion(equipoDTO.getDescripcion());
            equipo1.setCantidad(equipoDTO.getCantidad());
            equipo1.setPrecio(equipoDTO.getPrecio()); // Debe funcionar con BigDecimal
            return equipoMapper.toDTO(equipoRepository.save(equipo1));
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error al actualizar el equipo: " + e);
        }
    }

    @Override
    public EquipoDTO findById(Long aLong) throws ServiceException {
        try {
            Equipo equip =  equipoRepository.findById(aLong).orElseThrow(() -> new ServiceException("No existe el equipo"));
            return equipoMapper.toDTO(equip);
        }catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al leer el equipo con id " + aLong, e);
        }
    }

    @Override
    public void deleteById(Long aLong) throws ServiceException {
        try {
            if(!equipoRepository.findById(aLong).isPresent()){
                throw new ServiceException("No existe el equipo");
            }
            equipoRepository.deleteById(aLong);
        }catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al eliminar el equipo con id " + aLong, e);
        }
    }

    @Override
    public List<EquipoDTO> findAll() throws ServiceException {
        try {
            return equipoMapper.toDTOs(equipoRepository.findAll());
        }catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al listar los equipos " + e);
        }
    }
}