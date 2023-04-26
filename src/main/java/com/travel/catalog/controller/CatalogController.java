package com.travel.catalog.controller;

import com.travel.catalog.dto.CatalogCreateDto;
import com.travel.catalog.dto.CatalogDto;
import com.travel.catalog.handler.RespHandler;
import com.travel.catalog.model.CatalogModel;
import com.travel.catalog.service.imple.CatalogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("api")
public class CatalogController {

    @Autowired
    CatalogService catalogService;


//    @GetMapping("/catalog/{id}")
//    public ResponseEntity<CatalogModel> get(@PathVariable Integer id){
//        try {
//            CatalogModel catalogModel = catalogService.getCatalog(id);
//            return new ResponseEntity<CatalogModel>(catalogModel, HttpStatus.OK);
//        } catch (NoSuchElementException e){
//            return new ResponseEntity<CatalogModel>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @PutMapping("/catalog/{id}")
//    public ResponseEntity<?> update(@RequestBody CatalogModel catalogModel, @PathVariable Integer id){
//        try {
//            CatalogModel existCatalog = catalogService.getCatalog(id);
//            catalogService.save(catalogModel);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (NoSuchElementException e){
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @DeleteMapping("/catalog/{id}")
//    public void delete(@PathVariable Integer id) {
//        catalogService.deleteCatalog(id);
//    }

//    @PostMapping("/catalog")
//    public void add(@RequestBody CatalogModel catalogModel){
//        catalogService.save(catalogModel);
//    }

    @PostMapping("/catalog")
    public ResponseEntity<Object> createCatalogDetails(@RequestBody @Valid CatalogCreateDto catalogCreateDto){
        return RespHandler.responseBuilder("sukses, data telah berhasil di-simpan",HttpStatus.OK, catalogService.createCatalog(catalogCreateDto));
    }

    @PutMapping("/catalog/{id}")
    public ResponseEntity<?> updateCatalogDetails(@RequestBody @Valid CatalogDto catalogDto, @PathVariable Integer id) throws ParseException {
        return RespHandler.responseBuilder("sukses, data catalog telah berhasil di-update",HttpStatus.OK, catalogService.updateCatalog(catalogDto,id));
    }

    @GetMapping("/catalog/{id}")
    public ResponseEntity<Object> getCatalogDetails(@PathVariable Integer id){
        return RespHandler.responseBuilder("sukses, berikut detail data catalog",HttpStatus.OK, catalogService.getCatalog(id));
    }

    @GetMapping("/catalog")
    public ResponseEntity<Object> listCatalogDetails(){
        return RespHandler.responseBuilder("sukses, berikut list semua data catalog",HttpStatus.OK, catalogService.getAllCatalog());
    }

    @DeleteMapping("/catalog/{id}")
    public ResponseEntity<Object> deleteCatalogDetails(@PathVariable Integer id) {
        return RespHandler.responseBuilder("sukses, data telah berhasil di-delete",HttpStatus.OK, catalogService.deleteCatalog(id));
    }

}
