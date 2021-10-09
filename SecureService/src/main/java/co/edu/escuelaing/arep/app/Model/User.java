package co.edu.escuelaing.arep.app.Model;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class User {
    private String hash;
    private String name;
    private String password;

    public User(String name, String password){
        this.name = name;
        this.password = password;
        this.hash = Hashing.sha256().hashString(this.name+this.password ,StandardCharsets.UTF_8).toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
    public void createHash( ) {
        this.hash = Hashing.sha256().hashString(this.name+this.password ,StandardCharsets.UTF_8).toString();;
    }


    @Override
    public String toString() {
        return "User{" +
                "userName='" + name + '\'' +
                ", password='" + password + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}