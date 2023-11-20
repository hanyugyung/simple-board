# simple-board
간단 게시판 입니다.

---

### 주요 개발 환경

(back-end)

Java 17
Spring Boot 3.1.5
Gradle 7.4


---

### 테스트 방법

- 스웨거 주소 : localhost:8080/api-docs
- h2 console 주소 : localhost:8080/h2-console
- 서버 start
  1. 소스 코드 clone 후 gradle build
  2. jdk 17 환경변수로 설정되어있는 것은 필수~!
  3. cmd 켜서 java -jar (jar 파일명).jar
  4. 이 과정이 싫다면 ide 에서 서버 실행~!~!
- 처음 api 테스트 시 준비 사항
  1. api/user/sign-up 에서 회원가입 먼저 해야함
  2. api/*/login 에서 로그인 토큰 발급
  3. swagger openapi 에 토큰 등록? 후 다른 api 테스트 가능!


---

### 구현 기능

- 회원 가입
- 토큰 발급
- 게시판 글 작성
- 게시판 글 목록 보기
- 게시판 글 업데이트
- 게시판 글 삭제
- 댓글 작성(wip)
