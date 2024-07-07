package pl.otto.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import pl.otto.ecommerce.catalog.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class SqlTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup() {
        jdbcTemplate.execute("DROP TABLE `product_catalog__products` IF EXISTS");
        var sql = """
                CREATE TABLE `product_catalog__products` (
                id VARCHAR(100) NOT NULL,
                name VARCHAR(200) NOT NULL,
                description VARCHAR(255),
                price DECIMAL(12,2),
                PRIMARY KEY(id)
                );
                """;
        jdbcTemplate.execute(sql);
    }

    @Test
    void itQueryCurrDateViaSql() {
        var sql = """
            Select now() curr_date;
            """;

        var result = jdbcTemplate.queryForObject(sql, String.class);

        assert result.contains("2024");
    }

    @Test
    void itCreatesTable() {
        jdbcTemplate.execute("DROP TABLE `product_catalog__products` IF EXISTS");
        var sql = """
                CREATE TABLE `product_catalog__products` (
                id VARCHAR(100) NOT NULL,
                name VARCHAR(200) NOT NULL,
                description VARCHAR(255),
                price DECIMAL(12,2),
                PRIMARY KEY(id)
                );
                """;
        jdbcTemplate.execute(sql);
        var result = jdbcTemplate.queryForObject(
                "SELECT count(*) from `product_catalog__products`",
                Integer.class);
        assertThat(result).isEqualTo(0);
    }

    @Test
    void itInsertElement() {

        var sql = """
                INSERT INTO `product_catalog__products` (id,name,description,price)
                VALUES
                    ('12sd2d','example product X','nice one', 10.10),
                    ('945scx','example product Y','bad one', 20.20);
                """;
        jdbcTemplate.execute(sql);

        var result = jdbcTemplate.queryForObject("SELECT count(*) from `product_catalog__products`",Integer.class);
        assertThat(result).isEqualTo(2);
    }

    @Test
    void itInsertDynamicElement(){
        var product = new Product(UUID.randomUUID(),"Ex 1","Nice one");
        product.changePrice(BigDecimal.valueOf(111.11));

        var sql = """
                INSERT INTO `product_catalog__products` (id,name,description,price)
                VALUES
                    (?,?,?,?)
                """;
        jdbcTemplate.update(sql, product.getId(),product.getName(),product.getDescription(),product.getPrice());
        var result = jdbcTemplate.queryForObject("SELECT count(*) from `product_catalog__products`", Integer.class);
        assertThat(result).isEqualTo(1);


    }

    @Test
    void itSelectAll() {
        var sql = """
                INSERT INTO `product_catalog__products`(id, name, description, price)
                VALUES 
                    ('asdasd4', 'example product X', 'nice one', 10.10),
                    ('5g8dff4', 'example product Y', 'nice one', 20.20);
                """;
        jdbcTemplate.update(sql);

        List<Map<String, Object>> maps = jdbcTemplate.queryForList("Select *  from `product_catalog__products`");
        assertThat(maps)
                .hasSize(2)
                .extracting("NAME")
                .contains("example product X");

    }
    @Test
    void itSelectWithConditions() {
        var sql = """
                INSERT INTO `product_catalog__products` (id, name, description, price)
                VALUES
                    ('asdasd4', 'example product X', 'nice one', 10.10),
                    ('5g8dff4', 'example product Y', 'nice one', 20.20);
                """;
        jdbcTemplate.update(sql);

        Product product = jdbcTemplate.queryForObject(
                "Select * from `product_catalog__products` where id = ?",
                new Object[]{"asdasd4"},
                (rs,i) -> {
                    var loaded = new Product(
                            UUID.randomUUID(),
                            rs.getString("NAME"),
                            rs.getString("DESCRIPTION")
                    );
                    loaded.changePrice(rs.getBigDecimal("PRICE"));
                    return loaded;
                }
        );
        assertThat(product.getName()).isEqualTo("example product X");


}}
