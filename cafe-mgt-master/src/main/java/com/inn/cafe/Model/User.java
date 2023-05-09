package com.inn.cafe.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;



@NamedQuery(name ="User.findByEmailId", query="select u from User u where u.email=:email")
@NamedQuery(name ="User.findByUserName", query="select u from User u where u.name=:name")



@NamedQuery(name ="User.update", query="update User u set u.status=:status where u.id=:id")
@NamedQuery(name ="User.getAllUser", query="select  new com.inn.cafe.wrapper.UserWrapper(u.id, u.name, u.contactNumber, u.status, u.email) from User u where u.role='user'")
@NamedQuery(name ="User.getAllAdmin", query="select  u.email  from User u where u.role='admin'")
@Data
@Builder
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name ="user")
@NoArgsConstructor
@AllArgsConstructor


public class User implements Serializable {

private static final long  serialVersionUID =1L;
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="id")
private Integer Id;
@Column(name="contactNumber")
private String contactNumber;

@Column(name="email")
private String email;
@Column(name="name")
private String name;
@Column(name="password")
    private String password;
@Column(name="status")
    private String status;

@Column(name="role")
private String role;


}
