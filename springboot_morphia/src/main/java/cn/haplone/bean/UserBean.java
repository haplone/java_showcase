package cn.haplone.bean;

import org.mongodb.morphia.annotations.*;

/**
 * Created by z on 17-3-21.
 */
@Entity(value = "user")
@Indexes(@Index(name = "name", fields = {@Field(value = "surname"), @Field(value = "firstName")}))
public class UserBean extends BaseBean {

    protected String firstName;

    @AlsoLoad("lastName")
    protected String surname;
    @Indexed(unique = true, sparse = false)
    protected String email;
    @Transient
    protected String secret;
    @Embedded
    protected AddressBean address;
    protected int age;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
