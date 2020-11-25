package xmlexercise.xmlexercise.domain.dto.export;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlRootElement (name = "customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerTotalSalesDto {


    @XmlAttribute(name = "full-name")
    private String fullName;
    @XmlAttribute(name = "bought-cars")
    private int carsBought;
    @XmlAttribute(name = "spent-money")
    private BigDecimal moneySpent;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getCarsBought() {
        return carsBought;
    }

    public void setCarsBought(int carsBought) {
        this.carsBought = carsBought;
    }

    public BigDecimal getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(BigDecimal moneySpent) {
        this.moneySpent = moneySpent;
    }

    public CustomerTotalSalesDto() {
    }
}
