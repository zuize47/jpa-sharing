package com.nashtech.sharing.jpa.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.github.cdimascio.dotenv.Dotenv;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.ValidationMode;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;


@ApplicationScoped
@Log4j2
public class Config {

    private static final List<AutoCloseable> autoCloseables = new ArrayList<>();

    @Inject
    public Config () {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> autoCloseables.forEach(this::closeObject)));
    }

    @Produces
    @ApplicationScoped
    Dotenv dotenv () {
        return Dotenv.load();
    }

    @Produces
    @ApplicationScoped
    DataSource dataSource (Dotenv dotenv) {
        var config = new HikariConfig();
        config.setJdbcUrl(dotenv.get("JDBC_URL"));
        config.setUsername(dotenv.get("JDBC_USERNAME"));
        config.setPassword(dotenv.get("JDBC_PASSWORD"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource hikariDataSource = new HikariDataSource(config);
        autoCloseables.add(hikariDataSource);
        return hikariDataSource;
    }

    @Produces
    @ApplicationScoped
    EntityManagerFactory entityManagerFactory (DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("jakarta.persistence.nonJtaDataSource", dataSource);
        properties.put("jakarta.persistence.schema-generation.create-source", "metadata");
        properties.put("jakarta.persistence.schema-generation.drop-source", "metadata");
        properties.put("jakarta.persistence.schema-generation.scripts.action", "drop-and-create");
        properties.put("jakarta.persistence.schema-generation.scripts.create-target", "schema.sql");
        properties.put("jakarta.persistence.schema-generation.scripts.drop-target", "drop.sql");
        properties.put("jakarta.persistence.transactionType", "RESOURCE_LOCAL");
        properties.put("jakarta.persistence.validation.mode", ValidationMode.CALLBACK.name());
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.enable_lazy_load_no_trans", "true");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comments", "true");
        EntityManagerFactory entityManagerFactory = Persistence
            .createEntityManagerFactory("CDI", properties);
        autoCloseables.add(entityManagerFactory);
        return entityManagerFactory;

    }

    @SneakyThrows
    private void closeObject (AutoCloseable closeable) {
        closeable.close();
    }

}
