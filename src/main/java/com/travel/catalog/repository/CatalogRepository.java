package com.travel.catalog.repository;

import com.travel.catalog.model.CatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CatalogRepository extends JpaRepository<CatalogModel, Integer> {
    CatalogModel findByNameAndDate(String name, Date date);

//    @Modifying
//    @Query("UPDATE tbl_packages u set u.availability =:availability where u.destination =:name AND u.date =:date")
//    CatalogModel updateByNameAndDate(@Param("availability") Integer availability, @Param("name") String name, @Param("date") Date date);

}
