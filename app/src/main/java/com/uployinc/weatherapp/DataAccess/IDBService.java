package com.uployinc.weatherapp.DataAccess;

import com.uployinc.weatherapp.Common.IIndexable;

public interface IDBService {
    <TEntity extends IIndexable<TKey>, TKey> IRepository<TEntity,TKey> GetRepository();
}
