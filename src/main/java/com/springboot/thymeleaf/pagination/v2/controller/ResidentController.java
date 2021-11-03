package com.springboot.thymeleaf.pagination.v2.controller;

import com.springboot.thymeleaf.pagination.v2.dto.ResponseDto;
import com.springboot.thymeleaf.pagination.v2.model.Resident;
import com.springboot.thymeleaf.pagination.v2.service.ResidentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

// Causes Lombok to generate a logger field.
@Slf4j
@Controller
public class ResidentController {

    private static final int DEFAULT_PAGE_NUMBER = 1;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Autowired
    private ResidentService service;

    // URL - http://localhost:10091/
    @GetMapping(value = "/")
    public String viewIndexPage(Resident resident) {
        log.info("Redirecting the index page to the controller method for fetching the residents in a paginated fashion.");
        return "redirect:residents/paginated/" + DEFAULT_PAGE_NUMBER + "/" + DEFAULT_PAGE_SIZE;
    }

    @GetMapping(value = "/residents/paginated/{page}/{page-size}")
    public String getPaginatedResidents(@PathVariable(name = "page") final int pageNumber,
                                        @PathVariable(name = "page-size") final int pageSize, final Model model) {
        log.info("Getting the residents in a paginated way for page-number = {} and page-size = {}.", pageNumber, pageSize);
        final Page<Resident> paginatedResidents = service.getPaginatedResidents(pageNumber, pageSize);
        model.addAttribute("responseEntity", createResponseDto(paginatedResidents, pageNumber));
        return "index";
    }

    private ResponseDto createResponseDto(final Page<Resident> residentPage, final int pageNumber) {
        final Map<String, Integer> page = new HashMap<>();
        page.put("currentPage", pageNumber);
        /*
         Here we are fetching the total number of records from the Page interface of the Spring itself.
         We can also customize this logic based on the total number of elements retrieved from the query.
        */
        page.put("totalPages", residentPage.getTotalPages());
        page.put("totalElements", (int) residentPage.getTotalElements());
        return ResponseDto.create(residentPage.getContent(), page);
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") int id, Model model) {
        log.info("Getting the residents in a paginated way for resident id = {}.", id);
        Resident resident = service.getSelectedResident(id);
        log.info("Resident full name = {}.", resident.getFullName());
        model.addAttribute("resident", resident);
        return "update-user";
    }

    //@PostMapping("/update/{id}")
    @RequestMapping(value="/update/{id}", method= RequestMethod.POST, params="action=Update")
    public String updateUser(@Valid Resident resident, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-user";
        }
        log.info("Resident age = {}.", resident.getAge());
        log.info("Resident fullName = {}.", resident.getFullName());
        resident.setFullName(resident.getFullName());
        resident.setEmailAddress(resident.getEmailAddress());
        service.updateResident(resident);
        return "redirect:/residents/paginated/" + DEFAULT_PAGE_NUMBER + "/" + DEFAULT_PAGE_SIZE;
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST, params="action=Delete")
    public String handleDeleteResident(@PathVariable int id) {
        System.out.println("id to delete = " + id);
        service.deleteResident(id);
        System.out.println("successfully deleted id " + id);
        return "redirect:/residents/paginated/" + DEFAULT_PAGE_NUMBER + "/" + DEFAULT_PAGE_SIZE;
    }
}
