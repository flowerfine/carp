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
package cn.sliew.carp.plguin.jdbc.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class JdbcColumn {

    @JsonAlias({"TABLE_CAT", "table_cat"})
    private String catalog;

    @JsonAlias({"TABLE_SCHEM", "table_schem"})
    private String schema;

    @JsonAlias({"TABLE_NAME", "table_name"})
    private String table;

    @JsonAlias({"COLUMN_NAME", "column_name"})
    private String column;

    @JsonAlias({"DATA_TYPE", "data_type"})
    private Integer dataType;

    @JsonAlias({"TYPE_NAME", "type_name"})
    private String typeName;

    @JsonAlias({"COLUMN_SIZE", "column_size"})
    private Integer columnSize;

    @JsonAlias({"BUFFER_LENGTH"})
    private Integer bufferLength;

    @JsonAlias({"DECIMAL_DIGITS", "decimal_digits"})
    private Integer decimalDigits;

    /**
     * Radix (typically either 10 or 2)
     */
    @JsonAlias({"NUM_PREC_RADIX", "num_prec_radix"})
    private Integer numPrecRadix;

    @JsonAlias({"NULLABLE", "nullable"})
    private Integer nullable;

    @JsonAlias({"REMARKS", "remarks"})
    private String comment;

    @JsonAlias({"COLUMN_DEF", "column_def"})
    private String defaultValue;

    @JsonAlias({"SQL_DATA_TYPE"})
    private Integer sqlDataType;

    @JsonAlias({"SQL_DATETIME_SUB"})
    private Integer sqlDatetimeSub;

    /**
     * char 类型最大长度
     */
    @JsonAlias({"CHAR_OCTET_LENGTH"})
    private Integer charOctetLength;

    /**
     * 字段顺序，从 1 开始
     */
    @JsonAlias({"ORDINAL_POSITION"})
    private Integer ordinalPosition;

    /**
     * YES 或者 NO
     */
    @JsonAlias({"IS_NULLABLE"})
    private String isNullable;

    @JsonAlias({"SCOPE_CATALOG"})
    private String scopeCatalog;

    @JsonAlias({"SCOPE_SCHEMA"})
    private String scopeSchema;

    @JsonAlias({"SCOPE_TABLE"})
    private String scopeTable;

    @JsonAlias({"SOURCE_DATA_TYPE"})
    private Integer sourceDataType;

    /**
     * YES 或者 NO
     */
    @JsonAlias({"IS_AUTOINCREMENT"})
    private String isAutoincrement;

    /**
     * 是否是生成列。YES 或者 NO
     */
    @JsonAlias({"IS_GENERATEDCOLUMN"})
    private String isGeneratedcolumn;
}
