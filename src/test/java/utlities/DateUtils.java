package utlities;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtils {
    public static String returnNextMonth() {
        //Creating Date Object
        Date dNow = new Date();

        //Creating calendar object
        Calendar calendar = new GregorianCalendar();

        //Set Calendar date to current date
        calendar.setTime(dNow);

        //Create object of SimpleDateFormat
        //Defining date format to - example: (Feb-2024)
        SimpleDateFormat sdf = new SimpleDateFormat("MMM-dd-yyyy");
        //Incrementing the current month
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        calendar.add(Calendar.YEAR, 1);
        //Generating the date based on the specified format
        String date = sdf.format(calendar.getTime());
        return date;


    }
}
