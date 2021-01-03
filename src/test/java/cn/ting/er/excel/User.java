package cn.ting.er.excel;

import cn.ting.er.datamapping.annotation.Column;
import cn.ting.er.datamapping.annotation.Data;

import java.util.Date;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
@Data
public class User {
    @Column(name = "姓名")
    private String name;
    @Column(name = "年龄")
    private Integer age;
    @Column(name = "简介")
    private String desc;
    @Column(name = "生日")
    private Date birthday;

    public User() {
    }

    public User(String name, int age, String desc, Date birthday) {
        this.name = name;
        this.age = age;
        this.desc = desc;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", desc='" + desc + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
