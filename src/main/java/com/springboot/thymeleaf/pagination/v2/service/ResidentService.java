package com.springboot.thymeleaf.pagination.v2.service;

import com.springboot.thymeleaf.pagination.v2.model.Resident;
import com.springboot.thymeleaf.pagination.v2.repository.ResidentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// Causes Lombok to generate a logger field.
@Slf4j
@Service
public class ResidentService {

    @Autowired
    private ResidentRepository repository;

    public void save(final Resident resident) {
        repository.save(resident);
    }

    public long getResidentsCount() {
        log.info("Finding the total count of residents from the dB.");
        return repository.count();
    }

    public Page<Resident> getPaginatedResidents(final int pageNumber, final int pageSize) {
        log.info("Fetching the paginated residents from the dB.");
        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        return repository.findAll(pageable);
    }

    public Resident getSelectedResident(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
    }

    public void updateResident(Resident resident) {
        repository.save(resident);
    }

    public void deleteResident(int id) {
        repository.deleteById(id);
    }
}
