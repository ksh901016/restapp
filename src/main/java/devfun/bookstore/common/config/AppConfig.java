package devfun.bookstore.common.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@MapperScan("devfun.bookstore.common.mapper")
@Configuration
public class AppConfig {
    // 데이터베이스 접속, 트랜잭션 관리, DAO Service를 정의하는 부분 추가

    @Bean
    public DataSource dataSource(){
        // 메모리 방식이기 때문에 애플리케이션이 실행될때 HSQLDB가 초기화되면서 메모리에 적재된다.
        // (실행할 때마다 스키마 정보와 데이터를 적재시켜야함)
        return new EmbeddedDatabaseBuilder()
                .setName("testdb") // DB 이름 설정
                .setType(EmbeddedDatabaseType.HSQL) // DB 종류 설정
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        return sessionFactory.getObject();
    }
}
