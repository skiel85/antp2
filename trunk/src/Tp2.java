
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
	 * Runge-Kutta orden 4
	 */
	public static double k1(double tn, double Wn){		
		return dI_dT(tn,Wn);
	}
	
	public static double k2(double tn, double Wn,double h,double q1 ){		
		return dI_dT(tn+h,Wn+h*q1 );
	}
	
	public static double runge_Kutta_Orden4(double tn, double Wn, double h){		
		return Wn + (h/2) * ( q1( tn, Wn) + q2( tn, Wn, h, q1( tn, Wn )));
	}
	
	
	private PrintStream out = System.out;
	public void run() throws Exception {		
		Grilla grid = new Grilla( 5,  true);
		
		FileWriter writer = new FileWriter("results.html");
		writer.write("<html><head><title>Numérico</title>");
		writer.write("<style type=\"text/css\">");
		writer.write("table {font-family: courier; background-color: #faf0e6;}");
		writer.write("</style></head>");
		writer.write("<body><table border=\"1\"><caption>TRABAJO PRACTICO DE ANALISIS NUMERICO</caption><tr>");
		writer.write("<th>t</th>");
		writer.write("<th>Wn+1 (Euler)</th>");
		writer.write("<th>Wn+1 (runge_Kutta_Orden2)</th>");
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
		for(double tn=0;tn<21;tn+=h) {
			writer.write("<tr>");
			writer.write("<td>" + (new Double(grid.redondear(tn))).toString().replace(".",",") + "</td>");
			writer.write("<td>" + (new Double(grid.redondear(wnPlus1_euler))).toString().replace(".",",")+ "</td>");
			writer.write("<td>" + (new Double(grid.redondear(wnPlus1_runge_Kutta_Orden2))).toString().replace(".",",")+ "</td>");
			writer.write("</tr>");
			wnPlus1_euler=Euler( tn,wnPlus1_euler,h);
			wnPlus1_runge_Kutta_Orden2 = runge_Kutta_Orden2(tn, wnPlus1_runge_Kutta_Orden2,h);
		}		
		writer.write("</tr></table></pre></body></html>");
		writer.close();

	}
	
	public static void main(String[] args) throws Exception {
		Tp2 numerico = new Tp2();
		numerico.run();
	}	
	
}

