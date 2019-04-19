package me.luocaca.pluginapplication.entity;
/**
 * courseOpenId : gbrqan2plyjebi2f1vfkpa
 * courseName : 高等数学三：线性代数
 * studyYear : 2019
 * studyTerm : 春季
 * displayName : 胡彦
 * schedule : 100
 * isPracticeCourse : false
 * completed : 33
 * totalCount : 33
 * hoplinks : /study/html/content/process/?courseOpenId=gbrqan2plyjebi2f1vfkpa&schoolCode=003
 * noStudyNoExam : 0
 * expiredTime : /Date(1556639999000)/
 */

public class Course {

    public String courseOpenId;
    public String courseName;
    public int studyYear;
    public String studyTerm;
    public String displayName;
    public int schedule;
    public boolean isPracticeCourse;
    public int completed;
    public int totalCount;
    public String hoplinks;
    public String noStudyNoExam;
    public String expiredTime;


    @Override
    public String toString() {
        return "Course{" +
                "courseOpenId='" + courseOpenId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", studyYear=" + studyYear +
                ", studyTerm='" + studyTerm + '\'' +
                ", displayName='" + displayName + '\'' +
                ", schedule=" + schedule +
                ", isPracticeCourse=" + isPracticeCourse +
                ", completed=" + completed +
                ", totalCount=" + totalCount +
                ", hoplinks='" + hoplinks + '\'' +
                ", noStudyNoExam='" + noStudyNoExam + '\'' +
                ", expiredTime='" + expiredTime + '\'' +
                '}';
    }
}
