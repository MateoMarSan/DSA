package FightRadar;

/**
 *
 * @author Mateo Martinez Sanz
 * @author Hugo Mata Merino
 */
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

//Primera práctica EDA 2022
public class Practica {

	public static void main(String[] args) {
		Scanner entrada = new Scanner(System.in);
		System.out.println("Práctica EDA 2022-23");
		System.out.println("Fichero de estado: ");
		String ficheroInicial = entrada.nextLine();
		BufferedReader br = null;
		// Conversión de una cadena a lectura del fichero inicial
		try {
			br = new BufferedReader(new FileReader(ficheroInicial));

		} catch (Exception e) {
			System.out.println(e.getMessage());
			
		}
		String[] divideFichero = ficheroInicial.split("_");
		// Obtención de los parámetros
		int n = (int) Double.parseDouble(divideFichero[1]);
		double d = Double.parseDouble(divideFichero[2]);
		System.out.println("Parámetros: n = " + n + ", d = " + d + "\nModo[D]epuración o [M]edición?");
		if (entrada.nextLine().equals("D")) {
			modoDepuracion(br);
		} else {

			modoMedicion(br);
		}
		
		entrada.close();
	}

	//Métodos
	
	/*
	 * @param fichero inicial
	 */
	public static void modoDepuracion(BufferedReader f) {
		Scanner entrada = new Scanner(System.in);
		ArrayList <Usuario> usuarios = leerUsuarios(f);
		System.out.print("Fichero de movimientos: ");
		String cadena;
		String ruta = entrada.nextLine();
		//ArrayList donde meteremos el fichero de movimientos introducido por el usuario
		ArrayList <String> movimientos = new ArrayList<String>();
		BufferedReader br;
		//Lectura del fichero movimientos
		try {
			 br = new BufferedReader(new FileReader(ruta));
			while ((cadena = br.readLine()) != null) {
				movimientos.add(cadena);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Procesando...");
		//Obtenemos el tiempo al incio del proceso
		double tiempo0 = System.nanoTime() * Math.pow(10, -9);
		ArrayList <String> mensajes = new ArrayList <String>();
		String id = null;
		double x = 0;
		double y = 0;
		//Recorremos el arrayList movimientos (almacena el fichero) para obtener cada id, coordenada x e y
		for (int movimiento = 0; movimiento < movimientos.size(); movimiento++) {
			String[] partes = movimientos.get(movimiento).split(" ");
			id = partes[0];
			x = Double.parseDouble(partes[1]);
			y = Double.parseDouble(partes[2]);
			Usuario usuarioActual = null;
			for (int usuario = 0; usuario < usuarios.size(); usuario++) {
				if (usuarios.get(usuario).id.equals(id)) {
					usuarioActual = usuarios.get(usuario);
					usuarioActual.x = x; //nuevas posiciones x e y del usuario actual
					usuarioActual.y = y;
				}
			}
			ArrayList <String> mensajesActuales = funcionalidad(usuarios, usuarioActual);
			mensajes.addAll(mensajesActuales);
		}
		//Obtenemos el tiempo final
		double tiempo = (System.nanoTime() * Math.pow(10, -9)) - tiempo0;
		
		//Impresión por pantalla de los mensajes previo a haber llamado al método que los crea
		for (int mensaje = 0; mensaje < mensajes.size(); mensaje++) {
			System.out.println(mensajes.get(mensaje));
		}
		System.out.println("Tiempo: " + tiempo + " segundos");
		entrada.close();
	}

	/*
	 * @param fichero inicial
	 */
	public static void modoMedicion(BufferedReader f) {
		Scanner entrada = new Scanner(System.in);
		// Pedimos los numeros de movimientos a realizar
		System.out.print("Número de ciclos:");
		int numCiclos = entrada.nextInt();
		//Metemos los usuarios en un ArrayList
		ArrayList<Usuario> usuarios = leerUsuarios(f);
		System.out.println("Procesando...");
		double tiempoTotal = 0;
		for (int i = 0; i < numCiclos; i++) {
			//Esta variable indica el tiempo al comienzo del proceso
			double tiempo0 = System.nanoTime() * Math.pow(10, -9);
			// Ahora debemos crear un numero aleatorio de entre -0,5 y 0,5 para añadirlo a
			// la coordenada x e y, para cambiar la posicion de cada usuario
			for (int j = 0; j < usuarios.size(); j++) {
				double x = (double) (Math.random() * (0.5 - (-0.5)) + (-0.5));
				double y = (double) (Math.random() * (0.5 - (-0.5)) + (-0.5));
				usuarios.get(j).x += x;
				usuarios.get(j).y += y;

				funcionalidad(usuarios, usuarios.get(j));
			}
			//Calculamos el tiempo de cada ciclo y le restamos el tiempo incial
			double tiempoActual = (System.nanoTime() * Math.pow(10, -9)) - tiempo0;
			tiempoTotal = ((tiempoTotal + tiempoActual));
			System.out.println(tiempoActual + " segundos");
		}
		//Obtenemos el tiempo promedio, sumando todos los tiempos y diviendolo por el numero de ciclos
		double tiempoPromedio = (tiempoTotal / numCiclos);
		System.out.println("Promedio: " + tiempoPromedio + " segundos");
		entrada.close();
	}

	public static ArrayList<String> funcionalidad(ArrayList<Usuario> usuarios, Usuario usuarioActual) {
		ArrayList<String> mensajes = new ArrayList<String>();
		//Creamos un ArrayList donde meteremos los vecinos nuevos de un usuario
		ArrayList<Usuario> vecinosNuevos = new ArrayList<Usuario>();
		//Creamos un arraylist de id actuales que rellenaremos posteriormente
		ArrayList<String> idActuales = new ArrayList<String>();
		String simbolo = null;
		for (int usuario = 0; usuario < usuarios.size(); usuario++) {
			double d = usuarioActual.distancia(usuarios.get(usuario));
			//Miramos si son nuevos vecinos
			if (d < 2) {
				//Si sus id son iguales, no lo añadimos a vecinosNuevos puesto que se repetiria
				if (!(usuarios.get(usuario).id.equals(usuarioActual.id))) {
					vecinosNuevos.add(usuarios.get(usuario));
				}
			}
		}
		//Comparamos la nueva lista de vecinos con la antigua(con los vecinos anteriores que tenia el usuario)
		for (int usuario = 0; usuario < vecinosNuevos.size(); usuario++) {
			if ((usuarioActual.vecinos.indexOf(vecinosNuevos.get(usuario).id)) == -1) {
				if (usuarioActual.grupo == vecinosNuevos.get(usuario).grupo) {
					simbolo = "+=";
				} else {
					simbolo = "+/";
				}
				//Incluimos al usuario actual en la lista de vecinos del otro usuario
				vecinosNuevos.get(usuario).vecinos.add(usuarioActual.id);
				
				//Creamos los mensajes
				mensajes.add(usuarioActual.id + simbolo + vecinosNuevos.get(usuario).id);
				mensajes.add(vecinosNuevos.get(usuario).id + simbolo + usuarioActual.id);
			}
		}
		//Rellenamos el ArrayList de idActuales con los usuarios de los nuevos vecinos
		for (int i = 0; i < vecinosNuevos.size(); i++) {
			idActuales.add(vecinosNuevos.get(i).id);
		}
		
		for (int id = 0; id < usuarioActual.vecinos.size(); id++) {
			if (idActuales.indexOf(usuarioActual.vecinos.get(id)) == -1) {
				// Eliminamos al usuario actual de la lista de vecinos del otro usuario
				for (int usuario = 0; usuario < usuarios.size(); usuario++) {
					if (usuarios.get(usuario).id.equals(usuarioActual.vecinos.get(id))) {
						usuarios.get(usuario).vecinos.remove(usuarioActual.id);
					}

				}
				//Incluimos los mensajes
				mensajes.add(usuarioActual.id + "-" + usuarioActual.vecinos.get(id));
				mensajes.add(usuarioActual.vecinos.get(id) + "-" + usuarioActual.id);
			}
		}
		mensajes.add("- - -");
		
		usuarioActual.vecinos = idActuales;
		return mensajes;
	}

	//Método que crea un objeto nuevo de la clase Usuario de cada línea del fichero
	public static ArrayList <Usuario> leerUsuarios(BufferedReader f) {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
		String linea = null;
		try {
			while ((linea = f.readLine()) != null) {
				Usuario usuario = new Usuario(linea);
				usuarios.add(usuario);
			}
		} catch (Exception e) {
			System.out.println("No es posible la lectura");
			System.out.println(e.getMessage());
		}
		return usuarios;
	}
}
