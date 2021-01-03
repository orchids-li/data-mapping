package cn.ting.er.excel;

import cn.ting.er.datamapping.annotation.Column;
import cn.ting.er.datamapping.annotation.Data;
import cn.ting.er.datamapping.annotation.Group;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
@Data
public class Product {
    @Column(name = "商品名", order = 0)
    private String name;
    @Group("其他")
    @Column(name = "分类", order = 3)
    private String category;
    @Group("其他")
    @Column(name = "区域", order = 4)
    private String area;
    @Group("基本信息")
    @Column(name = "价格", order = 1)
    private double price;
    @Group("基本信息")
    @Column(name = "重量", order = 2)
    private int weight;

    public Product() {
    }

    public Product(String name, String category, String area, double price, int weight) {
        this.name = name;
        this.category = category;
        this.area = area;
        this.price = price;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", area='" + area + '\'' +
                ", price=" + price +
                ", weight=" + weight +
                '}';
    }
}
