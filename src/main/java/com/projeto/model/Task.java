package com.projeto.model;

import javafx.beans.property.*;

public class Task {
    private final IntegerProperty id = new SimpleIntegerProperty(0);
    
    private final StringProperty titulo = new SimpleStringProperty();
    private final StringProperty descricao = new SimpleStringProperty();
    private final StringProperty prioridade = new SimpleStringProperty();
    private final BooleanProperty concluida = new SimpleBooleanProperty(false);

    public Task() {}

    public Task(String titulo, String descricao, String prioridade) {
        this.titulo.set(titulo);
        this.descricao.set(descricao);
        this.prioridade.set(prioridade);
        this.concluida.set(false);
    }
    
    public Task(int id, String titulo, String descricao, String prioridade, boolean concluida) {
        this.id.set(id);
        this.titulo.set(titulo);
        this.descricao.set(descricao);
        this.prioridade.set(prioridade);
        this.concluida.set(concluida);
    }

    public int getId() { return id.get(); }
    public void setId(int i) { id.set(i); }
    public IntegerProperty idProperty() { return id; }

    public String getTitulo() { return titulo.get(); }
    public void setTitulo(String t){ titulo.set(t); }
    public StringProperty tituloProperty(){ return titulo; }

    public String getDescricao(){ return descricao.get(); }
    public void setDescricao(String d){ descricao.set(d); }
    public StringProperty descricaoProperty(){ return descricao; }

    public String getPrioridade(){ return prioridade.get(); }
    public void setPrioridade(String p){ prioridade.set(p); }
    public StringProperty prioridadeProperty(){ return prioridade; }

    public boolean isConcluida(){ return concluida.get(); }
    public void setConcluida(boolean c){ concluida.set(c); }
    public BooleanProperty concluidaProperty(){ return concluida; }
}