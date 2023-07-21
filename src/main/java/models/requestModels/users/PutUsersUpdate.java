package models.requestModels.users;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PutUsersUpdate {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
}
