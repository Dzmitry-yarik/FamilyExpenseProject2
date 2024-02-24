package by.dmitry.yarashevich.dao.category.impl;

import by.dmitry.yarashevich.dao.category.ExpenseCategoryDao;
import by.dmitry.yarashevich.dao.util.HibernateUtil;
import by.dmitry.yarashevich.models.ExpenseCategory;

import java.util.List;
import java.util.Optional;

public class CategoryHibernateDao implements ExpenseCategoryDao {

    @Override
    public void create(ExpenseCategory expenseCategory) {
        HibernateUtil.executeTransaction(session -> {
            session.persist(expenseCategory);
            return Optional.empty();
        });
    }

    @Override
    public List<ExpenseCategory> readAll() {
        return HibernateUtil.executeTransaction(session ->
                session.createQuery("from ExpenseCategory", ExpenseCategory.class).list());
    }

    @Override
    public ExpenseCategory get(int categoryId) {
        return HibernateUtil.executeTransaction(session ->
                session.get(ExpenseCategory.class, categoryId));
    }

    @Override
    public ExpenseCategory getCategoryByName(String categoryName) {
        return HibernateUtil.executeTransaction(session ->
                session.get(ExpenseCategory.class, categoryName));
    }

    @Override
    public void update(ExpenseCategory updatedCategory) {
        HibernateUtil.executeTransaction(session ->
                session.merge(updatedCategory));
    }

    @Override
    public void delete(int categoryId) {
        HibernateUtil.executeTransaction(session -> {
            ExpenseCategory expenseCategoryById = session.get(ExpenseCategory.class, categoryId);
            if (expenseCategoryById != null) {
                session.remove(expenseCategoryById);
            }
            return Optional.empty();
        });
    }
}