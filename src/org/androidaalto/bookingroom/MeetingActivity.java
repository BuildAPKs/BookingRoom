/**
   Copyright: 2011 Android Aalto

   This file is part of BookingRoom.

   BookingRoom is free software; you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation; either version 3 of the License, or
   (at your option) any later version.

   BookingRoom is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with BookingRoom. If not, see <http://www.gnu.org/licenses/>.
 */

package org.androidaalto.bookingroom;

import android.app.Activity;
import android.os.Bundle;
import android.text.format.Time;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class MeetingActivity extends Activity {

    public static final String EXTRA_END_TIME = "end";
    public static final String EXTRA_CONTACT_EMAIL = "email";
    public static final String EXTRA_CONTACT_NAME = "name";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_START_HOUR = "hour";
    public static final String EXTRA_DAY = "day";
    public static final String EXTRA_START_TIME = "start";

    EditText titleEdit, nameEdit, emailEdit;
    TimePicker startPicker, endPicker;
    TextView meetingHeader;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting);

        meetingHeader = (TextView) findViewById(R.id.meetingHeader);
        startPicker = (TimePicker) findViewById(R.id.startPicker);
        endPicker = (TimePicker) findViewById(R.id.endPicker);
        titleEdit = (EditText) findViewById(R.id.titleEdit);
        nameEdit = (EditText) findViewById(R.id.nameEdit);
        emailEdit = (EditText) findViewById(R.id.emailEdit);

        startPicker.setIs24HourView(true);
        endPicker.setIs24HourView(true);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            Time start = new Time();
            // Use full date if we've it. Otherwise, use day and hour
            String startStr = extras.getString(EXTRA_START_TIME);
            if (startStr != null) {
                start.parse(startStr);
            } else {
                start.setJulianDay(extras.getInt(EXTRA_DAY));
                start.hour = extras.getInt(EXTRA_START_HOUR);
                start.minute = 0;
            }

            Time end = new Time();
            // Use full date if we've it. Otherwise set the end to one hour
            // after start
            String endStr = extras.getString(EXTRA_END_TIME);
            if (endStr != null) {
                end.parse(endStr);
            } else {
                end.setJulianDay(extras.getInt(EXTRA_DAY));
                end.hour = extras.getInt(EXTRA_START_HOUR) + 1;
                if (end.hour > 23)
                    end.hour = 0;
                end.minute = 0;
            }

            meetingHeader.setText(meetingHeader.getText() + " - " + start.format("%d/%m/%Y"));

            startPicker.setCurrentHour(start.hour);
            startPicker.setCurrentMinute(start.minute);

            endPicker.setCurrentHour(end.hour);
            endPicker.setCurrentMinute(end.minute);

            titleEdit.setText(extras.getString(EXTRA_TITLE));
            nameEdit.setText(extras.getString(EXTRA_CONTACT_NAME));
            emailEdit.setText(extras.getString(EXTRA_CONTACT_EMAIL));
        }
    }
}
