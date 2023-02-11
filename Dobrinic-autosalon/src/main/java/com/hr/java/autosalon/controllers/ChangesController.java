package com.hr.java.autosalon.controllers;

import com.hr.java.autosalon.exceptions.ChangesException;
import com.hr.java.autosalon.main.Main;
import com.hr.java.autosalon.util.Change;
import com.hr.java.autosalon.util.ChangesWriter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controls the changes view
 */
public class ChangesController {

    @FXML
    private TableColumn<Change,String> beforeTableColumn;

    @FXML
    private TableColumn<Change,String> afterTableColumn;

    @FXML
    private TableColumn<Change,String> userTableColumn;

    @FXML
    private TableColumn<Change,String> timestampTableColumn;

    @FXML
    private TableView<Change> changesTableView;

    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    public void initialize(){
        beforeTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBefore().toString()));
        afterTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAfter().toString()));
        userTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        timestampTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTimestamp().toString()));

        try{
            changesTableView.setItems(FXCollections.observableArrayList(ChangesWriter.getChanges()).sorted((a,b) -> b.getTimestamp().compareTo(a.getTimestamp())));
        }catch (ChangesException e){
            logger.info(e.getMessage(),e);
        }
    }
}
