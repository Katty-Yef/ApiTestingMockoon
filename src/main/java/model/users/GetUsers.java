package model.users;
import lombok.Getter;

@Getter
public class GetUsers {
    private String token;

    public GetUsers(String token) {
        this.token = token;
    }
}
