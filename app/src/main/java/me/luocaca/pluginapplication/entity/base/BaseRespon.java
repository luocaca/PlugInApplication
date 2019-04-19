package me.luocaca.pluginapplication.entity.base;


import java.util.List;

import me.luocaca.pluginapplication.entity.Paging;

public class BaseRespon<T> {
    /**
     * code : 1
     * paging : {"total_count":4,"page_size":8,"page_index":1}
     * orderbys : expiredTime
     * list : [{"courseOpenId":"gbrqan2plyjebi2f1vfkpa","courseName":"高等数学三：线性代数","studyYear":2019,"studyTerm":"春季","displayName":"胡彦","schedule":100,"isPracticeCourse":false,"completed":33,"totalCount":33,"hoplinks":"/study/html/content/process/?courseOpenId=gbrqan2plyjebi2f1vfkpa&schoolCode=003","noStudyNoExam":"0","expiredTime":"/Date(1556639999000)/"},{"courseOpenId":"gbrqan2ppl1jeegaopet9g","courseName":"计算机应用基础","studyYear":2019,"studyTerm":"春季","displayName":"张辉","schedule":100,"isPracticeCourse":false,"completed":36,"totalCount":36,"hoplinks":"/study/html/content/process/?courseOpenId=gbrqan2ppl1jeegaopet9g&schoolCode=003","noStudyNoExam":"0","expiredTime":"/Date(1556639999000)/"},{"courseOpenId":"t-xlan2p0ojkmumysm-plq","courseName":"马克思主义基本原理","studyYear":2019,"studyTerm":"春季","displayName":"葛晓梅","schedule":100,"isPracticeCourse":false,"completed":31,"totalCount":31,"hoplinks":"/study/html/content/process/?courseOpenId=t-xlan2p0ojkmumysm-plq&schoolCode=003","noStudyNoExam":"0","expiredTime":"/Date(1556639999000)/"},{"courseOpenId":"t-xlan2p5lhliwqp9uluuw","courseName":"大学英语（本一）","studyYear":2019,"studyTerm":"春季","displayName":"黄金莲","schedule":100,"isPracticeCourse":false,"completed":35,"totalCount":35,"hoplinks":"/study/html/content/process/?courseOpenId=t-xlan2p5lhliwqp9uluuw&schoolCode=003","noStudyNoExam":"0","expiredTime":"/Date(1556639999000)/"}]
     * IsEducation : true
     */
    public int code;
    public Paging paging;
    public String orderbys;
    public boolean IsEducation;
    public List<T> list;


    public boolean isSucceed() {
        return code == 1;
    }

}
