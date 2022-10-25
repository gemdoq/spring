# 스프링에 대하여
## 스프링은 자바로 개발을 빠르고 효율적으로 할 수 있게 틀과 기준을 제시하는 프레임워크다.
스프링의 주요 특징으로는 IoC/DI, 서비스 추상화, AOP(모듈 독립화)다.
# gradle project로 spring
# maven repo에서 다음 라이브러리 build.gradle에 추가
testImplementation group: 'org.springframework.boot', name: 'spring-boot-starter-test', version: '2.7.5'
implementation group: 'org.springframework.boot', name: 'spring-boot-starter-jdbc', version: '2.7.5'
implementation group: 'mysql', name: 'mysql-connector-java', version: '8.0.31'
# domain 디렉토리와 dao 디렉토리 생성
# domain에는 User, dao에는 UserDao를 생성
## add(), findById() 구현
UserDao에는 add()를 구현해본다.
`DAO`를 만들때는 `DB접속 보안`을 중요시 해야한다.
Class.forname으로 드라이버를 실행하고,
SQL의 클래스Drivermanager는 getConnection()메서드를 통해 SQL에 접속할 수 있다.
인터페이스인 Statement를 상속받는 인터페이스 PreparedStatement
UserDao에 findById()를 구현해본다.
ResultSet은 쿼리문 실행 결과를 담는 객체이고, getString()메서드를 통해 값에 접근할 수 있다.
객체를 사용하고 나면, 역순으로 close()메서드를 사용해 중지한다.
## add()에 User타입 객체를 parameter로 받게 수정
# Test 클래스 작성
테스트는 통합테스트와 유닛테스트가 있다.
테스트는 TDD를 하기 위해 만든다.
로직을 테스트하면서 개발하기 때문에 일을 두번하게 된다.
하지만 오류없는 개발을 하기에 좋다.

