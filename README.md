# 📈 stock-dividend-system
미국 주식 배당금 사이트(yahoo finance)를 스크래핑하여 정보를 제공하는 서비스입니다.
- jwt토큰을 발행하여 member_role에 따라 인증,인가를 구현하였습니다.
- 트리 알고리즘을 이용하여 자동완성 기능을 구현하였습니다.
- 캐시를 이용하여 즐겨찾기 기능을 구현하였습니다.
## Stack
- Spring Boot
- java
- JPA
- H2
- Redis
- Jsoup

## Structure
![image](https://github.com/jrnecki/stock-dividend-system/assets/129943670/c5ec9f36-81c0-4124-93b6-32f8e5e3ff1c)
### Model
<img width="450" alt="image" src="https://github.com/jrnecki/stock-dividend-system/assets/129943670/a5276f62-44a8-4be8-a4c5-7e89c7927906">

## API
### Member
<img width="664" alt="image" src="https://github.com/jrnecki/stock-dividend-system/assets/129943670/e4cc1891-72c7-49a9-8540-8cde68b79922">

### Company
<img width="658" alt="image" src="https://github.com/jrnecki/stock-dividend-system/assets/129943670/a8c72665-d6f3-48e8-a493-cc3e56c80eae">

### Finance
<img width="650" alt="image" src="https://github.com/jrnecki/stock-dividend-system/assets/129943670/7cefed5d-7303-4c8c-b940-ef0c0c219e2f">
