package xmlexercise.xmlexercise.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface CustomerService {

    void seedCustomers() throws IOException, JAXBException;


    String getOrderedCustomersEx1() throws IOException, JAXBException;

    String getCustomersWhereSaleNotNullEx5() throws JAXBException;
}
