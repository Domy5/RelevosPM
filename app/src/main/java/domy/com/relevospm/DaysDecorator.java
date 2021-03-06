package domy.com.relevospm;

import android.graphics.Color;
import android.util.Log;

import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import domy.com.relevospm.Utiles.Dia4y2;
import domy.com.relevospm.Utiles.Utiles;

public class DaysDecorator implements DayDecorator {

    private String fecha;
    private String dne;
    private int grupo;

    public void setGrupo(int grupo) {
        this.grupo = grupo;
    }

    public String getDne() {
        return dne;
    }

    public void setDne(String dne) {
        this.dne = dne;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public void decorate(DayView dayView) {

        Calendar cal = Calendar.getInstance();

        cal.setTime(dayView.getDate());

        int d = cal.get(Calendar.DATE);
        int m  = cal.get(Calendar.MONTH) + 1; // ojo es un mes mas
        int y = cal.get(Calendar.YEAR);

       // Log.v("decorate: ", d +" "+ m +" " + " "+ y);
        String F[] = fecha.split("/");

          AgenteDAO agenteDao = new AgenteDAO();

          agenteDao.actualizarDatosAgentePeriodo(dne,Utiles.periodo_Mes("28",String.valueOf(Integer.parseInt(F[1])-1)));
          Agente agente = agenteDao.getAgente();
         // agente.getDNE();//ejemplo


          Log.v("no   ", Utiles.periodo_Mes(F[0],F[1]));
          Log.v("no -1", Utiles.periodo_Mes("28",String.valueOf(Integer.parseInt(F[1])-1)));
          Log.v("no +1", Utiles.periodo_Mes("1", String.valueOf(Integer.parseInt(F[1])+1)));


        switch (grupo) {

            case 1:
                if (GrupoLibra(dayView.getDate(), grupo)) {
                    dayView.setBackgroundColor(0xFF55FF00);
                }
                break;
            case 3:
                if (GrupoLibra(dayView.getDate(), grupo)) {
                    dayView.setBackgroundColor(0xff0400ff);
                }
                break;
            case 5:
                if (GrupoLibra(dayView.getDate(), grupo)) {
                    dayView.setBackgroundColor(0xFFFF0008);
                }
                break;
            default:

                break;
        }
    }
/*
        String D2[] = fechaV2.split("/");

        int Dia2 = Integer.parseInt(D2[2]);
        int Mes2 = Integer.parseInt(D2[1]);
        int anio2 = Integer.parseInt(D2[0]);

        Calendar C1 = new GregorianCalendar(anio1, Mes1 - 1, Dia1);
        Calendar C2 = new GregorianCalendar(anio2, Mes2 - 1, Dia2 + 1);

        Date Date1 = C1.getTime();
        Date Date2 = C2.getTime();


        if (dayView.getDate().after(Date1) && dayView.getDate().before(Date2)) {
            dayView.setBackgroundColor(Color.YELLOW);
            //dayView.setBackgroundResource(R.drawable.menu);
        }



    }*/

    private boolean GrupoLibra(Date date, int grupo) {

        //   int grupo = 3;

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String D[] = df.format(date).toString().split("-");

        int dia = Integer.parseInt(D[0]);
        int Mes = Integer.parseInt(D[1]);
        int anio = Integer.parseInt(D[2]);

        int Grupolibra = Dia4y2.dia(grupo, dia, Mes, anio); // si trabaja devuelve 0 si libra devuelve el dia


        if (Grupolibra != 0) {
            return true;
        }
        return false;
    }


    public static synchronized java.sql.Date restarFechasDias(java.sql.Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, -dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }
}