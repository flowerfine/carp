package cn.sliew.carp.support.annotation.processor.specs;

import com.palantir.javapoet.CodeBlock;

public enum GeneratedTryCatchSpecs {
    ;

    public static CodeBlock tryClause(CodeBlock methodCall, CodeBlock catchClause) {
        return CodeBlock.builder()
                .beginControlFlow("try")
                .add(methodCall)
                .endControlFlow()
                .add(catchClause)
                .build();
    }

    public static CodeBlock catchClause(CodeBlock exceptionCall) {
        return CodeBlock.builder()
                .beginControlFlow("catch ($T e)", Exception.class)
                .beginControlFlow("try")
                .addStatement(exceptionCall)
                .endControlFlow()
                .beginControlFlow("catch ($T innerException)", Exception.class)
                .addStatement("throw new $T(innerException)", RuntimeException.class)
                .endControlFlow()
                .addStatement("throw new $T(e)", RuntimeException.class)
                .endControlFlow()
                .build();
    }
}
