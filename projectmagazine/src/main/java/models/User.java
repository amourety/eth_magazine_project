package models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class User {

    private Long id;
    private String name;
    private String passwordHash;
    private String email;
    private Role role;
    private String first_name;
    private String surname;


}