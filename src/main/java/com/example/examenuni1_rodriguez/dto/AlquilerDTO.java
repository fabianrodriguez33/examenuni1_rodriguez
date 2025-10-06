package com.example.examenuni1_rodriguez.dto;

import com.fasterxml.jackson.annotation.JsonInclude; // <-- Agrega esta import para control de serialización (opcional)
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList; // <-- Agrega esta import
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // Opcional: Evita serializar campos null en respuestas
public class AlquilerDTO {
    private Long id;
    @NotNull(message = "La fecha de salida no puede ser nula")
    private LocalDate fechaSalida;
    private LocalDate fechaEntrada;
    @Size(max = 200, message = "La observación no puede exceder 200 caracteres")
    private String observacion;
    @NotNull(message = "El ID de la empresa no puede ser nulo")
    private Long empresaId;

    private List<DetalleDTO> detalles = new ArrayList<>(); // <-- ¡Esto es clave! Inicialízala vacía
}