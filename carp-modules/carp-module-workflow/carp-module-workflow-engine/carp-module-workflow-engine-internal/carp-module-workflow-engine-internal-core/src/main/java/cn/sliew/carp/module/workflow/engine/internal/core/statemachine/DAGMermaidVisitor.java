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
package cn.sliew.carp.module.workflow.engine.internal.core.statemachine;

import cn.sliew.carp.framework.dag.algorithm.*;
import com.alibaba.cola.statemachine.State;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.Transition;
import com.alibaba.cola.statemachine.Visitor;

import java.util.Map;

public class DAGMermaidVisitor implements Visitor {

    private DAG<DefaultDagNode> dag;

    public DAGMermaidVisitor() {
        dag = new DAG<>(true);
    }

    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> stateMachine) {
        return "";
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> stateMachine) {
        return dag.accept(MoreMermaidVisvitor.INSTANCE);
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        for (Transition transition : state.getAllTransitions()) {
            dag.addEdge(new DefaultDagNode(transition.getSource().getId().toString(), transition.getSource().getId().toString()),
                    new DefaultDagNode(transition.getTarget().getId().toString(), transition.getTarget().getId().toString()),
                    transition.getEvent().toString());
        }
        return "";
    }

    @Override
    public String visitOnExit(State<?, ?, ?> state) {
        return "";
    }
}
