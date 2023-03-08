package by.yatsukovich.repository;

import java.util.List;
import java.util.Optional;


public interface CRUDRepository<K, T> {

    Optional<T> findOne(K id);

    T findById(K id);

    List<T> findAll();

    T create(T object);

    T update(T object);

    void delete(K id);
}
