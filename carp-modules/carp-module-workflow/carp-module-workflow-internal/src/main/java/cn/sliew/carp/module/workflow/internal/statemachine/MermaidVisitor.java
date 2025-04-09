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
package cn.sliew.carp.module.workflow.internal.statemachine;

import com.alibaba.cola.statemachine.State;
import com.alibaba.cola.statemachine.StateMachine;
import com.alibaba.cola.statemachine.Transition;
import com.alibaba.cola.statemachine.Visitor;

public class MermaidVisitor implements Visitor {

    public static final MermaidVisitor INSTANCE = new MermaidVisitor();

    private String LF = ";\n";

    private MermaidVisitor() {
    }

    @Override
    public String visitOnEntry(StateMachine<?, ?, ?> stateMachine) {
        StringBuilder sb = new StringBuilder();
        sb.append("flowchart TD").append(LF);
        return sb.toString();
    }

    @Override
    public String visitOnExit(StateMachine<?, ?, ?> stateMachine) {
        return "";
    }

    @Override
    public String visitOnEntry(State<?, ?, ?> state) {
        StringBuilder sb = new StringBuilder();
        for (Transition transition : state.getAllTransitions()) {
            sb.append(transition.getSource().getId())
                    .append(String.format(" -- %s --->", transition.getEvent()))
                    .append(transition.getTarget().getId())
                    .append(LF);
        }
        return sb.toString();
    }

    @Override
    public String visitOnExit(State<?, ?, ?> state) {
        return "";
    }
}
