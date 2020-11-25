package xmlexercise.xmlexercise.domain.dto.export;


import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "customers")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesRootDto {

    @XmlElement(name = "customer")
    private List<CustomerTotalSalesDto> customers;

    public List<CustomerTotalSalesDto> getCustomers() {
        return customers;
    }

    public void setCustomers(List<CustomerTotalSalesDto> customers) {
        this.customers = customers;
    }

    public CustomerTotalSalesRootDto() {
    }
}
