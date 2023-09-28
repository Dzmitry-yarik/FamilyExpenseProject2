package by.dmitry.yarashevich.services;

import by.dmitry.yarashevich.dao.category.ExpenseCategoryDao;
import by.dmitry.yarashevich.dao.category.impl.CategoryHibernateDao;
import by.dmitry.yarashevich.models.ExpenseCategory;

import java.util.List;

public class ExpenseCategoryService {
    private ExpenseCategoryDao expenseCategoryDao = new CategoryHibernateDao();

    public ExpenseCategoryService() {
    }

    public void createExpenseCategory(String name) {
        ExpenseCategory expenseCategory = new ExpenseCategory(name);
        createCategory(expenseCategory);
    }

    public void createCategory(ExpenseCategory category) {
        expenseCategoryDao.createCategory(category);
    }

    public List<ExpenseCategory> readAllExpenseCategory() {
        return expenseCategoryDao.readAllExpenseCategory();
    }

    public ExpenseCategory getCategoryById(int categoryId) {
        return expenseCategoryDao.getCategoryById(categoryId);
    }

    public ExpenseCategory getCategoryByName(String categoryName) {
        return expenseCategoryDao.getCategoryByName(categoryName);
    }

    public void updateCategory(int id, String name) {
        ExpenseCategory updateCategory = new ExpenseCategory(id, name);
        updateCategory(updateCategory);
    }

    public void updateCategory(ExpenseCategory category) {
        expenseCategoryDao.updateCategory(category);
    }

    public void deleteCategory(int categoryId) {
        expenseCategoryDao.deleteCategory(categoryId);
    }
}
