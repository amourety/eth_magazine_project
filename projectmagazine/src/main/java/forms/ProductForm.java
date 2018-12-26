package forms;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductForm {
    private String name;
    private String price;
}
