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

package cn.sliew.carp.support.annotation.processor.plugins.jackson.javapoet;

import cn.sliew.carp.support.annotation.processor.plugins.AbstractMethodGeneratePlugin;
import cn.sliew.carp.support.annotation.processor.plugins.IndexedValue;
import cn.sliew.carp.support.annotation.processor.plugins.ElementMethod;
import cn.sliew.carp.support.annotation.processor.plugins.jackson.JacksonGetter;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Set;

public class JacksonJavapoetPlugin extends AbstractMethodGeneratePlugin {

    @Override
    public void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
    }

    @Override
    public Class<? extends Annotation> supported() {
        return JacksonGetter.class;
    }

    @Override
    public Collection<JavaFile> process(Set<? extends Element> annotated) {
        return null;
    }

    @Override
    protected TypeSpec createType(ElementMethod nameData, IndexedValue<ExecutableElement> indexedValue, ExecutableElement handlerMethod) {
        return null;
    }
}
