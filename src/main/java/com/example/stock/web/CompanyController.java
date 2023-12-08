package com.example.stock.web;

import com.example.stock.model.Company;
import com.example.stock.persist.entity.CompanyEntity;
import com.example.stock.service.CompanyService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company")
@AllArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    @GetMapping("/autocomplete")
    public ResponseEntity<?> autocomplete(@RequestParam String keyword){
        // var result = this.companyService.autoComplete(keyword);
        var result = this.companyService.getCompanyNamesByKeyword(keyword);
        return ResponseEntity.ok(result);

    }
    @GetMapping
    public ResponseEntity<?> searchCompany(final Pageable pageble){
        Page<CompanyEntity> companies = this.companyService.getAllCompany(pageble);
        return ResponseEntity.ok(companies);
    }

    @PostMapping
    public ResponseEntity<?> addCompany(@RequestBody Company request){
        String ticker = request.getTicker().trim();
        if(ObjectUtils.isEmpty(ticker)){
            throw new RuntimeException("ticker is empty()");
        }
        Company company = this.companyService.save(ticker);
        this.companyService.addAutocompleteKeyword(company.getName());
        return ResponseEntity.ok(company);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteCompany(){
        return null;
    }

}
