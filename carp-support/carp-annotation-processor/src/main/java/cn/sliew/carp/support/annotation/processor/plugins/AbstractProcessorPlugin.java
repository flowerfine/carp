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

package cn.sliew.carp.support.annotation.processor.plugins;

import cn.sliew.carp.support.annotation.processor.CarpProcessorPlugin;
import com.palantir.javapoet.JavaFile;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 生成一个新的类，实现特定接口
 */
public abstract class AbstractProcessorPlugin implements CarpProcessorPlugin {

    protected ProcessingEnvironment processingEnv;

    @Override
    public boolean support(ProcessingEnvironment processingEnv) {
        this.processingEnv = processingEnv;
        return false;
    }

    public abstract Class<? extends Annotation> supported();

    @Override
    public Collection<JavaFile> process(Set<? extends Element> annotated, RoundEnvironment roundEnv) {
        Map<ElementMethod, List<ExecutableElement>> nameDataListMap = ElementFilter
                .methodsIn(annotated)
                .stream()
                .collect(Collectors.groupingBy(ElementMethod::new));

        return nameDataListMap
                .entrySet()
                .stream()
                .flatMap(entry -> handle(entry.getKey(), entry.getValue()))
                .toList();
    }


    private Stream<JavaFile> handle(ElementMethod elementMethod, List<ExecutableElement> handlers) {
        return handlers.stream()
                .map(IndexedValue.indexed())
                .map(element -> handle(elementMethod, element));
    }

    private JavaFile handle(ElementMethod elementMethod, IndexedValue<ExecutableElement> indexedValue) {
        try {
            return getJavaFile(elementMethod, indexedValue);
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage(), indexedValue.value());
            throw e;
        }
    }

    protected abstract JavaFile getJavaFile(ElementMethod elementMethod, IndexedValue<ExecutableElement> indexedValue);

    protected void log(String msg) {
        if (processingEnv.getOptions().containsKey("debug")) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, msg);
        }
    }

    protected void error(String msg, Element element) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, element);
    }

    protected void error(String msg, Element element, AnnotationMirror annotation) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, msg, element, annotation);
    }

    protected void fatalError(Exception e) {
        // We don't allow exceptions of any kind to propagate to the compiler
        StringWriter writer = new StringWriter();
        e.printStackTrace(new PrintWriter(writer));
        fatalError(writer.toString());
    }

    protected void fatalError(String msg) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR: " + msg);
    }
}
