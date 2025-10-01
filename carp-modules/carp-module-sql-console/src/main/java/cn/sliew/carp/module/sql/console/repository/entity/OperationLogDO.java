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
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Data
@EqualsAndHashCode
@TableName("sql_console_operation_log")
public class OperationLogDO extends BaseAuditDO {

    private static final long serialVersionUID = 1L;

    /**
     * user id
     */
    private Long userId;

    /**
     * Data source connection ID
     */
    private Long dataSourceId;

    /**
     * schema name
     */
    private String schemaName;

    /**
     * DB name
     */
    private String databaseName;

    /**
     * ddl content
     */
    private String ddl;



    /**
     * status
     */
    private String status;

    /**
     * Number of operation lines
     */
    private Long operationRows;

    /**
     * Length of use
     */
    private Long useTime;

    /**
     * Extended Information
     */
    private String extendInfo;
}
