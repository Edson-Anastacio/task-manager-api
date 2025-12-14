package com.projeto.controller;

import com.projeto.model.Task;
import com.projeto.service.TaskService;
import javafx.beans.binding.Bindings;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class MainController {

    @FXML private TableView<Task> table;
    @FXML private TableColumn<Task, String> colTitulo;
    @FXML private TableColumn<Task, String> colPrioridade;
    @FXML private TableColumn<Task, Boolean> colConcluida;
    @FXML private ComboBox<String> filterCombo;
    @FXML private Button btnEditar;
    @FXML private Button btnRemover;
    @FXML private Button btnToggleConcluida;

    private final TaskService service = TaskService.getInstance();
    private FilteredList<Task> filtered;

    @FXML
    public void initialize(){
        // --- ALTERAÇÃO AQUI: CORREÇÃO DO ESPAÇO VAZIO ---
        // Isso força as colunas a ocuparem a largura total da tabela
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        // ------------------------------------------------

        colTitulo.setCellValueFactory(cell -> cell.getValue().tituloProperty());
        colPrioridade.setCellValueFactory(cell -> cell.getValue().prioridadeProperty());
        colConcluida.setCellValueFactory(new PropertyValueFactory<>("concluida"));

        filtered = new FilteredList<>(service.getTasks(), t -> true);
        table.setItems(filtered);

        filterCombo.getItems().addAll("Todas", "Ativas", "Concluídas");
        filterCombo.setValue("Todas");
        filterCombo.valueProperty().addListener((obs, oldV, newV) -> applyFilter(newV));

        btnEditar.disableProperty().bind(Bindings.isNull(table.getSelectionModel().selectedItemProperty()));
        btnRemover.disableProperty().bind(Bindings.isNull(table.getSelectionModel().selectedItemProperty()));
        btnToggleConcluida.disableProperty().bind(Bindings.isNull(table.getSelectionModel().selectedItemProperty()));

        // Configuração do CheckBox Clicável
        colConcluida.setCellFactory(tc -> new TableCell<Task, Boolean>() {
            private final CheckBox cb = new CheckBox();

            {
                cb.setOnAction(event -> {
                    Task task = getTableView().getItems().get(getIndex());
                    
                    if (task != null) {
                        task.setConcluida(cb.isSelected());
                        
                        service.update(task);
                        
                        table.refresh(); 
                    }
                });
            }

            @Override
            protected void updateItem(Boolean concluida, boolean empty) {
                super.updateItem(concluida, empty);
                if (empty || concluida == null) {
                    setGraphic(null);
                } else {
                    cb.setSelected(concluida);
                    setGraphic(cb); 
                }
            }
        });
    }

    private void applyFilter(String mode){
        switch (mode){
            case "Ativas":
                filtered.setPredicate(t -> !t.isConcluida());
                break;
            case "Concluídas":
                filtered.setPredicate(Task::isConcluida);
                break;
            default:
                filtered.setPredicate(t -> true);
        }
    }

    @FXML
    public void onAdicionar() throws IOException {
        Task novo = showTaskDialog(null);
        if (novo != null) {
            service.add(novo); 
        }
    }

    @FXML
    public void onEditar() throws IOException {
        Task selec = table.getSelectionModel().getSelectedItem();
        if (selec == null) return;

        Task resultado = showTaskDialog(selec);
        
        if (resultado != null) {
            service.update(selec); 
            table.refresh(); 
        }
    }

    @FXML
    public void onRemover(){
        Task selec = table.getSelectionModel().getSelectedItem();
        if (selec == null) return;
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remover tarefa selecionada?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> opt = alert.showAndWait();
        
        if (opt.isPresent() && opt.get() == ButtonType.YES){
            service.remove(selec); 
        }
    }

    @FXML
    public void onToggleConcluida(){
        Task selec = table.getSelectionModel().getSelectedItem();
        if (selec == null) return;
        
        selec.setConcluida(!selec.isConcluida());
        service.update(selec); 
        table.refresh();
    }

    private Task showTaskDialog(Task toEdit) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task-dialog.fxml"));
        
        javafx.scene.Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(toEdit == null ? "Adicionar Tarefa" : "Editar Tarefa");
        
        Scene scene = new Scene(root);

        String cssPath = getClass().getResource("/css/styles.css").toExternalForm();
        scene.getStylesheets().add(cssPath);
        
        stage.setScene(scene);
        
        TaskDialogController ctrl = loader.getController();
        if (toEdit != null) ctrl.setTask(toEdit);
        
        stage.showAndWait();
        return ctrl.getResult();
    }
}