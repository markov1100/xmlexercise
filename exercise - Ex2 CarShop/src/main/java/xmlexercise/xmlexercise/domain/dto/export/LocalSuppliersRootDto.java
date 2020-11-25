package xmlexercise.xmlexercise.domain.dto.export;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "suppliers")
@XmlAccessorType(XmlAccessType.FIELD)
public class LocalSuppliersRootDto {


    @XmlElement(name = "supplier")
    private List<LocalSupplierDto> suppliers;

    public List<LocalSupplierDto> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<LocalSupplierDto> suppliers) {
        this.suppliers = suppliers;
    }

    public LocalSuppliersRootDto() {
    }
}
