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

package cn.sliew.carp.support.annotation.processor.plugins.web;

import cn.sliew.carp.support.annotation.processor.specs.GeneratedMethodSpecs;
import cn.sliew.carp.support.annotation.processor.util.ElementUtil;
import com.palantir.javapoet.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Objects;

class HandlerWriter {

    private static final String CONTROLLER = "controller";

    private ExecutableElement httpMethodElement;
    private ExecutableElement produceElement;
    private ExecutableElement processElement;
    private ExecutableElement pathElement;
    private Types typeUtils;
    private TypeMirror requestType;

    HandlerWriter(ProcessingEnvironment processingEnv) {
        this.typeUtils = processingEnv.getTypeUtils();
        var handlerInterfaceElement = processingEnv.getElementUtils().getTypeElement(RequestHandler.class.getCanonicalName());
        if (Objects.nonNull(handlerInterfaceElement)) {
            this.httpMethodElement = ElementUtil.getMethodElement(handlerInterfaceElement, "method");
            this.produceElement = ElementUtil.getMethodElement(handlerInterfaceElement, "produce");
            this.processElement = ElementUtil.getMethodElement(handlerInterfaceElement, "process");
            this.pathElement = ElementUtil.getMethodElement(handlerInterfaceElement, "path");
            this.requestType = processingEnv.getElementUtils()
                    .getTypeElement(Request.class.getCanonicalName())
                    .asType();
        }
    }

    private static MethodSpec constructor(TypeName typeName) {
        return MethodSpec.constructorBuilder()
                .addParameter(ParameterSpec.builder(typeName, CONTROLLER).build())
                .addCode("this.%s = %s;".formatted(CONTROLLER, CONTROLLER))
                .build();
    }

    TypeSpec buildHandler(String handlerMethodName, ExecutableElement handler, TypeName typeName, RequestHandle annotation) {
        return TypeSpec.classBuilder(handlerMethodName)
                .addField(FieldSpec.builder(typeName, CONTROLLER, Modifier.FINAL, Modifier.PRIVATE).build())
                .addMethod(constructor(typeName))
//                .addAnnotation(Generated.class)
                .addSuperinterface(TypeName.get(RequestHandler.class))
                .addModifiers(Modifier.FINAL)
                .addMethods(List.of(
                        produce(annotation.produce()),
                        path(annotation.value()),
                        method(annotation.method()),
                        process(handler)
                ))
                .build();
    }

    private MethodSpec method(HttpMethod httpMethod) {
        return GeneratedMethodSpecs.generatedOverrideMethodSpec(httpMethodElement,
                CodeBlock.builder()
                        .add("return $T.$L;", httpMethod.getClass(), httpMethod.toString())
                        .build()
        );
    }

    private MethodSpec path(String value) {
        return GeneratedMethodSpecs.generatedOverrideMethodSpec(pathElement,
                CodeBlock.builder()
                        .add("return $S;", value)
                        .build()
        );
    }

    private MethodSpec produce(String produce) {
        return GeneratedMethodSpecs.generatedOverrideMethodSpec(produceElement,
                CodeBlock.builder()
                        .add("return $S;", produce)
                        .build()
        );
    }

    private MethodSpec process(ExecutableElement handlerMethod) {
        var controllerCall = controllerCall(handlerMethod, handlerMethod.getParameters());
        TypeKind handlerMethodType = handlerMethod.getReturnType().getKind();
        return handlerMethodType == TypeKind.VOID
                ? voidMethod(controllerCall)
                : valueReturningMethod(controllerCall);
    }

    private MethodSpec valueReturningMethod(CodeBlock controllerCall) {
        return MethodSpec.overriding(processElement)
                .addStatement("return $L", controllerCall)
                .build();
    }

    private MethodSpec voidMethod(CodeBlock controllerCall) {
        return MethodSpec.overriding(processElement)
                .addStatement(controllerCall)
                .addStatement("return $T.noContent()", Response.class)
                .build();
    }

    private CodeBlock controllerCall(ExecutableElement handlerMethod, List<? extends VariableElement> parameters) {
        if (parameters.size() > 1 || !doesParamTypesMatchRequest(parameters)) {
            throw new RuntimeException("Too many parameters or type of param is not Request");
        }
        return GeneratedMethodSpecs.generatedMethodCallSpec(parameters, CONTROLLER, handlerMethod);
    }

    private boolean doesParamTypesMatchRequest(List<? extends VariableElement> parameters) {
        var requestType = this.requestType;
        return parameters.stream().map(VariableElement::asType)
                .allMatch(paramType -> typeUtils.isSameType(paramType, requestType));
    }
}