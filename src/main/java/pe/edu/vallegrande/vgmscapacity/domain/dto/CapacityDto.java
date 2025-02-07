package pe.edu.vallegrande.vgmscapacity.domain.dto;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CapacityDto {
    private String capacityId;
    private CompentencyDto competencyId;
    private String name;
    private String status;
}
