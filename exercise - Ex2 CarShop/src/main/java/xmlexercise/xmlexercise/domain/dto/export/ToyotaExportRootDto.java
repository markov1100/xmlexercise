package xmlexercise.xmlexercise.domain.dto.export;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class ToyotaExportRootDto {

    @XmlElement(name = "car")
    private List<ToyotaExportDto> cars;

    public List<ToyotaExportDto> getCars() {
        return cars;
    }

    public void setCars(List<ToyotaExportDto> cars) {
        this.cars = cars;
    }

    public ToyotaExportRootDto() {
    }
}
