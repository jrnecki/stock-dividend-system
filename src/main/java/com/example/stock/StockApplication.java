package com.example.stock;

import com.example.stock.model.Company;
import com.example.stock.scrapper.Scraper;
import com.example.stock.scrapper.YahooFinanceScrapper;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.lang.annotation.Documented;

@SpringBootApplication
public class StockApplication {

	public static void main(String[] args) {
		//SpringApplication.run(StockApplication.class, args);

		Scraper yahooFinanceScrapper = new YahooFinanceScrapper();
		var result = yahooFinanceScrapper.scrapCompanyByTicker("MMMM");
		System.out.println(result);

	}

}
