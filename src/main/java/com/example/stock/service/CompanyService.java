package com.example.stock.service;

import com.example.stock.exception.impl.AlreadyExistTickerException;
import com.example.stock.exception.impl.NoCompanyException;
import com.example.stock.exception.impl.NoCompanyTickerException;
import com.example.stock.model.Company;
import com.example.stock.model.ScrapedResult;
import com.example.stock.persist.CompanyRepository;
import com.example.stock.persist.DividendRepository;
import com.example.stock.persist.entity.CompanyEntity;
import com.example.stock.persist.entity.DividendEntity;
import com.example.stock.scrapper.Scraper;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.Trie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CompanyService {
    private final Trie trie;
    private final Scraper yahooFinancialScrapper;

    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    public Company save(String ticker){
        boolean exists = this.companyRepository.existsByTicker(ticker);
        if(exists){
            throw new AlreadyExistTickerException();
        }

        return this.storeCompanyAndDividend(ticker);
    }

    public Page<CompanyEntity> getAllCompany(Pageable pageable){

        return this.companyRepository.findAll(pageable);
    }
    private Company storeCompanyAndDividend(String ticker){
        // ticker 를 기준으로 회사 스크래핑
        Company company = this.yahooFinancialScrapper.scrapCompanyByTicker(ticker);
        if(ObjectUtils.isEmpty(company)){
            throw new NoCompanyTickerException();
        }

        // 해당 회사가 존재할 경우, 회사의 배당금 정보 스크래핑
        ScrapedResult scrapedResult = this.yahooFinancialScrapper.scrap(company);

        // 스크래핑 결과
        CompanyEntity companyEntity = this.companyRepository.save(new CompanyEntity(company));
        List<DividendEntity> dividendEntities =
                scrapedResult.getDividends().stream()
                .map(e -> new DividendEntity(companyEntity.getId(),e))
                .collect(Collectors.toList());

        this.dividendRepository.saveAll(dividendEntities);
        return company;
    }

    public List<String> getCompanyNamesByKeyword(String keyword){
        Pageable limit = PageRequest.of(0,10);
        Page<CompanyEntity> companyEntities =
                this.companyRepository.findByNameStartingWithIgnoreCase(keyword,limit);
        return companyEntities.stream()
                .map(e -> e.getName())
                .collect(Collectors.toList());
    }

    public void addAutocompleteKeyword(String keyword){

        this.trie.put(keyword,null);
    }
    public List<String> autoComplete(String keyword){
        return (List<String>)this.trie.prefixMap(keyword).keySet()
                .stream()
                .collect(Collectors.toList());
    }
    public void deleteAutocompleteKeyword(String keyword){
        this.trie.remove(keyword);
    }

    public String deleteCompany(String ticker) {
        var company = this.companyRepository.findByTicker(ticker)
                .orElseThrow(NoCompanyException::new);
        // 해당 회사의 배당금 지우기
        this.dividendRepository.deleteAllByCompanyId(company.getId());
        // 회사 정보 지우기
        this.companyRepository.delete(company);
        // 자동완성에서 지우기
        this.deleteAutocompleteKeyword(company.getName());

        return company.getName();
    }
}
