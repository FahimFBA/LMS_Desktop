package com.lms.lmsdesktop.faculty;

public class CourseContent {

    // The existing fields and constructors

    public int getCourse_contentSerial() {
        return course_contentSerial;
    }

    public String getCourse_content_date() {
        return course_content_date;
    }

    public String getCourse_content_newOrUpdate() {
        return course_content_newOrUpdate;
    }

    public String getCourse_content_data() {
        return course_content_data;
    }

    public boolean getCourse_send_notification() {
        return course_send_notification;
    }

    public String getCourse_notes() {
        return course_notes;
    }



    private int course_contentSerial;
    private String course_content_date;
    private String course_content_newOrUpdate;
    private String course_content_data;
    private boolean course_send_notification;
    private String course_notes;

    public CourseContent(int course_contentSerial, String course_content_date, String course_content_newOrUpdate, String course_content_data, boolean course_send_notification, String course_notes) {
        this.course_contentSerial = course_contentSerial;
        this.course_content_date = course_content_date;
        this.course_content_newOrUpdate = course_content_newOrUpdate;
        this.course_content_data = course_content_data;
        this.course_send_notification = course_send_notification;
        this.course_notes = course_notes;
    }



    public void setCourse_contentSerial(int course_contentSerial) {
        this.course_contentSerial = course_contentSerial;
    }



    public void setCourse_content_date(String course_content_date) {
        this.course_content_date = course_content_date;
    }



    public void setCourse_content_newOrUpdate(String course_content_newOrUpdate) {
        this.course_content_newOrUpdate = course_content_newOrUpdate;
    }



    public void setCourse_content_data(String course_content_data) {
        this.course_content_data = course_content_data;
    }

    public boolean isCourse_send_notification() {
        return course_send_notification;
    }

    public void setCourse_send_notification(boolean course_send_notification) {
        this.course_send_notification = course_send_notification;
    }



    public void setCourse_notes(String course_notes) {
        this.course_notes = course_notes;
    }


}
