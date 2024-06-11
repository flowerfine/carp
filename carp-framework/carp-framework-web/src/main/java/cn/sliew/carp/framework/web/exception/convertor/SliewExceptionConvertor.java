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

package cn.sliew.carp.framework.web.exception.convertor;

import cn.sliew.carp.framework.common.model.ResponseVO;
import cn.sliew.carp.framework.exception.SliewException;
import cn.sliew.carp.framework.web.util.RequestParamUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

@Slf4j
public class SliewExceptionConvertor implements ExceptionConvertor<SliewException> {

    @Override
    public ResponseVO convert(SliewException exception, HttpServletRequest request, HttpServletResponse response) {
        String params = RequestParamUtil.formatRequestParams(request);
        log.error("{} {} {}", request.getMethod(), request.getRequestURI(), params, exception);
        ResponseVO errorInfo;
        if (StringUtils.hasText(exception.getCode())) {
            errorInfo = ResponseVO.error(exception.getCode(), exception.getMessage());
        } else {
            errorInfo = ResponseVO.error(exception.getMessage());
        }
        return errorInfo;
    }
}
