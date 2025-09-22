package com.example.examenuni1_rodriguez.mappers;

import com.example.examenuni1_rodriguez.dto.AlquilerDTO;
import com.example.examenuni1_rodriguez.entity.Alquiler;
import com.example.examenuni1_rodriguez.entity.Empresa;
import com.example.examenuni1_rodriguez.mappers.base.BaseMappers;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = DetalleMapper.class)
public interface AlquilerMapper extends BaseMappers<Alquiler, AlquilerDTO> {
    @Mapping(source = "empresaId.id", target = "empresaId")
    @Mapping(source = "detalles", target = "detalles")
    AlquilerDTO toDTO(Alquiler alquiler);

    @InheritInverseConfiguration
    @Mapping(target = "empresaId", ignore = true)
    @Mapping(target = "detalles", ignore = true)
    Alquiler toEntity(AlquilerDTO alquilerDTO);
}