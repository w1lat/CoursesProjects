package javaEE.serializationWithXML;

import java.util.Date;

/**
 * Created by Таня on 21.05.2015.
 */
public class User {
    private int age;
    private String name;
    public int salary;
    public User father;


    public User(){}
    public User(int age, String name, int salary, User father) {
        this.age = age;
        this.name = name;
        this.salary = salary;
        this.father = father;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", father=" + father +
                '}';
    }
}
