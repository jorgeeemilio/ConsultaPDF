package es.studium.ConsultasPDF;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;

public class Vista
{
	Frame ventana = new Frame("Consulta");
	TextArea txaConsulta = new TextArea(20,80);
	Button btnPDF = new Button("PDF");

	public Vista()
	{
		ventana.setLayout(new FlowLayout());
		ventana.setSize(600,450);
		ventana.setLocationRelativeTo(null);
		txaConsulta.append("Nº Factura\tFecha\t\tDNI\t\tNombre\n");
		ventana.add(txaConsulta);
		ventana.add(btnPDF);
		ventana.setVisible(true);
	}
}
