package com.mycompany.poe;

/**
 *
 * @author lab_services_student: Vuyolwethu Bovu
 */
public final class Login {

    private String username;
    private String password;

    private boolean successLogin = false;

    private String name;
    private String surname;

    public Login(String username, String password, String name, String surname) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
    }

    public boolean loginUser(Registration register) {
        successLogin = register.getUsername().equals(username)
                && register.getPassword().equals(password);
        return successLogin;
    }

    public String returnLoginStatus() {
        if (successLogin) {
            return "Welcome " + name + ", " + surname + " it is great to see you again.";
        }
        return "Username or password incorrect, please try again.";
    }
}
