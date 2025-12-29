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
package cn.sliew.carp.module.cep.service.impl;

import cn.sliew.carp.module.cep.groovy.FunctionInfo;
import cn.sliew.carp.module.cep.groovy.FunctionParameterVisitor;
import cn.sliew.carp.module.cep.service.CarpCepGroovyService;
import cn.sliew.carp.module.cep.service.param.GroovyFunctionParseParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.groovy.ast.ClassNode;
import org.codehaus.groovy.ast.CompileUnit;
import org.codehaus.groovy.control.CompilationUnit;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.codehaus.groovy.control.Phases;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class CarpCepGroovyServiceImpl implements CarpCepGroovyService {

    @Override
    public FunctionInfo parseGroovyScript(GroovyFunctionParseParam param) {
        CompilerConfiguration compiler = new CompilerConfiguration();
        compiler.setTargetBytecode(CompilerConfiguration.JDK8);
        CompilationUnit unit = new CompilationUnit(compiler);
        unit.addSource("script.groovy", param.getScript());
        unit.compile(Phases.SEMANTIC_ANALYSIS);

        CompileUnit ast = unit.getAST();
        if (Objects.nonNull(ast)) {
            for (ClassNode classNode : ast.getClasses()) {
                FunctionParameterVisitor visitor = new FunctionParameterVisitor(ast.getScriptSourceLocation("script.groovy"));
                classNode.visitContents(visitor);
                List<FunctionInfo> functions = visitor.getFunctions();
                if (CollectionUtils.size(functions) == 1) {
                    return functions.get(0);
                }
            }
        }
        throw new RuntimeException("Can't parse groovy script");
    }
}
