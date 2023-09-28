package by.dmitry.yarashevich.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.Set;

@Entity(name = "expensecategory")
@Table(name = "expensecategory")
@Builder
@Getter
@Setter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseCategory that = (ExpenseCategory) o;
        return category_id == that.category_id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category_id, name);
    }

    @Override
    public String toString() {
        return "ExpenseCategory{" +
                "id=" + category_id +
                ", name='" + name + '\'' +
                '}';
    }
}
