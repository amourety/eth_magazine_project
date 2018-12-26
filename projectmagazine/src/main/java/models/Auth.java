package models;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Auth {

    private Long id;
    private User user;
    private String cookieValue;

}
