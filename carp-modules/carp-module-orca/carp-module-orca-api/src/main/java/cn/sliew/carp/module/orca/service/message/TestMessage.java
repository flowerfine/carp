package cn.sliew.carp.module.orca.service.message;

import cn.sliew.carp.module.orca.spinnaker.keiko.core.Message;
import lombok.Data;

@Data
public class TestMessage extends Message {

    private String name;
}
