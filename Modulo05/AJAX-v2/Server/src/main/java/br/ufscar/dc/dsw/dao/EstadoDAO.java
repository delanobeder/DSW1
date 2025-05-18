package br.ufscar.dc.dsw.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Estado;

public class EstadoDAO extends GenericDAO<Estado> {

    private final static String CRIAR_SQL = "insert into Estado"
            + " (nome, sigla) values (?,?)";

    private final static String SELECT_SQL = "select id, nome, sigla "
            + " from Estado order by id";
            
    private final static String SELECT_SIGLA_SQL = "select id, nome "
            + " from Estado e where e.sigla = ?";
            
    @Override
    public void save(Estado estado) {

        try {
            PreparedStatement ps = this.connection.prepareStatement(CRIAR_SQL,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, estado.getNome());
            ps.setString(2, estado.getSigla());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            estado.setId(rs.getInt(1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public List<Estado> getAll() {

        List<Estado> lista = new ArrayList<>();

        try {
            Statement stmt = this.connection.createStatement();
            ResultSet res = stmt.executeQuery(SELECT_SQL);

            while (res.next()) {
                int id = res.getInt(1);
                String nome = res.getString(2);
                String sigla = res.getString(3);
                lista.add(new Estado(id, sigla, nome));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
        
    public Estado getBySigla(String sigla) {

        Estado estado = null;

        try {
            PreparedStatement stmt;
            stmt = this.connection.prepareStatement(SELECT_SIGLA_SQL);
            stmt.setString(1, sigla);
            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                int id = res.getInt(1);
                String nome = res.getString(2);
                estado = new Estado(id, sigla, nome);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return estado;
    }
}