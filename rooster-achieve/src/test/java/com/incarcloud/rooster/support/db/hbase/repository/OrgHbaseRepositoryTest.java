package com.incarcloud.rooster.support.db.hbase.repository;

import com.incarcloud.rooster.support.db.hbase.entity.Org;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * OrgHbaseRepository测试类
 *
 * @author Aaric, created on 2017-05-27T15:47.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class OrgHbaseRepositoryTest {

    @Autowired
    protected OrgHbaseRepository orgHbaseRepository;

    @Test
    public void testCreateOrg() throws Exception {
        orgHbaseRepository.add(new Org(1, 0, "ROOT", "ROOT", 0));
    }
}
