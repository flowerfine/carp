package cn.sliew.carp.module.persistence.api.selectors;

public interface Selector {

    <S> S accept(SelectorVisitor<S, ?> visitor);
}
