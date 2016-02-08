package domy.com.relevospm;

/**
 * Created by Domingo on 21/01/2016.
 */

import java.util.Calendar;
import java.util.GregorianCalendar;


public class Dia4y2 {


    public static boolean turnoBoolean(int Grupo, int dia, int mes, int anho) {

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

        int NumGrupo = 0;

        switch (Grupo) {

            case 1:
                NumGrupo = 27;  //"12/27/1995"
                break;
            case 2:
                NumGrupo = 26;  //"12/26/1995"
                break;
            case 3:
                NumGrupo = 31;  //"12/31/1995"
                break;
            case 4:
                NumGrupo = 30;  //"12/30/1995"
                break;
            case 5:
                NumGrupo = 29;  //"12/29/1995"
                break;
            case 6:
                NumGrupo = 28;  //"12/28/1995"
                break;
        }
        //int mess = mes -1;

        Calendar calendar_dada = new GregorianCalendar(anho, mes - 1, dia);
        Calendar calendar_fija = new GregorianCalendar(1995, 11, NumGrupo);

        java.sql.Date fecha_dada = new java.sql.Date(calendar_dada.getTimeInMillis());
        java.sql.Date fecha_fija = new java.sql.Date(calendar_fija.getTimeInMillis());

        boolean dia4y2 = false;

        if (((fecha_dada.getTime() / MILLSECS_PER_DAY) - (fecha_fija.getTime() / MILLSECS_PER_DAY)) % 6 < 2)

            dia4y2 = true;
        else
            dia4y2 = false;

        return dia4y2;

    }

    public static String turno(int Grupo, int dia, int mes, int anho) {

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

        int NumGrupo = 0;

        switch (Grupo) {

            case 5:
                NumGrupo = 29;  //"12/29/1995"
                break;
            case 6:
                NumGrupo = 28;  //"12/28/1995"
                break;
            case 1:
                NumGrupo = 27;  //"12/27/1995"
                break;
            case 2:
                NumGrupo = 26;  //"12/26/1995"
                break;
            case 3:
                NumGrupo = 31;  //"12/31/1995"
                break;
            case 4:
                NumGrupo = 30;  //"12/30/1995"
                break;

        }

        Calendar calendar_dada = new GregorianCalendar(anho, mes - 1, dia);
        Calendar calendar_fija = new GregorianCalendar(1995, 11, NumGrupo);

        java.sql.Date fecha_dada = new java.sql.Date(calendar_dada.getTimeInMillis());
        java.sql.Date fecha_fija = new java.sql.Date(calendar_fija.getTimeInMillis());

        String dia4y2 = null;
        if (((fecha_dada.getTime() / MILLSECS_PER_DAY) - (fecha_fija.getTime() / MILLSECS_PER_DAY)) % 6 < 2)

            dia4y2 = "DESCANSAR";
        else
            dia4y2 = "TRABAJAR";

        return dia4y2;

    }

    public static int dia(int Grupo, int dia, int mes, int anho) {
        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

        int NumGrupo = 0;

        switch (Grupo) {

            case 5:
                NumGrupo = 29;  //"12/29/1995"
                break;
            case 6:
                NumGrupo = 28;  //"12/28/1995"
                break;
            case 1:
                NumGrupo = 27;  //"12/27/1995"
                break;
            case 2:
                NumGrupo = 26;  //"12/26/1995"
                break;
            case 3:
                NumGrupo = 31;  //"12/31/1995"
                break;
            case 4:
                NumGrupo = 30;  //"12/30/1995"
                break;

        }

        Calendar calendar_dada = new GregorianCalendar(anho, mes - 1, dia);
        Calendar calendar_fija = new GregorianCalendar(1995, 11, NumGrupo);

        java.sql.Date fecha_dada = new java.sql.Date(calendar_dada.getTimeInMillis());
        java.sql.Date fecha_fija = new java.sql.Date(calendar_fija.getTimeInMillis());

        int dia4y2 = 0;
        if (((fecha_dada.getTime() / MILLSECS_PER_DAY) - (fecha_fija.getTime() / MILLSECS_PER_DAY)) % 6 < 2)
            dia4y2 = dia;
        else
            ;

        return dia4y2;

    }

    public static int GrupoLibra(int dia, int mes, int anho) {

        final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

        int NumGrupo = 0;

        int n1 = -1;
        do {
            n1 = n1 + 2;
            //System.out.println(n1+" ");


            if (n1 == 1)
                NumGrupo = 29;//NumGrupo = 27;  //"12/27/1995" grupo 1
            else
                //NumGrupo = 31;
                if (n1 == 3)
                    NumGrupo = 27;//NumGrupo = 31;  //"12/31/1995" grupo 3
                else if (n1 == 5)
                    NumGrupo = 31;//NumGrupo = 29;  //"12/29/1995" grupo 5

            Calendar calendar_dada = new GregorianCalendar(anho, mes - 1, dia);
            Calendar calendar_fija = new GregorianCalendar(1995, 11, NumGrupo);

            java.sql.Date fecha_dada = new java.sql.Date(calendar_dada.getTimeInMillis());
            java.sql.Date fecha_fija = new java.sql.Date(calendar_fija.getTimeInMillis());

            if (((fecha_dada.getTime() / MILLSECS_PER_DAY) - (fecha_fija.getTime() / MILLSECS_PER_DAY)) % 6 < 2)
                return n1;

        } while (n1 < 5);
        return n1;
    }

    public static int GrupoTrabaja(int dia, int mes, int anho) {

        int NumGrupo = 0;

        if (GrupoLibra(dia, mes, anho) == 1)
            NumGrupo = 35;
        else if (GrupoLibra(dia, mes, anho) == 3)
            NumGrupo = 15;
        else if (GrupoLibra(dia, mes, anho) == 5)
            NumGrupo = 13;

        return NumGrupo;

    }

    public static void main(String[] args) {

        int g = 1;

        int m = 1;

        System.out.println("1  " + dia(g, 1, m, 2014));
        System.out.println("2  " + dia(g, 2, m, 2014));
        System.out.println("3  " + dia(g, 3, m, 2014));
        System.out.println("4  " + dia(g, 4, m, 2014));
        System.out.println("5  " + dia(g, 5, m, 2014));
        System.out.println("6  " + dia(g, 6, m, 2014));
        System.out.println("7  " + dia(g, 7, m, 2014));
        System.out.println("8  " + dia(g, 8, m, 2014));
        System.out.println("9  " + dia(g, 9, m, 2014));
        System.out.println("10 " + dia(g, 10, m, 2014));
        System.out.println("11 " + dia(g, 11, m, 2014));
        System.out.println("12 " + dia(g, 12, m, 2014));
        System.out.println("13 " + dia(g, 13, m, 2014));
        System.out.println("14 " + dia(g, 14, m, 2014));
        System.out.println("15 " + dia(g, 15, m, 2014));
        System.out.println("16 " + dia(g, 16, m, 2014));
        System.out.println("17 " + dia(g, 17, m, 2014));
        System.out.println("18 " + dia(g, 18, m, 2014));
        System.out.println("19 " + dia(g, 19, m, 2014));
        System.out.println("20 " + dia(g, 20, m, 2014));
        System.out.println("21 " + dia(g, 21, m, 2014));
        System.out.println("22 " + dia(g, 22, m, 2014));
        System.out.println("23 " + dia(g, 23, m, 2014));
        System.out.println("24 " + dia(g, 24, m, 2014));
        System.out.println("25 " + dia(g, 25, m, 2014));
        System.out.println("26 " + dia(g, 26, m, 2014));
        System.out.println("27 " + dia(g, 27, m, 2014));
        System.out.println("28 " + dia(g, 28, m, 2014));
        System.out.println("29 " + dia(g, 29, m, 2014));
        System.out.println("30 " + dia(g, 30, m, 2014));
        System.out.println("-------------------");
        System.out.println("trabaja " + GrupoTrabaja(20, 1, 2015));
        System.out.println("libra " + GrupoLibra(20, 1, 2015));

    }
}