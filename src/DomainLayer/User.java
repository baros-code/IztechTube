package DomainLayer;

public class User {

    private String password;
    private String name;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Domain.User Name :" + name;
    }

     String toStringWithPassword() { // user password can be accessed only the objects that exist in same package with user.
        return toString() + "Domain.User Password : " + getPassword();
    }

}
