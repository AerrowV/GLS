package dat.dao;

import java.util.List;

public interface IDAO<T,I> {
    T create(T t);
    T findById(I i);
    List<T> findAll();
    T update(T t);
    void delete(I i);
}
