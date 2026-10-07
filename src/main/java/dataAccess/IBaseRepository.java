package dataAccess;

import java.util.Collection;
import java.util.Optional;

public interface IBaseRepository<T> {
    void insert(T entity);
    void update(T entity);
    void delete(int id);
    Optional<T> getById(int id);
    Collection<T> getAll();
}
