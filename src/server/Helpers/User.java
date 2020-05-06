package server.Helpers;

import java.io.Serializable;

public class User implements Serializable {

    private byte[] photo;
    private String firstname;
    private String lastname;
    private String birthday;
    private String login;
    private String password;
    private String phone;
    private String registration;
    private int state;
    private int id;

    public User (byte[] photo, String firstname, String lastname, String birthday, String login, String password, String phone, String registration, int state, int id) {
        this.photo = photo;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthday = birthday;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.registration = registration;
        this.state = state;
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getRegistration() {
        return registration;
    }

    public int getState() {
        return state;
    }

    public int getId() { return id; }
}
