package pucrs.tecn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;

public class Database {
	public static String DB_CONN_STRING_CREATE = "jdbc:derby:db;create=true";
    public static String DB_NAME = "db";
    public static String USER_NAME = "user";
    public static String PASSWORD = "123";
    private static DataSource dataSource;
    
	public static Connection getConexaoViaDriverManager() throws Exception {
        return DriverManager.getConnection(DB_CONN_STRING_CREATE, USER_NAME, PASSWORD);
    }

    public static Connection getConexaoViaDataSource() throws Exception {
        if (dataSource == null) {
            EmbeddedDataSource ds = new EmbeddedDataSource();
            ds.setDatabaseName(DB_NAME);
            ds.setCreateDatabase("create");
            ds.setUser(USER_NAME);
            ds.setPassword(PASSWORD);
            dataSource = ds;
        }
        return dataSource.getConnection();
    }
    
    public static void createTableProduct(){
    	try (Connection conexao = getConexaoViaDataSource()) {
            //Criar uma tabela
            String sql = "create table product ("
                    + "id	    integer   NOT NULL PRIMARY KEY,"
                    + "name	    varchar(50)  NOT NULL,"
                    + "price    decimal(19,4) NOT NULL,"
                    + "brand    varchar(50)  NOT NULL,"
                    + "weight   varchar(10)  NOT NULL,"
                    + "discount decimal(10,2)  NOT NULL"
            		+ ")";
            try (Statement comando = conexao.createStatement()) {
                comando.executeUpdate(sql);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public static void dropTableProduct(){
    	try (Connection conexao = Database.getConexaoViaDataSource()) {
            //Criar uma tabela
            String sql = "drop table product";
            try (Statement comando = conexao.createStatement()) {
                comando.executeUpdate(sql);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
