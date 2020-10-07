package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Editora;

public class EditoraDAO extends GenericDAO {

    public void insert(Editora editora) {

        String sql = "INSERT INTO Editora (cnpj, nome) VALUES (?, ?)";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement = conn.prepareStatement(sql);
            statement.setString(1, editora.getCNPJ());
            statement.setString(2, editora.getNome());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Editora> getAll() {

        List<Editora> listaEditoras = new ArrayList<>();

        String sql = "SELECT * from Editora";

        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String cnpj = resultSet.getString("cnpj");
                String nome = resultSet.getString("nome");
                Editora editora = new Editora(id, cnpj, nome);
                editora.setQtdeLivros(new LivroDAO().countByEditora(id));
                listaEditoras.add(editora);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaEditoras;
    }

    public void delete(Editora editora) {
        String sql = "DELETE FROM Editora where id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, editora.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Editora editora) {
        String sql = "UPDATE Editora SET cnpj = ?, nome = ?";
        sql += " WHERE id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, editora.getCNPJ());
            statement.setString(2, editora.getNome());
            statement.setLong(3, editora.getId());
            
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Editora get(Long id) {
        Editora editora = null;
        
        String sql = "SELECT * from Editora where id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cnpj = resultSet.getString("cnpj");
                String nome = resultSet.getString("nome");
                editora = new Editora(id, cnpj, nome);
                editora.setQtdeLivros(new LivroDAO().countByEditora(id));
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return editora;
    }
}