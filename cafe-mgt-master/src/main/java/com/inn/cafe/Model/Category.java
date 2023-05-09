package com.inn.cafe.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;





@NamedQuery(name ="Category.getAllCategory", query="select u from Category u ")

@Data
@Builder
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name ="category")
@NoArgsConstructor
@AllArgsConstructor

public class Category implements Serializable {

    public static final long serialVersionUID= 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")

    private Integer id;

    @Column(name="name")
   private String name;

}
