package services;

import dataAccess.BaseRepository;

import java.util.Collection;
import java.util.Optional;

public abstract class BaseServices<T> {
    protected final BaseRepository<T> repository;

    protected BaseServices(BaseRepository<T> repository) {
        this.repository = repository;
    }

    public void create(T entity) {
        repository.insert(entity);
    }

    public void update(T entity) {
        repository.update(entity);
    }

    public void delete(int id) {
        repository.delete(id);
    }

    public Optional<T> getById(int id) {
        return repository.getById(id);
    }

    public Collection<T> getAll() {
        return repository.getAll();
    }
}
