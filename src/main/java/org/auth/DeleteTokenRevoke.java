package org.auth;
import lombok.Getter;

@Getter
public class DeleteTokenRevoke {
    private String token;

    public DeleteTokenRevoke(String token) {
        this.token = token;
    }
}
