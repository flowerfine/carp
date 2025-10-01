/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.sliew.carp.module.sql.console.repository.entity;

import cn.sliew.carp.framework.mybatis.entity.BaseAuditDO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode
@TableName("sql_console_data_source")
public class DataSourceDO extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    /**
     * user id
     */
    private Long userId;

    /**
     * Database type
     */
    private String type;


    /**
     * Alias
     */
    private String alias;

    /**
     * connection address
     */
    private String url;

    /**
     * userName
     */
    private String userName;

    /**
     * password
     */
    private String password;

    /**
     * host address
     */
    private String host;

    /**
     * port
     */
    private String port;

    /**
     * ssh configuration information json
     */
    private String ssh;

    /**
     * ssl configuration information json
     */
    private String ssl;

    /**
     * sid
     */
    private String sid;

    /**
     * driver information
     */
    private String driver;

    /**
     * jdbc version
     */
    private String jdbc;

    /**
     * Custom extension field json
     */
    private String extendInfo;

    /**
     * driver_config configuration
     */
    private String driverConfig;

    /**
     * Connection Type
     */
    private String kind;

    /**
     * service name
     */
    private String serviceName;

    /**
     * Service type
     */
    private String serviceType;
}
