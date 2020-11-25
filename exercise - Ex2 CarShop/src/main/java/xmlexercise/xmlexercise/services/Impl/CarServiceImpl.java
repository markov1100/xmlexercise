package xmlexercise.xmlexercise.services.Impl;


import xmlexercise.xmlexercise.domain.dto.export.*;
import xmlexercise.xmlexercise.domain.dto.imports.CarImportDto;
import xmlexercise.xmlexercise.domain.dto.imports.CarImportRootDto;
import xmlexercise.xmlexercise.domain.entities.Car;
import xmlexercise.xmlexercise.domain.entities.Part;
import xmlexercise.xmlexercise.domain.repositories.CarRepository;
import xmlexercise.xmlexercise.domain.repositories.PartRepository;
import xmlexercise.xmlexercise.services.CarService;
import xmlexercise.xmlexercise.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.bind.JAXBException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {

    private final String CAR_PATH = "src/main/resources/XMLs/cars.xml";

    private final PartRepository partRepository;
    private final ModelMapper modelMapper;
    private final CarRepository carRepository;
    private final XmlParser xmlParser;

    @Autowired
    public CarServiceImpl(PartRepository partRepository, ModelMapper modelMapper, CarRepository carRepository, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.modelMapper = modelMapper;
        this.carRepository = carRepository;
        this.xmlParser = xmlParser;
    }

    @Override
    @Transactional
    public void seedCars() throws Exception {

        CarImportRootDto carImportRootDto = this.xmlParser.parseXml(CarImportRootDto.class, CAR_PATH);


        for (CarImportDto carImportDto : carImportRootDto.getCars()) {


            Car car = this.modelMapper.map(carImportDto, Car.class);

            car.setParts(getRandomParts());

            carRepository.saveAndFlush(car);
        }


    }

    @Override
    public String getToyotasEx2() throws JAXBException {


        List<ToyotaExportDto> dtos = this.carRepository
                .findAllByMakeOrderByModelAscTravelledDistanceDesc("Toyota")
                .stream()
                .map(t -> this.modelMapper.map(t, ToyotaExportDto.class))
                .collect(Collectors.toList());


        ToyotaExportRootDto rootDto = new ToyotaExportRootDto();

        rootDto.setCars(dtos);


        this.xmlParser.exportXml(rootDto, ToyotaExportRootDto.class, "src/main/resources/XMLs/Query2.xml");
        return "Check your resource folder for XML file : Query2.xml";
    }

    @Override
    public String carsAndPartsEx4() throws JAXBException {

         List<Car> all = this.carRepository.findAll();

        CarExportRootDto carRootDto = new CarExportRootDto();
        List<CarExportDto> carDto = new ArrayList<>();

        for (Car car : all) {
            CarExportDto carExportDto = this.modelMapper.map(car, CarExportDto.class);
//            PartExportRootDto partExportRootDto = new PartExportRootDto();
//            List<PartExportDto> partExportDtos = new ArrayList<>();
//
//
//            for (Part part : car.getParts()) {
//                PartExportDto map = this.modelMapper.map(part, PartExportDto.class);
//                partExportDtos.add(map);
//            }
//            partExportRootDto.setParts(partExportDtos);
//            carExportDto.setParts(partExportRootDto);

            carDto.add(carExportDto);
        }


        carRootDto.setCars(carDto);

        this.xmlParser.exportXml(carRootDto, CarExportRootDto.class, "src/main/resources/XMLs/Query4.xml");
        return "Check the resource folder for XMl file with name: Query4.xml";
    }


    private Set<Part> getRandomParts() throws Exception {
        Random random = new Random();
//        int randomI = random.nextInt(20)+10;
//        around 6k rows in car_parts if you use the above variable
        Set<Part> toReturn = new HashSet<>();
        for (int i = 0; i < 5 /*randomI*/; i++) {
            long randomParts = (long) (random.nextInt((int) this.partRepository.count())) + 1;
            Optional<Part> one = this.partRepository.findById(randomParts);
            if (one.isPresent()) {
                toReturn.add(one.get());
            } else {
                throw new Exception("Invalid random Part");
            }

        }

        return toReturn;
    }
}
