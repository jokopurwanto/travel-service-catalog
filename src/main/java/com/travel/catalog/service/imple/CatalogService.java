package com.travel.catalog.service.imple;

import com.travel.catalog.dto.CatalogDto;
import com.travel.catalog.model.CatalogModel;
import com.travel.catalog.repository.CatalogRepository;
import com.travel.catalog.service.ICatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService implements ICatalogService {

    @Autowired
    CatalogRepository catalogRepository;

    public void save(CatalogModel catalogModel){
        catalogRepository.save(catalogModel);
    }

    @Override
    public CatalogModel createCatalog(CatalogDto catalogDto) {
        CatalogModel catalogModel = CatalogModel.builder()
                .name(catalogDto.getName())
                .price(catalogDto.getPrice())
                .availability(catalogDto.getAvailability())
                .date(catalogDto.getStartDate())
                .build();
        return catalogRepository.save(catalogModel);
    }

    @Override
    public CatalogModel updateCatalog(CatalogDto catalogDto) {
        return null;
    }

    @Override
    public void deleteCatalog(Integer id) {
        catalogRepository.deleteById(id);
    }

    @Override
    public CatalogModel getCatalog(Integer id) {
        return catalogRepository.findById(id).get();
    }

    @Override
    public List<CatalogModel> getAllCatalog() {
        return catalogRepository.findAll();
    }
}
