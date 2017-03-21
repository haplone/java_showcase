package cn.haplone.dao;

import cn.haplone.bean.BaseBean;
import cn.haplone.utils.MongoUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by z on 17-3-21.
 */
@Component
public class MongoPersistence {
    private Datastore datastore;
    @Autowired
    private MongoUtils mongoUtils;

    @PostConstruct
    public void init() {
        this.datastore = mongoUtils.open();
    }

    public <E extends BaseBean> ObjectId save(E entity) {
        datastore.save(entity);
        return entity.getId();
    }

    public <E extends BaseBean> long count(Class<E> clazz) {
        assert clazz != null : "clazz 不能为空";
        return datastore.find(clazz).count();
    }

    public <E extends BaseBean> E get(Class<E> clazz, final ObjectId id) {
        assert clazz != null && id != null;
        return datastore.find(clazz).field("id").equal(id).get();
    }

    public <E extends BaseBean> E get(Class<E> clazz, Map<String, Object> filters) {
        assert clazz != null;

        Query<E> query = datastore.find(clazz);
        Set<String> keys = filters.keySet();
        Iterator<String> keyInterator = keys.iterator();
        while (keyInterator.hasNext()) {
            String key = keyInterator.next();
            query = query.filter(key, filters.get(key));
        }

        return query.get();
    }

    public <E extends BaseBean> List<E> getBy(Class<E> clazz, Map<String, ?> filters) {
        assert clazz != null;

        Query<E> query = datastore.find(clazz);
        if (filters != null) {
            Set<String> keys = filters.keySet();
            Iterator<String> keyInterator = keys.iterator();
            while (keyInterator.hasNext()) {
                String key = keyInterator.next();
                query = query.filter(key, filters.get(key));
            }
        }

        return query.asList();
    }

    public <E extends BaseBean> void empty(Class<E> clazz) {
        assert clazz != null;

        datastore.delete(datastore.createQuery(clazz));
    }

    public <E extends BaseBean> boolean exists(Class<E> clazz, Map<String, ?> filters) {
        List<E> list = this.getBy(clazz, filters);
        return list != null && list.size() > 0;
    }
}
