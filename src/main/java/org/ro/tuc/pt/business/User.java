package org.ro.tuc.pt.business;

import java.io.Serializable;

/**
 * This class will be used for storing the data of a user;
 * @author Chereji Iulia
 */
public class User implements Serializable {
    private String id;
    private String username;
    private String dateOfBirth;
    private String address;
    private String phone;
    private String email;
    private String password;

    /**
     * @param id String of format "Cnr" for clients, "Anr" for administrators, "Enr" for emplyees
     * @param username a nonempty String
     * @param dateOfBirth String of format "yyyy-mm-dd"
     * @param address any String
     * @param phone String of 10 digits
     * @param email String of format "xxxx@xxx.xxx"
     * @param password any String
     */
    public User(String id, String username, String dateOfBirth, String address, String phone, String email, String password) {
        this.id = id;
        this.username = username;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Client: "+id+"; " + username + "; address: "+address+"; phone: "+phone;
    }
}
