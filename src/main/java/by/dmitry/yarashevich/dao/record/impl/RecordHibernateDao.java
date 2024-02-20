package by.dmitry.yarashevich.dao.record.impl;

import by.dmitry.yarashevich.dao.record.ExpenseRecordDao;
import by.dmitry.yarashevich.dao.util.HibernateUtil;
import by.dmitry.yarashevich.models.ExpenseCategory;
import by.dmitry.yarashevich.models.ExpenseRecord;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

import java.util.List;
import java.util.Optional;

public class RecordHibernateDao implements ExpenseRecordDao {

    @Override
    public void create(ExpenseRecord expenseRecord) {
        HibernateUtil.executeTransaction(session -> {
            session.persist(expenseRecord);
            return Optional.empty();
        });
    }

    @Override
    public List<ExpenseRecord> readAll() {
        return HibernateUtil.executeTransaction(session ->
                    session.createQuery("from ExpenseRecord", ExpenseRecord.class).list());
    }

    @Override
    public ExpenseRecord get(int recordId) {
        return HibernateUtil.executeTransaction(session ->
                session.get(ExpenseRecord.class, recordId));
    }

    @Override
    public Optional<List<ExpenseRecord>> getRecordsByCategory(ExpenseCategory category) {
        return HibernateUtil.executeTransaction(session -> {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ExpenseRecord> criteriaQuery = criteriaBuilder.createQuery(ExpenseRecord.class);
            Root<ExpenseRecord> root = criteriaQuery.from(ExpenseRecord.class);

            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("category"), category));
            List<ExpenseRecord> records = session.createQuery(criteriaQuery).getResultList();
            return Optional.ofNullable(records);
        });
    }

    @Override
    public void update(ExpenseRecord updatedRecord) {
         HibernateUtil.executeTransaction(session ->
                 session.merge(updatedRecord));
    }

    @Override
    public void delete(int recordId) {
        HibernateUtil.executeTransaction(session ->
                session.get(ExpenseRecord.class, recordId));
    }
}