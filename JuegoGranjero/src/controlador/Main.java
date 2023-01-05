package controlador;

import java.io.IOException;
import java.util.Random;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import modelo.Jugador;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;


public class Main extends Application {
	public static Stage stageJuego = new Stage();
	public static Scene sceneMenu;
	
	public static final int wMenu = 400;
	public static final int hMenu = 600;
	
	static StackPane juego = new StackPane();
	static GridPane mapaPane = new GridPane();
	static Pane jugadorPane = new Pane();
	static HBox puntPane = new HBox();
	static HBox masUno = new HBox();
	
	static int anchoMapa = 5;
	static int altoMapa = 5;
	public static int[][] mapa = new int[altoMapa][anchoMapa];
	public static Jugador j;
	public static int puntuacion = 0;
	public static Label labPunt;
	public static int trigos = 2;
	
	@Override
	public void start(Stage stage) throws IOException {
		Scene sceneJuego = new Scene(juego);
		
		Scene sceneInstrucciones = FXMLLoader.load(getClass().getResource("/vista/Instrucciones.fxml"));
		stageJuego.setScene(sceneInstrucciones);
	//Menu
		GridPane fondoMenu = new GridPane();
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 6; j++) {
				Random rd = new Random();
				ImageView fotoFondo = new ImageView();
				switch(rd.nextInt(4)) {
				case 0:
					fotoFondo = new ImageView(new Image("texturas/100/arado.png"));
					break;
				case 1:
					fotoFondo = new ImageView(new Image("texturas/100/sinCrecer.png"));
					break;
				case 2:
					fotoFondo = new ImageView(new Image("texturas/100/crecido.png"));
					break;
				case 3:
					fotoFondo = new ImageView(new Image("texturas/100/agujero.png"));
					break;
				}
				fotoFondo.setOpacity(0.65);
				GridPane.setColumnIndex(fotoFondo, i);
				GridPane.setRowIndex(fotoFondo, j);
				fondoMenu.getChildren().add(fotoFondo);
			}
		}
		Pane menuPane = new Pane();
		menuPane.getChildren().addAll(new Rectangle(wMenu, hMenu, Color.BLACK), fondoMenu);
		VBox opciones = new VBox();
		
		BorderPane sliders = new BorderPane();
		ColorAdjust ca = new ColorAdjust();
		ca.setBrightness(1);
		//Slider de la altura del mapa
		Slider slAlto = new Slider();
		slAlto.setOrientation(Orientation.VERTICAL);
		slAlto.setMajorTickUnit(1);
		slAlto.setMinorTickCount(0);
		slAlto.setShowTickLabels(true);
		slAlto.setShowTickMarks(true);
		slAlto.setSnapToTicks(true);
		slAlto.setMax(7);
		slAlto.setMin(2);
		slAlto.setValue(5);
		slAlto.setEffect(ca);
		altoMapa = (int)slAlto.getValue();
		slAlto.valueProperty().addListener((ov, old_val, new_val) -> {
			altoMapa = (int)Double.parseDouble("" + new_val);
		});
		
		//Slider del ancho del mapa
		Slider slAncho = new Slider();
		slAncho.setMajorTickUnit(1);
		slAncho.setMinorTickCount(0);
		slAncho.setShowTickLabels(true);
		slAncho.setShowTickMarks(true);
		slAncho.setSnapToTicks(true);
		slAncho.setMax(13);
		slAncho.setMin(2);
		slAncho.setValue(5);
		slAncho.setEffect(ca);
		slAncho.setPrefWidth(200);
		anchoMapa = (int)slAncho.getValue();
		slAncho.valueProperty().addListener((ov, old_val, new_val) -> {
			anchoMapa = (int)Double.parseDouble("" + new_val);
		});
		
		Label labSl = new Label("Tamaño del mapa");
		labSl.setTextFill(Color.WHITE);
		labSl.setScaleX(1.5);
		labSl.setScaleY(1.5);
		labSl.setTranslateX(60);
		sliders.setTop(labSl);
		sliders.setLeft(slAlto);
		sliders.setBottom(slAncho);
		sliders.setTranslateX(-20);
		
		//Selector del nº de trigos
		HBox numTrigos = new HBox();
		ImageView iconoTrigo = new ImageView(new Image("texturas/60/puntuacion.png"));
		Button btTrigoMenos = new Button("-");
		btTrigoMenos.setPrefWidth(25);
		btTrigoMenos.setTranslateY(20);
		TextField tfTrigo = new TextField("" + trigos);
		tfTrigo.setPrefWidth(35);
		tfTrigo.setTranslateY(20);
		Button btTrigoMas = new Button("+");
		btTrigoMas.setPrefWidth(25);
		btTrigoMas.setTranslateY(20);
		numTrigos.getChildren().addAll(iconoTrigo, btTrigoMenos, tfTrigo, btTrigoMas);
		btTrigoMenos.setOnAction(e -> {
			if(trigos > 1) {
				trigos--;
				tfTrigo.setText("" + trigos);
			}
		});
		tfTrigo.setOnAction(e -> {
			trigos = Integer.parseInt(tfTrigo.getText());
		});
		btTrigoMas.setOnAction(e -> {
			trigos++;
			tfTrigo.setText("" + trigos);
		});
		
		//Boton para comenzar el juego
		Button btJugar = new Button("JUGAR");
		btJugar.setTranslateX(50);
		btJugar.setOnAction(e -> {
			puntuacion = 0;
			labPunt.setText("" + puntuacion);
			j.setX(anchoMapa/2);
			j.setY(altoMapa/2);
			j.iV.setImage(new Image("texturas/100/granjeroRobotFrente.png"));
			mapa = new int[altoMapa][anchoMapa];
			
			trigos = Integer.parseInt(tfTrigo.getText());
			if(trigos > (altoMapa * anchoMapa)/3) {//Solo puede haber la mitad del mapa de trigos
				trigos = (altoMapa * anchoMapa)/3;
			}
			//Planta los primeros trigos
			for(int i = 0; i < trigos; i++) {
				plantarTrigo();
				crecerTrigo();
			}
			plantarTrigo();
			dibujarMapa();
			stageJuego.setScene(sceneJuego);
//			juego.setScaleX(.5);
//			juego.setScaleY(.5);
			puntPane.setTranslateX((anchoMapa-1)*100);
		});
		
		opciones.setTranslateX(125);
		opciones.setTranslateY(100);
		opciones.setSpacing(30);
		opciones.getChildren().addAll(sliders, numTrigos, btJugar);
		menuPane.getChildren().addAll(opciones);
		sceneMenu = new Scene(menuPane, wMenu, hMenu);
//		stageJuego.setScene(sceneMenu);
		
		
	//Juego
		juego.getChildren().addAll(mapaPane, jugadorPane, puntPane);
		
		//Configuracion del panel de la puntuacion
		puntPane.setSpacing(15);
		puntPane.getChildren().add(new ImageView(new Image("texturas/60/puntuacion.png")));
		labPunt = new Label("" + puntuacion);
		labPunt.setScaleX(3); labPunt.setScaleY(3);
		labPunt.setTextFill(new Color(.83, .78, .35, 1));
		labPunt.setTranslateY(20);
		puntPane.getChildren().add(labPunt);
		
		j = new Jugador(mapa[0].length/2, mapa.length/2);
		juego.getChildren().add(masUno);
		
		//Moverse
		sceneJuego.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
				j.moverN();
			}else if(e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
				j.moverO();
			}else if(e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
				j.moverS();
			}else if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
				j.moverE();
			}else if(e.getCode() == KeyCode.ESCAPE) {
				stageJuego.setScene(sceneMenu);
			}
			
			//else if(e.getCode() == KeyCode.DIGIT1) {
//				crecerTrigo();
//			}else if(e.getCode() == KeyCode.DIGIT2) {
//				plantarTrigo();
//			}
//			mostrarPosJug();
//			mostrarMapa();
			
			//Se desplaza a la nueva posicion
			TranslateTransition tt = new TranslateTransition(Duration.millis(300), j.iV);
			tt.setToX(j.getX()*100);
			tt.setToY(j.getY()*100);
			tt.setCycleCount(1);
	        tt.play();
			
			//Comprueba si ha llegado a un trigo
			if(mapa[j.getY()][j.getX()] == 2) {
				puntuacion++;
//				System.out.println("\nPuntuacion: " + puntuacion);
				labPunt.setText("" + puntuacion);
				mapa[j.getY()][j.getX()] = 3;
				crecerTrigo();
				plantarTrigo();
				animacionMasUno();
				dibujarMapa();
			}
			
			
			//Victoria
			if(comprobarVictoria()) {
				Stage stageVictoria = new Stage();
				StackPane victoriaPane = new StackPane();
				victoriaPane.getChildren().add(new ImageView(new Image("texturas/100/crecido.png")));
				victoriaPane.getChildren().add(new Label("¡HAS GANADO!\n  Puntuación: " + puntuacion));
				victoriaPane.setScaleX(2);
				victoriaPane.setScaleY(2);
				stageVictoria.setScene(new Scene(victoriaPane, 200, 200));
				stageVictoria.getIcons().add(new Image("texturas/100/puntuacion.png"));
				stageVictoria.show();
				stageJuego.close();
			}
		});
		
		plantarTrigo();
		crecerTrigo();
		plantarTrigo();
		dibujarMapa();
		
		jugadorPane.getChildren().add(j.iV);
		
		stageJuego.setTitle("Juego");
		stageJuego.getIcons().add(new Image("texturas/100/puntuacion.png"));
//		stageJuego.setScene(sceneJuego);
		stageJuego.setResizable(false);
		stageJuego.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	//Dibuja el mapa
	public static void dibujarMapa() {
		mapaPane.getChildren().clear();
		for(int y = 0; y < mapa.length; y++) {
			for(int x = 0; x < mapa[y].length; x++) {
				ImageView suelo = new ImageView();
				if(mapa[y][x] == 0) {
					suelo = new ImageView(new Image("texturas/100/arado.png"));
				}else if(mapa[y][x] == 1) {
					suelo = new ImageView(new Image("texturas/100/sinCrecer.png"));
				}else if(mapa[y][x] == 2) {
					suelo = new ImageView(new Image("texturas/100/crecido.png"));
				}else if(mapa[y][x] == 3) {
					suelo = new ImageView(new Image("texturas/100/agujero.png"));
				}
				GridPane.setRowIndex(suelo, y);
				GridPane.setColumnIndex(suelo, x);
				mapaPane.getChildren().add(suelo);
			}
		}
	}
	
	//Cambia los 1 por 2
	public void crecerTrigo() {
		for(int y = 0; y < mapa.length; y++) {
			for(int x = 0; x < mapa[y].length; x++) {
				if(mapa[y][x] == 1) {
					mapa[y][x] = 2;
				}
			}
		}
	}
	
	//Crea un nuevo trigo sin crecer
	public void plantarTrigo() {
		Random rd = new Random();
		int y, x;
		do{
			y = rd.nextInt(mapa.length);
			x = rd.nextInt(mapa[0].length);
		}while(mapa[y][x] != 0);
		mapa[y][x] = 1;
	}
	
	//Comprueba si queda alguna celda libre
	public boolean comprobarVictoria() {
		boolean victoria = true;
		for(int y = 0; y < mapa.length; y++) {
			for(int x = 0; x < mapa[y].length; x++) {
				if(mapa[y][x] == 0) {
					victoria = false;
				}
			}
		}
		return victoria;
	}
	
	//Realiza la animacion de +1
	public static void animacionMasUno() {
		masUno.getChildren().clear();
		
		masUno.setTranslateX(j.getX()*100-30);
		masUno.setTranslateY(j.getY()*100-30);
		masUno.setScaleX(.8);
		masUno.setScaleY(.8);
		masUno.setSpacing(40);
		
		//Creacion del panel
		ImageView iv = new ImageView(new Image("texturas/60/puntuacion.png"));
		iv.setScaleX(1.3);
		iv.setScaleY(1.3);
		masUno.getChildren().add(iv);
		Label lab = new Label(" +1");
		lab.setTextFill(new Color(.83, .78, .35, 1));
		lab.setScaleX(5);
		lab.setScaleY(5);
		masUno.getChildren().add(lab);
		
		//Animacion
		FadeTransition ft = new FadeTransition(Duration.millis(700), masUno);
		ft.setFromValue(0);
		ft.setToValue(1);
		ft.setCycleCount(1);
//		ft.play();
		
		TranslateTransition tt = new TranslateTransition(Duration.millis(1400), masUno);
		tt.setFromY(masUno.getTranslateY());
		tt.setToY(masUno.getTranslateY()-150);
		tt.setCycleCount(1);
//		tt.play();
		
		FadeTransition ft2 = new FadeTransition(Duration.millis(700), masUno);
		ft2.setFromValue(1);
		ft2.setToValue(0);
		ft2.setCycleCount(1);
//		ft2.play();
		
		SequentialTransition fadeTransitions = new SequentialTransition();
		fadeTransitions.getChildren().addAll(ft, ft2);
		fadeTransitions.setCycleCount(1);
		ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(fadeTransitions, tt);
        parallelTransition.setCycleCount(1);
        parallelTransition.play();
	}
	
	//Muestra la pos del jugador en la consola
	public static void mostrarPosJug() {
		System.out.println("Posición del jugador:");
		for(int y = 0; y < mapa.length; y++) {
			for(int x = 0; x < mapa[y].length; x++) {
				if(y == j.getY() && x == j.getX()) {
					System.out.print("1 ");
				}else {
					System.out.print("0 ");
				}
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	//Muestra el mapa
		public static void mostrarMapa() {
			System.out.println("Mapa:");
			for(int y = 0; y < mapa.length; y++) {
				for(int x = 0; x < mapa[y].length; x++) {
					System.out.print(mapa[y][x] + " ");
				}
				System.out.println("");
			}
			System.out.println("");
		}
	
}
