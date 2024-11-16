package cn.sliew.carp.support.annotation.processor.plugins;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;

public record ElementMethod(TypeElement typeElement, Name annotatedMethodName) {
    ElementMethod(ExecutableElement element) {
        this((TypeElement) element.getEnclosingElement(), element.getSimpleName());
    }

    public String toMethodName(int index) {
        return "%s$%s$%d$Generated".formatted(typeElement.getSimpleName().toString(), annotatedMethodName.toString(), index);
    }
}