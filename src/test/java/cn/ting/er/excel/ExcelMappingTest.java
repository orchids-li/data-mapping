package cn.ting.er.excel;

import cn.ting.er.datamapping.Mappings;
import cn.ting.er.datamapping.definition.Mapping;
import cn.ting.er.datamapping.provider.excel.ExcelReader;
import cn.ting.er.datamapping.provider.excel.ExcelWriter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class ExcelMappingTest {
    private Path file;

    @Before
    public void setUp() throws Exception {
        file = Files.createTempFile("", "test.xlsx");
    }

    @After
    public void tearDown() throws Exception {
        Files.delete(file);
    }

    @Test
    public void testReader() throws IOException {
        List<User> users = new ArrayList<>();
        users.add(new User("Lily", 16, "HI", new Date(System.currentTimeMillis() - 300000000)));
        users.add(new User("Lucy", 17, "Hello", new Date(System.currentTimeMillis() - 200000000)));
        users.add(new User("Tom", 18, "Yo", new Date(System.currentTimeMillis() - 100000000)));
        Mapping<User> mapping = Mappings.from(User.class);
        try (ExcelWriter<User> writer = new ExcelWriter<>(file.toFile())) {
            writer.write(users, mapping);
        }
        try (ExcelReader<User> reader = new ExcelReader<>(file.toFile())) {
            List<User> read = reader.read(mapping);
            System.out.println(read);
        }

    }

    @Test
    public void testGroup() throws IOException {
        List<Family> families = new ArrayList<>();
        User mom1 = new User("Lily", 20, "Hi", new Date(System.currentTimeMillis() - 300000000));
        User dad1 = new User("Tom", 21, "Hello", new Date(System.currentTimeMillis() - 200000000));
        User mom2 = new User("Lucy", 22, "Hi", new Date(System.currentTimeMillis() - 100000000));
        families.add(new Family("LT", mom1, dad1));
        families.add(new Family("LN", mom2, null));
        Mapping<Family> mapping = Mappings.from(Family.class);
        try (ExcelWriter<Family> writer = new ExcelWriter<>(file.toFile())) {
            writer.write(families, mapping);
        }
        System.out.println("---------------");
        try (ExcelReader<Family> reader = new ExcelReader<>(file.toFile())) {
            List<Family> read = reader.read(mapping);
            System.out.println(read);
        }
    }

    @Test
    public void testGroup2() throws IOException {
        Product product1 = new Product("AD", "鞋", "中国", 111, 12);
        Product product2 = new Product("LV", "包", "中国", 2222, 22);
        List<Product> products = Arrays.asList(product1, product2);
        Mapping<Product> mapping = Mappings.from(Product.class);
        try (ExcelWriter<Product> writer = new ExcelWriter<>(file.toFile())) {
            writer.write(products, mapping);
        }
        System.out.println("---------------");
        try (ExcelReader<Product> reader = new ExcelReader<>(file.toFile())) {
            List<Product> read = reader.read(mapping);
            System.out.println(read);
        }
    }

    @Test
    public void testNumber() {
        double i = 0.200000000000000001;
        double j = 0.2;
        System.out.printf("%s===%s:%s", i, j, i == j);
    }

}
