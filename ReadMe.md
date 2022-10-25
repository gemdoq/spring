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
# 관심사의 분리
add()의 Connection만드는 부분과 findById()의 Connection만드는 부분의 중복되는 부분을 분리하자.
리팩토링의 진행단계는 메서드 분리, 클래스 분리, 인터페이스 적용의 단계가 있다.
## method 분리
Connection타입을 반환하는 makeConnection을 메서드로 분리한다.
## abs class 분리 및 상속해서 구현(생략)
Connection타입을 반환하는 makeConnection을 클래스로 분리한다.
## interface 적용
Connection타입을 반환하는 makeConnection을 인터페이스로 만들고 구현한다.
생성자 오버로딩을 통해 DI한다.
# IoC와 전략패턴
UserDao는 전략패턴에서 변하지 않는 중복되는 부분인 context에 해당한다.
## factory, bean
인터페이스를 구현체로 만들고 생성자를 통해 의존성을 주입하는 행위를 factory가 대신함. bean은 기능을 담당하는 각 모듈 유닛 하나하나.
팩토리에 spring을 적용해준다. (annotation @configuration, @bean)
테스트에 spring을 적용해준다. (annotation @extendwith, @contextconfiguration, @autowired)
## 예외처리
예외처리를 해주지 않으면 중간에 에러가 발생해서 객체의 생명이 소실되지 않아, 서버의 자원을 잡아먹게 된다.
즉, 에러가 발생해도 사용이 끝난 객체의 생명을 제어할 수 있도록 예외처리를 해준다.
## deleteAll()과 getCount()추가
## strategy
ps가 중복되기 때문에 인터페이스 psstrategy로 분리
DeleteAllStrategy와 AddStrategy 추가 및 적용 및 테스트
## context 분리
(225)deleteAll에서 context 분리 및 적용 후 인터페이스 statementstrategy 호출
## Datasource 사용
Java에 내장된 인터페이스 DataSource를 각 접속처에 맞게 구현체를 만들어 AWSConnectionMaker를 대체하자(ConnectionMaker대신 DataSource, AWSConnectionMaker대신 AWSDataSource)
팩토리 빈 추가
생성자 매개변수 타입변경
## anonymous 적용
## template callback
