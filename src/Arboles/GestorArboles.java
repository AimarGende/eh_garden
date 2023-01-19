package Arboles;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class GestorArboles {
	static final String HOST="jdbc:mysql://localhost/";
	static final String BBDD="arboles";
	static final String USERNAME="root";
	static final String PASSWORD="";
	public static void run() throws SQLException, ClassNotFoundException {
		final int INSERTAR_ARBOL=1;
		final int ELIMINAR_ARBOL=2;
		final int MODIFICAR_ARBOL=3;
		final int VISUALIZAR_ARBOLES=4;
		final int SALIR=0;
		int opcion=0;
		
		Scanner sc=new Scanner(System.in);
		Connection conexion;
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
					insertarArbol(sc,st);
					System.out.println("se ha insertado el arbol correctamente");
					break;
				case ELIMINAR_ARBOL:
					eliminarArbol(sc,st);
					System.out.println("Se ha eliminado el arbol");
					break;
				case MODIFICAR_ARBOL:
					modificarArbol(sc,st);
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

	private static void visualizarArboles(Statement st, Scanner sc) throws SQLException {
		String select="SLECT * FROM arboles";
		ResultSet result=st.executeQuery(select);
		System.out.println(result.getString(1)+"-"+result.getString(2)+"-"+result.getString(3)+"-"+result.getDouble(4)+"-"+result.getString(5));
		
	}

	private static void insertarArbol(Scanner sc, Statement st) throws SQLException {
		System.out.println("Introduce la sentencia sql para insertar el arbol, EJ:INSERT INTO arboles (nombre_comun, nombre_cientifico_habitat, altura, origen) VALUES('NombreArbol','NombreCientifico','Habitat',altura,'Origen');");
		String sentencia=sc.nextLine();
		st.execute(sentencia);
	}
	
	private static void eliminarArbol(Scanner sc, Statement st) throws SQLException {
		System.out.println("Introduce la sentencia sql para eliminar el arbol, EJ: DELETE FROM arboles WHERE atributo=valor;");
		String sentencia=sc.nextLine();
		st.execute(sentencia);
	}
	
	private static void modificarArbol(Scanner sc, Statement st) throws SQLException {
		System.out.println("Introduce la sentencia sql para actualizar el arbol, EJ:UPDATE arboles SET nombre_de_atributo=nuevo_valor WHERE nombre_de_atributo=valor_antiguo;");
		String sentencia=sc.nextLine();
		st.executeUpdate(sentencia);
		
	}




}
