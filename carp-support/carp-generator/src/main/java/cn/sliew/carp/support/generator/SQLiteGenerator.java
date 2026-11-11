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
package cn.sliew.carp.support.generator;

import cn.sliew.carp.support.generator.helper.MybatisPlusHelper;

public class SQLiteGenerator {

    private final static String AUTHOR = "wangqi";
    private final static String URL = "jdbc:sqlite:/Users/mac/Documents/repository/flowerfine/helm-template/data/data-agent-semantic.db?journal_mode=WAL&synchronous=FULL&busy_timeout=10000&date_class=TEXT";
    private final static String USERNAME = "";
    private final static String PASSWORD = ""; //NOSONAR
    private static final String BASE_PACKAGE = "io.github.malonetalk";
    private static final String MODULE = "carp";

    private final static String SCHEMA = "main";
    /**
     * just add table names here and run the {@link #main(String[])} method.
     */
    private static final String[] TABLES = {"data_sources"};

    public static void main(String[] args) {
        MybatisPlusHelper.generatorSQLite(URL, USERNAME, PASSWORD, SCHEMA, AUTHOR, BASE_PACKAGE, MODULE, TABLES);
    }
}
