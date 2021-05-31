package com.uployinc.weatherapp.DataAccess;

public interface IDBService {
    <TEntity extends IIndexable<TKey>, TKey> IRepository<TEntity,TKey> GetRepository();
}
