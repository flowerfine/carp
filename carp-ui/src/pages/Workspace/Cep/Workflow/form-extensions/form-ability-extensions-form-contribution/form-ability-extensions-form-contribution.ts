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

import { injectable } from 'inversify';
import { DecoratorAbility, SetterAbility, type FormContribution, type FormManager } from '@flowgram.ai/form-core';

import { setters } from '../setters';
import { decorators } from '../decorators';

@injectable()
export class FormAbilityExtensionsFormContribution implements FormContribution {
  onRegister(formManager: FormManager): void {
    setters.forEach(setter => {
      formManager.registerAbilityExtension(SetterAbility.type, setter);
    });

    decorators.forEach(decorator => {
      formManager.registerAbilityExtension(DecoratorAbility.type, decorator);
    });
  }
}
