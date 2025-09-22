package com.example.examenuni1_rodriguez.mappers;

import com.example.examenuni1_rodriguez.dto.EmpresaDTO;
import com.example.examenuni1_rodriguez.entity.Empresa;
import com.example.examenuni1_rodriguez.mappers.base.BaseMappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmpresaMapper extends BaseMappers<Empresa, EmpresaDTO> {
}