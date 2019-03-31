package devfun.bookstore.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
public class AppConfig {
    // 데이터베이스 접속, 트랜잭션 관리, DAO Service를 정의하는 부분 추가

    @Bean
    public DataSource dataSource(){
        // 메모리 방시기기 때문에 애플리케이션이 실행될때 HSQLDB가 초기화되면서 메모리에 적재된다. (실행할 때마다 스키마 정보와 데이터를 적재시켜야함)
        return new EmbeddedDatabaseBuilder()
                .setName("testdb") // DB 이름 설정
                .setType(EmbeddedDatabaseType.HSQL) // DB 종류 설정
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }
}
