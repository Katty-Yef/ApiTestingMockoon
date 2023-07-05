package org.auth;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostCreateToken {
    private String email;
    private String password;

}