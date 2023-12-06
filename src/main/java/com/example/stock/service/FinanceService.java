package com.example.stock.service;

import com.example.stock.model.Company;
import com.example.stock.model.Dividend;
import com.example.stock.model.ScrapedResult;
import com.example.stock.persist.CompanyRepository;
import com.example.stock.persist.DividendRepository;
import com.example.stock.persist.entity.CompanyEntity;
import com.example.stock.persist.entity.DividendEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FinanceService {
    private final CompanyRepository companyRepository;
    private final DividendRepository dividendRepository;
    public ScrapedResult getDividendByCompanyName(String companyName){
        // 1. 회사명을 기준으로 회사 정보 조회
         CompanyEntity company =this.companyRepository.findByName(companyName)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회사명입니다."));
        // 2. 조회된 회사 id로 배당금 정보 조회
        List< DividendEntity> dividendEntityList=dividendRepository.findAllByCompanyId(company.getId());

        // 3. 결과 조합 후 반환
        List<Dividend> dividends = dividendEntityList.stream()
                .map(e -> Dividend.builder()
                        .date(e.getDate())
                        .dividend(e.getDividend())
                        .build())
                .collect(Collectors.toList());

        return new ScrapedResult(Company.builder()
                .ticker(company.getTicker())
                .name(company.getName())
                .build(),
                dividends);
    }
}
