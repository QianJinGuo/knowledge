package tech.jinguo;

import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class KeepTeamsFlexible {
    public static void main(String[] args) throws AWTException, InterruptedException {
        Robot robot = new Robot();
        Random random = new Random();
        DayOfWeek dayOfWeek;
        LocalDateTime startWorkTime, startLaunchTime, endLaunchTime, endWorkTime;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        ArrayList<LocalDate> holidays = new ArrayList<>();
        //Please add holidays as below if needed:
        //holidays.add(LocalDate.parse("2022-12-13"));
        //holidays.add(LocalDate.parse("2022-12-16"));

        ArrayList<LocalDateTime[]> oooList = new ArrayList<>();
        //Please add more ooo as below if needed:
        /*
        LocalDateTime outOfOfficeStartTime = LocalDateTime.parse("2022-12-13 13:00:00",formatter);
        LocalDateTime outOfOfficeEndTime = LocalDateTime.parse("2022-12-13 18:00:00",formatter);
        LocalDateTime outOfOfficeStartTime2 = LocalDateTime.parse("2022-12-18 10:00:00",formatter);
        LocalDateTime outOfOfficeEndTime2 = LocalDateTime.parse("2022-12-20 18:00:00",formatter);
        oooList.add(new LocalDateTime[]{outOfOfficeStartTime,outOfOfficeEndTime});
        oooList.add(new LocalDateTime[]{outOfOfficeStartTime2,outOfOfficeEndTime2});
        */

        while (true) {
            final LocalDate currentDate = LocalDate.now();
            final LocalDateTime currentDateTime = LocalDateTime.now();
            startWorkTime = LocalDateTime.of(currentDate, LocalTime.parse("10:00:00"));
            startLaunchTime = LocalDateTime.of(currentDate, LocalTime.parse("12:00:00"));
            endLaunchTime = LocalDateTime.of(currentDate, LocalTime.parse("13:00:00"));
            endWorkTime = LocalDateTime.of(currentDate, LocalTime.parse("18:00:00"));
            dayOfWeek = DayOfWeek.of(currentDate.get(ChronoField.DAY_OF_WEEK));
            boolean isOffWork = currentDateTime.isBefore(startWorkTime) || currentDateTime.isAfter(endWorkTime);
            boolean isRestOfLaunch = currentDateTime.isAfter(startLaunchTime) && currentDateTime.isAfter(endLaunchTime);
            boolean isWeekends = dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
            boolean isHoliday = holidays.stream().anyMatch(currentDate::equals);
            boolean isOOO = oooList.stream().anyMatch(localDateTimes -> currentDateTime.isAfter(localDateTimes[0]) && currentDateTime.isBefore(localDateTimes[1]));
            if (isWeekends || isHoliday) {
                System.out.printf("currentDateTime:%s, isWeekends:%b , isHoliday:%b%n", formatter.format(currentDateTime), isWeekends, isHoliday);
                TimeUnit.DAYS.sleep(1L);
                continue;
            }
            if (isOffWork || isOOO) {
                System.out.printf("currentDateTime:%s, isOffWork:%b , isOOO:%b%n", formatter.format(currentDateTime), isOffWork, isOOO);
                TimeUnit.HOURS.sleep(1L);
                continue;
            }
            if (isRestOfLaunch) {
                System.out.printf("currentDateTime:%s, isRestOfLaunch:%b%n", formatter.format(currentDateTime), isRestOfLaunch);
                TimeUnit.MINUTES.sleep(8L);
                continue;
            }
            robot.mouseMove(random.nextInt(100), random.nextInt(100));
            System.out.printf("currentWorkTime:%s", formatter.format(currentDateTime));
            TimeUnit.MINUTES.sleep(4L);
        }
    }
}
