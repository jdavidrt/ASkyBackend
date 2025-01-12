package ASKy.Backend.dto.request;

import lombok.Data;

@Data
public class ExpertFilterRequest {
    private String specialty; // Filtrar por especialidad
    private String name; // Buscar por nombre
    private String orderBy; // Relevancia, precio, calificación
}
