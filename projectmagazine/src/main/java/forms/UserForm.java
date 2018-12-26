package forms;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserForm {
    private String name;
    private String hashPassword;
    private String email;
    private String first_name;
    private String surname;
}
