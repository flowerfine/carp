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

import com.google.auto.common.BasicAnnotationProcessor;
import com.palantir.javapoet.JavaFile;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;

/**
 * 参考 lombok.core.AnnotationProcessor.ProcessorDescriptor
 * @see BasicAnnotationProcessor.Step
 */
public interface CarpProcessorPlugin {

    boolean support(ProcessingEnvironment processingEnv);

    Class<? extends Annotation> supported();

    Collection<JavaFile> process(Set<? extends Element> annotated, RoundEnvironment roundEnv);

}