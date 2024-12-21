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
package cn.sliew.carp.module.http.sync.job.util;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * 聚水潭开放平台下载 jar 包内的工具类，copy 过来的
 */
public enum ApiUtil {
    ;

    public static String getSign(String signKey, Map<String, String> params) {
        String sortedStr = getSortedParamStr(params);
        String paraStr = signKey + sortedStr;
        return createSign(paraStr);
    }

    public static String getSortedParamStr(Map<String, String> params) {
        Set<String> sortedParams = new TreeSet(params.keySet());
        StringBuilder stringBuilder = new StringBuilder();
        for (String key : sortedParams) {
            if (!"sign".equalsIgnoreCase(key)) {
                String value = params.get(key);
                if (StringUtils.isNotBlank(value)) {
                    stringBuilder.append(key).append(value);
                }
            }
        }
        return stringBuilder.toString();
    }

    public static String createSign(String str) {
        if (str != null && str.length() != 0) {
            char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

            try {
                MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                mdTemp.update(str.getBytes("UTF-8"));
                byte[] md = mdTemp.digest();
                int j = md.length;
                char[] buf = new char[j * 2];
                int k = 0;

                for (int i = 0; i < j; ++i) {
                    byte byte0 = md[i];
                    buf[k++] = hexDigits[byte0 >>> 4 & 15];
                    buf[k++] = hexDigits[byte0 & 15];
                }

                return new String(buf);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            return null;
        }
    }
}
