

/**
	 * Clase Grilla
	 * Se utiliza para representar los numeros usando una determinada 
	 * precicion y un determinado tipo de redondeo
	 */
	public class Grilla {
		/**
		 * 
		 */
		int t = 0;
		boolean redondeoSimetrico = true;
		
		/**
		 * Error relativo producido por la representación de un número en la grilla
		 */
		public double error() {
			return Math.pow(10, -this.t+1);
		}
		
		/**
		 * t es la cantidad de digitos de precicion
		 * redondeoSimetrico indica si se redondea simetrico o truncado
		 */
		public Grilla(int t, boolean redondeoSimetrico){
			this.t = t;
			this.redondeoSimetrico = redondeoSimetrico;
		}
		
		
		/**
		 * Si viene expresado como notacion cientifica lo deja en forma normal
		 */
		/*public String prepararNumero(double x){
			String s=String.valueOf(x);
			if(s.indexOf("E")>-1){
				return moverComa(quitarE(s), getExponente(s));
			}
			return s;
		}*/
		
		/**
		 * repite una cadena de caracteres las veces que se necesite
		 */
		public String repetirCadena( String str, int veces){
			String resultado="";
			if (veces<1)
				return "";
			
			for (int i=1;i<=veces;i++){
				resultado +=str;
			}
			return resultado;
		}
		
		/**
		 * Mueve la coma de un numero segun la cantidad de lugares que se necesite
		 */
		public String moverComa( String numero, int lugares ){
			String result;
			if (lugares==0)
				return numero;
			
			int signo=(lugares>=0)?1:-1;
			int posComa=numero.indexOf(".");
			
			if (signo==1){
				result=	numero+repetirCadena("0",-numero.length()+(posComa+ lugares)+2) ;
			}else{
				result=	repetirCadena("0",-lugares-posComa+1) +numero;
			}
			posComa=result.indexOf(".");
			result=quitarComa(result);
			posComa--;
			int nuevaPosicionComa=posComa+lugares;
			
			String concatenacion="";
			for (int i=0;i<result.length();i++){
				concatenacion+=result.substring(i,i+1);
				if (i==nuevaPosicionComa){
					concatenacion+=".";
				}
			}				
			
			return concatenacion;
		}
		
		/**
		 * elimina la coma del numero
		 */
		public String quitarComa(String numero){
			if (numero.indexOf(".")>=0){
				return numero.substring(0,numero.indexOf("."))+ numero.substring(numero.indexOf(".")+1);	
			}else{
				return numero;
			}		
		}
		/**
		 * inserta la coma en una cadena, en la posicion elegida
		 */
		public String insertarComa(String base,int posicion){
			return base.substring(0,posicion+1)+"."+ base.substring(posicion+1);
		}
		
		/**
		 * se queda con la mantisa del numero
		 */
		public String quitarE( String numero){
			if(numero.indexOf("E")>-1){
				return numero.substring(0,numero.indexOf("E"));
			}
			return numero;
		}
		
			
		
		/**
		 * redondea un numero a t digitos de precicion usando el tipo de redondeo elegido
		 */
//		public double redondear(double x){
//			if (x == 0)
//				return 0.0;
//			int sign = (x>=0)?1:-1;
//			x=Math.abs(x);
//			double c ;//= Math.log10(x);
//			c = new Double(Math.log10(x)).intValue() + 1;
//			if (redondeoSimetrico)
//				x = x + 0.5 * Math.pow(10,c-t);
//			x = new Double(x*Math.pow(10,t-c)).intValue();
//			x = sign*x*Math.pow(10,c-t);
//			return x;
//		}
		
		
		
		/**
		 * redondea un numero a t digitos de precicion usando el tipo de redondeo elegido
		 */
		/*public double redondear2(double x){
			int signo=(x>=0)?1:-1;
			x=Math.abs(x);
			String s=""//prepararNumero(x);			
			int posicionComa=s.indexOf(".")-1;
			s=quitarComa(s);
			String result=s;
			int contadorDigitosNoNulos=0;
			boolean primerNoNuloEncontrado=false;
			boolean redondeoRealizado=false;
			for (int i=0;i<s.length() ;i++){
				if (redondeoRealizado){
					result+="0";
				}else{
					if(!s.substring(i,i+1).equals("0")){
						primerNoNuloEncontrado=true;
					}
					if ( primerNoNuloEncontrado){
						contadorDigitosNoNulos++;
						if (s.length()>i+1 && contadorDigitosNoNulos==this.t ){
							int digitoActual=Integer.parseInt(s.substring(i,i+1));
							if (this.redondeoSimetrico && Integer.parseInt(s.substring(i+1,i+2))>=5){
								digitoActual+=1;
							}
							result=s.substring(0,i) + String.valueOf(digitoActual);
							redondeoRealizado=true;
						}
					}
				}
			}			
			
			return signo*Double.parseDouble(insertarComa(result, posicionComa));
		}
		
		*/
		/**
		 * devuelve el exponente de un numero expresado en notacion cientifica
		 */
		public int getExponente( double n){
			String s = String.valueOf(n);
			if(n==0)
				return 0;
			if (s.indexOf("E")>-1){
				return Integer.parseInt(s.substring(s.indexOf("E")+1,s.length()))+1;
			}else{
				if(n>10){
					double log=Math.log10(Math.abs(n));
					return (int)((log==new Double(log).intValue() )?log:log-1)+2;
				}else{
					double log=Math.log10(Math.abs(n));
					return (int)((log==new Double(log).intValue() )?log:log-1)+1;
				}
			}
		}
		
		public String normalizar(double numero){
			int signo=(numero>=0)?1:-1;
			numero=Math.abs(numero);
			String s=String.valueOf(Math.abs(numero));
			if (s.indexOf("E")>-1)
				return "."+quitarComa(quitarE(s)) ;
			//return "0."+quitarComa(quitarE(s)) ;
			if (Math.abs(numero)>1){
				//return insertarComa(quitarComa("0"+s),0);
				return "."+quitarComa(s);
			}else{
				String sinComa= quitarComa(s);
				String result="";
				String actual="";
				boolean primerNoCeroEncontrado=false;
				for (int i=0;i<sinComa.length() ;i++){
					actual=sinComa.substring(i,i+1);
					if (!actual.equals("0") && !primerNoCeroEncontrado){
						primerNoCeroEncontrado=true;
						result+=".";
					}
					if(primerNoCeroEncontrado){
						result+=actual;
					}
				}
				return result;				
			}
		}
		
		public String formatearATDigitos(String s,int t){
			String result="";
			int contadorDigitos=0;
			for (int i=0;contadorDigitos<t ;i++){
				if (!(i<s.length())){
					result+="0";	
				}else{
					result+=s.substring(i,i+1);
				}				
				contadorDigitos++;
			}
			return result;
		}
		
		
		
		public double redondear(double numero){
			//no olvodar el exponente
			//System.out.println("num:" + numero);
			if (Math.abs(numero)==1 || numero==0){
				//System.out.println("result ( a "+ this.t+") : " + numero);
				return numero;				
			}
			String signo=(numero>=0)?"":"-";
			int exponente = getExponente(numero);
			String normalizado=normalizar(numero);
			String sinComa = quitarComa(normalizado);
			String formateadoATmasUnDigitos=formatearATDigitos(sinComa,this.t+1);
			long m=Long.parseLong(formatearATDigitos(sinComa,this.t));
			
			if(Integer.parseInt(formateadoATmasUnDigitos.substring( t,t+1)) >=5 && this.redondeoSimetrico){
				m++;
			}
			
			double result=Double.parseDouble(signo+"0."+String.valueOf(m)+ "E" + String.valueOf(exponente));
			//System.out.println("result ( a "+ this.t+") : " + result );
			return result;
		}	
	}