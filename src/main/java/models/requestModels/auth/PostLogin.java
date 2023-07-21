package models.requestModels.auth;
import lombok.Setter;

@Setter
public class PostLogin {
    private String email;
    private String password;
}