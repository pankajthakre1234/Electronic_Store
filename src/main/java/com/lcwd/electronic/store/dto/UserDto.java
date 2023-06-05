package com.lcwd.electronic.store.dto;

import com.lcwd.electronic.store.entity.CustomField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class UserDto extends CustomFieldDto{

    private Integer userId;

    @NotEmpty(message = "User Name must Not Be Empty and Name Should Be Valid or Not Empty")
    @Size(min = 5, max = 15)
    private String name;

    @NotEmpty(message = "Invalid User Email Id")
    @Size(min = 12,max = 25)
    private String email;

    @NotEmpty(message = "Password Should be proper and valid")
    @Size(min = 5,max = 16)
    private String password;

    @NotEmpty(message = "The Gender must be proper and Valid")
    private String gender;

    @NotEmpty(message = "Write About Your Self")
    private String about;

    private String imageName;
}
