package ca.sheridancollege.musleho.ShineTennis.bean;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private UUID userId;
    private String username;
    private String encPassword;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String homeAddress;
}
