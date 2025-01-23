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
package cn.sliew.carp.module.orca.spinnaker.orca.core.exceptions;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.Collections;
import java.util.Map;

import static java.lang.String.format;

@Slf4j
@Order(Ordered.LOWEST_PRECEDENCE)
public class DefaultExceptionHandler implements ExceptionHandler {

    @Override
    public boolean handles(Exception e) {
        return true;
    }

    @Override
    public ExceptionHandler.Response handle(String taskName, Exception e) {
        Map<String, Object> exceptionDetails =
                ExceptionHandler.responseDetails(
                        "Unexpected Task Failure", Collections.singletonList(e.getMessage()));
        exceptionDetails.put("stackTrace", Throwables.getStackTraceAsString(e));
        log.warn(format("Error occurred during task %s", taskName), e);
        return new ExceptionHandler.Response(
                e.getClass().getSimpleName(), taskName, exceptionDetails, false);
    }
}
