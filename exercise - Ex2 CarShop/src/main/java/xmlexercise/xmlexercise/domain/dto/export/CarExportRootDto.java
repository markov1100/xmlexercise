package xmlexercise.xmlexercise.domain.dto.export;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "cars")
@XmlAccessorType(XmlAccessType.FIELD)
public class CarExportRootDto {

    @XmlElement(name = "car")
    private List<CarExportDto> cars;

    public List<CarExportDto> getCars() {
        return cars;
    }

    public void setCars(List<CarExportDto> cars) {
        this.cars = cars;
    }

    public CarExportRootDto() {
    }
}
