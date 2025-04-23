package cn.sliew.carp.module.persistence.api.selectors;

import lombok.Getter;

@Getter
public class IdSelector implements Selector {

    private final Long id;

    public IdSelector(Long id) {
        this.id = id;
    }

    @Override
    public <S> S accept(SelectorVisitor<S> visitor) {
        return visitor.visit(this);
    }
}
