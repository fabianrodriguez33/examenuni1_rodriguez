package com.example.examenuni1_rodriguez.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmpresaDTO {
    private Long id;
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    @NotBlank(message = "El RUC no puede estar vacío")
    @Size(max = 20, message = "El RUC no puede exceder 20 caracteres")
    private String ruc;
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;
}
