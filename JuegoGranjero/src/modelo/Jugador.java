package modelo;

import controlador.Main;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Jugador {
//Atributos
	private int x;
	private int y;
//	public ImageView iV = new ImageView(new Image("texturas/60/granjeroFrenteQuieto.png"));
	public ImageView iV = new ImageView(new Image("texturas/100/granjeroRobotFrente.png"));
	
//Metodos
	public Jugador(int x, int y) {
		this.x = x;
		this.y = y;
		iV.setTranslateX(x*100);
		iV.setTranslateY(y*100);
	}
	
	public void moverN() {
		if(y > 0 && Main.mapa[y-1][x] != 3) {
			y--;
		}
//		iV.setImage(new Image("texturas/100/granjeroEspaldaQuieto.png"));
		iV.setImage(new Image("texturas/100/granjeroRobotEspalda.png"));
	}
	public void moverO() {
		if(x < Main.mapa[0].length-1 && Main.mapa[y][x+1] != 3) {
			x++;
		}
//		iV.setImage(new Image("texturas/100/granjeroDerQuieto.png"));
		iV.setImage(new Image("texturas/100/granjeroRobotDer.png"));
	}
	public void moverS() {
		if(y < Main.mapa.length-1 && Main.mapa[y+1][x] != 3) {
			y++;
		}
//		iV.setImage(new Image("texturas/100/graneroFrenteQuieto.png"));
		iV.setImage(new Image("texturas/100/granjeroRobotFrente.png"));
	}
	public void moverE() {
		if(x > 0 && Main.mapa[y][x-1] != 3) {
			x--;
		}
//		iV.setImage(new Image("texturas/100/granjeroIzqQuieto.png"));
		iV.setImage(new Image("texturas/100/granjeroRobotIzq.png"));
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
		iV.setTranslateX(x*100);
	}

	public void setY(int y) {
		this.y = y;
		iV.setTranslateY(y*100);
	}
	
	
}
