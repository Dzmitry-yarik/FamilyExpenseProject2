package by.dmitry.yarashevich.dao.record;

import by.dmitry.yarashevich.dao.ObjectDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;

import java.util.List;
import java.util.Optional;

public interface ExpenseRecordDao extends ObjectDao<ExpenseRecord> {
    Optional<List<ExpenseRecord>> getRecordsByCategory(ExpenseCategory category);
}