package by.dmitry.yarashevich.dao.category;

import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.User;

import java.util.List;

public interface ExpenseCategoryDao {
    void createCategory(ExpenseCategory category);
    List<ExpenseCategory> readAllExpenseCategory();
    ExpenseCategory getCategoryById(int categoryId);
    ExpenseCategory getCategoryByName(String categoryName);
    void updateCategory(ExpenseCategory category);
    void deleteCategory(int categoryId);
}
