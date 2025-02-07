package pe.edu.vallegrande.vgmscapacity.domain.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document(collection = "capacity")
public class Capacity {

    @Id
    private String capacityId;
    private String competencyId;
    private String name;
    private String status;
    private String noteValue;

}
