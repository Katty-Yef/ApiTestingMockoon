package org.users;
import lombok.Getter;

@Getter
public class PutUsersUpdate {
    public String userId;
    public String firstName;
    public String lastName;
    public String email;
    public String token;

    public PutUsersUpdate(String userId, String firstName, String lastName, String email, String token) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.token = token;
    }
}
