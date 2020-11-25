package xmlexercise.xmlexercise.services.Impl;

import xmlexercise.xmlexercise.domain.dto.imports.PartImportDto;
import xmlexercise.xmlexercise.domain.dto.imports.PartImportRootDto;
import xmlexercise.xmlexercise.domain.entities.Part;
import xmlexercise.xmlexercise.domain.entities.Supplier;
import xmlexercise.xmlexercise.domain.repositories.PartRepository;
import xmlexercise.xmlexercise.domain.repositories.SupplierRepository;
import xmlexercise.xmlexercise.services.PartService;
import xmlexercise.xmlexercise.utils.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class PartServiceImpl implements PartService {


    private final String PARTS_PATH = "src/main/resources/XMLs/parts.xml";

    public final PartRepository partRepository;
    public final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, SupplierRepository supplierRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.partRepository = partRepository;
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }


    @Override
    public void seedParts() throws Exception {

        PartImportRootDto importRootDto = this.xmlParser.parseXml(PartImportRootDto.class,PARTS_PATH);


        for (PartImportDto partDto : importRootDto.getParts()) {
            Part part = this.modelMapper.map(partDto, Part.class);

            part.setSupplier(getRandomSupplier());
        this.partRepository.saveAndFlush(part);
        }




    }

    private Supplier getRandomSupplier() throws Exception {

        Random random = new Random();

        int size = random.nextInt((int) this.supplierRepository.count()) + 1;

        Optional<Supplier> supplier = this.supplierRepository.findById((long) size);


        if (supplier.isPresent()) {
            return supplier.get();
        } else {
            throw new Exception("Supplier doesnt exist");
        }


    }


}
