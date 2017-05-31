package com.incarcloud.rooster.support.db.hbase.repository;

import com.incarcloud.rooster.support.db.hbase.entity.Org;

/**
 * OrgHbaseRepository
 *
 * @author Aaric, created on 2017-05-27T13:42.
 * @since 1.0-SNAPSHOT
 */
public interface OrgHbaseRepository {

    void add(Org... orgs) throws Exception;
}
