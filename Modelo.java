package es.studium.ConsultasPDF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Modelo
{
	// Método conectar BD
	public Connection conectar()
	{
		Connection c = null;
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/clientes?serverTimezone=UTC";
		String login = "root";
		String password = "Studium2019;";
		try
		{
			//Cargar los controladores para el acceso a la BD
			Class.forName(driver);
			//Establecer la conexión con la BD clientes
			c = DriverManager.getConnection(url, login, password);
		}
		catch (ClassNotFoundException cnfe)
		{
			System.out.println("Error 1-"+cnfe.getMessage());
		}
		catch (SQLException sqle)
		{
			System.out.println("Error 2-"+sqle.getMessage());
		}
		return (c);
	}

	// Método desconectar BD
	public void cerrar(Connection conexion)
	{
		try
		{
			if(conexion!=null)
			{
				conexion.close();
			}
		}
		catch (SQLException error)
		{
			System.out.println("Error 3-"+error.getMessage());
		}
	}

	// Método obtener datos BD
	public String consulta(Connection conexion)
	{
		String datos = "";
		Statement statement = null;
		ResultSet rs = null;
		String sentencia = "SELECT idFactura, DATE_FORMAT(fechaFactura, \"%d/%m/%Y\") AS 'fecha', dniCliente, \r\n" +
				"CONCAT(nombreCliente, CONCAT(\" \", apellidosCliente)) AS 'nombre'\r\n" +
				"FROM clientes\r\n" +
				"JOIN facturas ON facturas.idClienteFK = clientes.idCliente ORDER BY 1;";
		try
		{
			//Crear una sentencia
			statement = conexion.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			rs = statement.executeQuery(sentencia);
			while(rs.next())
			{
				datos = datos + rs.getInt("idFactura") + "\t" + "\t";
				datos = datos + rs.getString("fecha")+ "\t";
				datos = datos + rs.getString("dniCliente")+ "\t";
				datos = datos + rs.getString("nombre") + "\n";
			}
		}
		catch (SQLException error)
		{
			System.out.println("Error 4-"+error.getMessage());
		}
		return (datos);
	}
	
	public ArrayList<String> consultarPDF(Connection conexion)
	{
		ArrayList<String> datos = new ArrayList<String>();
		Statement statement = null;
		ResultSet rs = null;
		//Crear una sentencia
		try
		{
			statement = conexion.createStatement();
			//Crear un objeto ResultSet para guardar lo obtenido
			//y ejecutar la sentencia SQL
			String sentencia = "SELECT idFactura, DATE_FORMAT(fechaFactura, '%d/%m/%Y') AS 'Fecha', dniCliente,"
					+ " CONCAT(nombreCliente, CONCAT(' ' ,apellidosCliente)) AS 'Nombre'"
					+ " FROM facturas JOIN clientes ON facturas.idClienteFK = clientes.idCliente"
					+ " ORDER BY 1;";
			rs = statement.executeQuery(sentencia);
			while(rs.next())
			{
				datos.add(rs.getString("idFactura"));
				datos.add(rs.getString("Fecha"));
				datos.add(rs.getString("dniCliente"));
				datos.add(rs.getString("Nombre"));
			}
		}
		catch (SQLException e)
		{
			System.out.println("Error 4-"+e.getMessage());
		}
		return(datos);
	}
}
