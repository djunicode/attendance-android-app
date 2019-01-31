package io.github.djunicode.attendanceapp.StudentSide.Models;

public class Subject{

    String subName;
    int outOf, attended;    //null on teacher side
    //Date etc not yet added

    public Subject(String subName, int outOf, int attended) {
        this.subName = subName;
        this.outOf = outOf;
        this.attended = attended;
    }

    public String getSubName() {
        return subName;
    }

    public int getOutOf() {
        return outOf;
    }

    public int getAttended() {
        return attended;
    }
}
