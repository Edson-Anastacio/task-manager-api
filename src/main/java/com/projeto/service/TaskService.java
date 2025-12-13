package com.projeto.service;

import com.projeto.TaskDAO; 
import com.projeto.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TaskService {

    private static TaskService instance;
    
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();
    
    private final TaskDAO dao = new TaskDAO();

    private TaskService() {
        atualizarListaDoBanco();
    }

    public static TaskService getInstance(){
        if (instance == null) instance = new TaskService();
        return instance;
    }

    public ObservableList<Task> getTasks(){ 
        return tasks; 
    }

    // === MÉTODOS QUE CONECTAM TELA <-> BANCO ===

    public void atualizarListaDoBanco() {
        tasks.clear(); 
        tasks.addAll(dao.listarTodas()); 
    }

    public void add(Task t){ 
        dao.adicionar(t); 
        tasks.add(t);     
    }

    public void remove(Task t){ 
        dao.deletar(t);   
        tasks.remove(t);  
    }
    
    public void update(Task t) {
        dao.atualizar(t); 
    }
}