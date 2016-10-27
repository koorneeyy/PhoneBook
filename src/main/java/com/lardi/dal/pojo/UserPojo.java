package com.lardi.dal.pojo;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserPojo {
    @NotNull
    @Size(min=5, max=30 ,message = "Кількість символів має бути в діапазоні 5-30")
    @Pattern(regexp = "[a-zA-Z]*",message = "Допускаються лише англійські літери")
    String nameLogin;

    @NotNull
    @Size(min=5, max=100,message = "Кількість символів має бути в діапазоні 5-100")

    String fullName;

    @NotNull
    @Size(min=5, max=100,message = "Кількість символів має бути в діапазоні 5-100")
    String password;


    public String getNameLogin() {
        return nameLogin;
    }

    public void setNameLogin(String nameLogin) {
        this.nameLogin = nameLogin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return  nameLogin +";"+fullName+";"+password+System.lineSeparator();
    }
}
