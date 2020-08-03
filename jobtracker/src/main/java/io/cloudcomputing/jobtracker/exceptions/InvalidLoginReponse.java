package io.cloudcomputing.jobtracker.exceptions;

public class InvalidLoginReponse {
    //the json object we want to return
    private String username;
    private String password;

    public InvalidLoginReponse() {
        this.username = "Invalid Username";
        this.password = "Invalid Password";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
