package xyz.lizhaorong.entity;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    @NotBlank(message = "姓名不能为空")
    private String name;
    private boolean gender;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
