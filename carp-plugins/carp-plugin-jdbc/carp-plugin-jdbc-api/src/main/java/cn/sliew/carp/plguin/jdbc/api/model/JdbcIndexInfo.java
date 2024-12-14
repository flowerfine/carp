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
 *
 */

package cn.sliew.carp.plguin.jdbc.api.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class JdbcIndexInfo {

    @JsonAlias({"TABLE_CAT", "table_cat"})
    private String catalog;

    @JsonAlias({"TABLE_SCHEM", "table_schem"})
    private String schema;

    @JsonAlias({"TABLE_NAME", "table_name"})
    private String table;

    @JsonAlias({"COLUMN_NAME"})
    private String column;

    @JsonAlias({"NON_UNIQUE"})
    private Boolean nonUnique;

    @JsonAlias({"INDEX_QUALIFIER"})
    private String indexQualifier;

    @JsonAlias({"INDEX_NAME"})
    private String index;

    @JsonAlias({"TYPE"})
    private Integer type;

    @JsonAlias({"ORDINAL_POSITION"})
    private Integer ordinalPosition;

    @JsonAlias({"ASC_OR_DESC"})
    private String ascOrDesc;

    @JsonAlias({"CARDINALITY"})
    private Integer cardinality;

    @JsonAlias({"PAGES"})
    private Long pages;

    @JsonAlias({"FILTER_CONDITION"})
    private String filterCondition;
}
