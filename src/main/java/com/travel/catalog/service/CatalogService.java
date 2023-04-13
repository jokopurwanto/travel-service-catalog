package com.travel.catalog.service;

import com.travel.catalog.model.CatalogModel;
import com.travel.catalog.repository.CatalogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    public List<CatalogModel> listAll(){
        return catalogRepository.findAll();
    }

    public CatalogModel get(Integer id){
        return catalogRepository.findById(id).get();
    }

    public void save(CatalogModel catalogModel){
        catalogRepository.save(catalogModel);
    }

    public void delete(Integer id){
        catalogRepository.deleteById(id);
    }

}
