package by.dmitry.yarashevich.dao.record.impl;

import by.dmitry.yarashevich.dao.record.ExpenseRecordDao;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;

public class RecordHibernateDao implements ExpenseRecordDao {

    private SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();

    @Override
    public void create(ExpenseRecord expenseRecord) {
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

//    @Override
//    public void createUser(User user) {
//        HibernateUtil.executeTransaction(session -> {
//            session.persist(user);
//            return null;
//        });
//    }


    @Override
    public List<ExpenseRecord> readAll() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            List<ExpenseRecord> list = session.createQuery("from ExpenseRecord", ExpenseRecord.class).list();

            transaction.commit();
            return list;
        } catch (Exception e) {
            transaction.rollback();
            return new ArrayList<>();
        }
    }

    @Override
    public ExpenseRecord get(int recordId) {
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

    @Override
    public void update(ExpenseRecord updatedRecord) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(updatedRecord);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int recordId) {
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