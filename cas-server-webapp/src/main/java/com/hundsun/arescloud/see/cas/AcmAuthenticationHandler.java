/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.hundsun.arescloud.see.cas;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.jasig.cas.adaptors.jdbc.AbstractJdbcUsernamePasswordAuthenticationHandler;
import org.jasig.cas.authentication.handler.AuthenticationException;
import org.jasig.cas.authentication.principal.UsernamePasswordCredentials;

/**
 * acm用户名密码认证处理器.
 *
 * <p>
 *     密码加密方式:1.MD5加密 2.sha512加密,两者满足一种就可以
 * </p>
 *
 * @author zhangxr
 * @version 1.0
 */
public class AcmAuthenticationHandler extends AbstractJdbcUsernamePasswordAuthenticationHandler {

    @Override
    protected final boolean authenticateUsernamePasswordInternal(final UsernamePasswordCredentials credentials) throws AuthenticationException {
        final String username = getPrincipalNameTransformer().transform(credentials.getUsername());
        final String password = credentials.getPassword();

        if (StringUtils.isBlank(username)||StringUtils.isBlank(password)){
            log.warn("username or password is null");
            return false;
        }

        final String dbPassword;
        try{
            dbPassword = getJdbcTemplate().queryForObject("select auth_password from tb_user where login_name = ?", String.class, username);
            if (dbPassword==null){
                return false;
            }
        }catch (org.springframework.dao.EmptyResultDataAccessException e){
            log.info("username not found,username【{}】",username);
            return false;
        }

        return dbPassword.equalsIgnoreCase(DigestUtils.sha512Hex(password))
                || dbPassword.equalsIgnoreCase(DigestUtils.md5Hex(password));

    }
}