package com.example.examenuni1_rodriguez.mappers;

import com.example.examenuni1_rodriguez.dto.DetalleDTO;
import com.example.examenuni1_rodriguez.entity.Detalle;
import com.example.examenuni1_rodriguez.mappers.base.BaseMappers;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleMapper extends BaseMappers<Detalle, DetalleDTO> {
    @Mapping(source = "alquilerId.id", target = "alquilerId")
    @Mapping(source = "equipoId.id", target = "equipoId")
    DetalleDTO toDTO(Detalle detalle);

    @InheritInverseConfiguration
    @Mapping(target = "alquilerId", ignore = true)
    @Mapping(target = "equipoId", ignore = true)
    Detalle toEntity(DetalleDTO detalleDTO);
}