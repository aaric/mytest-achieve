package com.incarcloud.rooster.support.db.hbase.repository.impl;

import com.incarcloud.rooster.support.abs.AbstractHbaseRepositoryObject;
import com.incarcloud.rooster.support.db.hbase.entity.Org;
import com.incarcloud.rooster.support.db.hbase.repository.OrgHbaseRepository;
import com.incarcloud.rooster.support.util.HbaseUtils;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * OrgHbaseRepositoryImpl
 *
 * @author Aaric, created on 2017-05-27T14:17.
 * @since 1.0-SNAPSHOT
 */
@Repository
public class OrgHbaseRepositoryImpl extends AbstractHbaseRepositoryObject implements OrgHbaseRepository {

    @Override
    public void add(Org... orgs) throws Exception {
        if(null == orgs || 0 == orgs.length) {
            throw new IllegalArgumentException("orgs is null or the length is 0.");
        }
        Put put;
        List<Put> putList = new ArrayList<>();
        for (Org org: orgs) {
            put = new Put(Bytes.toBytes(HbaseUtils.generatePrimaryKey(org.getId())));
            put.addColumn(Bytes.toBytes(Org.FAMILY_NAME), Bytes.toBytes("id"), Bytes.toBytes(org.getId()));
            put.addColumn(Bytes.toBytes(Org.FAMILY_NAME), Bytes.toBytes("parentId"), Bytes.toBytes(org.getParentId()));
            put.addColumn(Bytes.toBytes(Org.FAMILY_NAME), Bytes.toBytes("name"), Bytes.toBytes(org.getName()));
            put.addColumn(Bytes.toBytes(Org.FAMILY_NAME), Bytes.toBytes("code"), Bytes.toBytes(org.getCode()));
            put.addColumn(Bytes.toBytes(Org.FAMILY_NAME), Bytes.toBytes("type"), Bytes.toBytes(org.getType()));
            putList.add(put);
        }
        getTable(Org.TABLE_NAME).put(putList);
    }
}
