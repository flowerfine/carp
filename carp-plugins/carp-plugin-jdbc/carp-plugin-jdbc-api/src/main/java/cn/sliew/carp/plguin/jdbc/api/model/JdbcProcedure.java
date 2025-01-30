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
public class JdbcProcedure {

    @JsonAlias({"PROCEDURE_CAT"})
    private String catalog;

    @JsonAlias({"PROCEDURE_SCHEM"})
    private String schema;

    @JsonAlias({"PROCEDURE_NAME"})
    private String procedure;

    @JsonAlias({"reserved1"})
    private String reserved1;

    @JsonAlias({"reserved2"})
    private String reserved2;

    @JsonAlias({"reserved3"})
    private String reserved3;

    @JsonAlias({"REMARKS"})
    private String comment;

    @JsonAlias({"PROCEDURE_TYPE"})
    private Integer type;

    @JsonAlias({"SPECIFIC_NAME"})
    private String specificName;
}
