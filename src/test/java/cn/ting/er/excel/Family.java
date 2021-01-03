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
public class Family {
    @Column(name = "名称")
    private String name;
    @Group("妈妈")
    private User mom;
    @Group("爸爸")
    private User dad;

    public Family() {
    }

    public Family(String name, User mom, User dad) {
        this.name = name;
        this.mom = mom;
        this.dad = dad;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getMom() {
        return mom;
    }

    public void setMom(User mom) {
        this.mom = mom;
    }

    public User getDad() {
        return dad;
    }

    public void setDad(User dad) {
        this.dad = dad;
    }

    @Override
    public String toString() {
        return "Family{" +
                "name='" + name + '\'' +
                ", mom=" + mom +
                ", dad=" + dad +
                '}';
    }
}
