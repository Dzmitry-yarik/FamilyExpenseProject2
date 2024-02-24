package by.dmitry.yarashevich.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "user")
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int user_id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<ExpenseRecord> recordSet;

    public User() {
    }

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(int user_id, String name, String password) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
    }

    public User(int user_id, String name, String password, Set<ExpenseRecord> recordSet) {
        this.user_id = user_id;
        this.name = name;
        this.password = password;
        this.recordSet = recordSet;
    }
}