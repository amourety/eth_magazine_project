package models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Image {
    private byte[] imageData;
    private String imageFileName;
}
