package br.ufscar.dc.dsw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AcessaBD {

	public static void main(String[] args) {
		try {

			/* Setup para uso do banco de dados MySQL */
			
			Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Livraria";
			Connection con = (Connection) DriverManager.getConnection(url,
					"root", "root");

			/* Setup para uso do banco de dados Derby */

			/*
			 * Class.forName("org.apache.derby.jdbc.ClientDriver");
             * String url =  "jdbc:derby://localhost:1527/Livraria";
			 * Connection con = (Connection) DriverManager.getConnection(url, 
			 *         "root", "root");
			 */

			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from Livro");
			while (rs.next()) {
				System.out.print(rs.getString("Titulo"));
				System.out.print(", " + rs.getString("Autor"));
				System.out.print(", " + rs.getInt("Ano"));
				System.out.println(" (R$ " + rs.getFloat("Preco") + ")");
			}
			stmt.close();
			con.close();
		} catch (ClassNotFoundException e) {
			System.out.println("A classe do driver de conexão não foi encontrada!");
		} catch (SQLException e) {
			System.out.println("O comando SQL não pode ser executado!");
		}
	}
}
