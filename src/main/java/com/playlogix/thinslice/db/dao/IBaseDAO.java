package com.playlogix.thinslice.db.dao;

import java.util.List;
import java.util.Optional;

/**
 * Created by Stephen on 2016/08/05.
 */
public interface IBaseDAO<T, K> {
    Optional<T> get(K id);
    List<T> getList();
    void save(T entity);
    boolean delete(K id);
}
