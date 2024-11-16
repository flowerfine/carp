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

import cn.sliew.carp.support.annotation.processor.CarpProcessorPlugin;
import cn.sliew.carp.support.annotation.processor.util.ProcessingEnvUtils;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WebPlugin implements CarpProcessorPlugin {

    private ProcessingEnvironment processingEnv;
    private HandlerWriter handlerWriter;

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        this.handlerWriter = new HandlerWriter(processingEnv);
    }

    @Override
    public Class<? extends Annotation> supported() {
        return RequestHandle.class;
    }

    @Override
    public Collection<JavaFile> process(Set<? extends Element> annotated) {
        return ElementFilter.methodsIn(annotated).stream()
                .collect(Collectors.groupingBy(NameData::new))
                .entrySet()
                .stream()
                .flatMap(entry -> handle(entry.getKey(), entry.getValue()))
                .toList();
    }


    private Stream<JavaFile> handle(NameData nameData, List<ExecutableElement> handlers) {
        return handlers.stream()
                .map(IndexedValue.indexed())
                .map(element -> handle(nameData, element));
    }

    private JavaFile handle(NameData nameData, IndexedValue<ExecutableElement> indexedValue) {
        try {
            return getJavaFile(nameData, indexedValue);
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), indexedValue.value());
            throw e;
        }
    }

    private JavaFile getJavaFile(NameData nameData, IndexedValue<ExecutableElement> indexedValue) {
        var handlerMethod = indexedValue.value();
        var typeSpec = createType(nameData, indexedValue, handlerMethod);
        String packageName = ProcessingEnvUtils.getPackageName(processingEnv, handlerMethod);
        return JavaFile.builder(packageName, typeSpec).build();
    }

    private TypeSpec createType(NameData nameData, IndexedValue<ExecutableElement> indexedValue, ExecutableElement handlerMethod) {
        return handlerWriter.buildHandler(
                nameData.toHandlerMethodName(indexedValue.index()),
                handlerMethod,
                TypeName.get(handlerMethod.getEnclosingElement().asType()),
                handlerMethod.getAnnotation(RequestHandle.class)
        );
    }

    private record NameData(Name controllerName, Name handleName) {
        NameData(ExecutableElement element) {
            this(element.getEnclosingElement().getSimpleName(), element.getSimpleName());
        }

        String toHandlerMethodName(int index) {
            return "%s$%s$%s$handler".formatted(controllerName.toString(), handleName.toString(), index);
        }
    }
}
