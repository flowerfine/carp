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

package cn.sliew.carp.framework.dag.service;

import cn.sliew.carp.framework.dag.algorithm.DAG;
import cn.sliew.carp.framework.dag.service.dto.DagConfigComplexDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigDTO;
import cn.sliew.carp.framework.dag.service.dto.DagConfigStepDTO;
import cn.sliew.carp.framework.dag.service.param.DagConfigSimpleAddParam;
import cn.sliew.carp.framework.dag.service.param.DagConfigSimpleUpdateParam;
import cn.sliew.carp.framework.dag.x6.graph.DagGraphVO;
import com.google.common.graph.Graph;

import java.util.List;

public interface DagConfigComplexService {

    DagConfigComplexDTO selectOne(Long dagId);

    DagConfigDTO selectSimpleOne(Long dagId);

    Graph<DagConfigStepDTO> getDag(Long dagId);

    DAG<DagConfigStepDTO> getDagNew(Long dagId);

    Long insert(DagConfigSimpleAddParam param);

    boolean update(DagConfigSimpleUpdateParam param);

    void replace(Long dagId, DagGraphVO graph);

    Long clone(Long dagId);

    boolean delete(Long dagId);

    boolean deleteBatch(List<Long> dagIds);
}
