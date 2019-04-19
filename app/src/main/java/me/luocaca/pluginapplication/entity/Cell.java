package me.luocaca.pluginapplication.entity;

import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

import me.luocaca.pluginapplication.adapter.DesignAdapter;

public class Cell implements Serializable, MultiItemEntity, IExpandable {
    public static final int type_two = 2;

    /**
     * icon : video
     * title : 马克思主义基本原理 第一讲第一节
     * id : nbufaospglrjhdqvdttgxw
     * sortOrder : 1
     * courseDocId : xtqhaiwlhzte-xxewl05mq
     * lectureId :
     * status : true
     * lastLearned : false
     */


    public String icon;
    public String title;
    public String id;
    public int sortOrder;
    public String courseDocId;
    public String lectureId;
    public boolean status;
    public boolean lastLearned;


    @Override
    public boolean isExpanded() {
        return false;
    }

    @Override
    public void setExpanded(boolean expanded) {

    }

    @Override
    public List<Cell> getSubItems() {
        return null;
    }

    @Override
    public int getLevel() {
        return DesignAdapter.TYPE_LEVEL_1;
    }

    @Override
    public int getItemType() {
        return DesignAdapter.TYPE_LEVEL_1;
    }
}
