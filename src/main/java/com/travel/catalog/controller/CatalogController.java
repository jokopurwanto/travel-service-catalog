package com.travel.catalog.controller;

import com.travel.catalog.model.CatalogModel;
import com.travel.catalog.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @GetMapping("/catalog")
    public List<CatalogModel> list(){
        return catalogService.listAll();
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<CatalogModel> get(@PathVariable Integer id){
        try {
            CatalogModel catalogModel = catalogService.get(id);
            return new ResponseEntity<CatalogModel>(catalogModel, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<CatalogModel>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/catalog")
    public void add(@RequestBody CatalogModel catalogModel){
        catalogService.save(catalogModel);
    }

    @PutMapping("/catalog/{id}")
    public ResponseEntity<?> update(@RequestBody CatalogModel catalogModel, @PathVariable Integer id){
        try {
            CatalogModel existCatalog = catalogService.get(id);
            catalogService.save(catalogModel);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/catalog/{id}")
    public void delete(@PathVariable Integer id) {
        catalogService.delete(id);
    }

}
