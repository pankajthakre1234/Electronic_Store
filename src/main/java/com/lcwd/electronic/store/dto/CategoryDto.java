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

    private Integer catId;

    @NotEmpty(message = "title must Not be Empty")
    @Size(min = 2,max = 100,message = "Title minimum 2 character")
    private String title;

    @NotEmpty(message = "Must Not Be Empty")
    @Size(max = 200,message = "Description is Required..!!")
    private String description;

    @ImageNameValid
    private String categoryImage;

}
