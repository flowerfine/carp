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
package cn.sliew.carp.module.security.core.util;

import cn.hutool.crypto.digest.DigestUtil;

public enum PasswordUtil {
    ;

    private static final int DIGEST_SIZE = 3;

    public static Boolean checkPassword(String loginPassword, String password, String salt) {
        String digestPassword = digestPassword(loginPassword, salt);
        return password.equals(digestPassword);
    }

    public static String digestPassword(String password, String salt) {
        String digestPassword = password;
        for (int i = 0; i < DIGEST_SIZE; i++) {
            digestPassword = doDigestPassword(digestPassword, salt);
        }
        return digestPassword;
    }

    private static String doDigestPassword(String password, String salt) {
        String saltPassword = password + ":" + salt;
        return DigestUtil.md5Hex(saltPassword);
    }


}
