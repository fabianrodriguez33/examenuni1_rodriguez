package com.example.examenuni1_rodriguez.mappers.base;

import java.util.List;

public interface BaseMappers<E,DTO>{
    DTO toDTO(E entity);
    E toEntity(DTO dto);
    List<DTO> toDTOs(List<E> List);
    List<E> toEntity(List<DTO> List);
}
