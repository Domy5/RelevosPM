package domy.com.relevospm;

import com.imanoweb.calendarview.DayDecorator;
import com.imanoweb.calendarview.DayView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import domy.com.relevospm.Utiles.Dia4y2;

public class DaysDecorator1 implements DayDecorator {

    @Override
    public void decorate(DayView dayView){

            //  if (!isPastDay(dayView.getDate())) {
            if (GrupoLibra(dayView.getDate(),1)) {

                dayView.setBackgroundColor(0xFF55FF00);

                //    dayView.setTextColor(0xFF0000);
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

        // test your condition, if Date specified is before today
        if (Grupolibra != 0) {
            return true;
        }
        return false;
    }


}