package controlador;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class ControladorInstrucciones {

    @FXML
    public Scene sceneInstrucciones;

    @FXML
    private Label tituloJuego;

    @FXML
    private Label labJuego1;

    @FXML
    private Label labJuego2;

    @FXML
    private ImageView IVTrigo;

    @FXML
    private Label labJuego3;

    @FXML
    private Label labJuego4;

    @FXML
    private ImageView IVHierba;

    @FXML
    private Label labJuego5;

    @FXML
    private ImageView IVAgujero;

    @FXML
    private Label labJuego6;

    @FXML
    private Label labJuego7;

    @FXML
    private Label tituloControles;

    @FXML
    private Label labControlesFlechas;

    @FXML
    private Label labControlesEsc;

    @FXML
    private Label labContinuar;

    @FXML
    void continuar(KeyEvent event) {
    	Main.stageJuego.setScene(Main.sceneMenu);
    }

}
