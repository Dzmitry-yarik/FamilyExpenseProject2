package by.dmitry.yarashevich.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "expensecategory")
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ExpenseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private int category_id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    private Set<ExpenseRecord> recordSet;

    public ExpenseCategory() {
    }

    public ExpenseCategory(String name) {
        this.name = name;
    }

    public ExpenseCategory(int category_id, String name) {
        this.category_id = category_id;
        this.name = name;
    }

    public ExpenseCategory(String name, Set<ExpenseRecord> recordList) {
        this.name = name;
        this.recordSet = recordList;
    }

    public ExpenseCategory(int category_id, String name, Set<ExpenseRecord> recordSet) {
        this.category_id = category_id;
        this.name = name;
        this.recordSet = recordSet;
    }
}
