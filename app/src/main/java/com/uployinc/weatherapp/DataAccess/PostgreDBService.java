package com.uployinc.weatherapp.DataAccess;

import android.util.Pair;

import com.uployinc.weatherapp.Common.IIndexable;
import com.uployinc.weatherapp.Models.CustomSpot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Predicate;

public class PostgreDBService implements IDBService{
    private static PostgreDBService _instance;
    private Connection connection;
    private final String address;
    private final String databaseName = "weather_api";
    private final String user;
    private final String pass;

    private PostgreDBService(String username, String password, String address) throws SQLException, ClassNotFoundException {
        this.user = username;
        this.pass = password;
        this.address = address;
        connection = getConnection();
    }

    public static synchronized PostgreDBService GetInstance(String username, String password, String address) throws SQLException, ClassNotFoundException {
        if(_instance == null) _instance = new PostgreDBService(username, password, address);
        return _instance;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connection == null) {
            Class.forName("org.postgresql.Driver");
            String url = String.format(Locale.getDefault(),"jdbc:postgresql://%s/%s", this.address, this.databaseName);
            connection = DriverManager.getConnection(url, user, pass);
        }
        return connection;
    }

    public <TEntity extends IIndexable<TKey>, TKey> IRepository<TEntity, TKey> GetRepository() {
        throw new UnsupportedOperationException();
    }

    public IRepository<CustomSpot, UUID> GetCustomSpotRepository(){
        return new IRepository<CustomSpot, UUID>() {
            @Override
            public void Insert(CustomSpot spot) {
                try {
                    Statement stmt = connection.createStatement();
                    Pair<Double, Double> coordinates = spot.getCoordinates();
                    stmt.executeUpdate("INSERT INTO locations (id, latitude, longitude, name, description) " +
                            "VALUES ('"+spot.getId()+"', "+coordinates.first+", "+coordinates.second+", '"+spot.getName()+"', '"+spot.getDescription()+"');");
                    stmt.close();
                    connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public CustomSpot Replace(CustomSpot spot) {
                try {
                    Statement stmt = connection.createStatement();
                    Pair<Double, Double> coordinates = spot.getCoordinates();
                    stmt.executeUpdate("UPDATE locations SET latitude="+coordinates.first+", longitude="+coordinates.second+", name='"+spot.getName()+"', description='"+spot.getDescription()+"' " +
                                        "WHERE id='"+spot.getId()+"';");
                    stmt.close();
                    connection.commit();
                    return GetById(spot.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void Delete(CustomSpot spot) {
                try {
                    Statement stmt = connection.createStatement();
                    stmt.executeUpdate("DELETE FROM locations WHERE id="+spot.getId()+";");
                    stmt.close();
                    connection.commit();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public List<CustomSpot> SearchFor(Predicate<CustomSpot> predicate) {
                List<CustomSpot> list = GetAll();
                if(list == null || list.size() == 0) return null;
                for(CustomSpot spot : list) {
                    if(!predicate.test(spot)) list.remove(spot);
                }
                return list;
            }

            @Override
            public List<CustomSpot> GetAll() {
                try {
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery( "SELECT * FROM locations;" );
                    List<CustomSpot> result = new ArrayList<>();
                    while(rs.next()) {
                        result.add(new CustomSpot(rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getString("name"), rs.getString("description"), UUID.fromString(rs.getString("user_id"))));
                    }
                    stmt.close();
                    return result;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public CustomSpot GetById(UUID id) {
                try {
                    Statement stmt = connection.createStatement();
                    ResultSet rs = stmt.executeQuery( "SELECT * FROM locations WHERE id="+id+";" );
                    if(rs.next()) {
                        CustomSpot result = new CustomSpot(rs.getDouble("latitude"), rs.getDouble("longitude"), rs.getString("name"), rs.getString("description"), UUID.fromString(rs.getString("user_id")));
                        stmt.close();
                        return result;
                    }else{
                        stmt.close();
                        return null;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }
}
