package com.revature.ExpenseReport.Model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

<<<<<<< HEAD
@Entity
@Table(name = "app_users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
    }
=======
import javax.swing.*;

@Entity
@Table(
        name = "app_users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username"})
        }
>>>>>>> origin
)
@Data
@NoArgsConstructor
public class AppUser {
<<<<<<< HEAD
    //Fields
=======
    // Fields
>>>>>>> origin
    @Id
    @GeneratedValue
    private Long userid;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
<<<<<<< HEAD
    @Column(name = "user_role")
    private String userrole;


    //Constructor
    public AppUser(String username, String password, String role){
=======

    @Column(name = "user_role")
    private String userrole;

    // Constructor
    public AppUser( String username, String password, String role) {
>>>>>>> origin
        this.username = username;
        this.password = password;
        this.userrole = role;
    }

<<<<<<< HEAD
    //Methods


=======
    // Methods
>>>>>>> origin
}
