package com.example.examenuni1_rodriguez.repository;

import com.example.examenuni1_rodriguez.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}