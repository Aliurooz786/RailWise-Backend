package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    public enum Role {
        ROLE_USER,
        ROLE_ADMIN
    }

    @Id
    private String id;

    private String firstName;
    private String lastName;
    private String passWord;
    private String role;
    private int age;

    @Indexed(unique = true)
    private String email;
}