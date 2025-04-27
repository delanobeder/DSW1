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

			/* Setup para uso do banco de dados PostgreSQL */

			/*
			 * Class.forName("org.postgresql.Driver");
			 * String url = "jdbc:postgresql://localhost:5432/Livraria";
			 * Connection con = (Connection) DriverManager.getConnection(url, "root", "root");
			 */
			
			/* Setup para uso do banco de dados Derby */

			/*
			 * Class.forName("org.apache.derby.jdbc.ClientDriver");
			 * String url = "jdbc:derby://localhost:1527/Livraria";
			 * Connection con = (Connection) DriverManager.getConnection(url,
			 * "root", "root");
			 */

			Statement stmt = con.createStatement();
			String query = "SELECT l.titulo, l.autor, l.ano, l.preco, e.nome FROM Livro l, Editora e WHERE l.editora_id = e.id;";
			ResultSet rs = stmt.executeQuery(query);
			while (rs.next()) {
				System.out.print(rs.getString("titulo"));
				System.out.print(", " + rs.getString("autor"));
				System.out.print(", " + rs.getInt("ano"));
				System.out.print(", " + rs.getString("nome"));
				System.out.println(" (R$ " + rs.getFloat("preco") + ")");
			}
			stmt.close();
			con.close();
		} catch (ClassNotFoundException e) {
			System.out.println("A classe do driver de conexão não foi encontrada!");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("O comando SQL não pode ser executado!");
		}
	}
}
