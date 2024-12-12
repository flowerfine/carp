package cn.sliew.carp.module.persistence.api.selectors;

import lombok.Getter;

import static com.google.common.base.Preconditions.checkNotNull;

@Getter
public final class NotSelector implements Selector {

    private final Selector selector;

    NotSelector(Selector selector) {
        this.selector = checkNotNull(selector, "selector is null");
    }

    public <C> C accept(SelectorVisitor<C, ?> visitor) {
        return visitor.visit(this);
    }
}
