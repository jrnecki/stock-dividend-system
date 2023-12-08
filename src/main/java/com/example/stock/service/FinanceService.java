package com.example.stock.service;

import com.example.stock.model.Company;
import com.example.stock.model.Dividend;
import com.example.stock.model.ScrapedResult;
import com.example.stock.model.constants.CacheKey;
import com.example.stock.persist.CompanyRepository;
import com.example.stock.persist.DividendRepository;
import com.example.stock.persist.entity.CompanyEntity;
import com.example.stock.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    // 캐시 기준
    // 요청이 자주 들어오는가?
    // 자주 변경되는 데이터 인가?
    @Cacheable(key="#companyName", value= CacheKey.KEY_FINANCE)
    public ScrapedResult getDividendByCompanyName(String companyName){
        log.info("serach company ->" + companyName);
        // 1. 회사명을 기준으로 회사 정보 조회
         CompanyEntity company =this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));
        // 2. 조회된 회사 id로 배당금 정보 조회
        List< DividendEntity> dividendEntityList=dividendRepository.findAllByCompanyId(company.getId());

        // 3. 결과 조합 후 반환
        List<Dividend> dividends = dividendEntityList.stream()
                .map(e -> new Dividend(e.getDate(),e.getDividend()))
                .collect(Collectors.toList());

        return new ScrapedResult(
                new Company(company.getTicker(),company.getName()), dividends);
    }
}
