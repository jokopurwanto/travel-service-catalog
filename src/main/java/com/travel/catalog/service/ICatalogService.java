package com.travel.catalog.service;

import com.travel.catalog.dto.CatalogCreateDto;
import com.travel.catalog.dto.CatalogDto;
import com.travel.catalog.model.CatalogModel;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface ICatalogService {
    public CatalogModel createCatalog(CatalogCreateDto catalogCreateDto);
    public Map<String, Object> updateCatalog(CatalogDto catalogDto) throws ParseException;
    public Map<String, Object> deleteCatalog(Integer id);
    public CatalogModel getCatalog(Integer id);
    public List<CatalogModel> getAllCatalog();



}
