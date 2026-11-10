package tn.esprit.autoloc.service;

import java.util.List;

public interface ICrudService<T> {
    T create(T entity);

    T findById(Long id);

    List<T> findAll();

    T update(Long id, T entity);

    void deleteById(Long id);
}
