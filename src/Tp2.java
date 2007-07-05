
import java.io.FileWriter;
import java.io.PrintStream;


public class Tp2 {
	/**
	 * Resistencia inicial
	 */
	public static double R0=2.1;
	
	/**
	 * 
	 */
	public static double K=0.9;
	 
	/**
	 * Capacidad del capacitor
	 */
	public static double C=1.1;
	
	/**
	 * Derivada de E(t)
	 */
	public static double dE_dT(double tn){		
		return  220 * (2 * Math.PI / 50) * Math.cos(2 * Math.PI * tn / 50);
	}
	
	/**
	 * Derivada de I(t)
	 * Para los metodos de discretización f = dy/dt, en este caso y=I(t), ==> f=dI(t)/dt 
	 */
	public static double dI_dT(double tn, double In){		
		return ((1/R0)*dE_dT(tn)-(In / (R0 * C))) / (1 + (2 * K / R0) * In);
	}
	
	
	/**
	 * Iteración del método de Euler
	 */
	public static double Euler(double tn, double Wn, double h){		
		return Wn + h * dI_dT( tn,Wn);
	}
	
	/**
	 * Runge-Kutta orden 2
	 */
	
	/**
	 * q1=f(tn,Wn)=dI_dT(tn,Wn) 
	 */
	public static double q1(double tn, double Wn){		
		return dI_dT(tn,Wn);
	}
	
	/**
	 * q2=f(tn+1,Wn+h*q1)=dI_dT(tn+1,Wn+h*q1) 
	 */
	public static double q2(double tn, double Wn,double h,double q1 ){		
		return dI_dT(tn+h,Wn+h*q1 );
	}
	
	/**
	 * Wn+1=Wn+ h/2 * (q1 + q2) 
	 */
	public static double runge_Kutta_Orden2(double tn, double Wn, double h){
		double q1=q1( tn, Wn );
		double q2=q2( tn, Wn, h, q1 );
		return Wn + (h/2) * ( q1 + q2);
	}	
	
	/**
	 * Crank-Nicolson
	 */
	
	public static double rk4_q1(double tn, double Wn, double h) {
		return h * dI_dT(tn, Wn);
	}
	
	public static double rk4_q2(double tn, double Wn, double h, double q1) {
		return h * dI_dT(tn + h/2, Wn + 0.5 * q1);
	}
	
	public static double rk4_q3(double tn, double Wn, double h, double q2) {
		return h * dI_dT(tn + h/2, Wn + 0.5 * q2);
	}
	
	public static double rk4_q4(double tn, double Wn, double h, double q3) {
		return h * dI_dT(tn + h, Wn + q3);
	}
	
	public static double rk4_wnPlus1(double tn, double Wn, double h) {
		double q1 = rk4_q1(tn, Wn, h);
		double q2 = rk4_q2(tn, Wn, h, q1);
		double q3 = rk4_q3(tn, Wn, h, q2);
		double q4 = rk4_q4(tn, Wn, h, q3);
		return Wn + (q1 + 2*q2 + 2*q3 + q4)/6;
	}
	
	/*
	public static double k1(double tn, double Wn){		
		return dI_dT(tn,Wn);
	}
	
	/**
	 * un+1 = un + k/2 [ f ( un+1, tn+1 ) + f ( un, tn ) ] 
	 */
	public static double crank_Nicolson(double tn,double Wn,double WnPlus1, double h){
		double tnPlus1=tn+h;
		return Wn + (h/2) * (dI_dT(tnPlus1, WnPlus1) + dI_dT(tn, Wn));
	}
	
	public static double runge_Kutta_Orden4(double tn, double Wn, double h){		
		return Wn + (h/2) * ( q1( tn, Wn) + q2( tn, Wn, h, q1( tn, Wn )));
	}
	
	
	
	private PrintStream out = System.out;
	public void run() throws Exception {		
		Grilla grid = new Grilla(6,  true);
		
		FileWriter writer = new FileWriter("results.html");
		writer.write("<html><head><title>Numérico</title>");
		writer.write("<style type=\"text/css\">");
		writer.write("table {font-family: courier; background-color: #faf0e6;}");
		writer.write("</style></head>");
		writer.write("<body><table border=\"1\"><caption>TRABAJO PRACTICO DE ANALISIS NUMERICO</caption><tr>");
		writer.write("<th>t</th>");
		writer.write("<th>Wn+1 (Euler)</th>");
		writer.write("<th>Wn+1 (runge_Kutta_Orden2)</th>");
		writer.write("<th>Wn+1 (runge_Kutta_Orden4)</th>");
		writer.write("<th>Wn+1 (crank_Nicolson)</th>");
		writer.write("</tr><tr>");		
	
//		
		out.println("           TRABAJO PRACTICO DE ANALISIS NUMERICO          ");
		out.println("           -------------------------------------          ");
		out.println("");
		out.println(" --------------------------------------------------------");
		out.println("    FUNCION   |DIGITOS|   ORDEN   |   RESULTADO");
		
		double h=0.001;		
		double wnPlus1_euler=0;
		double wnPlus1_runge_Kutta_Orden2=0;
		double wnPlus1_runge_Kutta_Orden4=0;
		double wnPlus1_Crank_Nicolson=0;
		for(double tn=0;tn<21;tn+=h) {
			writer.write("<tr>");
//			writer.write("<td>" + (new Double(grid.redondear(tn))).toString().replace(".",",") + "</td>");
//			writer.write("<td>" + (new Double(grid.redondear(wnPlus1_euler))).toString().replace(".",",")+ "</td>");
//			writer.write("<td>" + (new Double(grid.redondear(wnPlus1_runge_Kutta_Orden2))).toString().replace(".",",")+ "</td>");
//			writer.write("<td>" + (new Double(grid.redondear(wnPlus1_runge_Kutta_Orden4))).toString().replace(".",",")+ "</td>");
//			writer.write("<td>" + (new Double(grid.redondear(wnPlus1_Crank_Nicolson))).toString().replace(".",",")+ "</td>");
			writer.write("<td>" + tn + "</td>");
			writer.write("<td>" + wnPlus1_euler + "</td>");
			writer.write("<td>" + wnPlus1_runge_Kutta_Orden2 + "</td>");
			writer.write("<td>" + wnPlus1_runge_Kutta_Orden4 + "</td>");
			writer.write("<td>" + wnPlus1_Crank_Nicolson + "</td>");
			writer.write("</tr>");
			wnPlus1_euler=Euler( tn,wnPlus1_euler,h);
			wnPlus1_runge_Kutta_Orden2 = runge_Kutta_Orden2(tn, wnPlus1_runge_Kutta_Orden2,h);
			wnPlus1_runge_Kutta_Orden4 = rk4_wnPlus1(tn, wnPlus1_runge_Kutta_Orden4,h);
			wnPlus1_Crank_Nicolson=crank_Nicolson(tn, wnPlus1_Crank_Nicolson, wnPlus1_runge_Kutta_Orden2, h);			
		}		
		writer.write("</tr></table></pre></body></html>");
		writer.close();

	}
	
	public static void main(String[] args) throws Exception {
		Tp2 numerico = new Tp2();
		numerico.run();
	}	
	
}

