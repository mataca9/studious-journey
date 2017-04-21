package pucrs.tecn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.sql.DataSource;
import org.apache.derby.jdbc.EmbeddedDataSource;

/**
 *
 * @author Gustavo de Lima e Matheus Streb
 */
public class ProjectJDBC {

    public static String DB_CONN_STRING_CREATE = "jdbc:derby:db;create=true";
    public static String DB_NAME = "db";
    public static String USER_NAME = "user";
    public static String PASSWORD = "123";
    private static DataSource dataSource;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Passo1: criar base de dados
        //Obter e usar conexao via try-with-resources
        //Conexao sera fechada automaticamente ao final do bloco
    	dropTableProduct();
    	createTableProduct();

        //Passo2: inserir dados
        insertProduct(1, "Whey Protein Concentrado", (float)76.50, "Growth", "1kg", 10);
        insertProduct(2, "Whey Protein Isolado", (float)139.50, "Growth", "1kg", 10);
        insertProduct(3, "Basic Whey Protein", (float)34.20, "Growth", "1kg", 10);
        insertProduct(4, "100% Whey Protein", (float)161.10, "Optimun", "909g", 10);
        insertProduct(5, "100% Whey Protein", (float)81.00, "Max Titanium", "900g", 10);
        
        //Passo3: consultar dados
        //selectAllProducts();
        countProducts();
        selectProduct(2);
        renameProduct(2, "wheyzin");
        selectProduct(2);
        deleteProduct(2);
        countProducts();
    }

    private static Connection getConexaoViaDriverManager() throws Exception {
        return DriverManager.getConnection(DB_CONN_STRING_CREATE, USER_NAME, PASSWORD);
    }

    private static Connection getConexaoViaDataSource() throws Exception {
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
    
    private static void insertProduct(int id, String name, float price, String brand, String weight, float discount){
    	try (Connection conexao = getConexaoViaDriverManager()) {
            //Inserir dados na tabela
            String sqlInsertion = "insert into product(id, name, price, brand, weight, discount) values(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement comando = conexao.prepareStatement(sqlInsertion)) {
            	comando.setInt(1, id);
            	comando.setString(2, name);
            	comando.setFloat(3, price);
            	comando.setString(4, brand);
            	comando.setString(5, weight);
            	comando.setFloat(6, discount);
                if (comando.executeUpdate() > 0) {
                    System.out.println("Insercao efetuada com sucesso");
                } else {
                    System.out.println("Falha na insercao");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    private static void createTableProduct(){
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
    
    private static void dropTableProduct(){
    	try (Connection conexao = getConexaoViaDataSource()) {
            //Criar uma tabela
            String sql = "drop table product";
            try (Statement comando = conexao.createStatement()) {
                comando.executeUpdate(sql);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static void selectAllProducts(){
    	try (Connection conexao = getConexaoViaDriverManager()) {
            //Consultar dados da tabela
            String sql3 = "select * from product";
            try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
                try (ResultSet resultados = comando.executeQuery()) {
                    while (resultados.next()) {
                        System.out.println(String.format("\nID: %d\nName: %s\nPrice: %.2f", resultados.getInt("id"), resultados.getString("name"), resultados.getFloat("price")));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static void selectProduct(int id){
    	try (Connection conexao = getConexaoViaDriverManager()) {
            //Consultar dados da tabela
            String sql3 = "select * from product where id = ?";
            try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
            	comando.setInt(1, id);
                try (ResultSet resultados = comando.executeQuery()) {
                    while (resultados.next()) {
                        System.out.println(String.format("\nID: %d\nName: %s\nPrice: %.2f", resultados.getInt("id"), resultados.getString("name"), resultados.getFloat("price")));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static void countProducts(){
    	try (Connection conexao = getConexaoViaDriverManager()) {
            //Consultar dados da tabela
            String sql3 = "select count(*) as count from product";
            try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
                try (ResultSet resultados = comando.executeQuery()) {
                    while (resultados.next()) {
                        System.out.println(String.format("\n%d produtos encontrados", resultados.getInt("count")));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static void renameProduct(int id, String newName){
    	try (Connection conexao = getConexaoViaDriverManager()) {
            //Consultar dados da tabela
            String sql3 = "update product set name = ? where id = ?";
            try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
            	comando.setString(1, newName);
            	comando.setInt(2, id);
            	comando.execute();
            	System.out.println("\nProduct updated successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private static void deleteProduct(int id){
    	try (Connection conexao = getConexaoViaDriverManager()) {
            //Consultar dados da tabela
            String sql3 = "delete from product where id = ?";
            try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
            	comando.setInt(1, id);
            	comando.execute();
            	System.out.println("\nProduct deleted successfully");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
