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

package cn.sliew.carp.module.http.sync.framework.model.internal;

import cn.sliew.carp.module.http.sync.framework.model.Result;
import cn.sliew.carp.module.http.sync.framework.model.SubTask;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProcessResult implements Result {

    private boolean success = false;
    private String message;
    private Throwable throwable;

    private SubTask subTask;

    @Override
    public boolean isSuccess() {
        return success;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getThrowable() {
        return throwable;
    }

    @Override
    public SubTask getSubTask() {
        return subTask;
    }

    public static ProcessResult success(SubTask subTask) {
        ProcessResult result = new ProcessResult();
        result.setSuccess(true);
        result.setSubTask(subTask);
        result.setMessage("success");
        return result;
    }

    public static ProcessResult failure(SubTask subTask) {
        return failure(subTask, "internal error");
    }

    public static ProcessResult failure(SubTask subTask, String message) {
        ProcessResult result = new ProcessResult();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }

    public static ProcessResult failure(SubTask subTask, Throwable throwable) {
        ProcessResult result = new ProcessResult();
        result.setSuccess(false);
        result.setThrowable(throwable);
        result.setMessage(throwable.getMessage());
        return result;
    }

}
