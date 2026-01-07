/*
 * Copyright 2025 coze-dev Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import React, { useCallback, useMemo } from 'react';

import { type interfaces } from 'inversify';
import { PlaygroundReactProvider, WorkflowDocumentContainerModule, Plugin, FlowDocumentContainerModule, FlowRendererContainerModule, createNodeCorePlugin, createFreeStackPlugin } from '@flowgram.ai/free-layout-editor';
import { createFreeAutoLayoutPlugin } from '@flowgram.ai/free-auto-layout-plugin';

import { WorkflowRenderContainerModule } from './workflow-render-container-module';
import { WorkflowLoader } from './workflow-loader';

export interface WorkflowRenderProviderProps {
  children: React.ReactElement;
  containerModules?: interfaces.ContainerModule[];
  preset?: () => Plugin[];
  parentContainer?: interfaces.Container;
}

/**
 * Canvas Engine Rendering
 */
export const WorkflowRenderProvider = (props: WorkflowRenderProviderProps) => {
  const modules = useMemo(
    () => [
      FlowDocumentContainerModule, // default document
      FlowRendererContainerModule, // default rendering
      WorkflowDocumentContainerModule, // extended document
      WorkflowRenderContainerModule, // extended rendering
      ...(props.containerModules || []),
    ],
    [],
  );

  const preset = useCallback(
    () => [
      createFreeAutoLayoutPlugin({}),
      createFreeStackPlugin({}), // rendering hierarchy management
      createNodeCorePlugin({}),
      ...(props.preset?.() || []),
    ],
    [],
  );

  return (
    <PlaygroundReactProvider
      parentContainer={props.parentContainer}
      containerModules={modules}
      plugins={preset}
    >
      <WorkflowLoader />
      {props.children}
    </PlaygroundReactProvider>
  );
};
