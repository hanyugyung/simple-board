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
- ddl : 서버 실행 후 코드 내의 ddl.sql 파일로 테이블을 만들어주세요
- 서버 start
  1. 소스 코드 clone 후 gradle build
  2. jdk 17 환경변수로 설정되어있는 것은 필수~!
  3. cmd 켜서 java -jar (jar 파일명).jar
  4. 이 과정이 싫다면 ide 에서 서버 실행~!~!
- 처음 api 테스트 시 준비 사항
  1. api/user/sign-up 에서 회원가입 먼저 해야함
  2. api/*/login 에서 로그인 토큰 발급
  3. swagger openapi 에 토큰 등록? 후 다른 api 테스트 가능!
- 파일 업로드 요청 시 확인할 점
  1. 저장 위치는 windows 기준 C:\board\upload-image 으로 잡았는데 mac 의 경우 파일 저장 경로와 파일서비스 내의 파일 구분자를 mac 기준으로 변경해야합니다. 


---

### 구현 기능

- 회원 가입
- 토큰 발급
- 내 정보 조회
- 게시판 글 작성
- 게시판 글 목록 보기
- 게시판 글 업데이트
- 게시판 글 삭제
- 댓글 작성
- 파일 업로드
