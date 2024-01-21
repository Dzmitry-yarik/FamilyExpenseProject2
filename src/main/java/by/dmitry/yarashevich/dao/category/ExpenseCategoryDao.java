package by.dmitry.yarashevich.dao.category;

import by.dmitry.yarashevich.dao.ObjectDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.User;

import java.util.List;

public interface ExpenseCategoryDao extends ObjectDao<ExpenseCategory> {
    ExpenseCategory getCategoryByName(String categoryName);
}
