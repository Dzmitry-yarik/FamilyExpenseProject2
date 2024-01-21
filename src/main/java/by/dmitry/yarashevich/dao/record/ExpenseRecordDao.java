package by.dmitry.yarashevich.dao.record;

import by.dmitry.yarashevich.dao.ObjectDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import by.dmitry.yarashevich.models.User;

import java.util.List;

public interface ExpenseRecordDao extends ObjectDao<ExpenseRecord> {
    List<ExpenseRecord> getRecordsByCategory(ExpenseCategory category);
}