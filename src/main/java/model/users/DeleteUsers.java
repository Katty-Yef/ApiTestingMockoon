package model.users;
import lombok.Getter;

@Getter
public class DeleteUsers {
    private String token;

    public DeleteUsers(String token) {
        this.token = token;
    }
}
