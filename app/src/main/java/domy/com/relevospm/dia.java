package domy.com.relevospm;

import java.util.GregorianCalendar;

public class dia {

    static int febrero;

    public static void main(String[] args) {

        System.out.println(Fecha(29, 2, 2015));
        System.out.println(Fecha("29/2/2016"));
        System.out.println(Fecha(2, 3, 2016));

    }

    static int Fecha(String fecha) {

        String[] S = fecha.split("/");

        int resultado = Fecha(Integer.parseInt(S[0]), Integer.parseInt(S[1]), Integer.parseInt(S[2]));

        return resultado;
    }

    static int Fecha(int dia, int mes, int anio) {

        GregorianCalendar calendar = new GregorianCalendar();

        if (calendar.isLeapYear(anio)) {
            //System.out.println("El a�o es bisiesto");
            febrero = 29;
        } else {
            //System.out.println("El a�o no es bisiesto");
            febrero = 28;
        }

        // un vector para alamcenar los dias que tiene cada mes, suponemos que

        int[] diasDeMeses = new int[]{31, febrero, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 30, 31};

        //	String[] nombresDeMeses = new String[] { "", "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
        //			"Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" };
        //	 pedimos al usuario el mes y el dia
        //	int mes = Integer.parseInt(input("Digite el numero del mes (1 para Enero):"));
        //	int dia = Integer.parseInt(input("Digite el numero del dia:"));

        int sumaDeDias = 0;
        for (int i = 0; i < mes - 1; i++) {
            sumaDeDias = sumaDeDias + diasDeMeses[i];
        }

        sumaDeDias = sumaDeDias + dia;

        //	JOptionPane.showMessageDialog(null,
        //			"Desde Enero 1 hasta " + nombresDeMeses[mes] + ", " + dia + " hay " + sumaDeDias + " dias");
        //	System.exit(0);

        return sumaDeDias;
    }
}