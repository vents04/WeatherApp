package com.uployinc.weatherapp.DataAccess;

import java.util.List;
import java.util.function.Predicate;

public interface IRepository<TEntity extends IIndexable<TKey>, TKey>
    {
        void Insert(TEntity entity);
        TEntity Replace(TEntity entity);
        void Delete(TEntity entity);
        List<TEntity> SearchFor(Predicate<TEntity> predicate);
        List<TEntity> GetAll();
        TEntity GetById(TKey id);
    }
