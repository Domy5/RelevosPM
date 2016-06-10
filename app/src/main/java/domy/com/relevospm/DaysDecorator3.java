package domy.com.relevospm;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import domy.com.relevospm.Utiles.Dia4y2;

public class DaysDecorator3 implements DayDecorator {

    @Override
    public void decorate(DayView dayView){
        {
            //  if (!isPastDay(dayView.getDate())) {
            if (GrupoLibra(dayView.getDate(),3)) {

                dayView.setBackgroundColor(0xff0400ff);

                //    dayView.setTextColor(0xFF0000);
            }
        }
    }

    public void decorate(DayView dayView,int grupo) {
      //  if (!isPastDay(dayView.getDate())) {
            if (GrupoLibra(dayView.getDate(),grupo)) {

               dayView.setBackgroundColor(0xFF55FF00);

            //    dayView.setTextColor(0xFF0000);
        }
    }

    private boolean isPastDay(Date date) {
        Calendar c = Calendar.getInstance();

        // set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // and get that as a Date
        Date today = c.getTime();

        // test your condition, if Date specified is before today
        if (date.before(today)) {
            return true;
        }
        return false;
    }

    private boolean GrupoLibra(Date date, int grupo) {

     //   int grupo = 3;

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String D[] = df.format(date).toString().split("-");

        int dia = Integer.parseInt(D[0]);
        int Mes = Integer.parseInt(D[1]);
        int anio = Integer.parseInt(D[2]);

       int Grupolibra = Dia4y2.dia(grupo, dia, Mes, anio); // si trabaja devuelve 0 si libra devuelve el dia

        // test your condition, if Date specified is before today
        if (Grupolibra != 0) {
            return true;
        }
        return false;
    }


}