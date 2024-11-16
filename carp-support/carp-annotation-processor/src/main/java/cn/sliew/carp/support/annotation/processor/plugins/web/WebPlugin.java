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

import cn.sliew.carp.support.annotation.processor.plugins.AbstractClassGeneratePlugin;
import cn.sliew.carp.support.annotation.processor.plugins.IndexedValue;
import cn.sliew.carp.support.annotation.processor.plugins.ElementMethod;
import com.palantir.javapoet.TypeName;
import com.palantir.javapoet.TypeSpec;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import java.lang.annotation.Annotation;

public class WebPlugin extends AbstractClassGeneratePlugin {

    private HandlerWriter handlerWriter;

    @Override
    public boolean support(ProcessingEnvironment processingEnv) {
        super.support(processingEnv);
        this.handlerWriter = new HandlerWriter(processingEnv);
        return false;
    }

    @Override
    public Class<? extends Annotation> supported() {
        return RequestHandle.class;
    }

    @Override
    protected TypeSpec createType(ElementMethod elementMethod, IndexedValue<ExecutableElement> indexedValue, ExecutableElement handlerMethod) {
        return handlerWriter.buildHandler(
                elementMethod.toMethodName(indexedValue.index()),
                handlerMethod,
                TypeName.get(handlerMethod.getEnclosingElement().asType()),
                handlerMethod.getAnnotation(RequestHandle.class)
        );
    }

}
