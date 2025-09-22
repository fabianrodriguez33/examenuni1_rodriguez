package com.example.examenuni1_rodriguez.service.impl;

import com.example.examenuni1_rodriguez.controller.exceptions.ResourceNotFoundException;
import com.example.examenuni1_rodriguez.dto.EmpresaDTO;
import com.example.examenuni1_rodriguez.entity.Empresa;
import com.example.examenuni1_rodriguez.mappers.EmpresaMapper;
import com.example.examenuni1_rodriguez.repository.EmpresaRepository;
import com.example.examenuni1_rodriguez.service.service.EmpresaService;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpresaServiceImpl implements EmpresaService {
    private final EmpresaRepository empresaRepository;
    private final EmpresaMapper empresaMapper;

    public EmpresaServiceImpl(EmpresaRepository empresaRepository, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaMapper = empresaMapper;
    }

    @Override
    public EmpresaDTO create(EmpresaDTO empresaDTO) throws ServiceException {
        try {
            Empresa empresa = empresaMapper.toEntity(empresaDTO);
            Empresa empresaSaved = empresaRepository.save(empresa);
            return empresaMapper.toDTO(empresaSaved);
        } catch (Exception e) {
            throw new ServiceException("Error al crear Empresa",e);
        }
    }

    @Override
    public EmpresaDTO update(Long aLong, EmpresaDTO empresaDTO) throws ServiceException {
        try {
            Empresa empresa = empresaRepository.findById(aLong).orElseThrow(() -> new ResourceNotFoundException("Empresa no encontrada"));
            empresa.setNombre(empresaDTO.getNombre());
            empresa.setRuc(empresaDTO.getRuc());
            empresa.setDireccion(empresaDTO.getDireccion());
            Empresa empresaUpdate = empresaRepository.save(empresa);
            return empresaMapper.toDTO(empresaUpdate);
        } catch (ResourceNotFoundException e) {
            throw (e);
        }catch (Exception e) {
            throw new ServiceException("Error al actualizar Empresa",e);
        }
    }

    @Override
    public EmpresaDTO findById(Long aLong) throws ServiceException {
        try {
            Empresa empresa = empresaRepository.findById(aLong).orElseThrow(() -> new ResourceNotFoundException("Empresa con ID "+aLong+" no fue encontrada"));
            return empresaMapper.toDTO(empresa);
        } catch (ResourceNotFoundException e) {
            throw (e);
        } catch (Exception e) {
            throw new ServiceException("Error al leer la empresa con id " + aLong, e);
        }
    }

    @Override
    public void deleteById(Long aLong) throws ServiceException {
        try {
            if(!empresaRepository.findById(aLong).isPresent()){
                throw new ResourceNotFoundException("Empresa con ID "+aLong+" no fue encontrada");
            }
            empresaRepository.deleteById(aLong);
        }catch (ResourceNotFoundException e) {
            throw (e);
        }catch (Exception e) {
            throw new ServiceException("Error al eliminar la empresa con id " + aLong, e);
        }
    }

    @Override
    public List<EmpresaDTO> findAll() throws ServiceException {
        try {
            List<Empresa> empresas = empresaRepository.findAll();
            return empresaMapper.toDTOs(empresas);
        }catch (Exception e) {
            throw new ServiceException("Error al listar las empresas",e);
        }
    }
}