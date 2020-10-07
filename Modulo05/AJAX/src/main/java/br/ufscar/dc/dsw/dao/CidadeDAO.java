package br.ufscar.dc.dsw.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;

public class CidadeDAO extends GenericDAO<Cidade> {

    private final static String SQL_SAVE = "insert into Cidade"
            + " (nome, estado_id) values (?,?)";

    private final static String SQL_SELECT_ESTADO = "select id, nome"
            + " from Cidade as c where c.estado_id = ? order by id";

    private final static String SQL_SELECT_NOME = "select c.id, c.nome,"
            + " e.id, e.sigla, e.nome from Cidade as c, Estado as e"
            + " where UPPER(c.nome) like ? and c.estado_id = e.id"
            + " order by c.nome";

    private final static String SQL_SELECT_ALL = "select c.id, c.nome,"
            + " e.id, e.sigla, e.nome from Cidade as c, Estado as e"
            + " where c.estado_id = e.id order by c.id";

    @Override
    public void save(Cidade cidade) {
        try {
            PreparedStatement ps = this.connection.prepareStatement(SQL_SAVE,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, cidade.getNome());
            ps.setInt(2, cidade.getEstado().getId());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            cidade.setId(rs.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cidade> getAll() {

        List<Cidade> lista = new ArrayList<>();

        try {
            Statement stmt = this.connection.createStatement();
            ResultSet res = stmt.executeQuery(SQL_SELECT_ALL);

            while (res.next()) {
                int id = res.getInt(3);
                String sigla = res.getString(4);
                String nome = res.getString(5);
                Estado estado = new Estado(id, sigla, nome);
                id = res.getInt(1);
                nome = res.getString(2);
                Cidade cidade = new Cidade(id, nome, estado);

                lista.add(cidade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Cidade> getByEstado(Estado estado) {
        List<Cidade> lista = new ArrayList<>();

        try {
            PreparedStatement stmt;
            stmt = this.connection.prepareStatement(SQL_SELECT_ESTADO);
            stmt.setInt(1, estado.getId());
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                int id = res.getInt(1);
                String nome = res.getString(2);
                lista.add(new Cidade(id, nome, estado));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public List<Cidade> getByNome(String s) {

        List<Cidade> lista = new ArrayList<>();

        try {
            PreparedStatement stmt;
            stmt = this.connection.prepareStatement(SQL_SELECT_NOME);
            stmt.setString(1, "%"+ s.toUpperCase() + "%");
            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                int id = res.getInt(3);
                String sigla = res.getString(4);
                String nome = res.getString(5);
                Estado estado = new Estado(id, sigla, nome);
                id = res.getInt(1);
                nome = res.getString(2);
                Cidade cidade = new Cidade(id, nome, estado);

                lista.add(cidade);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }
}