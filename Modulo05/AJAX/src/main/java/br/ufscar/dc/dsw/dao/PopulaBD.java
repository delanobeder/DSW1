package br.ufscar.dc.dsw.dao;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import br.ufscar.dc.dsw.domain.Cidade;
import br.ufscar.dc.dsw.domain.Estado;

public class PopulaBD {

	static Map<String, Estado> map = new HashMap<>();

	private static void populaEstados() {

		try {
			EstadoDAO dao = new EstadoDAO();

			if (dao.getAll().size() == 0) { // checando se já não foi populado anteriormente

				InputStream stream = new FileInputStream("./estados.txt");
				InputStreamReader isr = new InputStreamReader(stream);
				BufferedReader reader = new BufferedReader(isr);
				String line = reader.readLine();

				while (line != null) {
					StringTokenizer tokenizer = new StringTokenizer(line, ",");

					Estado estado = new Estado();
					String sigla = tokenizer.nextToken();
					estado.setSigla(sigla);
					estado.setNome(tokenizer.nextToken());
					dao.save(estado);

					map.put(sigla, estado);

					System.out.println("Salvo: " + estado);

					line = reader.readLine();
				}
				stream.close();
				isr.close();
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void populaCidades() {

		try {
			
			CidadeDAO dao = new CidadeDAO();
			
			if (dao.getAll().size() == 0) { // checando se já não foi populado anteriormente

				String line;
				String nome;
				Estado estado;
				String sigla;

				InputStream stream = new FileInputStream("./cidades.txt");
				InputStreamReader isr = new InputStreamReader(stream);
				BufferedReader reader = new BufferedReader(isr);

				line = reader.readLine();

				while (line != null) {
					StringTokenizer tokenizer = new StringTokenizer(line, ",");
					sigla = tokenizer.nextToken();
					sigla = sigla.substring(1, 3);
					nome = tokenizer.nextToken();
					nome = nome.substring(1, nome.length() - 1);
					estado = map.get(sigla);
					Cidade cidade = new Cidade();
					cidade.setNome(nome);
					cidade.setEstado(estado);
					dao.save(cidade);
					System.out.println("Salvo: " + cidade);
					line = reader.readLine();
				}
				stream.close();
				isr.close();
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		populaEstados();
		populaCidades();
	}
}