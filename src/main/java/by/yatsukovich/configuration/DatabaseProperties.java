package by.yatsukovich.configuration;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:database.properties")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class DatabaseProperties {

    @Value("${POSTRGES_DRIVER_NAME}")
    private String driverName;

    @Value("${DATABASE_URL}")
    private String url;

    @Value("${DATABASE_PORT}")
    private String port;

    @Value("${DATABASE_NAME}")
    private String name;

    @Value("${DATABASE_LOGIN}")
    private String login;

    @Value("${DATABASE_PASSWORD}")
    private String password;

    @Value("${JDBC_URL}")
    private String jdbcUrl;

    @Value("${POOL_SIZE}")
    private Integer poolSize;
}
