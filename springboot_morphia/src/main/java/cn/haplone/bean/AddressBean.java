package cn.haplone.bean;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by z on 17-3-21.
 */
@Embedded
public class AddressBean {
    protected String street;
    protected String zip;

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
