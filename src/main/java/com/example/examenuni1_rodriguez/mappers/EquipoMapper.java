package com.example.examenuni1_rodriguez.mappers;

import com.example.examenuni1_rodriguez.dto.EquipoDTO;
import com.example.examenuni1_rodriguez.entity.Equipo;
import com.example.examenuni1_rodriguez.mappers.base.BaseMappers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EquipoMapper extends BaseMappers<Equipo, EquipoDTO> {
}
