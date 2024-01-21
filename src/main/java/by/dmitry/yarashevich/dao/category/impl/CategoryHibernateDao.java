package by.dmitry.yarashevich.dao.category.impl;

import by.dmitry.yarashevich.dao.category.ExpenseCategoryDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class CategoryHibernateDao implements ExpenseCategoryDao {
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public void createCategory(ExpenseCategory expenseCategory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(expenseCategory);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<ExpenseCategory> readAllExpenseCategory() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<ExpenseCategory> list = session.createQuery("from ExpenseCategory", ExpenseCategory.class).list();
            transaction.commit();
            return list;
        } catch (Exception e) {
            transaction.rollback();
            return new ArrayList<>();
        }
    }

    @Override
    public ExpenseCategory getCategoryById(int categoryId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            ExpenseCategory categoryById = session.get(ExpenseCategory.class, categoryId);
            transaction.commit();
            session.close();
            System.out.println();
            return categoryById;
        } catch (Exception e) {
            transaction.rollback();
            return new ExpenseCategory();
        }
    }

    @Override
    public ExpenseCategory getCategoryByName(String categoryName) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            ExpenseCategory categoryById = session.get(ExpenseCategory.class, categoryName);
            transaction.commit();
            session.close();
            System.out.println();
            return categoryById;
        } catch (Exception e) {
            transaction.rollback();
            return new ExpenseCategory();
        }
    }

    @Override
    public void updateCategory(ExpenseCategory updatedCategory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(updatedCategory);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteCategory(int categoryId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            ExpenseCategory categoryById = session.get(ExpenseCategory.class, categoryId);
            session.delete(categoryById);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }
}