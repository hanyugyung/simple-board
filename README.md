# simple-board
간단 게시판 입니다.

---

### 주요 개발 환경

(back-end)

- Java 17
- Spring Boot 3.1.5
- Gradle 7.4


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
- 파일 업로드 위치 : 프로젝트 폴더 내의 upload-file 폴더 (없으면 스스로 생성) 내부에 생성


---

### 구현 기능

- 회원 가입
- 토큰 발급
- 내 정보 조회
- 특정 사용자 정보 조회
- 내 정보 수정
- 게시판 글 작성
- 게시판 글 목록 보기(with paging)
- 게시판 글 업데이트
- 게시판 글 삭제
- 댓글 작성
- 댓글 삭제
- 파일 업로드
- 게시글 좋아요/좋아요 취소

### 사용자 에러 정의 규칙

- http 에러코드를 따르지만 http 에러코드는 의미가 포괄적이라 모든 비즈니스 상황에 대처할 수 없으므로, 상세 코드와 같이 응답값을 내려준다
- 비즈니스 상 발생할 수 있는 오류는 200 이외의 에러코드를 내려주면서 아래 예시처럼 result success 와 서버에서 정의된 상세코드를 준다
```
{
  "result": "SUCCESS", // 비즈니스 상 성공
  "data": null,
  "errorCode": "90300", // 상세코드
  "errorMessage": "인증이 필요합니다." // 상세메시지
}
```
- 비즈니스 오류가 아닌 클라이언트 호출 오류는 result fail 와 스프링에서 exception 으로 정의된 메시지를 준다.
- 상세코드 또한 http 에러코드 그대로 내려준다
```
{
  "result": "FAIL",
  "data": null,
  "errorCode": "400",
  "errorMessage": "[로그인 아이디는 필수 입력 값 입니다.]"
}
```

### 상세 에러 코드 및 메시지

- 스웨거로 작성하는데에 번거로움이 있어, 아래 블록으로 대체합니다.
- 현행화가 안될 수도 있어서 소스코드의 CommonResponse 클래스의 하단을 참고해주세요.
```
SUCCESS("90000", "Request Success")
USER_LOGIN_ID_ALREADY_EXISTED("90100", "이미 존재하는 계정입니다.")
USER_FAIL_LOGIN("90101", "로그인에 실패하였습니다.")
INVALID_PARAMETER("90200", "유효하지 않은 파라미터입니다.")
USER_FAIL_AUTHORIZATION("90300", "인증이 필요합니다.")
USER_FAIL_ACCESS("90301", "권한이 필요합니다.")
NOT_FOUND_BOARD("90400", "해당 게시글은 삭제되었거나 존재하지 않는 게시글입니다.")
INVALID_ACCESS_TO_BOARD("90401", "작성자 외에 게시물을 수정하거나 삭제할 수 없습니다.")
NOT_FOUND_COMMENT("90402", "해당 댓글은 이미 삭제되었거나 존재하지 않습니다.")
ALREADY_LIKED("90403", "이미 좋아요를 눌렀습니다.")
ALREADY_CANCELED("90404", "이미 취소되었습니다.")
INVALID_FILE("90500", "유효하지 않은 파일입니다.")
INTERNAL_SERVER_ERROR("99999", "서버 내부 오류입니다.")
```
