package xmlexercise.xmlexercise.services;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface SupplierService {

        void seedSuppliers() throws IOException, JAXBException;

        String getLocalSupplierEx3() throws JAXBException;

}
