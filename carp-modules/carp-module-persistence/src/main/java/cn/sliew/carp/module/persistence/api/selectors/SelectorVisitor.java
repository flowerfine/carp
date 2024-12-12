package cn.sliew.carp.module.persistence.api.selectors;

public interface SelectorVisitor<S, V> {
    
    S visit(AllSelector selector);

    S visit(NotSelector selector);

    S visit(AndSelector selector);

    S visit(OrSelector selector);
}
