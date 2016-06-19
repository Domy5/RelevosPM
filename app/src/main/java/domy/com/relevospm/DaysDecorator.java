package domy.com.relevospm;

import android.graphics.Color;

import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import domy.com.relevospm.Utiles.Dia4y2;

public class DaysDecorator implements DayDecorator {

    public String getFechaV1() {
        return fechaV1;
    }

    public void setFechaV1(String fecha1) {
        this.fechaV1 = fecha1;
    }

    public String getFechaV2() {
        return fechaV2;
    }

    public void setFechaV2(String fecha2) {
        this.fechaV2 = fecha2;
    }

    private String fechaV1 ;
    private String fechaV2 ;
    private int Grupo;


    public int getGrupo() {
        return Grupo;
    }
    public void setGrupo(int grupo) {
        Grupo = grupo;
    }




    @Override
    public void decorate(DayView dayView) {

        String D1[] = fechaV1.split("-");

        int Dia1 = Integer.parseInt(D1[2]);
        int Mes1 = Integer.parseInt(D1[1]);
        int anio1 = Integer.parseInt(D1[0]);

        String D2[] = fechaV2.split("-");

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

        switch (Grupo) {

            case 1:
                if (GrupoLibra(dayView.getDate(),Grupo)) {
                dayView.setBackgroundColor(0xFF55FF00);
            }
                break;
            case 3:
                if (GrupoLibra(dayView.getDate(),Grupo)) {
                dayView.setBackgroundColor(0xff0400ff);
            }
                break;
            case 5:
                if (GrupoLibra(dayView.getDate(),Grupo)) {
                dayView.setBackgroundColor(0xFFFF0008);
            }
                break;
            default:

                break;
        }

    }

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