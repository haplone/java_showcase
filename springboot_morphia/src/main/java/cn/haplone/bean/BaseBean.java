package cn.haplone.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.PrePersist;
import org.mongodb.morphia.annotations.Version;

import java.util.Date;

/**
 * Created by z on 17-3-21.
 */
public abstract class BaseBean {

    @Id
    protected ObjectId id;

    protected Date creationDate;
    protected Date lastChange;

    @Version
    private long version;

    public ObjectId getId() {
        return id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public Date getLastChange() {
        return lastChange;
    }

    @PrePersist
    public void prePersist(){
        this.creationDate = ( creationDate==null)? new Date():creationDate;
        this.lastChange = (lastChange==null)? new Date() : lastChange;
    }

    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }
}
