package com.travel.catalog.service.imple;

import com.travel.catalog.dto.CatalogCancelOrder;
import com.travel.catalog.dto.CatalogCreateDto;
import com.travel.catalog.dto.CatalogDto;
import com.travel.catalog.handler.CatalogNotFoundException;
import com.travel.catalog.model.CatalogModel;
import com.travel.catalog.repository.CatalogRepository;
import com.travel.catalog.service.ICatalogService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
public class CatalogService implements ICatalogService {

    @Autowired
    CatalogRepository catalogRepository;

//    public void save(CatalogModel catalogModel){
//        catalogRepository.save(catalogModel);
//    }

    @Override
    public CatalogModel createCatalog(CatalogCreateDto catalogCreateDto) {
        //convert date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(catalogCreateDto.getDate().toString(), formatter);
        LocalDate localDate = LocalDate.now();

        //validasi current and start date
        if(localDate.isAfter(date))
            throw new CatalogNotFoundException("Tanggal tidak valid");

        //save data db
        CatalogModel catalogModel = CatalogModel.builder()
                .name(catalogCreateDto.getName())
                .price(catalogCreateDto.getPrice())
                .availability(catalogCreateDto.getAvailability())
                .date(catalogCreateDto.getDate())
                .build();
        return catalogRepository.save(catalogModel);
    }

    @Override
    public Map<String, Object> cancelBooking(CatalogCancelOrder catalogCancelOrder) throws ParseException {
        //convert date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = LocalDate.parse(catalogCancelOrder.getEndDate().toString(), formatter);
        LocalDate startDate = LocalDate.parse(catalogCancelOrder.getStartDate().toString(), formatter);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        Integer availbility, addAvailbility;
        LocalDate date = startDate;
        LocalDate localDate = LocalDate.now();
        Map<String,Object> response = new LinkedHashMap<>();

        //validasi current and start date
        if(localDate.isAfter(startDate))
            throw new CatalogNotFoundException("Tanggal tidak valid");

        //validasi start dan end date
        if(startDate.equals(endDate))
            throw new CatalogNotFoundException("Tanggal Start dan End Date tidak bisa sama");

        //total return availbility
        availbility = Math.toIntExact(catalogCancelOrder.getTotalPerson() * days);

        //loop start to end date
        while (date.isBefore(endDate) || date.equals(endDate)) {

            //convert date
            Date dateTmp = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());

            //create object catalog model
            CatalogModel catalogMdl = (CatalogModel) catalogRepository.findByNameAndDate(catalogCancelOrder.getName(), dateTmp);

            //add current avail + total return availbility
            addAvailbility = catalogMdl.getAvailability() + availbility;

            //update data
            java.sql.Date sqlDate = java.sql.Date.valueOf(date);
            CatalogModel catalogMdlSave = CatalogModel.builder()
                    .id(catalogMdl.getId())
                    .name(catalogMdl.getName())
                    .price(catalogMdl.getPrice())
                    .availability(addAvailbility)
                    .date(sqlDate)
                    .build();
            catalogRepository.save(catalogMdlSave);

            //counter date plus 1
            date = date.plusDays(1);
        }

        response.put("destination", catalogCancelOrder.getName());
        response.put("startDate",catalogCancelOrder.getStartDate());
        response.put("endDate",catalogCancelOrder.getEndDate());
        response.put("totalPerson", catalogCancelOrder.getTotalPerson());
        return response;
    }

    @Override
    public Map<String, Object> updateCatalog(CatalogDto catalogDto) throws ParseException {

        //convert date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = LocalDate.parse(catalogDto.getEndDate().toString(), formatter);
        LocalDate startDate = LocalDate.parse(catalogDto.getStartDate().toString(), formatter);
        long days = ChronoUnit.DAYS.between(startDate, endDate);
        LocalDate localDate = LocalDate.now();

        //math total book
        Integer priceMdl = Integer.parseInt(catalogDto.getPrice());
        Integer totalPersonMdl = catalogDto.getTotalPerson();
        Integer totalPrice = priceMdl * totalPersonMdl;
        Integer totalBooks = Math.toIntExact(totalPersonMdl * days);

        //convert localdate & variabel validasi availbility
        LocalDate date = startDate;
        Integer cekAvailbility, avail;

        //print date calender & total book
        System.out.println("Start Date :"+catalogDto.getStartDate());
        System.out.println("End Date :"+catalogDto.getEndDate());
        System.out.println("Start Date Conv :"+startDate);
        System.out.println("End Date Conv :"+endDate);
        System.out.println("Total Days :"+days);
        System.out.println("Total Book : "+totalPersonMdl+" * "+days+" = "+totalBooks);

        //validasi current and start date
        if(localDate.isAfter(startDate))
            throw new CatalogNotFoundException("Tanggal tidak valid");

        //validasi tanggal start dan end date
        if(startDate.equals(endDate))
            throw new CatalogNotFoundException("Tanggal Start dan End Date tidak bisa sama");

        while (date.isBefore(endDate) || date.equals(endDate)) {
            System.out.println("Tanggal Counter : "+ date);
            Date dateTmp = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
            System.out.println("Tanggal CounterTmp : "+ date);

            //get data if null
            if(catalogRepository.findByNameAndDate(catalogDto.getName(), dateTmp) == null)
                throw new CatalogNotFoundException("Data yang dicari tidak ditemukan dengan tujuan " + catalogDto.getName() + " tanggal " + date);

            //get data if not null
            CatalogModel catalogMdl = (CatalogModel) catalogRepository.findByNameAndDate(catalogDto.getName(), dateTmp);
            System.out.println("ketersedian : " + catalogMdl.getAvailability());

            //validasi availbility
            avail = catalogMdl.getAvailability();
            cekAvailbility = Math.toIntExact(avail-totalBooks);
            System.out.println("Cek Availbility : "+avail+" - "+totalBooks+" = "+ cekAvailbility);
            if(avail == 0)
                throw new CatalogNotFoundException("Full, saat ini belum ada yang kosong untuk tanggal " + date);
            if (cekAvailbility < 0)
                throw new CatalogNotFoundException("Ketersedian untuk tanggal "+date+" tersisa "+avail);

            //update data
            java.sql.Date sqlDate = java.sql.Date.valueOf(date);
            CatalogModel catalogMdlSave = CatalogModel.builder()
                .id(catalogMdl.getId())
                .name(catalogMdl.getName())
                .price(catalogMdl.getPrice())
                .availability(cekAvailbility)
                .date(sqlDate)
                .build();
            catalogRepository.save(catalogMdlSave);

            //counter date plus 1
            date = date.plusDays(1);
        }

        //result function
        Map<String,Object> response = new HashMap<>();
        response.put("destination",catalogDto.getName());
        response.put("totalPrice", totalPrice);
        response.put("totalPerson", totalPersonMdl);
        response.put("totalDays", days);
        response.put("startDate", startDate);
        response.put("endDate", endDate);
        return response;
    }

    @Override
    public CatalogModel getCatalog(Integer id) {
        if(catalogRepository.findById(id).isEmpty())
            throw new CatalogNotFoundException("Data yang dicari tidak ditemukan");
        return catalogRepository.findById(id).get();
    }

    @Override
    public List<CatalogModel> getAllCatalog() {
        return catalogRepository.findAll();
    }

    @Override
    public Map<String, Object> deleteCatalog(Integer id) {
        if(catalogRepository.findById(id).isEmpty())
            throw new CatalogNotFoundException("Data yang dicari tidak ditemukan");

        CatalogModel catalogModel = catalogRepository.findById(id).get();
        Map<String,Object> response = new HashMap<>();
        response.put("id", catalogModel.getId());
        response.put("nama", catalogModel.getName());
        response.put("harga", catalogModel.getPrice());
        response.put("tanggal",catalogModel.getDate());
        catalogRepository.deleteById(id);
        return response;
    }
}
