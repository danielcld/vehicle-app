package com.danielcld.vehicleapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Integer id;
    @Column(name = "user_name")
    @Length(min = 5, message = "*Nazwa uzytkownika musi zawierac co najmniej 5 znakow")
    @NotEmpty(message = "*Wprowadz nazwe uzytkownika")
    private String userName;
    @Column(name = "email")
    @Email(message = "*Wprowadz prawidlowy adres email")
    @NotEmpty(message = "*Wprowadz adres email")
    private String email;
    @Column(name = "password")
    @Length(min = 5, message = "*Haslo musi zawierac co najmniej 5 znakow")
    @NotEmpty(message = "*Please provide your password")
    private String password;
    @Column(name = "name")
    @NotEmpty(message = "*Podaj imie")
    private String name;
    @Column(name = "last_name")
    @NotEmpty(message = "*Podaj nazwisko")
    private String lastName;
    @Column(name = "active")
    private Boolean active;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Vehicle> vehicles;



}
