package by.dmitry.yarashevich.models;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Entity(name = "expenserecord")
@Table(name = "expenserecord")
@Builder
@Getter
@Setter
public class ExpenseRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="record_id")
    private int record_id;

    @Column(name="name")
    private String name;


    @Column(name="amount")
    private double amount;

    @Column(name="date")
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable=false)
    private ExpenseCategory category;

    public ExpenseRecord() {
    }

    public ExpenseRecord(String name, double amount, LocalDate date) {
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public ExpenseRecord(int record_id, String name, double amount, LocalDate date) {
        this.record_id = record_id;
        this.name = name;
        this.amount = amount;
        this.date = date;
    }

    public ExpenseRecord(int record_id, String name, double amount, LocalDate date, User user) {
        this.record_id = record_id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.user = user;
    }

    public ExpenseRecord(String name, double amount, LocalDate date, User user, ExpenseCategory category) {
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.category = category;
    }

    public ExpenseRecord(int record_id, String name, double amount, LocalDate date, User user, ExpenseCategory category) {
        this.record_id = record_id;
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.user = user;
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpenseRecord that = (ExpenseRecord) o;
        return record_id == that.record_id && Double.compare(that.amount, amount) == 0 && Objects.equals(name, that.name) && Objects.equals(date, that.date) && Objects.equals(user, that.user) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(record_id, name, amount, date, user, category);
    }

    @Override
    public String  toString() {
        return "ExpenseRecord{" +
                "record_id=" + record_id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                ", user=" + user +
                ", category=" + category +
                '}';
    }
}