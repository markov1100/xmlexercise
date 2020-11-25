package xmlexercise.xmlexercise;

import xmlexercise.xmlexercise.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;


@Component
public class Runner implements CommandLineRunner {
    private final ConfigurableApplicationContext applicationContext;
    private final SupplierService supplierService;
    private final PartService partService;
    private final CarService carService;
    private final CustomerService customerService;
    private final SaleService saleService;


    @Autowired
    public Runner(ConfigurableApplicationContext applicationContext, SupplierService supplierService, PartService partService, CarService carService, CustomerService customerService, SaleService saleService) {
        this.applicationContext = applicationContext;
        this.supplierService = supplierService;
        this.partService = partService;
        this.carService = carService;
        this.customerService = customerService;
        this.saleService = saleService;

    }

    @Override
    public void run(String... args) throws Exception {
        this.supplierService.seedSuppliers();
        this.partService.seedParts();
        this.carService.seedCars();
        this.customerService.seedCustomers();
        this.saleService.seedSales();


        System.out.println(customerService.getOrderedCustomersEx1());
        System.out.println(carService.getToyotasEx2());
        System.out.println(supplierService.getLocalSupplierEx3());
        System.out.println(carService.carsAndPartsEx4());
        System.out.println(customerService.getCustomersWhereSaleNotNullEx5());
        System.out.println(saleService.getDiscountedSalesEx6());




        applicationContext.close();
        System.err.println("S H U T D O W N");


    }
}
