package io.github.djunicode.attendanceapp.TeacherSide.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Lecture implements Parcelable {

    private String subjectName;
    private String timing;
    private String classRoomName;
    private String division;
    private String year;
    Integer attendanceTaken,predicted;
    String type;

    public Integer getPredicted() {
        return predicted;
    }

    public Integer getAttendanceTaken() {
        return attendanceTaken;
    }

    public Lecture(String type,String subjectName, String timing, String classRoomName, String division, String year,Integer attendanceTaken,Integer predicted) {
        this.subjectName = subjectName;
        this.type=type;
        this.timing = timing;
        this.classRoomName = classRoomName;
        this.division = division;
        this.year = year;
        this.predicted=predicted;
        this.attendanceTaken=attendanceTaken;
    }

    public String getType() {
        return type;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getTiming() {
        return timing;
    }

    public String getClassRooomName() {
        return classRoomName;
    }

    public String getDivision() { return division; }

    public String getYear() { return year; }

    protected Lecture(Parcel in) {
        subjectName = in.readString();
        timing = in.readString();
        classRoomName = in.readString();
        division = in.readString();
        year = in.readString();
        if (in.readByte() == 0) {
            attendanceTaken = null;
        } else {
            attendanceTaken = in.readInt();
        }
        type = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(subjectName);
        dest.writeString(timing);
        dest.writeString(classRoomName);
        dest.writeString(division);
        dest.writeString(year);
        if (attendanceTaken == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(attendanceTaken);
        }
        dest.writeString(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel in) {
            return new Lecture(in);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };
}
