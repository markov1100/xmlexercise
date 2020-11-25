package xmlexercise.xmlexercise.domain.dto.export.exerciseSix;


import javax.xml.bind.annotation.*;

@XmlRootElement(name = "sale")
@XmlAccessorType(XmlAccessType.FIELD)
public class SaleExportDto {


    @XmlElement(name = "car")
    private CarExportSixDto carExportSixDtoDto;

    @XmlElement(name = "customer-name")
    private String customerName;

    @XmlElement(name = "discount")
    private double discount;

    @XmlElement(name = "price")
    private double price;


    @XmlElement(name = "price-with-discount")
    private double discountedPrice;

    public CarExportSixDto getCarExportSixDtoDto() {
        return carExportSixDtoDto;
    }

    public void setCarExportSixDtoDto(CarExportSixDto carExportSixDtoDto) {
        this.carExportSixDtoDto = carExportSixDtoDto;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public SaleExportDto() {
    }
}


