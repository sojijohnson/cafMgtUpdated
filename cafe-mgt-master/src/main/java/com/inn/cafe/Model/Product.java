package com.inn.cafe.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.annotation.Lazy;

import java.io.Serializable;

@Data
@Builder
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name ="user")
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    public final long serialVersionUID=123456L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;


    @Column(name="name")
    private String name;
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="category_fk",nullable = false)
   private Category category;

   @Column(name="description")
   private String description;
   @Column(name="price")
   private Integer price;
}
