## - 웹 크롤링

JSoup

### [GET] /admin/crawl-lotto-spot 

> 1등 당첨지역 크롤링, json 파일생성

### [GET] /, /top-stores

> 1등 당첨지역 검색, 조회

---

## - AI 프롬프트 api 호출 테스트

Spring Ai + Google Vertex Ai Gemini

### [GET] http://localhost:8070/api/chat

``` json
{
    "message" : "너는 이름이 왜 잼민이야?"
}
```

``` curl
curl --location --request GET 'http://localhost:8070/api/chat' \
--header 'Content-Type: application/json' \
--data '{
    "message" : "너는 이름이 왜 잼민이야?"
}'
```


## 참고

- https://docs.spring.io/spring-ai/reference/index.html
- https://ai.google.dev/gemini-api/docs
- https://velog.io/@hanni/Spring-Boot3-Spring-AI를-활용한-초간단-Chat-Model-API-구현하기-Gemini-ChatGPT
- https://kjs990114.tistory.com/55