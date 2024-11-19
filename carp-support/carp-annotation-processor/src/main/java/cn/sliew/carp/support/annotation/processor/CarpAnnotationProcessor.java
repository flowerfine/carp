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

package cn.sliew.carp.support.annotation.processor;

import cn.sliew.carp.support.annotation.processor.plugins.web.WebPlugin;
import com.google.auto.common.BasicAnnotationProcessor;
import com.google.auto.service.AutoService;
import com.google.common.collect.Lists;
import com.palantir.javapoet.JavaFile;

import javax.annotation.processing.*;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 参考 lombok.core.AnnotationProcessor
 *
 * @see BasicAnnotationProcessor
 */
@SupportedAnnotationTypes("*")
@AutoService(Processor.class)
public class CarpAnnotationProcessor extends AbstractProcessor {

    private List<CarpProcessorPlugin> registered = Lists.newArrayList();
    private List<CarpProcessorPlugin> active = Lists.newArrayList();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        registered.add(new WebPlugin());
        active = registered.stream().filter(plugin -> plugin.support(processingEnv)).collect(Collectors.toList());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        try {
            processInternal(annotations, roundEnv);
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Exception occurred %s".formatted(e));
        }
        return false;
    }

    protected void processInternal(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        active.stream().map(plugin -> plugin.process(roundEnv.getElementsAnnotatedWith(plugin.supported()), roundEnv))
                .flatMap(Collection::stream)
                .forEach(this::writeFile);
    }

    private void writeFile(JavaFile javaFile) {
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Failed to write definition %s".formatted(javaFile));
        }
    }

}