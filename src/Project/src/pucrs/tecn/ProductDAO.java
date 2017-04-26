package pucrs.tecn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class ProductDAO {
	
    public void insertProduct(int id, String name, float price, String brand, String weight, float discount){
    	try (Connection conexao = Database.getConexaoViaDriverManager()) {
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
    
    
    public List<Product> selectAllProducts(){
    	List<Product> result = new ArrayList<>();
    	try (Connection conexao = Database.getConexaoViaDriverManager()) {
            //Consultar dados da tabela
            String sql3 = "select * from product";
            try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
                try (ResultSet resultados = comando.executeQuery()) {
                	
                    while (resultados.next()) {
                    	result.add(new Product(resultados.getInt("id"), resultados.getString("name"), resultados.getFloat("price"), resultados.getString("brand"), resultados.getString("weight"), resultados.getFloat("discount")));
                    }
                    
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    	return result;
    }
    
    public Product selectProduct(int id){
    	try (Connection conexao = Database.getConexaoViaDriverManager()) {
            //Consultar dados da tabela
            String sql3 = "select * from product where id = ?";
            try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
            	comando.setInt(1, id);
                try (ResultSet resultados = comando.executeQuery()) {
                    while (resultados.next()) {
                        return new Product(resultados.getInt("id"), resultados.getString("name"), resultados.getFloat("price"), resultados.getString("brand"), resultados.getString("weight"), resultados.getFloat("discount"));
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    	return null;
    }
    
    public int countProducts(){
    	try (Connection conexao = Database.getConexaoViaDriverManager()) {
            //Consultar dados da tabela
            String sql3 = "select count(*) as count from product";
            try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
                try (ResultSet resultados = comando.executeQuery()) {
                    while (resultados.next()) {
                        return resultados.getInt("count");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    	return 0;
    }
    
    public void renameProduct(int id, String newName){
    	try (Connection conexao = Database.getConexaoViaDriverManager()) {
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
    
    public void deleteProduct(int id){
    	try (Connection conexao = Database.getConexaoViaDriverManager()) {
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
