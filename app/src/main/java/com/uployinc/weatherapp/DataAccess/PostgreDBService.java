package com.uployinc.weatherapp.DataAccess;

import android.annotation.SuppressLint;

import com.uployinc.weatherapp.Common.IIndexable;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgreDBService implements IDBService{
    private static PostgreDBService _instance;
    private Connection connection;
    private final String hostAddress = "84.40.90.18";
    private final String databaseName = "weather_api";
    private final int portNumber = 5432;
    private final String user = "postgres";
    private final String pass = "admin";
    @SuppressLint("DefaultLocale")
    private final String url = String.format("jdbc:postgresql://%s:%d/%s", this.hostAddress, this.portNumber, this.databaseName);

    private PostgreDBService(){
        connection = getConnection();
    }

    public PostgreDBService GetInstance(){
        if(_instance == null) _instance = new PostgreDBService();
        return _instance;
    }

    public Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connection = DriverManager.getConnection(url, user, pass);
            } catch (Exception e) {
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    public <TEntity extends IIndexable<TKey>, TKey> IRepository<TEntity, TKey> GetRepository() {
        return null;
    }
}
