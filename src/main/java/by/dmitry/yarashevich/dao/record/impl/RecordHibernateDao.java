package by.dmitry.yarashevich.dao.record.impl;

import by.dmitry.yarashevich.dao.category.impl.CategoryHibernateDao;
import by.dmitry.yarashevich.dao.record.ExpenseRecordDao;
import by.dmitry.yarashevich.dao.user.impl.UserHibernateDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import by.dmitry.yarashevich.models.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecordHibernateDao implements ExpenseRecordDao {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    public static void main(String[] args) {
        UserHibernateDao userHibernateDao = new UserHibernateDao();
        User user = new User("Jon", "Smitt");
        userHibernateDao.createUser(user);

        CategoryHibernateDao categoryHibernateDao = new CategoryHibernateDao();
        ExpenseCategory expenseCategory = new ExpenseCategory("Разное", user);
        categoryHibernateDao.createCategory(expenseCategory);

        RecordHibernateDao recordHibernateDao = new RecordHibernateDao();
        ExpenseRecord expenseRecord = new ExpenseRecord("йцуйц", 51.0, LocalDate.of(2023, 9, 3), user, expenseCategory);
        recordHibernateDao.createRecord(expenseRecord);
    }

    @Override
    public void createRecord(ExpenseRecord expenseRecord) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(expenseRecord);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<ExpenseRecord> readAllExpenseRecord() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<ExpenseRecord> list = session.createQuery("from expenserecord", ExpenseRecord.class).list();

            transaction.commit();
            return list;
        } catch (Exception e) {
            transaction.rollback();
            return new ArrayList<>();
        }
    }

    @Override
    public ExpenseRecord getRecordById(int recordId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            ExpenseRecord recordById = session.get(ExpenseRecord.class, recordId);
            transaction.commit();
            session.close();
            System.out.println();
            return recordById;
        } catch (Exception e) {
            transaction.rollback();
            return new ExpenseRecord();
        }
    }

    @Override
    public List<ExpenseRecord> getRecordsByCategory(ExpenseCategory category) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        List<ExpenseRecord> recordsByUser = null;

        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ExpenseRecord> criteriaQuery = criteriaBuilder.createQuery(ExpenseRecord.class);
            Root<ExpenseRecord> root = criteriaQuery.from(ExpenseRecord.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("category"), category));
            recordsByUser = session.createQuery(criteriaQuery).getResultList();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            System.err.println("Произошла ошибка: " + e.getMessage());
        } finally {
            session.close();
        }

        return recordsByUser;
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
    public void updateRecord(ExpenseRecord updatedRecord) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(updatedRecord);
            transaction.commit();             //подтверждаем изменения
        } catch (Exception e) {
            transaction.rollback();           //отказываем изменения
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteRecord(int recordId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            ExpenseRecord recordById = session.get(ExpenseRecord.class, recordId);
            session.delete(recordById);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }
}