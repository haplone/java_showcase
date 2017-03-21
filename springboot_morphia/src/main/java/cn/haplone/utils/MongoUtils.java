package cn.haplone.utils;

import cn.haplone.bean.BaseBean;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Created by z on 17-3-21.
 */
@Service
public class MongoUtils {
    @Value("${mongo.url}")
    private String url;
    @Value("${mongo.default.db.name}")
    private String dbName;

    private MongoClient mongoClient;

    private Morphia morphia;

    @PostConstruct
    public void init(){
        morphia = new Morphia();
        morphia.mapPackage(BaseBean.class.getPackage().getName());
        mongoClient = new MongoClient(new MongoClientURI(url));
    }

    public Datastore open(String dbName){
        Datastore datastore = morphia.createDatastore(mongoClient,dbName);
        datastore.ensureIndexes();
        return datastore;
    }

    /**
     * 默认使用配置的数据库
     * @return
     */
    public Datastore open(){
        return this.open(this.dbName);
    }

}
