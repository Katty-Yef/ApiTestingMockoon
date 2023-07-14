package model.auth;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostLogin {
    private String email;
    private String password;
}