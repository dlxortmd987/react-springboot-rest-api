package com.prgms.kdt.danawa;

import com.prgms.kdt.danawa.order.repository.OrderJdbcRepository;
import com.prgms.kdt.danawa.order.repository.OrderRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@TestConfiguration
public class JdbcTestConfig {

    @Bean
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .driverClassName("org.h2.Driver")
                .url("jdbc:h2:mem:testdb;MODE=MySQL;DATABASE_TO_LOWER=TRUE")
                .username("sa")
                .password("")
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean
    public OrderRepository orderRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        return new OrderJdbcRepository(namedParameterJdbcTemplate);
    }

}
