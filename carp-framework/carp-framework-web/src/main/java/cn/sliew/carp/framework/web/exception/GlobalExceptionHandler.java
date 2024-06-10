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

package cn.sliew.carp.framework.web.exception;

import cn.sliew.carp.framework.common.enums.ResponseCodeEnum;
import cn.sliew.carp.framework.common.model.ResponseVO;
import cn.sliew.carp.framework.exception.SliewException;
import cn.sliew.carp.framework.web.util.RequestParamUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * springmvc request param convert to request mapping param exception
     */
    @ResponseBody
    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ResponseVO> conversionFailed(ConversionFailedException e,
                                                       HandlerMethod method,
                                                       HttpServletRequest request,
                                                       HttpServletResponse response) {
        String params = RequestParamUtil.formatRequestParams(request);
        log.error("{} {} {}", SecurityUtil.getCurrentUserName(), request.getMethod(), request.getRequestURI(), params, e);
        final TypeDescriptor sourceType = e.getSourceType();
        final TypeDescriptor targetType = e.getTargetType();
        final Object value = e.getValue();
        ResponseVO errorInfo = ResponseVO.error(String.format("springmvc convert %s from %s to %s error",
                value, sourceType.getName(), targetType.getName()));
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ResponseVO> bindException(BindException e,
                                                    HandlerMethod method,
                                                    HttpServletRequest request,
                                                    HttpServletResponse response) {
        String params = RequestParamUtil.formatRequestParams(request);
        log.error("[{}] {} {} {}", SecurityUtil.getCurrentUserName(), request.getMethod(), request.getRequestURI(), params, e);
        StringBuilder sb = new StringBuilder();
        for (FieldError fieldError : e.getFieldErrors()) {
            String message = String.format("server reject [%s] value [%s] with rules: %s;",
                    fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
            sb.append(message);
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        ResponseVO errorInfo = ResponseVO.error(sb.toString());
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(SliewException.class)
    public ResponseEntity<ResponseVO> custom(SliewException e,
                                             HandlerMethod method,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        String params = RequestParamUtil.formatRequestParams(request);
        log.error("[{}] {} {} {}", SecurityUtil.getCurrentUserName(), request.getMethod(), request.getRequestURI(), params, e);
        ResponseVO errorInfo;
        if (StringUtils.hasText(e.getCode())) {
            errorInfo = ResponseVO.error(e.getCode(), e.getMessage());
        } else {
            errorInfo = ResponseVO.error(e.getMessage());
        }
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseVO> exception(Exception e,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        String params = RequestParamUtil.formatRequestParams(request);
        log.error("[{}] {} {} {}", SecurityUtil.getCurrentUserName(), request.getMethod(), request.getRequestURI(), params, e);
        ResponseVO errorInfo = ResponseVO.error(I18nUtil.get(ResponseCodeEnum.ERROR.getValue()));
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseVO> accessDenied(AccessDeniedException e,
                                                   HandlerMethod method,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        String params = RequestParamUtil.formatRequestParams(request);
        log.error("[{}] {} {} {}", SecurityUtil.getCurrentUserName(), request.getMethod(), request.getRequestURI(), params, e);
        ResponseVO errorInfo = ResponseVO.error(ResponseCodeEnum.ERROR_NO_PRIVILEGE.getCode(),
                I18nUtil.get(ResponseCodeEnum.ERROR_NO_PRIVILEGE.getValue()));
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

    /**
     * primary key duplicate
     */
    @ResponseBody
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ResponseVO> duplicateKey(DuplicateKeyException e,
                                                   HandlerMethod method,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) {
        String params = RequestParamUtil.formatRequestParams(request);
        log.error("[{}] {} {} {}", SecurityUtil.getCurrentUserName(), request.getMethod(), request.getRequestURI(), params, e);
        ResponseVO errorInfo = ResponseVO.error(ResponseCodeEnum.ERROR_DUPLICATE_DATA.getCode(),
                I18nUtil.get(ResponseCodeEnum.ERROR_DUPLICATE_DATA.getValue()));
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

    @ResponseBody
    @ExceptionHandler(MailException.class)
    public ResponseEntity<ResponseVO> mail(MailException e,
                                           HandlerMethod method,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        String params = RequestParamUtil.formatRequestParams(request);
        log.error("[{}] {} {} {}", SecurityUtil.getCurrentUserName(), request.getMethod(), request.getRequestURI(), params, e);
        ResponseVO errorInfo = ResponseVO.error(ResponseCodeEnum.ERROR_EMAIL.getCode(),
                I18nUtil.get(ResponseCodeEnum.ERROR_EMAIL.getValue()));
        return new ResponseEntity<>(errorInfo, HttpStatus.OK);
    }

}
