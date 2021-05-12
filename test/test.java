
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.mobilePIDEV.services.ServiceParticipation;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author DELL
 */
public class test {

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy MM dd");
        Date date = new Date();
        String sDate1 = "2021 04 01";
        try {
            Date date1 = new SimpleDateFormat("yyyy MM dd").parse(sDate1);
           // long diffInMillies = Math.abs(date.getTime() - date1.getTime());
           int days = (int) (Math.abs(date.getTime() - date1.getTime()) / (1000*60*60*24));
            System.out.println(days);

//        long daysBetween = ChronoUnit.DAYS.between(date1, date);
//        LocalDate date2 = LocalDate.parse(sDate1, dtf);
//        LocalDate date3 = LocalDate.parse(dateFormat.format(date), dtf);
//        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//        long diff = Duration.between(date3, date2).toDays();
//        System.out.println(diffInMillies + " days ago");
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }

//        System.out.println(dateFormat.format(diff));
    }

}
