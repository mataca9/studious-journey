package pucrs.tecn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.derby.jdbc.EmbeddedDataSource;

public class ExemploToursDB {

	public static String DB_NAME = "toursdb";
	private static DataSource dataSource;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String[] args) {
		try (Connection conexao = getConexaoViaDataSource()) {
			// Consulta a lista de cidades cujo nome começa com 'S'
			String sql3 = "select * from cities where city_name like 'S%'";
			try (PreparedStatement comando = conexao.prepareStatement(sql3)) {
				try (ResultSet resultados = comando.executeQuery()) {
					while (resultados.next()) {
						System.out.println(String.format("%04d - %s",
								resultados.getInt("city_id"),
								resultados.getString("city_name")));
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private static Connection getConexaoViaDataSource() throws Exception {
		if (dataSource == null) {
			EmbeddedDataSource ds = new EmbeddedDataSource();
			ds.setDatabaseName(DB_NAME);
			dataSource = ds;
		}
		return dataSource.getConnection();
	}
}
