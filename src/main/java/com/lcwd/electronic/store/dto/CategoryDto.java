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

    @NotEmpty(message = "Title minimum 5 character")
    @Size(min = 5,max = 100,message = "title must Not be Empty")
    private String title;

    @NotEmpty(message = "Must Not Be Empty")
    @Size(max = 200,message = "Description is Required..!!")
    private String description;

    @ImageNameValid
    private String categoryImage;

}
