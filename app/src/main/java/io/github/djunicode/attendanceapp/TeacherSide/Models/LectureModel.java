package io.github.djunicode.attendanceapp.TeacherSide.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class LectureModel implements Parcelable {
    private String subject;
    private String startTime;
    private String endTime;
    private String division;
    private String roomNumber;
    private String date;
    private Integer semester;
    private int dayOfTheWeek;
    private SubjectModel subjectModel;

    public LectureModel() {}

    public String getDate() {
        return date;
    }


    public SubjectModel getSubjectModel() {
        return subjectModel;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getDivision() {
        return division;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public void setDayOfTheWeek(int dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public void setSubjectModel(
            SubjectModel subjectModel) {
        this.subjectModel = subjectModel;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    //Parcelable implementation starts here
    protected LectureModel(Parcel in) {
        startTime = in.readString();
        endTime = in.readString();
        division = in.readString();
        roomNumber = in.readString();
        date = in.readString();
        if (in.readByte() == 0) {
            semester = null;
        } else {
            semester = in.readInt();
        }
        dayOfTheWeek = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(startTime);
        dest.writeString(endTime);
        dest.writeString(division);
        dest.writeString(roomNumber);
        dest.writeString(date);
        if (semester == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(semester);
        }
        dest.writeInt(dayOfTheWeek);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<LectureModel> CREATOR = new Creator<LectureModel>() {
        @Override
        public LectureModel createFromParcel(Parcel in) {
            return new LectureModel(in);
        }

        @Override
        public LectureModel[] newArray(int size) {
            return new LectureModel[size];
        }
    };
    //Parcelable implementation ends here

}
