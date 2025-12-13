package com.projeto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SetupBanco {
    
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/postgres";
        String usuario = "postgres";
        String senha = "minhasenha";

        String sql = "CREATE TABLE IF NOT EXISTS tarefas (" +
                     "id SERIAL PRIMARY KEY," +
                     "titulo VARCHAR(100) NOT NULL," +
                     "descricao TEXT," +
                     "prioridade VARCHAR(20)," +
                     "concluida BOOLEAN DEFAULT FALSE" +
                     ");";

        try (Connection conn = DriverManager.getConnection(url, usuario, senha);
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);
            System.out.println(" Tabela 'tarefas' criada com sucesso no Docker!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}