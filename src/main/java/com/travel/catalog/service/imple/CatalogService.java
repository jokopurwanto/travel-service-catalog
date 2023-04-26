package com.travel.catalog.service.imple;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CatalogService implements ICatalogService {

    @Autowired
    CatalogRepository catalogRepository;

//    public void save(CatalogModel catalogModel){
//        catalogRepository.save(catalogModel);
//    }

    @Override
    public CatalogModel createCatalog(CatalogCreateDto catalogCreateDto) {
        CatalogModel catalogModel = CatalogModel.builder()
                .name(catalogCreateDto.getName())
                .price(catalogCreateDto.getPrice())
                .availability(catalogCreateDto.getAvailability())
                .date(catalogCreateDto.getDate())
                .build();
        return catalogRepository.save(catalogModel);
    }

    @Override
    public Map<String, Object>  updateCatalog(CatalogDto catalogDto, Integer id) throws ParseException {

        //jika id pada url path variable salah maka exception
        if(catalogRepository.findById(id).isEmpty())
            throw new CatalogNotFoundException("Data yang dicari tidak ditemukan");

        //jika id tidak di input pada body tidak sama dengan path variable maka exception
        if(catalogDto.getId() != id)
            throw new CatalogNotFoundException("Data id yang di-input belum sesuai");

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate endDate = LocalDate.parse(catalogDto.getEndDate().toString(), formatter);
        final LocalDate startDate = LocalDate.parse(catalogDto.getStartDate().toString(), formatter);
        final long days = ChronoUnit.DAYS.between(startDate, endDate);

        //print data calender
        System.out.println("Start Date :"+catalogDto.getStartDate());
        System.out.println("End Date :"+catalogDto.getEndDate());
        System.out.println("Start Date Conv :"+startDate);
        System.out.println("End Date Conv :"+endDate);
        System.out.println("Total Book Days :"+days);


        //validasi cek availability
        CatalogModel catalogModel1 = catalogRepository.findById(id).get();
        System.out.println("Total Avail :"+catalogModel1.getAvailability());
        Integer availability = catalogModel1.getAvailability();
        Integer cekAvail = Math.toIntExact(availability-days);
        System.out.println("Cek Avail : "+availability+" - "+days+" = "+cekAvail);

//        if(availability == 0){
//            throw new CatalogNotFoundException("Full, saat belum ada yang kosong");
//        } else if (cekAvail < 0) {
//            throw new CatalogNotFoundException("Sisa ketersediaam saat ini : "+availability);
//        }
        if(availability == 0){
            throw new CatalogNotFoundException("Full, saat belum ada yang kosong");
        }


        Integer priceMdl = Integer.parseInt(catalogDto.getPrice());
        Integer totalPersonMdl = catalogDto.getTotalPerson();
        Integer totalPrice = priceMdl * totalPersonMdl;
        Integer totalBook = Math.toIntExact(totalPersonMdl * days);
        System.out.println("Total Book : "+totalPersonMdl+" * "+days+" = "+totalBook);

        LocalDate date = startDate;
        Integer cekAvailbility;
        Integer avail;
        while (date.isBefore(endDate) || date.equals(endDate)) {
            System.out.println("Tanggal Counter : "+ date);
            Date dateTmp = new SimpleDateFormat("yyyy-MM-dd").parse(date.toString());
            System.out.println("Tanggal CounterTmp : "+ date);
//            CatalogModel catalogMdl = (CatalogModel) catalogRepository.findByName(catalogDto.getName());
//            CatalogModel catalogMdl = (CatalogModel) catalogRepository.findByDate(dateTmp);
            if(catalogRepository.findByNameAndDate(catalogDto.getName(), dateTmp) == null)
                throw new CatalogNotFoundException("Data yang dicari tidak ditemukan dengan tujuan " + catalogDto.getName() + " tanggal " + date);
            CatalogModel catalogMdl = (CatalogModel) catalogRepository.findByNameAndDate(catalogDto.getName(), dateTmp);
            System.out.println("ketersedian : " + catalogMdl.getAvailability());
            avail = catalogMdl.getAvailability();
            cekAvailbility = Math.toIntExact(avail-totalBook);
            System.out.println("Cek Availbility : "+avail+" - "+totalBook+" = "+ cekAvailbility);
           if (cekAvailbility < 0) {
                throw new CatalogNotFoundException("Sisa ketersediaam saat ini : "+avail+" untuk tanggal "+date);
            }

//           catalogMdl.setId(catalogDto.getId());
//           catalogMdl.setName(catalogDto.getName());
//           catalogMdl.setPrice(catalogDto.getPrice());
//           catalogMdl.setAvailability(cekAvailbility);
//           System.out.println("before insert tgl");
           java.sql.Date sqlDate = java.sql.Date.valueOf(date);
//           catalogMdl.setDate(sqlDate);
//
//           catalogRepository.save(catalogMdl);

            CatalogModel catalogModel2 = CatalogModel.builder()
                .id(catalogMdl.getId())
                .name(catalogMdl.getName())
                .price(catalogMdl.getPrice())
                .availability(cekAvailbility)
                .date(sqlDate)
                .build();
            catalogRepository.save(catalogModel2);

//            catalogRepository.updateByNameAndDate(cekAvailbility,catalogDto.getName(),dateTmp);

            date = date.plusDays(1);
        }


        //jika id sesuai maka data akan diupdate
//        CatalogModel catalogModel = CatalogModel.builder()
//                .id(catalogDto.getId())
//                .name(catalogDto.getName())
//                .price(catalogDto.getPrice())
//                .availability(cekAvail)
//                .date(catalogDto.getStartDate())
//                .build();
//        return catalogRepository.save(catalogModel);
        Map<String,Object> response = new HashMap<>();
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
