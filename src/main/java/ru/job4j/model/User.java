package ru.job4j.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.TimeZone;

@Entity
@Table(name = "todo_user")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;
    private String name;
    private String login;
    private String password;
    @Column(name = "user_zone")
    private TimeZone timezone;
}
