package domy.com.relevospm;

import android.graphics.Color;

import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import domy.com.relevospm.Utiles.Dia4y2;

public class DaysDecoratorV implements DayDecorator {

    public String getFecha1() {
        return fecha1;
    }

    public void setFecha1(String fecha1) {
        this.fecha1 = fecha1;
    }

    public String getFecha2() {
        return fecha2;
    }

    public void setFecha2(String fecha2) {
        this.fecha2 = fecha2;
    }

    private String fecha1 ;
    private String fecha2 ;


    @Override
    public void decorate(DayView dayView) {

        String D1[] = fecha1.split("-");

        int Dia1 = Integer.parseInt(D1[2]);
        int Mes1 = Integer.parseInt(D1[1]);
        int anio1 = Integer.parseInt(D1[0]);

        String D2[] = fecha2.split("-");

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
    }


    public static synchronized java.sql.Date restarFechasDias(java.sql.Date fch, int dias) {
        Calendar cal = new GregorianCalendar();
        cal.setTimeInMillis(fch.getTime());
        cal.add(Calendar.DATE, -dias);
        return new java.sql.Date(cal.getTimeInMillis());
    }
}