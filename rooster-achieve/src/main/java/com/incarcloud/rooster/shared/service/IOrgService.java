package com.incarcloud.rooster.shared.service;

import com.incarcloud.rooster.shared.result.IOrg;

import java.util.List;

/**
 * IOrgService
 *
 * @author Aaric, created on 2017-05-27T11:45.
 * @since 1.0-SNAPSHOT
 */
public interface IOrgService {

    IOrg createOrg(IOrg org, Integer systemId, Integer operateUserId) throws Exception;
    void removeOrg(Integer orgId, Integer systemId, Integer operateUserId) throws Exception;
    List<IOrg> getTreeList(Integer systemId, Integer operateUserId) throws Exception;
}
