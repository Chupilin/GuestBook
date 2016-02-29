package by.chupilin.web.guestbook.dao;

import by.chupilin.web.guestbook.models.Model;

import java.util.List;

public interface ItemDAO<T extends Model> {

    // CRUD operations
    public void insert(T model);

    public T getById(Long id);

    public List<T> getAll();

    public void update(T model);

    public void deleteById(Long id);

}
