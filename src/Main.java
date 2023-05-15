import java.util.ArrayList;
import java.util.List;
/**
 * An example of using the MeetingScheduler class to search for available meeting timeslots
 * Author: Anastasiya Ronskaya
 * Date: 15.05.2023
 */
public class Main {
    public static void main(String[] args) {

        // Creating two calendars to search for available time intervals

        MeetingScheduler.Calendar calendar1 = new MeetingScheduler.Calendar();
        calendar1.working_hours = new MeetingScheduler.WorkingHours();
        calendar1.working_hours.start = "09:00";
        calendar1.working_hours.end = "19:55";
        calendar1.planned_meeting = new ArrayList<>();
        MeetingScheduler.Meeting meeting1 = new MeetingScheduler.Meeting();
        meeting1.start = "09:00";
        meeting1.end = "10:30";
        MeetingScheduler.Meeting meeting2 = new MeetingScheduler.Meeting();
        meeting2.start = "12:00";
        meeting2.end = "13:00";
        MeetingScheduler.Meeting meeting3 = new MeetingScheduler.Meeting();
        meeting3.start = "16:00";
        meeting3.end = "18:00";
        calendar1.planned_meeting.add(meeting1);
        calendar1.planned_meeting.add(meeting2);
        calendar1.planned_meeting.add(meeting3);

        MeetingScheduler.Calendar calendar2 = new MeetingScheduler.Calendar();
        calendar2.working_hours = new MeetingScheduler.WorkingHours();
        calendar2.working_hours.start = "10:00";
        calendar2.working_hours.end = "18:30";
        calendar2.planned_meeting = new ArrayList<>();
        MeetingScheduler.Meeting meeting4 = new MeetingScheduler.Meeting();
        meeting4.start = "10:00";
        meeting4.end = "11:30";
        MeetingScheduler.Meeting meeting5 = new MeetingScheduler.Meeting();
        meeting5.start = "12:30";
        meeting5.end = "14:30";
        MeetingScheduler.Meeting meeting6 = new MeetingScheduler.Meeting();
        meeting6.start = "14:30";
        meeting6.end = "15:00";
        MeetingScheduler.Meeting meeting7 = new MeetingScheduler.Meeting();
        meeting7.start = "16:00";
        meeting7.end = "17:00";
        calendar2.planned_meeting.add(meeting4);
        calendar2.planned_meeting.add(meeting5);
        calendar2.planned_meeting.add(meeting6);
        calendar2.planned_meeting.add(meeting7);

        int meetingDuration = 30; // Meeting duration in minutes

        // Search for available time slots for a meeting
        List<String[]> availableTimes = MeetingScheduler.findMeetingTimes(calendar1, calendar2, meetingDuration);

        // Display available time intervals on the screen
        System.out.println("Available time slots for meetings:");
        for (String[] time : availableTimes) {
            System.out.println("[" + time[0] + ", " + time[1] + "]");
        }
    }
}
