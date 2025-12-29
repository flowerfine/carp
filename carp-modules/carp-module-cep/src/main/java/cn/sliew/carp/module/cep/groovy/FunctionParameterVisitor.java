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
package cn.sliew.carp.module.cep.groovy;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.ast.ClassCodeVisitorSupport;
import org.codehaus.groovy.ast.MethodNode;
import org.codehaus.groovy.ast.Parameter;
import org.codehaus.groovy.ast.expr.*;
import org.codehaus.groovy.control.SourceUnit;

import java.util.ArrayList;
import java.util.List;

public class FunctionParameterVisitor extends ClassCodeVisitorSupport {

    private final SourceUnit sourceUnit;
    @Getter
    private final List<FunctionInfo> functions;

    public FunctionParameterVisitor(SourceUnit sourceUnit) {
        this.sourceUnit = sourceUnit;
        this.functions = new ArrayList<>();
    }

    @Override
    public void visitMethod(MethodNode node) {
        if (StringUtils.startsWith(node.getName(), "$")) {
            return;
        }
        if (StringUtils.equalsAny(node.getName(), "main", "run")) {
            return;
        }

        FunctionInfo functionInfo = new FunctionInfo();
        functionInfo.setName(node.getName());
        functionInfo.setIsPublic(node.isPublic());
        functionInfo.setReturnType(node.getReturnType().getTypeClass());
        List<ParamInfo> paramInfos = new ArrayList<>();
        functionInfo.setParams(paramInfos);

        for (Parameter parameter : node.getParameters()) {
            ParamInfo paramInfo = new ParamInfo();
            paramInfo.setName(parameter.getName());
            paramInfo.setType(parameter.getType().getTypeClass());
            if (parameter.hasInitialExpression()) {
                Object defaultValue = extractExpressionValue(parameter.getInitialExpression());
                paramInfo.setDefaultValue(defaultValue);
            }
            paramInfos.add(paramInfo);
        }
        functions.add(functionInfo);
        super.visitMethod(node);
    }

    private Object extractExpressionValue(Expression expr) {
        if (expr == null) {
            return null;
        }

        if (expr instanceof ConstantExpression) {
            return ((ConstantExpression) expr).getValue();
        } else if (expr instanceof VariableExpression) {
            return ((VariableExpression) expr).getName();
        } else if (expr instanceof PropertyExpression) {
            PropertyExpression prop = (PropertyExpression) expr;
            return extractExpressionValue(prop.getObjectExpression())
                    + "." + prop.getPropertyAsString();
        } else if (expr instanceof MethodCallExpression) {
            MethodCallExpression methodCall = (MethodCallExpression) expr;
            return extractExpressionValue(methodCall.getMethod()) + "()";
        } else if (expr instanceof ClosureExpression) {
            return "{ ... }";
        } else if (expr instanceof ListExpression) {
            return "[...]";
        } else if (expr instanceof MapExpression) {
            return "{...}";
        } else if (expr instanceof BinaryExpression) {
            BinaryExpression binary = (BinaryExpression) expr;
            return extractExpressionValue(binary.getLeftExpression())
                    + " " + binary.getOperation().getText()
                    + " " + extractExpressionValue(binary.getRightExpression());
        }

        return expr.getText();
    }

    @Override
    protected SourceUnit getSourceUnit() {
        return sourceUnit;
    }
}
