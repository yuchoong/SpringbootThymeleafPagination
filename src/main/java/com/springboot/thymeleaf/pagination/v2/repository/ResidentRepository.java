package com.springboot.thymeleaf.pagination.v2.repository;

import com.springboot.thymeleaf.pagination.v2.model.Resident;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResidentRepository extends PagingAndSortingRepository<Resident, Integer> {

}
