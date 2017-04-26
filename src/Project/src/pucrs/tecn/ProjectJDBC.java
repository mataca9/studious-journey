package pucrs.tecn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

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

    	Database.dropTableProduct();
    	Database.createTableProduct();
    	ProductDAO pDao = new ProductDAO();
        //Passo2: inserir dados
    	pDao.insertProduct(1, "Whey Protein Concentrado", (float)76.50, "Growth", "1kg", 10);
    	pDao.insertProduct(2, "Whey Protein Isolado", (float)139.50, "Growth", "1kg", 10);
    	pDao.insertProduct(3, "Basic Whey Protein", (float)34.20, "Growth", "1kg", 10);
    	pDao.insertProduct(4, "100% Whey Protein", (float)161.10, "Optimun", "909g", 10);
    	pDao.insertProduct(5, "100% Whey Protein", (float)81.00, "Max Titanium", "900g", 10);
        
        //Passo3: consultar dados
        List<Product> products = pDao.selectAllProducts();
        
        for(Product p : products)
        	System.out.println(p.toString());
        
    	System.out.println("Count: " + pDao.countProducts());
    	
    	System.out.println(pDao.selectProduct(2).toString());
    	pDao.renameProduct(2, "wheyzin");
    	System.out.println(pDao.selectProduct(2).toString());
    	pDao.deleteProduct(2);
    	System.out.println("Count: " + pDao.countProducts());
    }

}
