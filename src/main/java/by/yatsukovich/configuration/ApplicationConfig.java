package by.yatsukovich.configuration;

import by.yatsukovich.repository.jdbc.CommonJdbcCRUDRepo;
import by.yatsukovich.repository.jdbc.JdbcCRUDRepo;
import by.yatsukovich.repository.jdbc.JdbcConnectionProvider;
import by.yatsukovich.repository.jdbc.JdbcConnectionProviderImpl;
import by.yatsukovich.util.RandomValuesGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class ApplicationConfig {
    @Bean
    JdbcConnectionProvider jdbcConnectionProvider(DatabaseProperties databaseProperties) {
        return new JdbcConnectionProviderImpl(databaseProperties);
    }

    @Bean
    JdbcCRUDRepo jdbcCRUDRepo(JdbcConnectionProvider jdbcConnectionProvider) {
        return new CommonJdbcCRUDRepo(jdbcConnectionProvider);
    }

    @Bean
    public RandomValuesGenerator getRandomGenerator() {
        return new RandomValuesGenerator();
    }
}
