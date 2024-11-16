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

package cn.sliew.carp.support.annotation.processor.specs;

import com.palantir.javapoet.CodeBlock;
import com.palantir.javapoet.MethodSpec;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import java.util.List;

public enum GeneratedMethodSpecs {
    ;

    public static MethodSpec generatedOverrideMethodSpec(ExecutableElement overrideMethod, CodeBlock methodBody) {
        return MethodSpec.overriding(overrideMethod)
                .addCode(methodBody)
                .build();
    }

    public static CodeBlock generatedMethodCallSpec(List<? extends VariableElement> parameters, String callObject, ExecutableElement callMethod) {
        var methodCallParams = parameters.stream().map(VariableElement::getSimpleName).map(Name::toString)
                .findFirst()
                .map(__ -> "(arg0)")
                .orElse("()");
        return CodeBlock.builder()
                .add(callObject + ".$L$L", callMethod.getSimpleName().toString(), methodCallParams)
                .build();
    }
}
