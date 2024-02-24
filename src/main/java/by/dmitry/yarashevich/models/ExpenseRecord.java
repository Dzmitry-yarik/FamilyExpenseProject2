package by.dmitry.yarashevich.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "expenserecord")
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
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

    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER )
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
}