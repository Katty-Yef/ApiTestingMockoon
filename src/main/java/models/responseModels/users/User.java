package models.responseModels.users;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public String id;
    public String firstName;
    public String lastName;
    public String email;
}
