package forms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import models.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {
    String name;
    String surname;
    String email;
    String letter;
    Long userid;
}
