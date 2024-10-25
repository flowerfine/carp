package cn.sliew.carp.processor.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@ToString
public class UserData {

    private Integer id;

    private String name;

    private String gender;

    private BigDecimal age;

    private Integer groupIndex;

    private String groupName;

    private String manQuestion1;

    private String manQuestion2;

    private String womenQuestion1;

    private String womenQuestion2;

    private String otherQuestion1;

    private String otherQuestion2;

    private Map<String, Object> dynamicColumns;
}
