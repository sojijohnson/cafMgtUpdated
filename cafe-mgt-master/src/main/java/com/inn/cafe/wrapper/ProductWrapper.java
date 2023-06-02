package com.inn.cafe.wrapper;

import com.inn.cafe.Model.Category;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class ProductWrapper {
    private Integer id;



    private String name;


    private String status;

    private Integer categoryId;
    private String categoryName;


    private String description;

    private Integer price;
    public  ProductWrapper(){

    }
    public  ProductWrapper(Integer id,String name, String status, String categoryName,Integer categoryId, String description,Integer price){
        super();


        this.id=id;
        this.name=name;
        this.status=status;
        this.categoryName=categoryName;
        this.categoryId=categoryId;

        this.description=description;
        this.price=price;

    }
    public  ProductWrapper(Integer id,String name){
        super();


        this.id=id;
        this.name=name;


    }

    public  ProductWrapper(Integer id,String name, String description, Integer price){
        super();


        this.id=id;
        this.name=name;
        this.description=description;
        this.price=price;


    }
}
