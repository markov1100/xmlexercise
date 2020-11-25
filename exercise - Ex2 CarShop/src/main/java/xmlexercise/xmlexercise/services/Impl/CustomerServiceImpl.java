package xmlexercise.xmlexercise.services.Impl;

import xmlexercise.xmlexercise.domain.dto.export.CustomerOrderedExportDto;
import xmlexercise.xmlexercise.domain.dto.export.CustomerOrderedRootExportDto;
import xmlexercise.xmlexercise.domain.dto.export.CustomerTotalSalesDto;
import xmlexercise.xmlexercise.domain.dto.export.CustomerTotalSalesRootDto;
import xmlexercise.xmlexercise.domain.dto.imports.CustomerImportDto;
import xmlexercise.xmlexercise.domain.dto.imports.CustomerImportRootDto;
import xmlexercise.xmlexercise.domain.entities.Customer;
import xmlexercise.xmlexercise.domain.entities.Part;
import xmlexercise.xmlexercise.domain.entities.Sale;
import xmlexercise.xmlexercise.domain.repositories.CustomerRepository;
import xmlexercise.xmlexercise.services.CustomerService;
import xmlexercise.xmlexercise.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {


    private final String CUSTOMERS_PATH = "src/main/resources/XMLs/customers.xml";
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedCustomers() throws IOException, JAXBException {
        CustomerImportRootDto customerImportRootDto = this.xmlParser.parseXml(CustomerImportRootDto.class, CUSTOMERS_PATH);
        for (CustomerImportDto customerImportDto : customerImportRootDto.getCustomers()) {


            Customer customer = this.modelMapper.map(customerImportDto, Customer.class);

            this.customerRepository.saveAndFlush(customer);

        }

    }

    @Override
    public String getOrderedCustomersEx1() throws IOException, JAXBException {
        List<CustomerOrderedExportDto> dto = this.customerRepository
                .findAllAndSort()
                .stream()
                .map(c -> this.modelMapper.map(c, CustomerOrderedExportDto.class))
                .collect(Collectors.toList());


        CustomerOrderedRootExportDto rootExportDto = new CustomerOrderedRootExportDto();

        rootExportDto.setCustomers(dto);


        this.xmlParser.exportXml(rootExportDto, CustomerOrderedRootExportDto.class, "src/main/resources/XMLs/Query1.xml");


        return "Check your resource XML folder for: Query1";
    }

    @Override
    public String getCustomersWhereSaleNotNullEx5() throws JAXBException {

        Set<Customer> allBySalesIsNotNull = this.customerRepository.findAllBySalesIsNotNull();


        List<CustomerTotalSalesDto> customerDtos = new ArrayList<>();

        for (Customer customer : allBySalesIsNotNull) {
            CustomerTotalSalesDto csDto = this.modelMapper.map(customer, CustomerTotalSalesDto.class);
            csDto.setFullName(customer.getName());
            csDto.setCarsBought(customer.getSales().size());
            csDto.setMoneySpent(calculateMoneySpent(customer));

            customerDtos.add(csDto);
        }

        customerDtos = customerDtos.stream()
                .sorted(Comparator.comparing(CustomerTotalSalesDto::getCarsBought).thenComparing(CustomerTotalSalesDto::getMoneySpent).reversed())
                .collect(Collectors.toList());


        CustomerTotalSalesRootDto result = new CustomerTotalSalesRootDto();
        result.setCustomers(customerDtos);

        this.xmlParser.exportXml(result,CustomerTotalSalesRootDto.class,"src/main/resources/XMLs/Query5.xml");

        return "Check your recourse folder for file name: Query5.xml";
    }


    private BigDecimal calculateMoneySpent(Customer customer) {
        BigDecimal price = new BigDecimal(0);

        for (Sale sale : customer.getSales()) {
            for (Part part : sale.getCar().getParts()) {
                price = price.add(part.getPrice());
            }
        }


        return price;

    }
}
