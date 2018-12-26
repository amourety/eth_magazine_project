package models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Basket {
    private Long id;
    private Long order_id;
    private User user;
    private List<Product> products;
    private String track;

    public Basket(User basketOwner) {
        this.user = basketOwner;
    }


    public Product findByName(String name){
        for (Product product : products){
            if(product.getName().equals(name)){
                return product;
            }
        }
        return null;
    }

    public void add(Product product){
        System.out.println(product.toString());
        products.remove(findByName(product.getName()));
        products.add(product);

    }

    public void delete(Product product) {
        products.remove(findByName(product.getName()));
        products.add(product);
    }

    public void deleteAll() {
        products.clear();
    }
}