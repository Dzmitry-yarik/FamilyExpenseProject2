package by.dmitry.yarashevich.dao.category.impl;

import by.dmitry.yarashevich.dao.category.ExpenseCategoryDao;
import by.dmitry.yarashevich.dao.user.impl.UserHibernateDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class CategoryHibernateDao implements ExpenseCategoryDao {
    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {
        UserHibernateDao userHibernateDao = new UserHibernateDao();
        User user = new User("Jeck", "Pit");
        userHibernateDao.createUser(user);

        CategoryHibernateDao categoryDao = new CategoryHibernateDao();
        categoryDao.createCategory(new ExpenseCategory("111", user));
    }

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
            List<ExpenseCategory> list = session.createQuery("from expensecategory", ExpenseCategory.class).list();

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

//    public List<Person> readAllPersonsNameStartWithAndAgeBetween(String prefixName, int minAge, int maxAge) {
//        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
//        Session session = sessionFactory.openSession();
//        Transaction transaction = session.beginTransaction();
//        try {
//            Criteria criteria = session.createCriteria(Person.class);
////            criteria.add();
//            criteria.add(Restrictions.and(
//                    Restrictions.ilike("name", prefixName + "%"),
//                    Restrictions.ge("age", minAge),
//                    Restrictions.le("age", maxAge)));
////            criteria.add(Restrictions.between("age", minAge, maxAge));
//
//            List<Person> list = criteria.list();
//            transaction.commit();
//            System.out.println(list);
//            return list;
//        } catch (Exception e) {
//            transaction.rollback();
//            return new ArrayList<Person>();
//        }
//    }

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