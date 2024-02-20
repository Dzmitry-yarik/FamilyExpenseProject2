package by.dmitry.yarashevich.services;

import by.dmitry.yarashevich.dao.record.ExpenseRecordDao;
import by.dmitry.yarashevich.dao.record.impl.RecordHibernateDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import by.dmitry.yarashevich.models.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ExpenseRecordService {

     private final ExpenseRecordDao expenseRecordDao = new RecordHibernateDao();

    public ExpenseRecordService() {
    }

    public void createRecord(String name, double amount, LocalDate date, User user, ExpenseCategory category) {
        ExpenseRecord expenseRecord = new ExpenseRecord(name, amount, date, user, category);
        createRecord(expenseRecord);
    }

    public void createRecord(ExpenseRecord record) {
        expenseRecordDao.create(record);
    }

    public List<ExpenseRecord> readAllExpenseRecord() {
        return expenseRecordDao.readAll();
    }

    public ExpenseRecord getRecordById(int recordId) {
        return expenseRecordDao.get(recordId);
    }

    public Optional<List<ExpenseRecord>> getRecordsByCategory(ExpenseCategory category) {
        return expenseRecordDao.getRecordsByCategory(category);
    }

    public void updateRecord(String name, double amount, LocalDate date, User user, ExpenseCategory category) {
        ExpenseRecord expenseRecord = new ExpenseRecord(name, amount, date, user, category);
            updateRecord(expenseRecord);
    }

    public void updateRecord(ExpenseRecord record) {
        expenseRecordDao.update(record);
    }

    public void deleteRecord(int recordId) {
        expenseRecordDao.delete(recordId);
    }
}
