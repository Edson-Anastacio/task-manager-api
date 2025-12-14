package com.projeto;

import javafx.scene.control.CheckBox;

public class Tarefa {
    private int id;
    private String titulo; 
    private CheckBox status;

    public Tarefa(int id, String titulo, boolean concluida) {
        this.id = id;
        this.titulo = titulo;
        
        this.status = new CheckBox();
        this.status.setSelected(concluida); 

        this.status.setOnAction(evento -> {
            if (status.isSelected()) {
                System.out.println("✅ Tarefa concluída: " + titulo);
            } else {
                System.out.println("⭕ Tarefa reaberta: " + titulo);
            }
        });
    }

    
    public int getId() { return id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public CheckBox getStatus() { return status; }
    public void setStatus(CheckBox status) { this.status = status; }
}