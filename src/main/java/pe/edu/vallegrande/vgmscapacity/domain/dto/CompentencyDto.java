package pe.edu.vallegrande.vgmscapacity.domain.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class CompentencyDto {

    @Id
    private String competencyId;
    private String name;
    private String description;
    private String status;
}
