package xmlexercise.xmlexercise.services.Impl;

import xmlexercise.xmlexercise.domain.dto.export.exerciseSix.CarExportSixDto;
import xmlexercise.xmlexercise.domain.dto.export.exerciseSix.SaleExportDto;
import xmlexercise.xmlexercise.domain.dto.export.exerciseSix.SaleExportRootDto;
import xmlexercise.xmlexercise.domain.entities.Car;
import xmlexercise.xmlexercise.domain.entities.Customer;
import xmlexercise.xmlexercise.domain.entities.Part;
import xmlexercise.xmlexercise.domain.entities.Sale;
import xmlexercise.xmlexercise.domain.repositories.CarRepository;
import xmlexercise.xmlexercise.domain.repositories.CustomerRepository;
import xmlexercise.xmlexercise.domain.repositories.SaleRepository;
import xmlexercise.xmlexercise.services.SaleService;
import xmlexercise.xmlexercise.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    @Autowired
    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, XmlParser xmlParser, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedSales() {
        // seven sales
        for (int i = 0; i < 7; i++) {
            Sale sale = new Sale();
            sale.setDiscount(getRandomDiscount());
            sale.setCar(getRandomCar());
            sale.setCustomer(getRandomCustomer());

            saleRepository.saveAndFlush(sale);
        }

    }

    @Override
    public String getDiscountedSalesEx6() throws JAXBException {

        List<Sale> sales = this.saleRepository.findAll();

        SaleExportRootDto saleExportRootDto = new SaleExportRootDto();
        List<SaleExportDto> saleExportDtos = new ArrayList<>();


        for (Sale sale : sales) {
            SaleExportDto saleExportDto = new SaleExportDto();
            saleExportDto.setCarExportSixDtoDto(this.modelMapper.map(sale.getCar(), CarExportSixDto.class));
            saleExportDto.setCustomerName(sale.getCustomer().getName());
            saleExportDto.setDiscount( ((double)sale.getDiscount() /(double) 100));


            Set<Part> partsOfCurrentCar = sale.getCar().getParts();
            double priceTotalParts = 0;
            for (Part part : partsOfCurrentCar) {
                priceTotalParts += part.getPrice().doubleValue();
            }
            saleExportDto.setPrice( priceTotalParts);
            double price = saleExportDto.getPrice();
//unsatisfying result was met when tried with BigDecimal, please ignore double use for the sake of Exercise
            double discountToExtract = price*saleExportDto.getDiscount();

            saleExportDto.setDiscountedPrice(price-discountToExtract);

            saleExportDtos.add(saleExportDto);
        }
        saleExportRootDto.setSales(saleExportDtos);

       this.xmlParser.exportXml(saleExportRootDto,SaleExportRootDto.class,"src/main/resources/XMLs/Query6.xml");

        return "Check your resource folder for file name: Query6.xml";

    }

    private int getRandomDiscount() {
        List<Integer> discountRange = new ArrayList<>();

        discountRange.add(0);
        discountRange.add(5);
        discountRange.add(10);
        discountRange.add(15);
        discountRange.add(20);
        discountRange.add(30);
        discountRange.add(40);
        discountRange.add(50);

        Random random = new Random();

        int index = random.nextInt(7);
        return discountRange.get(index);
    }

    private Car getRandomCar() {
        Random random = new Random();
        long id = (long) random.nextInt((int) this.carRepository.count()) + 1;
        return this.carRepository.findById(id).get();
    }

    private Customer getRandomCustomer() {
        Random random = new Random();
        long id = (long) random.nextInt((int) this.customerRepository.count()) + 1;
        return this.customerRepository.findById(id).get();
    }
}
