package by.dmitry.yarashevich.dao;

import java.util.List;

public interface ObjectDao<T> {
    void create(T user);
    T get(int id);
    void update(T user);
    void delete(int id);
    List<T> readAll();

}
