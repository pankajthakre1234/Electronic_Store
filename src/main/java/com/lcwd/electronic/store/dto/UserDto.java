package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entity.CustomField;
import com.lcwd.electronic.store.validate.ImageNameValid;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends CustomFieldDto{

    private Integer userId;

    @NotEmpty(message = "User Name must Not Be Empty and Name Should Be Valid or Not Empty")
    @Size(min = 5, max = 15)
    private String name;

    @NotEmpty(message = "User Email ID Is Required")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message = "Invalid User Email")
    @Size(min = 12,max = 25)
    private String email;

    @NotEmpty(message = "Password Should be proper and valid")
    @Size(min = 5,max = 8)
    private String password;

    @NotEmpty(message = "The Gender must be proper and Valid")
    @Size(min = 4,max = 6)
    private String gender;

    @NotEmpty(message = "Write About Your Self")
    private String about;

    @ImageNameValid
    private String imageName;
}
