package edu.ntust.prlab.sessionmanager;

/**
 * 用來代表Member的類別。
 */
public class Member {

    /**
     * 用來代表Member的編號。
     */
    private int id;

    /**
     * 用來代表Member的名稱
     */
    private String name;

    /**
     * 用來代表Member的年齡
     */
    private int age;

    /**
     * 用來代表Member的性別
     */
    private Gender gender;

    public Member() {
    }

    public int getId() {
        return id;
    }

    public Member setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Member setName(String name) {
        this.name = name;
        return this;
    }

    public int getAge() {
        return age;
    }

    public Member setAge(int age) {
        this.age = age;
        return this;
    }

    public Gender getGender() {
        return gender;
    }

    public Member setGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    /**
     * 用來區分性別的Enum
     */
    enum Gender {
        MALE, FEMALE
    }

}
