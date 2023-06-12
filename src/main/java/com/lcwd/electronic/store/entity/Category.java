package com.lcwd.electronic.store.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_Id")
    private Integer catId;

    @Column(name = "category_title")
    private String title;

    @Column(name = "category_description")
    private String description;

    private String categoryImage;

}
