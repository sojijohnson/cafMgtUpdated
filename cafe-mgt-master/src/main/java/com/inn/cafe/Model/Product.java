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
@Table(name ="product")
@NoArgsConstructor
@AllArgsConstructor

@NamedQuery(name="Product.getAllProduct", query="select new com.inn.cafe.wrapper.ProductWrapper(p.id, p.name, p.status,p.category.name, p.category.id, p.description,p.price) from Product p")
@NamedQuery(name="Product.updateProductStatus", query = "update Product p set p.status=:status where p.id=:id")
@NamedQuery(name="Product.getCategory", query = "select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name) from Product p where p.category.id=:id and p.status='true'")
@NamedQuery(name="Product.getCatById", query = "select new com.inn.cafe.wrapper.ProductWrapper(p.id,p.name,p.description,p.price)from Product p where p.id=:id ")

public class Product implements Serializable {

    public final long serialVersionUID=123456L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;


    @Column(name="name")
    private String name;

    @Column(name="status")
    private String status;
   @ManyToOne(fetch = FetchType.LAZY)
   @JoinColumn(name="category_fk",nullable = false)
   private Category category;

   @Column(name="description")
   private String description;
   @Column(name="price")
   private Integer price;
}
