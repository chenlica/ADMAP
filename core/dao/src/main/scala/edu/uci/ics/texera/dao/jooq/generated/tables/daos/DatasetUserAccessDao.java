/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * 
 * This file is generated by jOOQ.
 */
package edu.uci.ics.texera.dao.jooq.generated.tables.daos;


import edu.uci.ics.texera.dao.jooq.generated.enums.PrivilegeEnum;
import edu.uci.ics.texera.dao.jooq.generated.tables.DatasetUserAccess;
import edu.uci.ics.texera.dao.jooq.generated.tables.records.DatasetUserAccessRecord;

import java.util.List;

import org.jooq.Configuration;
import org.jooq.Record2;
import org.jooq.impl.DAOImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class DatasetUserAccessDao extends DAOImpl<DatasetUserAccessRecord, edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess, Record2<Integer, Integer>> {

    /**
     * Create a new DatasetUserAccessDao without any configuration
     */
    public DatasetUserAccessDao() {
        super(DatasetUserAccess.DATASET_USER_ACCESS, edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess.class);
    }

    /**
     * Create a new DatasetUserAccessDao with an attached configuration
     */
    public DatasetUserAccessDao(Configuration configuration) {
        super(DatasetUserAccess.DATASET_USER_ACCESS, edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess.class, configuration);
    }

    @Override
    public Record2<Integer, Integer> getId(edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess object) {
        return compositeKeyRecord(object.getDid(), object.getUid());
    }

    /**
     * Fetch records that have <code>did BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess> fetchRangeOfDid(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(DatasetUserAccess.DATASET_USER_ACCESS.DID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>did IN (values)</code>
     */
    public List<edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess> fetchByDid(Integer... values) {
        return fetch(DatasetUserAccess.DATASET_USER_ACCESS.DID, values);
    }

    /**
     * Fetch records that have <code>uid BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess> fetchRangeOfUid(Integer lowerInclusive, Integer upperInclusive) {
        return fetchRange(DatasetUserAccess.DATASET_USER_ACCESS.UID, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>uid IN (values)</code>
     */
    public List<edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess> fetchByUid(Integer... values) {
        return fetch(DatasetUserAccess.DATASET_USER_ACCESS.UID, values);
    }

    /**
     * Fetch records that have <code>privilege BETWEEN lowerInclusive AND
     * upperInclusive</code>
     */
    public List<edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess> fetchRangeOfPrivilege(PrivilegeEnum lowerInclusive, PrivilegeEnum upperInclusive) {
        return fetchRange(DatasetUserAccess.DATASET_USER_ACCESS.PRIVILEGE, lowerInclusive, upperInclusive);
    }

    /**
     * Fetch records that have <code>privilege IN (values)</code>
     */
    public List<edu.uci.ics.texera.dao.jooq.generated.tables.pojos.DatasetUserAccess> fetchByPrivilege(PrivilegeEnum... values) {
        return fetch(DatasetUserAccess.DATASET_USER_ACCESS.PRIVILEGE, values);
    }
}
