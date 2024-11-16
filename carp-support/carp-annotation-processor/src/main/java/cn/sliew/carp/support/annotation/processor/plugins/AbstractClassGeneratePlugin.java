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

import cn.sliew.carp.support.annotation.processor.util.ProcessingEnvUtils;
import com.palantir.javapoet.JavaFile;
import com.palantir.javapoet.TypeSpec;

import javax.lang.model.element.ExecutableElement;

/**
 * 生成一个新的类，实现特定接口。
 * Annotation Processor 只能实现编译时获取特定的注解标记的类，
 * 获取到之后生成新的类和修改已有的类已经超出了 Annotation Processor 的功能。
 * 当需要新生成类时可以使用 javapoet 等工具，生成新的 .java 文件
 * 修改已有的类则需要使用 javac，这是 java 的内部类。目前没有相关的库可以辅助生成，
 * 做的比较好的也就是 lombok。参考：https://stackoverflow.com/a/70008734
 */
public abstract class AbstractClassGeneratePlugin extends AbstractProcessorPlugin {

    @Override
    protected JavaFile getJavaFile(ElementMethod elementMethod, IndexedValue<ExecutableElement> indexedValue) {
        var annotedMethod = indexedValue.value();
        var typeSpec = createType(elementMethod, indexedValue, annotedMethod);
        String packageName = ProcessingEnvUtils.getPackageName(processingEnv, annotedMethod);
        return JavaFile.builder(packageName, typeSpec).build();
    }

    protected abstract TypeSpec createType(ElementMethod elementMethod, IndexedValue<ExecutableElement> indexedValue, ExecutableElement handlerMethod);

}
