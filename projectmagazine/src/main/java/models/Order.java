package models;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Order {

    Long id;
    Long user_id;
    Long basket_id;
    String text;
    String track;
}
