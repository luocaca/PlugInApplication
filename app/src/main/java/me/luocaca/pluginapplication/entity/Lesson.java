package me.luocaca.pluginapplication.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

import me.luocaca.pluginapplication.adapter.DesignAdapter;

public class Lesson extends AbstractExpandableItem<Cell> implements Serializable, MultiItemEntity, IExpandable<Cell> {



    /**
     * id : jhufaospxalafdrqufchag
     * title : 马克思主义产生的时代背景
     * sortOrder : 1
     * status : 2
     * cells : [{"icon":"video","title":"马克思主义基本原理 第一讲第一节","id":"nbufaospglrjhdqvdttgxw","sortOrder":1,"courseDocId":"xtqhaiwlhzte-xxewl05mq","lectureId":"","status":true,"lastLearned":false},{"icon":"doc","title":"马基原理（函授、业余）教学大纲","id":"nbufaosp0pxprumxy2umca","sortOrder":2,"courseDocId":"uboiaiwltzfl7qluiw4awq","lectureId":"","status":true,"lastLearned":false},{"icon":"doc","title":"《马克思主义基本原理概论》课程说明","id":"nbufaosps65gncbavi-wow","sortOrder":3,"courseDocId":"uboiaiwl8bhiehhguou1zg","lectureId":"","status":true,"lastLearned":false}]
     */

    public String id;
    public String title;
    public int sortOrder;
    public int status;
    public List<Cell> cells;

    public boolean isExpand = false;


    @Override
    public boolean isExpanded() {
        return isExpand;
    }

    @Override
    public void setExpanded(boolean expanded) {

        this.isExpand = expanded;
    }

    @Override
    public List<Cell> getSubItems() {
        return cells;
    }

    @Override
    public int getLevel() {
        return DesignAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getItemType() {
        return DesignAdapter.TYPE_LEVEL_0;
    }
}
