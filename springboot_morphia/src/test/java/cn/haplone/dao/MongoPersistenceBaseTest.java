package cn.haplone.dao;

import cn.haplone.App;
import cn.haplone.bean.AddressBean;
import cn.haplone.bean.UserBean;
import com.mongodb.DuplicateKeyException;
import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNull;

/**
 * Created by z on 17-3-21.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MongoPersistenceBaseTest {
    @Autowired
    private ApplicationContext ctx;
    @Autowired
    private MongoPersistence dao;

    private UserBean user;

    @Before
    public void initUser() {
        dao.empty(UserBean.class);

        user = new UserBean();
        user.setFirstName("san");
        user.setSurname("zhang");
        user.setEmail("za@tech.cn");
        user.setSecret("this is secret");
        user.setAge(25);

        AddressBean address = new AddressBean();
        address.setStreet("yinhe soho");
        address.setZip("100100");
        user.setAddress(address);

        dao.save(user);
    }

    @Test
    public void save() {
        assertNotNull("user id 应该不为空", user.getId());
        assertEquals("只有一条记录", 1, dao.count(UserBean.class));
    }

    @Test
    public void update() {
        UserBean modifier = new UserBean();
        modifier.setId(new ObjectId(user.getId().toHexString()));
        modifier.setAge(22);
        modifier.setFirstName("jack");
        dao.save(modifier);

        UserBean persistent = dao.get(UserBean.class, new ObjectId(user.getId().toHexString()));
        assertEquals("修改 int", 22, persistent.getAge());
        assertEquals("修改string", "jack", persistent.getFirstName());
        assertNull("修改时未填充字段为空", persistent.getSurname());
    }

    @Test(expected = DuplicateKeyException.class)
    public void checkUniqueBySave() {
        UserBean folkUser = new UserBean();
        folkUser.setEmail("za@tech.cn");

        dao.save(folkUser);
    }

    @Test
    public void getOne() {
        Map map = new HashMap();
        map.put("email =", "za@tech.cn");

        UserBean persitent = dao.get(UserBean.class, map);
        assertNotNull("数据读取失败", persitent);

        assertEquals("获取数据库中字段一只", "san", persitent.getFirstName());
        assertNull(" @Transient 字段不能读出数据", persitent.getSecret());
        assertEquals("@Embedded数据读取", "100100", persitent.getAddress().getZip());
    }

    @Test
    public void getNone() {
        Map map = new HashMap();
        map.put("email =", "zda@tech.cn");

        UserBean persitent = dao.get(UserBean.class, map);
        assertNull("数据读取失败", persitent);

    }

    @Test
    public void count() {
        assertEquals("数据只读取到一条", 1, dao.count(UserBean.class));
    }

    @Test
    public void getAll() {
        List<UserBean> list = dao.getBy(UserBean.class, null);
        assertEquals("无条件查询", 1, list.size());

        batchSave();
        list = dao.getBy(UserBean.class, null);
        assertEquals("无条件查询", 4, list.size());

    }

    @Test
    public void getList() {
        batchSave();

        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("address.zip =", "100100");
        List<UserBean> list = dao.getBy(UserBean.class, filters);
        assertEquals("等值查询", 4, list.size());

        filters = new HashMap<String, Object>();
        filters.put("firstName =", "san");
        list = dao.getBy(UserBean.class, filters);
        assertEquals("等值查询", 2, list.size());


        filters = new HashMap<String, Object>();
        filters.put("age >", 22);
        list = dao.getBy(UserBean.class, filters);
        assertEquals("匹配条件--大于", 2, list.size());


        filters = new HashMap<String, Object>();
        filters.put("firstName =", "san");
        filters.put("age >", 22);
        list = dao.getBy(UserBean.class, filters);
        assertEquals("等值查询", 1, list.size());

    }

    @Test
    public void existed() {
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("address.zip =", "100100");
        Assert.assertTrue("existed 存在", dao.exists(UserBean.class, filters));
    }

    @Test
    public void notExisted() {
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("age <", 5);
        Assert.assertFalse("existed 不存在", dao.exists(UserBean.class, filters));
    }

    protected void batchSave() {
        UserBean user1 = new UserBean();
        user1.setFirstName("si");
        user1.setSurname("li");
        user1.setEmail("ls@tech.cn");
        user1.setSecret("this is secret");
        user1.setAge(20);

        AddressBean address = new AddressBean();
        address.setStreet("yinhe soho");
        address.setZip("100100");
        user1.setAddress(address);

        dao.save(user1);

        UserBean user2 = new UserBean();
        user2.setFirstName("wu");
        user2.setSurname("wang");
        user2.setEmail("ww@tech.cn");
        user2.setSecret("this is secret");
        user2.setAge(50);

        address = new AddressBean();
        address.setStreet("yinhe soho");
        address.setZip("100100");
        user2.setAddress(address);

        dao.save(user2);


        UserBean user3 = new UserBean();
        user3.setFirstName("san");
        user3.setSurname("zhang");
        user3.setEmail("za2@tech.cn");
        user3.setSecret("this is secret");

        address = new AddressBean();
        address.setStreet("yinhe soho");
        address.setZip("100100");
        user3.setAddress(address);

        dao.save(user3);
    }


}
