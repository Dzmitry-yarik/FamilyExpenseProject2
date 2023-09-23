package by.dmitry.yarashevich.dao.record;

import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import by.dmitry.yarashevich.models.User;

import java.util.List;

public interface ExpenseRecordDao {
    void createRecord(ExpenseRecord record);
    List<ExpenseRecord> readAllExpenseRecord();
    ExpenseRecord getRecordById(int recordId);
    List<ExpenseRecord> getRecordsByCategory(ExpenseCategory category);
    void updateRecord(ExpenseRecord record);
    void deleteRecord(int recordId);
}