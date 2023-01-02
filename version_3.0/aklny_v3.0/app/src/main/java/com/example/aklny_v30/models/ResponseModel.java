package com.example.aklny_v30.models;

import androidx.annotation.NonNull;

public class ResponseModel {
        private int year;
        private int month;
        private int day;
        private int hour;
        private int minute;
        private int seconds;
        private int milliSeconds;
        private String dateTime;
        private String date;
        private String time;
        private String timeZone;
        private String dayOfWeek;
        private boolean dstActive;

        @NonNull
        @Override
        public String toString() {
                return "response: " + dateTime;
        }

        public int getYear() {return year;}
        public void setYear(int year) {this.year = year;}

        public int getMonth() {return month;}
        public void setMonth(int month) {this.month = month;}

        public int getDay() {return day;}
        public void setDay(int day) {this.day = day;}

        public int getHour() {return hour;}
        public String getDayString() {
                if(day < 10){
                        return "0" + day;
                }
                return  "" + day;
        }public String getMonthString() {
                if(month < 10){
                        return "0" + month;
                }
                return  "" + month;
        }public String getHourString() {
                if(hour < 10){
                        return "0" + hour;
                }
                return  "" + hour;
        }
        public String getMinuteString() {
                if(minute < 10){
                        return "0" + minute;
                }
                return  "" + minute;
        }
        public void setHour(int hour) {this.hour = hour;}

        public int getMinute() {return minute;}
        public void setMinute(int minute) {this.minute = minute;}

        public int getSeconds() {return seconds;}
        public void setSeconds(int seconds) {this.seconds = seconds;}

        public int getMilliSeconds() {return milliSeconds;}
        public void setMilliSeconds(int milliSeconds) {this.milliSeconds = milliSeconds;}

        public String getDateTime() {return dateTime;}
        public void setDateTime(String dateTime) {this.dateTime = dateTime;}

        public String getDate() {return date;}
        public void setDate(String date) {this.date = date;}

        public String getTime() {return time;}
        public void setTime(String time) {this.time = time;}

        public String getTimeZone() {return timeZone;}
        public void setTimeZone(String timeZone) {this.timeZone = timeZone;}

        public String getDayOfWeek() {return dayOfWeek;}
        public void setDayOfWeek(String dayOfWeek) {this.dayOfWeek = dayOfWeek;}

        public boolean isDstActive() {return dstActive;}
        public void setDstActive(boolean dstActive) {this.dstActive = dstActive;}

}