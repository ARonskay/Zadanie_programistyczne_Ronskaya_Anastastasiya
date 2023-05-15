import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * An example of using the MeetingScheduler class to search for available meeting timeslots
 * Author: Anastasiya Ronskaya
 * Date: 15.05.2023
 */
class MeetingScheduler {
    /**
     * A method for finding available time slots for an appointment in two calendars.
     *
     * @param calendar1       Calendar 1
     * @param calendar2       Calendar 2
     * @param meetingDuration Meeting duration in minutes
     * @return List of available time slots for meetings
     */
    public static List<String[]> findMeetingTimes(Calendar calendar1, Calendar calendar2, int meetingDuration) {
        List<String[]> availableTimes = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        try {
            Date startTime1 = dateFormat.parse(calendar1.working_hours.start);
            Date endTime1 = dateFormat.parse(calendar1.working_hours.end);
            Date startTime2 = dateFormat.parse(calendar2.working_hours.start);
            Date endTime2 = dateFormat.parse(calendar2.working_hours.end);

            // Finding the maximum start time between two calendars
            Date start = startTime1.after(startTime2) ? startTime1 : startTime2;

            // Finding the minimum ending time between two calendars
            Date end = endTime1.before(endTime2) ? endTime1 : endTime2;

            // Iterating through a time window
            Date currentTime = start;
            while (currentTime.before(end)) {
                boolean isAvailable = true;

                // Check that there are no scheduled appointments on both calendars
                for (Meeting meeting : calendar1.planned_meeting) {
                    Date meetingStart = dateFormat.parse(meeting.start);
                    Date meetingEnd = dateFormat.parse(meeting.end);
                    if (currentTime.equals(meetingStart) || (currentTime.after(meetingStart) && currentTime.before(meetingEnd))) {
                        isAvailable = false;
                        break;
                    }
                }

                if (isAvailable) {
                    for (Meeting meeting : calendar2.planned_meeting) {
                        Date meetingStart = dateFormat.parse(meeting.start);
                        Date meetingEnd = dateFormat.parse(meeting.end);
                        if (currentTime.equals(meetingStart) || (currentTime.after(meetingStart) && currentTime.before(meetingEnd))) {
                            isAvailable = false;
                            break;
                        }
                    }
                }

                // If available, add to the list of available time slots
                if (isAvailable) {
                    String[] availableTime = {dateFormat.format(currentTime), dateFormat.format(getEndTime(currentTime, meetingDuration))};
                    availableTimes.add(availableTime);
                }
                // Going to the next time
                currentTime = getEndTime(currentTime, meetingDuration);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return availableTimes;
    }

    /**
     * Obtain a finite time based on the initial time and duration.
     *
     * @param startTime Start time
     * @param duration  Meeting duration in minutes
     * @return End time
     */
    private static Date getEndTime(Date startTime, int duration) {
        long endTimeMillis = startTime.getTime() + duration * 60000;
        return new Date(endTimeMillis);
    }

    /**
     * A class that presents a calendar with office hours and scheduled appointments.
     */
    static class Calendar {
        WorkingHours working_hours;
        List<Meeting> planned_meeting;
    }

    /**
     *A class representing work hours.
     */
    static class WorkingHours {
        String start;
        String end;
    }

    /**
     * A class representing an encounter with a start time and an end time.
     */
    static class Meeting {
        String start;
        String end;
    }
}
