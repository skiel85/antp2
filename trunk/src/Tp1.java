
import java.io.FileWriter;
import java.io.PrintStream;

public class Tp1 {
	
	/**
	 * Calcula el termino k de la serie indicada en numeroSerie
	 */
	private double calcularFuncionSinError(double x, int numeroSerie) throws Exception{
		switch(numeroSerie){
		case 1:
			return f(x);
		case 2:
			return g(x);
		case 3:
			return h(x);
		default:
			throw new Exception("Numero de serie desconocido");
		}
	}
	
	public double f(double x) {
		return Math.log(x+1);
	}
	public double g(double x) {
		return Math.exp(x);
	}
	public double h(double x) {
		return Math.sin(x);
	}
	
	/**
	 * Calcula el termino k de la serie indicada en numeroSerie
	 */
	private double calcularErrorTruncamiento(int k, double x, int numeroSerie) throws Exception{
		switch(numeroSerie){
		case 1:
			return errorTruncamientoF(k, x);
		case 2:
			return errorTruncamientoG(k, x);
		case 3:
			return errorTruncamientoH(2*k+1, x);
		default:
			throw new Exception("Numero de serie desconocido");
		}
	}

	
	public double errorTruncamientoF(int n, double x) {
		return Math.pow(x, n+1) / (2 * n);
	}

	public double errorTruncamientoG(int n, double x) throws Exception {
		return Math.pow(x, 2*(n+1)) / factorial(n+1, null);
	}
	
	public double errorTruncamientoH(int n, double x) throws Exception {
		if (n/2 == new Double(n/ 2).intValue()) 
			return (Math.sin(x) * Math.pow(x, n+1)) / factorial(n+1, null);
		else
			return (Math.cos(x) * Math.pow(x, n+1)) / factorial(n+1, null);
	}
	
	
	/**
	 * Devuelve el valor de la funcion indicada en numeroSerie para el punto X
	 */
	public valorConError[] calcularFuncion(valorConError X, Grilla grid, boolean desc,int cantidadIteraciones,int numeroSerie,int kInicial)  throws Exception {
		valorConError r = new valorConError();
		valorConError numeroIteraciones = new valorConError();
		
		if (desc) {
			int contadorIteraciones=0;
			boolean exit=false;
			for(int k=kInicial;!exit;k++) {
				valorConError nKmas1=calcularTermino(X, grid,r,k,numeroSerie);
				exit = (contadorIteraciones==0) ? false : (r.valor==nKmas1.valor);
				r = nKmas1;
				contadorIteraciones++;
			}	
			numeroIteraciones.valor = contadorIteraciones;
		} else {
			for(int k=cantidadIteraciones;k>=kInicial;k--) {
				r = calcularTermino(X, grid, r, k,numeroSerie);
			}
			numeroIteraciones.valor=cantidadIteraciones;
		}
		return new valorConError[] { r, numeroIteraciones };
	}
	
	/**
	 * Calcula el termino k de la serie indicada en numeroSerie
	 */
	private valorConError calcularTermino(valorConError X,Grilla grid, valorConError nK, int iteracion, int numeroSerie) throws Exception{
		switch(numeroSerie){
		case 1:
			return terminoSerieF(X, grid, nK, iteracion);
		case 2:
			return terminoSerieG(X, grid, nK, iteracion);
		case 3:
			return terminoSerieH(X, grid,nK, iteracion);
		default:
				throw new Exception("Numero de serie desconocido");
		}
	}

	class valorConError {
		public valorConError(double pValor, double pError) {
			valor = pValor;
			error = pError;
		}
		
		public valorConError() {
			
		}
		
		public double valor;
		public double error;
	}
	
	/**
	 * Calcula el termino k de la serie F y se lo suma al valor anterior
	 */
	private valorConError terminoSerieF(valorConError X, Grilla grid, valorConError nK, int k) throws Exception {

		valorConError a = new valorConError();
		valorConError b = new valorConError();
		valorConError c = new valorConError();
		valorConError d = new valorConError();
		valorConError e = new valorConError();
		valorConError r = new valorConError();
		
		
		a.valor = grid.redondear(k-1);
		b.valor = grid.redondear(pow(new valorConError(-1, 0),a.valor,grid).valor);
		c.valor = grid.redondear(pow(X,k,grid).valor);
		d.valor = grid.redondear(b.valor * c.valor);			
		e.valor = grid.redondear(d.valor / k);
		r.valor = grid.redondear(nK.valor + e.valor);
		
		b.error = pow(new valorConError(-1, 0),a.valor,grid).error + grid.error();
		c.error = pow(X, k,grid).error + grid.error();
		d.error = b.error + c.error + grid.error();
		e.error = d.error + grid.error();
		r.error = (nK.valor/(nK.valor+e.valor))*nK.error +(e.valor/(nK.valor+e.valor))*e.error + grid.error();
		
		return r;
	}
	
	/**
	 * Calcula el termino k de la serie G y se lo suma al valor anterior
	 */
	private valorConError terminoSerieG(valorConError X, Grilla grid, valorConError nK, int k) throws Exception {

		valorConError a = new valorConError();
		valorConError b = new valorConError();
		valorConError c = new valorConError();
		
		valorConError r = new valorConError();


		a.valor = grid.redondear(pow(X,k,grid).valor);
		b.valor = grid.redondear(factorial(k,grid));
		c.valor = grid.redondear(a.valor/b.valor);
		r.valor = grid.redondear(nK.valor + c.valor);
		
		a.error = pow(X,k,grid).error + grid.error();
		b.error = grid.error();
		c.error = a.error - b.error + grid.error();
		r.error = (nK.valor/(nK.valor+c.valor))*nK.error +(c.valor/(nK.valor+c.valor))*c.error + grid.error();
		
		
		return r;
	}
	
	/**
	 * Calcula el termino k de la serie H y se lo suma al valor anterior
	 */
	private valorConError terminoSerieH(valorConError X, Grilla grid, valorConError nK, int k) throws Exception {

		valorConError a = new valorConError();
		valorConError b = new valorConError();
		valorConError c = new valorConError();
		valorConError d = new valorConError();
		valorConError e = new valorConError();
		valorConError f = new valorConError();
		valorConError g = new valorConError();
		valorConError r = new valorConError();
		
		/*try{*/
		
		 a.valor = grid.redondear(pow(new valorConError(-1, 0),k,grid).valor);
		 b.valor = grid.redondear(2*k);
		 c.valor = grid.redondear(b.valor+1);
		 d.valor = grid.redondear(pow(X,c.valor,grid).valor);
		 e.valor = grid.redondear(a.valor*d.valor);	
		 f.valor = grid.redondear(factorial((new Double(c.valor)).intValue(),grid));
		 g.valor = grid.redondear(e.valor/f.valor);
		 r.valor = grid.redondear(nK.valor+g.valor);
		 
		 a.error = pow(new valorConError(-1, 0),k,grid).error + grid.error();
		 b.error = grid.error();
		 c.error = b.error *(b.valor/(b.valor+1))+ grid.error();
		 d.error = pow(X,c.valor,grid).error + grid.error();
		 e.error = a.error+d.error+grid.error();	
		 f.error = 0+grid.error();
		 g.error = e.error-f.error + grid.error();
		 r.error = (nK.valor/(nK.valor+g.valor))*nK.error +(g.valor/(nK.valor+g.valor))*g.error + grid.error();
		 
		return r;
		/*}catch(Exception ex){
			int sss=0;
			sss++;
			return 0;
		}*/
	}
	
	/**
	 * Devuelve el factorial de x
	 */
	public double factorial(int x, Grilla grid) throws Exception {
		double a = 0, b = 1;
		if (x>=0) {
			if (x==0 || x==1)
				return 1;
			else {
				while(a!=x) {
					a++;
					if (grid!=null) 
						b=grid.redondear( b*a);
					else
						b= b*a;
				}
				return b;
			}
		} else {
			throw new Exception("Error calculando factorial de " + x);
		}
	}
	
	/**
	 * Devuelve base ^ exponente
	 * Utiliza la grilla para redondear cada operacion 
	 */
	public valorConError pow(valorConError base, double exponente, Grilla grid) throws Exception {
		double resultado = base.valor;
		double error = 0; 
		if (exponente==0 && base.valor==0){
			throw new Exception("No se puede elevar cero a la potencia 0");
		}
		if (exponente<0){
			throw new Exception("No se soportan exponentes negativos");
		}
		if (exponente == 0 ) {
			return new valorConError(1, grid.error());
		} else {
			for(int a=1; a<exponente; a++) {
				resultado = grid.redondear(resultado * base.valor);
				error += base.error + grid.error();
			}
		}
		return new valorConError(resultado, error);
	}
	
	private PrintStream out = System.out;

	public void run() throws Exception {


		Grilla gg = new Grilla( 3, true);
		/*double n=0.002896198350657628;
		System.out.println("n         : " + n);
		System.out.println("normalizado : " + gg.normalizar(n));
		System.out.println("exp " + gg.getExponente(n));
		System.out.println("redondear " + gg.redondear(n));
		System.out.println("----" );
		*/
		/*
		double min=-10;
		double max=10;
		for(int i=0;i<100;i++){
			double exp = (Math.random()*(max-min))+min;
			double n=Math.random()*Math.pow(10,exp ) ;
			//System.out.println("exp:"+exp);
			//System.out.println("redondear ("+n+") con t="+ gg.t+ ": " + gg.redondear(n));	
			System.out.println("Compara:" + (Math.abs(n) ==  Double.parseDouble(gg.normalizar(n)+ "E"+ gg.getExponente(n))) + ". Expo ("+n+")="+ gg.normalizar(n)+ "E"+ gg.getExponente(n));
			//System.out.println(Math.abs(n) ==  Double.parseDouble(gg.normalizar(n)+ "E"+ gg.getExponente(n)));
			
		}
		*/
		
	
		FileWriter writer = new FileWriter("results.html");
		writer.write("<html><head><title>Numérico</title>");
		writer.write("<style type=\"text/css\">");
		writer.write("table {font-family: courier; background-color: #faf0e6;}");
		writer.write("</style></head>");
		writer.write("<body><table border=\"1\"><caption>TRABAJO PRACTICO DE ANALISIS NUMERICO</caption><tr>");
		writer.write("<th>Función</th>");
		writer.write("<th>Dígitos</th>");
		writer.write("<th>Orden</th>");
		writer.write("<th>Resultado</th>");
		writer.write("<th>Resultado exacto</th>");
		writer.write("<th>Iter</th>");
		writer.write("<th>Err red rel</th>");
		writer.write("<th>Err trunc rel</th>");
		writer.write("<th>Err exacto rel</th>");
		writer.write("<th>Err red abs</th>");
		writer.write("<th>Err trunc abs</th>");
		writer.write("<th>Err exacto abs</th>");
		writer.write("</tr><tr>");
		

		
	
//
		
		out.println("           TRABAJO PRACTICO DE ANALISIS NUMERICO          ");
		out.println("           -------------------------------------          ");
		out.println("");
		out.println(" --------------------------------------------------------");
		out.println("    FUNCION   |DIGITOS|   ORDEN   |   RESULTADO");
		
		valorConError puntoACalcular = new valorConError();
		Grilla grid;
		boolean desc;
		for(int functionCounter=1;functionCounter<=3;functionCounter++) {
			for(int pointCounter=1;pointCounter<=3; pointCounter++) {
				switch (pointCounter) {
					case 1:
						puntoACalcular = new valorConError(0.1,0);
						break;
					case 2:
						puntoACalcular = new valorConError(0.5,0);
						break;
					case 3:
						puntoACalcular = new valorConError(0.99,0);
						break;
				}
				
				for(int digits=2;digits<=6;digits++) {
					grid=new Grilla( digits, true);
					puntoACalcular.error = grid.error();
					valorConError[] resultado = new valorConError[] {new valorConError(), new valorConError()};
					for(int orderCount=0;orderCount<=1;orderCount++) {
						desc=(orderCount==0);
						String order="< a >";
						if (desc)
							order="> a <";
						
						
						String espacio = (puntoACalcular.valor==0.99)?"":" ";
						String nombreFuncion = null;
						int kInicial=0;
						switch (functionCounter){
							case 1:
								nombreFuncion = "f";
								kInicial=1;
								break;
							case 2:
								nombreFuncion = "g";
								break;
							case 3:
								nombreFuncion = "h";
								break;
						}
						
						
						resultado=calcularFuncion(puntoACalcular,grid, desc, (int)(resultado[1].valor), functionCounter,kInicial);
						out.println("    "+ nombreFuncion +"("+ puntoACalcular.valor +")   "+ espacio +"|   "+ digits +"   |   "+ order +"   |   " + resultado[0].valor);
						//out.println((int)resultado[1]);
						
						Grilla gridErrores = new Grilla( 3, true);
						
						double valorFuncionSinError = calcularFuncionSinError(puntoACalcular.valor, functionCounter);
						double errorExacto = resultado[0].valor - valorFuncionSinError;
						
						writer.write("<td>" + nombreFuncion + "(" + puntoACalcular.valor + ")</td>");
						writer.write("<td>" + digits + "</td>");
						writer.write("<td>" + order.replace("<", "&lt;").replace(">", "&gt;") +  "</td>");
						writer.write("<td>" + resultado[0].valor + "</td>");
						writer.write("<td>" + grid.redondear(valorFuncionSinError) + "</td>");
						writer.write("<td>" + (int)resultado[1].valor + "</td>");
						writer.write("<td>" + gridErrores.redondear(Math.abs(resultado[0].error)) + "</td>");
						writer.write("<td>" + gridErrores.redondear(Math.abs(calcularErrorTruncamiento((int)resultado[1].valor, puntoACalcular.valor, functionCounter) / resultado[0].valor)) + "</td>");
						writer.write("<td>" + gridErrores.redondear(errorExacto/valorFuncionSinError) + "</td>");
						writer.write("<td>" + gridErrores.redondear(Math.abs(resultado[0].error * resultado[0].valor))  + "</td>");
						writer.write("<td>" + gridErrores.redondear(Math.abs(calcularErrorTruncamiento((int)resultado[1].valor, puntoACalcular.valor, functionCounter))) + "</td>");
						writer.write("<td>" + gridErrores.redondear(errorExacto) + "</td>");
						
						
						writer.write("</tr><tr>");
						writer.flush();
						
					}
				}
			}
			if (functionCounter<3) out.println("");
		}
		
		
		writer.write("</tr></table></pre></body></html>");
		writer.close();


	}
	
	public static void main(String[] args) throws Exception {
		Tp1 numerico = new Tp1();
		numerico.run();
	}		
		
		
		
		
}

