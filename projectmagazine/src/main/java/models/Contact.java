package models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Contact {
    Long id;
    Long userid;
    String name;
    String surname;
    String email;
    String letter;
    Boolean isReading;
    String answer;
}
