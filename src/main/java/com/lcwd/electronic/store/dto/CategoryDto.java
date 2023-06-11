package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDto {

    @NotEmpty
    private Integer catId;

    @NotEmpty(message = "Title Should be Proper and Must Not Be Empty")
    @Size(min = 5,max = 100)
    private String title;

    @NotEmpty(message = "Must Not Be Empty")
    @Size(max = 200)
    private String description;

    @ImageNameValid
    private String categoryImage;

}
