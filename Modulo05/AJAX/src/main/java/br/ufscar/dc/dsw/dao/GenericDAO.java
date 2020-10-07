package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

public abstract class GenericDAO<T> {

    protected Connection connection;

    public GenericDAO() {
        try {
        	
            /* Setup para uso do banco de dados MySQL */
        	
        	Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Cidades", "root", "root");
            
            /* Setup para uso do banco de dados Derby */
            
            // Class.forName("org.apache.derby.jdbc.ClientDriver");
            // connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Cidades", "root", "root");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    abstract public void save(T t);

    abstract List<T> getAll();
}