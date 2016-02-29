package by.chupilin.web.guestbook.services;

import by.chupilin.web.guestbook.models.Model;

import java.util.List;

public interface ItemService<T extends Model> {

    public void insert(T model);

    public T getById(Long id);

    public List<T> getAll();

    public void update(T model);

    public void deleteById(Long id);

}
