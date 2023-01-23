package Arboles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GestorArboles {
	static final String HOST="jdbc:mysql://localhost/";
	static final String BBDD="arboles";
	static final String USERNAME="root";
	static final String PASSWORD="";
	static Connection conexion;
	public static void run() throws SQLException, ClassNotFoundException {
		final int INSERTAR_ARBOL=1;
		final int ELIMINAR_ARBOL=2;
		final int MODIFICAR_ARBOL=3;
		final int VISUALIZAR_ARBOLES=4;
		final int SALIR=0;
		int opcion=0;
		Arbol arbol=new Arbol();
		Scanner sc=new Scanner(System.in);
		
		Class.forName("com.mysql.cj.jdbc.Driver");
		conexion = DriverManager.getConnection(HOST+BBDD,USERNAME,PASSWORD);
		Statement st=conexion.createStatement();
		
		
		do {
			System.out.println("-------MENU-------");
			System.out.println(INSERTAR_ARBOL+". insertar arbol:");
			System.out.println(ELIMINAR_ARBOL+". eliminar  arbol");
			System.out.println(MODIFICAR_ARBOL+". actualizar informacion del arbol:");
			System.out.println(VISUALIZAR_ARBOLES+". visualizar arboles");
			System.out.println("Escoja una opcion:");
			opcion=Integer.parseInt(sc.nextLine());
			switch(opcion) {
				case INSERTAR_ARBOL :
					insertarArbol(sc,arbol);
					System.out.println("se ha insertado el arbol correctamente");
					break;
				case ELIMINAR_ARBOL:
					eliminarArbol(sc);
					System.out.println("Se ha eliminado el arbol");
					break;
				case MODIFICAR_ARBOL:
					modificarArbol(sc);
					System.out.println("Se ha modificado el arbol");
					break;
				case VISUALIZAR_ARBOLES:
					visualizarArboles(st,sc);
					break;
				case SALIR:
					break;
				default:
					break;
			}
			
		}while(opcion!=SALIR);
	}



	private static void insertarArbol(Scanner sc,Arbol arbol) throws SQLException {
		PreparedStatement pt=conexion.prepareStatement("INSERT INTO arboles (nombre_comun, nombre_cientifico, habitat, altura, origen) VALUES(?,?,?,?,?);");
		
		System.out.println("Introduce el nombre del arbol");
		arbol.setNombreComun(sc.nextLine());
		System.out.println("Introduce el nombre cientifico del arbol");
		arbol.setNombreCientifico(sc.nextLine());
		System.out.println("Introduce el habitat del arbol");
		arbol.setHabitat(sc.nextLine());
		System.out.println("Introduce la altura del arbol");
		arbol.setAltura(Double.parseDouble(sc.nextLine()));
		System.out.println("Introduce el origen del arbol");
		arbol.setOrigen(sc.nextLine());
		
		pt.setString(1, arbol.getNombreComun());
		pt.setString(2, arbol.getNombreCientifico());
		pt.setString(3, arbol.getHabitat());
		pt.setDouble(4, arbol.getAltura());
		pt.setString(5, arbol.getOrigen());
		
		pt.execute();
	}
	
	private static void eliminarArbol(Scanner sc) throws SQLException {
		int id;
		PreparedStatement pt=conexion.prepareStatement("DELETE FROM arboles WHERE id= ? ;");
		System.out.println("Inserta el id que quieres eliminar");
		id=Integer.parseInt(sc.nextLine());
		pt.setInt(1, id);
		pt.execute();
	}
	
	private static void modificarArbol(Scanner sc) throws SQLException {
		String atrib;
		String newValue;
		int id;
		
		System.out.println("Introduce la id  del arbol que quieres cambiar");
		id=Integer.parseInt(sc.nextLine());
		System.out.println("Introduce el atributo del arbol que quieres actualizar: Las opciones son nombre_comun, nombre_cientifico, habitat, altura y origen");
		atrib=sc.nextLine().toLowerCase();
		System.out.println("Introduce el nuevo valor del atibuto:");
		newValue=sc.nextLine();
		
		PreparedStatement prep=conexion.prepareStatement("UPDATE arboles SET "+atrib+"= (?) WHERE id="+id+";");
		if(atrib.equals("altura")) {
			prep.setDouble(1, Double.parseDouble(newValue));
		}
		else {
			prep.setString(1, newValue);
		}
		prep.executeUpdate();
	}

	private static void visualizarArboles(Statement st, Scanner sc) throws SQLException {
		String select="SELECT * FROM arboles ";
		ResultSet result=st.executeQuery(select);
		while(result.next()) {
			System.out.println(result.getInt(1)+"-"+result.getString(2)+"-"+result.getString(3)+"-"+result.getString(4)+"-"+result.getDouble(5)+"-"+result.getString(6));
		}
	}


}
