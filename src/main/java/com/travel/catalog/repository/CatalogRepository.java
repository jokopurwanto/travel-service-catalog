package com.travel.catalog.repository;

import com.travel.catalog.model.CatalogModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CatalogRepository extends JpaRepository<CatalogModel, Integer> {
}
