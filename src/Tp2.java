
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
	public static double dE_dt(double tn){		
		return  220 * (2 * Math.PI / 50) * Math.cos(2 * Math.PI * tn / 50);
	}
	
	/**
	 * Derivada de I(t)
	 * Para los metodos de discretización f = dy/dt, en este caso y=I(t), ==> f=dI(t)/dt 
	 */
	public static double di_dt(double tn, double in){		
		return ((1/R0)*dE_dt(tn)-(in / (R0 * C))) / (1 + (2 * K / R0) * in);
	}
	
	/*public static double dq_dt(double tn, double in){		
		return in;
	}*/
	
	
	/**
	 * Iteración del método de Euler
	 */
	public static double euler_inPlus1(double tn, double in, double h){		
		return in + h * di_dt(tn,in);
	}
	
	public static double euler_qnPlus1(double tn, double qn, double in, double h){		
		return qn + h * in;
	}
	
	
	/**
	 * Runge-Kutta orden 2
	 */
	
	/**
	 * q1=f(tn,in)=dI_dT(tn,in) 
	 */
	public static double rk2_inPlus1_q1(double tn, double in){		
		return di_dt(tn, in);
	}
	
	public static double rk2_qnPlus1_q1(double tn, double in){		
		return in;
	}
	
	/**
	 * q2=f(tn+1,in+h*q1)=dI_dT(tn+1,in+h*q1) 
	 */
	public static double rk2_inPlus1_q2(double tn, double in,double h,double q1 ){		
		return di_dt(tn+h,in+h*q1 );
	}
	
	public static double rk2_qnPlus1_q2(double tn, double qn, double in, double h,double q1 ){		
		return in;
	}
	
	/**
	 * in+1=in+ h/2 * (q1 + q2) 
	 */
	public static double rk2_inPlus1(double tn, double in, double h){
		double q1=rk2_inPlus1_q1( tn, in );
		double q2=rk2_inPlus1_q2( tn, in, h, q1 );
		return in + (h/2) * ( q1 + q2);
	}
	
	public static double rk2_qnPlus1(double tn, double qn, double in, double h){
		double q1 = rk2_qnPlus1_q1( tn, in );
		double q2 = rk2_qnPlus1_q2( tn, qn, in, h, q1 );
		return qn + (h/2) * ( q1 + q2);
	}
	
	/**
	 * Crank-Nicolson
	 */
	
	public static double rk4_inPlus1_q1(double tn, double in, double h) {
		return h * di_dt(tn, in);
	}
	public static double rk4_qnPlus1_q1(double tn, double qn, double in, double h) {
		return h * in;
	}
	
	public static double rk4_inPlus1_q2(double tn, double in, double h, double q1) {
		return h * di_dt(tn + h/2, in + 0.5 * q1);
	}
	public static double rk4_qnPlus1_q2(double tn, double qn, double in, double h, double q1) {
		return h * in;
	}
	
	public static double rk4_inPlus1_q3(double tn, double in, double h, double q2) {
		return h * di_dt(tn + h/2, in + 0.5 * q2);
	}
	public static double rk4_qnPlus1_q3(double tn, double qn, double in, double h, double q2) {
		return h * in;
	}
	
	public static double rk4_inPlus1_q4(double tn, double in, double h, double q3) {
		return h * di_dt(tn + h, in + q3);
	}
	public static double rk4_qnPlus1_q4(double tn, double qn, double in, double h, double q3) {
		return h * in;
	}
	
	public static double rk4_inPlus1(double tn, double in, double h) {
		double q1 = rk4_inPlus1_q1(tn, in, h);
		double q2 = rk4_inPlus1_q2(tn, in, h, q1);
		double q3 = rk4_inPlus1_q3(tn, in, h, q2);
		double q4 = rk4_inPlus1_q4(tn, in, h, q3);
		return in + (q1 + 2*q2 + 2*q3 + q4)/6;
	}
	public static double rk4_qnPlus1(double tn, double qn, double in, double h) {
		double q1 = rk4_qnPlus1_q1(tn, qn, in, h);
		double q2 = rk4_qnPlus1_q2(tn, qn, in, h, q1);
		double q3 = rk4_qnPlus1_q3(tn, qn, in, h, q2);
		double q4 = rk4_qnPlus1_q4(tn, qn, in, h, q3);
		return qn + (q1 + 2*q2 + 2*q3 + q4)/6;
	}
	
	/*
	public static double k1(double tn, double in){		
		return dI_dT(tn,in);
	}
	
	/**
	 * un+1 = un + k/2 [ f ( un+1, tn+1 ) + f ( un, tn ) ] 
	 */
	public static double cranknicholson_inPlus1(double tn,double in,double inPlus1, double h){
		double tnPlus1=tn+h;
		return in + (h/2) * (di_dt(tnPlus1, inPlus1) + di_dt(tn, in));
	}
	
	/*
	public static double runge_Kutta_Orden4(double tn, double in, double h){		
		return in + (h/2) * ( rk2_inPlus1_q1( tn, in) + rk2_inPlus1_q2( tn, in, h, rk2_inPlus1_q1( tn, in )));
	}
	*/
	
	
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
		writer.write("<th>in+1 (Euler)</th>");
		writer.write("<th>qn+1 (Euler)</th>");
		writer.write("<th>in+1 (RK2)</th>");
		writer.write("<th>qn+1 (RK2)</th>");
		writer.write("<th>in+1 (RK4)</th>");
		writer.write("<th>in+1 (Crank-Nicolson)</th>");
		writer.write("</tr><tr>");		
	
		
		double h=0.01;		
		double inPlus1_euler=0;
		double qnPlus1_euler=0;
		double inPlus1_rk2=0;
		double qnPlus1_rk2=0;
		double inPlus1_rk4=0;
		double qnPlus1_rk4=0;
		double inPlus1_cranknicholson=0;
		for(double tn=0;tn<21;tn+=h) {
			writer.write("<tr>");
			writer.write("<td>" + (new Double(tn)).toString().replace(".",",") + "</td>");
			writer.write("<td>" + (new Double(inPlus1_euler)).toString().replace(".",",")+ "</td>");
			writer.write("<td>" + (new Double(qnPlus1_euler)).toString().replace(".",",")+ "</td>");
			writer.write("<td>" + (new Double(inPlus1_rk2)).toString().replace(".",",")+ "</td>");
			writer.write("<td>" + (new Double(qnPlus1_rk2)).toString().replace(".",",")+ "</td>");
			writer.write("<td>" + (new Double(inPlus1_rk4)).toString().replace(".",",")+ "</td>");
			writer.write("<td>" + (new Double(qnPlus1_rk4)).toString().replace(".",",")+ "</td>");
			writer.write("<td>" + (new Double(inPlus1_cranknicholson)).toString().replace(".",",")+ "</td>");
			
			writer.write("</tr>");
			qnPlus1_euler = euler_qnPlus1(tn,qnPlus1_euler, inPlus1_euler,h);
			inPlus1_euler = euler_inPlus1(tn,inPlus1_euler,h);
			qnPlus1_rk2 = rk2_qnPlus1(tn, qnPlus1_rk2, inPlus1_rk2,h);
			inPlus1_rk2 = rk2_inPlus1(tn, inPlus1_rk2,h);
			qnPlus1_rk4 = rk4_qnPlus1(tn, qnPlus1_rk4, inPlus1_rk4 ,h);
			inPlus1_rk4 = rk4_inPlus1(tn, inPlus1_rk4,h);
			inPlus1_cranknicholson = cranknicholson_inPlus1(tn, inPlus1_cranknicholson, inPlus1_rk2, h);			
		}		
		writer.write("</tr></table></pre></body></html>");
		writer.close();

	}
	
	public static void main(String[] args) throws Exception {
		Tp2 numerico = new Tp2();
		numerico.run();
	}	
	
}

