package dat.backend.model.entities;

import java.util.Objects;

public class User
{
    private String email;
    private String password;
    private String role;
    private String name;
    private float balance = 0;

    public User(String name, String email, String password, float balance, String role)
    {
        this.email = email;
        this.password = password;
        this.role = role;
        this.name = name;
        this.balance = balance;
    }
    public User(String name, String email, String password)
    {
        this.email = email;
        this.password = password;
        this.role = "user";
        this.name = name;
        this.balance = 0;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String username)
    {
        this.email = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword()) &&
                getRole().equals(user.getRole());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getEmail(), getPassword(), getRole());
    }

    @Override
    public String toString()
    {
        return "User{" +
                "brugerNavn='" + email + '\'' +
                ", kodeord='" + password + '\'' +
                ", rolle='" + role + '\'' +
                '}';
    }
}
