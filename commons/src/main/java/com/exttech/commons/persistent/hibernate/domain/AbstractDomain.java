package com.exttech.commons.persistent.hibernate.domain;

import java.util.Calendar;

/**
 * AbstractDomain for all domain class
 * User: zhangxingyu
 * Email:<a href="mailto:zxysoft@gmail.com">zxysoft@gmail.com</a>
 * Date: 8/8/12
 * Time: 12:52 AM
 */
public abstract class AbstractDomain<PK> {
    /**
     * primary key
     */
    private PK id;

    /**
     * create time
     */
    private Calendar createTime;

    /**
     * update time
     */
    private Calendar updateTime;

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    public Calendar getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Calendar createTime) {
        this.createTime = createTime;
    }

    public Calendar getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Calendar updateTime) {
        this.updateTime = updateTime;
    }
}
