package com.travel.catalog.service;

import com.travel.catalog.dto.CatalogDto;
import com.travel.catalog.model.CatalogModel;

import java.util.List;

public interface ICatalogService {
    public CatalogModel createCatalog(CatalogDto catalogDto);
    public CatalogModel updateCatalog(CatalogDto catalogDto);
    public void deleteCatalog(Integer id);
    public CatalogModel getCatalog(Integer id);
    public List<CatalogModel> getAllCatalog();

}
