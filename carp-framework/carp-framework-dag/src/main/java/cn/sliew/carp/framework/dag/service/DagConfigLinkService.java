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

import cn.sliew.carp.framework.dag.repository.entity.DagConfigLink;
import cn.sliew.carp.framework.dag.service.dto.DagConfigLinkDTO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DagConfigLinkService extends IService<DagConfigLink> {

    List<DagConfigLinkDTO> listLinks(Long dagId);

    boolean add(DagConfigLinkDTO linkDTO);

    boolean update(DagConfigLinkDTO linkDTO);

    void upsert(DagConfigLinkDTO linkDTO);

    boolean deleteByDag(Long dagId);

    boolean deleteBatchByDag(List<Long> dagIds);

    boolean deleteSurplusLinks(Long dagId, List<String> linkIds);

    int clone(Long sourceDagId, Long targetDagId);
}
