package com.saleor.saleor_api.table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

@Table(name = "User", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "user_name"
        })
})
public class User {
    @Id
    @Column( name = "id", nullable = false, unique = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name="user_name",nullable = false)
    private String username;

    @Column(name="password",nullable = false )
    private String password;

    @Column(name="title" ,nullable = false)
    private String title;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "image")
    private String image;

    public User(String image, String username, String password, Boolean active, String phone, String email, String title) {
        this.username = username;
        this.password = password;
        this.active = active;
        this.phone = phone;
        this.email = email;
        this.title = title;
        this.image = image;
    }
}