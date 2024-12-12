package cn.sliew.carp.module.persistence.api.selectors;

import lombok.Getter;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
public final class OrSelector implements Selector {

    private final List<Selector> selectors;

    OrSelector(Selector... selectors) {
        checkArgument(ArrayUtils.isNotEmpty(selectors), "No selectors provided");
        this.selectors = Arrays.asList(selectors);
    }

    public <C> C accept(SelectorVisitor<C, ?> visitor) {
        return visitor.visit(this);
    }
}
