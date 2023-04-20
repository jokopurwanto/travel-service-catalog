package com.travel.catalog.controller;

import com.travel.catalog.dto.CatalogDto;
import com.travel.catalog.handler.RespHandler;
import com.travel.catalog.model.CatalogModel;
import com.travel.catalog.service.imple.CatalogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("api")
public class CatalogController {

    @Autowired
    CatalogService catalogService;

    @GetMapping("/catalog")
    public List<CatalogModel> list(){
        return catalogService.getAllCatalog();
    }

//    @GetMapping("/catalog/{id}")
//    public ResponseEntity<CatalogModel> get(@PathVariable Integer id){
//        try {
//            CatalogModel catalogModel = catalogService.getCatalog(id);
//            return new ResponseEntity<CatalogModel>(catalogModel, HttpStatus.OK);
//        } catch (NoSuchElementException e){
//            return new ResponseEntity<CatalogModel>(HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/catalog/{id}")
    public ResponseEntity<?> update(@RequestBody CatalogModel catalogModel, @PathVariable Integer id){
        try {
            CatalogModel existCatalog = catalogService.getCatalog(id);
            catalogService.save(catalogModel);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/catalog/{id}")
    public void delete(@PathVariable Integer id) {
        catalogService.deleteCatalog(id);
    }

//    @PostMapping("/catalog")
//    public void add(@RequestBody CatalogModel catalogModel){
//        catalogService.save(catalogModel);
//    }

    @PostMapping("/catalog")
    public ResponseEntity<CatalogModel> saveCat(@RequestBody @Valid CatalogDto catalogDto){
        return new ResponseEntity<>(catalogService.createCatalog(catalogDto), HttpStatus.CREATED);
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<Object> getCatalogDetails(@PathVariable Integer id){
        return RespHandler.responseBuilder("sukses, berikut detail data catalog",HttpStatus.OK, catalogService.getCatalog(id));
    }

}
