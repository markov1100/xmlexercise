package xmlexercise.xmlexercise.services;

import javax.xml.bind.JAXBException;

public interface SaleService {

    void seedSales();

    String getDiscountedSalesEx6() throws JAXBException;
}
