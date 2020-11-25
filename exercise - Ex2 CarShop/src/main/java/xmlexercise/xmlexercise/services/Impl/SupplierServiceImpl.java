package xmlexercise.xmlexercise.services.Impl;

import xmlexercise.xmlexercise.domain.dto.export.LocalSupplierDto;
import xmlexercise.xmlexercise.domain.dto.export.LocalSuppliersRootDto;
import xmlexercise.xmlexercise.domain.dto.imports.SupplierImportDto;
import xmlexercise.xmlexercise.domain.dto.imports.SupplierImportRootDto;
import xmlexercise.xmlexercise.domain.entities.Supplier;
import xmlexercise.xmlexercise.domain.repositories.SupplierRepository;
import xmlexercise.xmlexercise.services.SupplierService;
import xmlexercise.xmlexercise.utils.XmlParser;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import javax.xml.bind.JAXBException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final static String SUPPLIER_PATH = "src/main/resources/XMLs/suppliers.xml";
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public SupplierServiceImpl(SupplierRepository supplierRepository, ModelMapper modelMapper, XmlParser xmlParser) {
        this.supplierRepository = supplierRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public void seedSuppliers() throws JAXBException {

        SupplierImportRootDto supplierImportRootDto = this.xmlParser.parseXml(SupplierImportRootDto.class, SUPPLIER_PATH);


        for (SupplierImportDto supplier : supplierImportRootDto.getSuppliers()) {

            this.supplierRepository.saveAndFlush((this.modelMapper.map(supplier, Supplier.class)));
        }


    }

    @Override
    public String getLocalSupplierEx3() throws JAXBException {


        List<LocalSupplierDto> dtos = this.supplierRepository.findAllByImporterIsNot(true)
                .stream()
                .map(s -> this.modelMapper.map(s, LocalSupplierDto.class))
                .collect(Collectors.toList());

        Set<Supplier> allByImporterIs = this.supplierRepository.findAllByImporterIsNot(true);

        int i = 0;
        for (Supplier byImporterIs : allByImporterIs) {
            dtos.get(i++).setPartsCount(byImporterIs.getParts().size());
        }
        LocalSuppliersRootDto rootDto = new LocalSuppliersRootDto();

        rootDto.setSuppliers(dtos);

        this.xmlParser.exportXml(rootDto,LocalSuppliersRootDto.class,"src/main/resources/XMLs/Query3.xml");
        return "Check your resource folder for XML file: Query3.xml";
    }
}
