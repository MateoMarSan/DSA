package FightRadar;

/**
 *
 * @author Mateo Martinez Sanz
 * @author Hugo Mata Merino
 */
import java.util.ArrayList;

public class Usuario {
	
	//Atributos de la clase
	public String id;
	public double x;
	public double y;
	public  boolean grupo;
	public ArrayList <String> vecinos;
	
	//Constructor
	public Usuario(String linea){
		String [] partes = linea.split(" ");
		id = partes[0];
		x = Double.parseDouble(partes[1]);
		y = Double.parseDouble(partes[2]);
		if (partes[3].equals("S")) {
			grupo = true;
		}else {
			grupo = false;
		}
		vecinos = new ArrayList<>();
		for (int i = 4; i < partes.length; i++) {
			vecinos.add(partes[i]);
		}
	}
	
	//Métodos de la clase
	public double distancia(Usuario otro) {
		double dis;
		dis = Math.sqrt(Math.pow(x - otro.x, 2)+Math.pow(y - otro.y, 2));
		return dis;
	}
	
}

