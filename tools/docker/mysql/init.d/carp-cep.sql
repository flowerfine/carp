create database if not exists carp default character set utf8mb4 collate utf8mb4_unicode_ci;
use carp;

drop table if exists carp_cep_rule;
create table carp_cep_rule
(
    `id`            bigint      not null auto_increment comment '自增主键',
    `namespace`     varchar(64) not null,
    `name`          varchar(64) not null comment '名称',
    `uuid`          varchar(64) not null,
    `type`          varchar(32) not null,
    `nodes`         text,
    `edges`         text,
    `skip_strategy` varchar(32) not null,
    `window`        varchar(128),
    `function`      varchar(128),
    `remark`        text,
    `creator`       varchar(32) comment '创建人',
    `create_time`   datetime    not null default current_timestamp comment '创建时间',
    `editor`        varchar(32) comment '修改人',
    `update_time`   datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_uuid (`namespace`, `uuid`),
    key             idx_name (`namespace`, `name`)
) engine = innodb comment = 'cep rule';

drop table if exists carp_cep_workflow;
create table carp_cep_workflow
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `namespace`   varchar(64) not null,
    `name`        varchar(64) not null comment '名称',
    `uuid`        varchar(64) not null,
    `type`        varchar(32) not null,
    `body`        text,
    `remark`      text,
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_uuid (`namespace`, `uuid`),
    key           idx_name (`namespace`, `name`)
) engine = innodb comment = 'cep workflow';

insert into `carp_cep_workflow` (`id`, `namespace`, `name`, `uuid`, `type`, `body`, `remark`, `creator`, `editor`)
values (1, 'default', 'demo-test-run', 'acd13a6c344140e48feaaa0bf6e56122', 'user',
        '{"nodes":[{"id":"start_0","type":"start","meta":{"position":{"x":180,"y":601}},"data":{"title":"Start","outputs":{"type":"object","properties":{"petId":{"type":"integer","default":20}},"required":["petId"]}}},{"id":"end_0","type":"end","meta":{"position":{"x":1091,"y":601}},"data":{"title":"End","inputsValues":{"body":{"type":"ref","content":["http_rDGIH","body"],"extra":{"index":0}},"statusCode":{"type":"ref","content":["http_rDGIH","statusCode"],"extra":{"index":1}},"headers":{"type":"ref","content":["http_rDGIH","headers"],"extra":{"index":2}}},"inputs":{"type":"object","properties":{"body":{"type":"string"},"statusCode":{"type":"integer"},"headers":{"type":"object","required":[],"properties":{}}}}}},{"id":"http_rDGIH","type":"http","meta":{"position":{"x":632,"y":423}},"data":{"title":"HTTP_1","outputs":{"type":"object","properties":{"body":{"type":"string"},"headers":{"type":"object"},"statusCode":{"type":"integer"}}},"api":{"method":"GET","url":{"type":"template","content":"https://petstore.swagger.io/v2/pet/{{start_0.petId}}"}},"body":{"bodyType":"none"},"timeout":{"timeout":10000,"retryTimes":1}}}],"edges":[{"sourceNodeID":"start_0","targetNodeID":"http_rDGIH"},{"sourceNodeID":"http_rDGIH","targetNodeID":"end_0"}]}',
        null, 'sys', 'sys');
insert into `carp_cep_workflow` (`id`, `namespace`, `name`, `uuid`, `type`, `body`, `remark`, `creator`, `editor`)
values (2, 'default', 'demo-free-layout', 'acd13a6c344140e48feaaa0bf6e56123', 'user',
        '{"nodes":[{"id":"start_0","type":"start","meta":{"position":{"x":180,"y":601.2}},"data":{"title":"Start","outputs":{"type":"object","properties":{"query":{"type":"string","default":"Hello Flow."},"enable":{"type":"boolean","default":true},"array_obj":{"type":"array","items":{"type":"object","properties":{"int":{"type":"number"},"str":{"type":"string"}}}}}}}},{"id":"condition_0","type":"condition","meta":{"position":{"x":1100,"y":546.2}},"data":{"title":"Condition","conditions":[{"key":"if_0","value":{"left":{"type":"ref","content":["start_0","query"]},"operator":"contains","right":{"type":"constant","content":"Hello Flow."}}}]}},{"id":"end_0","type":"end","meta":{"position":{"x":2968,"y":601.2}},"data":{"title":"End","inputsValues":{"success":{"type":"constant","content":true,"schema":{"type":"boolean"}},"query":{"type":"ref","content":["start_0","query"]}},"inputs":{"type":"object","properties":{"success":{"type":"boolean"},"query":{"type":"string"}}}}},{"id":"159623","type":"comment","meta":{"position":{"x":180,"y":775.2}},"data":{"size":{"width":240,"height":150},"note":"hi ~\\n\\nthis is a comment node\\n\\n- flowgram.ai"}},{"id":"http_rDGIH","type":"http","meta":{"position":{"x":640,"y":421.35}},"data":{"title":"HTTP_1","outputs":{"type":"object","properties":{"body":{"type":"string"},"headers":{"type":"object"},"statusCode":{"type":"integer"}}},"api":{"method":"GET","url":{"type":"template","content":""}},"body":{"bodyType":"JSON"},"timeout":{"timeout":10000,"retryTimes":1}}},{"id":"loop_Ycnsk","type":"loop","meta":{"position":{"x":1460,"y":0}},"data":{"title":"Loop_1","loopFor":{"type":"ref","content":["start_0","array_obj"]},"loopOutputs":{"acm":{"type":"ref","content":["llm_6aSyo","result"]}},"outputs":{"type":"object","required":[],"properties":{"acm":{"type":"array","items":{"type":"string"}}}}},"blocks":[{"id":"llm_6aSyo","type":"llm","meta":{"position":{"x":344,"y":0}},"data":{"title":"LLM_3","inputsValues":{"modelName":{"type":"constant","content":"gpt-3.5-turbo"},"apiKey":{"type":"constant","content":"sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"},"apiHost":{"type":"constant","content":"https://mock-ai-url/api/v3"},"temperature":{"type":"constant","content":0.5},"systemPrompt":{"type":"template","content":"# Role\\nYou are an AI assistant.\\n"},"prompt":{"type":"template","content":""}},"inputs":{"type":"object","required":["modelName","apiKey","apiHost","temperature","prompt"],"properties":{"modelName":{"type":"string"},"apiKey":{"type":"string"},"apiHost":{"type":"string"},"temperature":{"type":"number"},"systemPrompt":{"type":"string","extra":{"formComponent":"prompt-editor"}},"prompt":{"type":"string","extra":{"formComponent":"prompt-editor"}}}},"outputs":{"type":"object","properties":{"result":{"type":"string"}}}}},{"id":"llm_ZqKlP","type":"llm","meta":{"position":{"x":804,"y":0}},"data":{"title":"LLM_4","inputsValues":{"modelName":{"type":"constant","content":"gpt-3.5-turbo"},"apiKey":{"type":"constant","content":"sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"},"apiHost":{"type":"constant","content":"https://mock-ai-url/api/v3"},"temperature":{"type":"constant","content":0.5},"systemPrompt":{"type":"template","content":"# Role\\nYou are an AI assistant.\\n"},"prompt":{"type":"template","content":""}},"inputs":{"type":"object","required":["modelName","apiKey","apiHost","temperature","prompt"],"properties":{"modelName":{"type":"string"},"apiKey":{"type":"string"},"apiHost":{"type":"string"},"temperature":{"type":"number"},"systemPrompt":{"type":"string","extra":{"formComponent":"prompt-editor"}},"prompt":{"type":"string","extra":{"formComponent":"prompt-editor"}}}},"outputs":{"type":"object","properties":{"result":{"type":"string"}}}}},{"id":"block_start_PUDtS","type":"block-start","meta":{"position":{"x":32,"y":167.1}},"data":{}},{"id":"block_end_leBbs","type":"block-end","meta":{"position":{"x":1116,"y":167.1}},"data":{}}],"edges":[{"sourceNodeID":"block_start_PUDtS","targetNodeID":"llm_6aSyo"},{"sourceNodeID":"llm_6aSyo","targetNodeID":"llm_ZqKlP"},{"sourceNodeID":"llm_ZqKlP","targetNodeID":"block_end_leBbs"}]},{"id":"group_nYl6D","type":"group","meta":{"position":{"x":1624,"y":698.2}},"data":{"parentID":"root","blockIDs":["llm_8--A3","llm_vTyMa"]}},{"id":"llm_8--A3","type":"llm","meta":{"position":{"x":180,"y":0}},"data":{"title":"LLM_1","inputsValues":{"modelName":{"type":"constant","content":"gpt-3.5-turbo"},"apiKey":{"type":"constant","content":"sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"},"apiHost":{"type":"constant","content":"https://mock-ai-url/api/v3"},"temperature":{"type":"constant","content":0.5},"systemPrompt":{"type":"template","content":"# Role\\nYou are an AI assistant.\\n"},"prompt":{"type":"template","content":"# User Input\\nquery:{{start_0.query}}\\nenable:{{start_0.enable}}"}},"inputs":{"type":"object","required":["modelName","apiKey","apiHost","temperature","prompt"],"properties":{"modelName":{"type":"string"},"apiKey":{"type":"string"},"apiHost":{"type":"string"},"temperature":{"type":"number"},"systemPrompt":{"type":"string","extra":{"formComponent":"prompt-editor"}},"prompt":{"type":"string","extra":{"formComponent":"prompt-editor"}}}},"outputs":{"type":"object","properties":{"result":{"type":"string"}}}}},{"id":"llm_vTyMa","type":"llm","meta":{"position":{"x":640,"y":10}},"data":{"title":"LLM_2","inputsValues":{"modelName":{"type":"constant","content":"gpt-3.5-turbo"},"apiKey":{"type":"constant","content":"sk-xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"},"apiHost":{"type":"constant","content":"https://mock-ai-url/api/v3"},"temperature":{"type":"constant","content":0.5},"systemPrompt":{"type":"template","content":"# Role\\nYou are an AI assistant.\\n"},"prompt":{"type":"template","content":"# LLM Input\\nresult:{{llm_8--A3.result}}"}},"inputs":{"type":"object","required":["modelName","apiKey","apiHost","temperature","prompt"],"properties":{"modelName":{"type":"string"},"apiKey":{"type":"string"},"apiHost":{"type":"string"},"temperature":{"type":"number"},"systemPrompt":{"type":"string","extra":{"formComponent":"prompt-editor"}},"prompt":{"type":"string","extra":{"formComponent":"prompt-editor"}}}},"outputs":{"type":"object","properties":{"result":{"type":"string"}}}}}],"edges":[{"sourceNodeID":"start_0","targetNodeID":"http_rDGIH"},{"sourceNodeID":"http_rDGIH","targetNodeID":"condition_0"},{"sourceNodeID":"condition_0","targetNodeID":"loop_Ycnsk","sourcePortID":"if_0"},{"sourceNodeID":"condition_0","targetNodeID":"llm_8--A3","sourcePortID":"else"},{"sourceNodeID":"llm_vTyMa","targetNodeID":"end_0"},{"sourceNodeID":"loop_Ycnsk","targetNodeID":"end_0"},{"sourceNodeID":"llm_8--A3","targetNodeID":"llm_vTyMa"}],"globalVariable":{"type":"object","required":[],"properties":{"userId":{"type":"string"}}}}',
        null, 'sys', 'sys');
insert into `carp_cep_workflow` (`id`, `namespace`, `name`, `uuid`, `type`, `body`, `remark`, `creator`, `editor`)
values (3, 'default', 'demo-loop', 'e581f8c971044ca0ae5c071f968518a3', 'user',
        '{"nodes":[{"id":"100001","type":"start","meta":{"position":{"x":-498,"y":-10}},"data":{"title":"Start","outputs":{"type":"object","properties":{"petId":{"type":"array","extra":{"index":1},"default":"[10, 20, 30]","items":{"type":"integer"}}},"required":["petId"]}}},{"id":"900001","type":"end","meta":{"position":{"x":1781,"y":-38.60000000000002}},"data":{"title":"End","inputsValues":{"loop":{"type":"ref","content":["loop_dhA_P"]}},"inputs":{"type":"object","properties":{"loop":{"type":"object","required":[],"properties":{}}}}}},{"id":"loop_dhA_P","type":"loop","meta":{"position":{"x":-43,"y":-134.39999999999998}},"data":{"title":"Loop_1","loopOutputs":{},"outputs":{"type":"object","required":[],"properties":{}},"loopFor":{"type":"ref","content":["100001","petId"]}},"blocks":[{"id":"block_start_pS5jD","type":"block-start","meta":{"position":{"x":32,"y":182.39999999999998}},"data":{}},{"id":"block_end__lu7R","type":"block-end","meta":{"position":{"x":1403,"y":158.80624999999998}},"data":{}},{"id":"http_cJZTl","type":"http","meta":{"position":{"x":286.5,"y":0}},"data":{"title":"HTTP_1","api":{"method":"GET","url":{"type":"template","content":"https://petstore.swagger.io/v2/pet/{{loop_dhA_P_locals.item}}"}},"body":{"bodyType":"none"},"headersValues":{},"paramsValues":{},"outputs":{"type":"object","properties":{"body":{"type":"string"},"headers":{"type":"object"},"statusCode":{"type":"integer"}}},"timeout":{"timeout":10000,"retryTimes":1},"headers":{"type":"object","properties":{}},"params":{"type":"object","properties":{}}}},{"id":"condition_tLdPX","type":"condition","meta":{"position":{"x":710.5,"y":122.4}},"data":{"title":"Condition","conditions":[{"value":{"left":{"type":"ref","content":["http_cJZTl","statusCode"]},"operator":"eq","right":{"type":"constant","content":200,"schema":{"type":"number"}}},"key":"if_6NFET"}]}},{"id":"break_590Wc","type":"break","meta":{"position":{"x":1200.5,"y":248.80624999999998}},"data":{"title":"Break_2"}},{"id":"continue_iA2Fm","type":"continue","meta":{"position":{"x":1185.5,"y":137.80624999999998}},"data":{"title":"Continue_2"}}],"edges":[{"sourceNodeID":"block_start_pS5jD","targetNodeID":"http_cJZTl"},{"sourceNodeID":"http_cJZTl","targetNodeID":"condition_tLdPX"},{"sourceNodeID":"condition_tLdPX","targetNodeID":"break_590Wc","sourcePortID":"else"},{"sourceNodeID":"condition_tLdPX","targetNodeID":"continue_iA2Fm","sourcePortID":"if_6NFET"}]}],"edges":[{"sourceNodeID":"100001","targetNodeID":"loop_dhA_P"},{"sourceNodeID":"loop_dhA_P","targetNodeID":"900001"}]}',
        null, 'sys', 'sys');
insert into `carp_cep_workflow` (`id`, `namespace`, `name`, `uuid`, `type`, `body`, `remark`, `creator`, `editor`)
values (4, 'default', 'demo-condition', '9bbeda0bff834417ab57e1cff13d57bf', 'user',
        '{"nodes":[{"id":"100001","type":"start","meta":{"position":{"x":-34.2573735242809,"y":0}},"data":{"title":"Start","outputs":{"type":"object","properties":{"petId":{"type":"integer","extra":{"index":1},"default":20}},"required":["petId"]}}},{"id":"900001","type":"end","meta":{"position":{"x":1793.0335352459101,"y":0}},"data":{"title":"End","inputsValues":{"body":{"type":"ref","content":["http_c54nM","body"],"extra":{"index":0}},"statusCode":{"type":"ref","content":["http_c54nM","statusCode"],"extra":{"index":1}},"headers":{"type":"ref","content":["http_c54nM","headers"],"extra":{"index":2}},"success":{"type":"ref","content":["variable_9vi64","success"],"extra":{"index":3}}},"inputs":{"type":"object","properties":{"body":{"type":"string"},"statusCode":{"type":"integer"},"headers":{"type":"object","required":[],"properties":{}},"success":{"type":"boolean"}}}}},{"id":"http_c54nM","type":"http","meta":{"position":{"x":406.9661548135828,"y":-177.4}},"data":{"title":"HTTP_1","api":{"method":"GET","url":{"type":"template","content":"https://petstore.swagger.io/v2/pet/{{100001.petId}}"}},"body":{"bodyType":"none"},"headersValues":{},"paramsValues":{},"outputs":{"type":"object","properties":{"body":{"type":"string"},"headers":{"type":"object"},"statusCode":{"type":"integer"}}},"timeout":{"timeout":10000,"retryTimes":1},"headers":{"type":"object","properties":{}},"params":{"type":"object","properties":{}}}},{"id":"condition_u94KR","type":"condition","meta":{"position":{"x":848.1896831514464,"y":-55}},"data":{"title":"Condition","conditions":[{"value":{"left":{"type":"ref","content":["http_c54nM","statusCode"]},"operator":"eq","right":{"type":"constant","content":200,"schema":{"type":"number"}}},"key":"if_33v8T"}]}},{"id":"variable_9vi64","type":"variable","meta":{"position":{"x":1310.7491119427336,"y":-177.4}},"data":{"title":"Variable_1","assign":[{"operator":"declare","left":"success","right":{"type":"constant","content":true,"schema":{"type":"boolean"}}}],"outputs":{"type":"object","required":[],"properties":{"success":{"type":"boolean"}}}}},{"id":"variable_kaPkb","type":"variable","meta":{"position":{"x":1310.7491119427336,"y":177.4}},"data":{"title":"Variable_2","assign":[{"operator":"declare","left":"success","right":{"type":"constant","content":false,"schema":{"type":"boolean"}}}],"outputs":{"type":"object","required":[],"properties":{"success":{"type":"boolean"}}}}}],"edges":[{"sourceNodeID":"100001","targetNodeID":"http_c54nM"},{"sourceNodeID":"variable_9vi64","targetNodeID":"900001"},{"sourceNodeID":"variable_kaPkb","targetNodeID":"900001"},{"sourceNodeID":"http_c54nM","targetNodeID":"condition_u94KR"},{"sourceNodeID":"condition_u94KR","targetNodeID":"variable_9vi64","sourcePortID":"if_33v8T"},{"sourceNodeID":"condition_u94KR","targetNodeID":"variable_kaPkb","sourcePortID":"else"}]}',
        null, 'sys', 'sys');
insert into `carp_cep_workflow` (`id`, `namespace`, `name`, `uuid`, `type`, `body`, `remark`, `creator`, `editor`)
values (5, 'default', 'demo-code', 'c577cae5ade4443dab753afa13909c1f', 'user',
        '{"nodes":[{"id":"100001","type":"start","meta":{"position":{"x":0,"y":0}},"data":{"title":"Start","outputs":{"type":"object","properties":{"petId":{"type":"string","extra":{"index":1},"default":"20"}},"required":["petId"]}}},{"id":"900001","type":"end","meta":{"position":{"x":1760,"y":36}},"data":{"title":"End"}},{"id":"http_k3e6O","type":"http","meta":{"position":{"x":435,"y":-177.4}},"data":{"title":"HTTP_1","api":{"method":"GET","url":{"type":"template","content":"https://petstore.swagger.io/v2/pet/{{100001.petId}}"}},"body":{"bodyType":"none"},"headersValues":{},"paramsValues":{},"outputs":{"type":"object","properties":{"body":{"type":"string"},"headers":{"type":"object"},"statusCode":{"type":"integer"}}},"timeout":{"timeout":10000,"retryTimes":1},"headers":{"type":"object","properties":{}},"params":{"type":"object","properties":{}}}},{"id":"condition_R5KCg","type":"condition","meta":{"position":{"x":871,"y":-55}},"data":{"title":"Condition","conditions":[{"value":{"left":{"type":"ref","content":["http_k3e6O","statusCode"]},"operator":"eq","right":{"type":"constant","content":200,"schema":{"type":"number"}}},"key":"if_mVrfL"}]}},{"id":"code_mEXyi","type":"code","meta":{"position":{"x":1337,"y":-187}},"data":{"title":"Code_1","inputsValues":{"input":{"type":"ref","content":["100001","petId"],"extra":{"index":0}}},"script":{"language":"javascript","content":"// Here, you can retrieve input variables from the node using ''params'' and output results using ''ret''.\\n// ''params'' has been correctly injected into the environment.\\n// Here''s an example of getting the value of the parameter named ''input'' from the node input:\\n// const input = params.input;\\n// Here''s an example of outputting a ''ret'' object containing multiple data types:\\n// const ret = { \\"name\\": ''Xiaoming'', \\"hobbies\\": [\\"Reading\\", \\"Traveling\\"] };\\n\\nasync function main({ params }) {\\n  // Build the output object\\n  const ret = {\\n    key0: params.input + params.input, // Concatenate the input parameter ''input'' twice\\n    key1: [\\"hello\\", \\"world\\"], // Output an array\\n    key2: { // Output an Object\\n      key21: \\"hi\\"\\n    },\\n  };\\n\\n  return ret;\\n}"},"outputs":{"type":"object","properties":{"key0":{"type":"string"},"key1":{"type":"array","items":{"type":"string"}},"key2":{"type":"object","properties":{"key21":{"type":"string"}}}}},"inputs":{"type":"object","properties":{"input":{"type":"string"}}}}}],"edges":[{"sourceNodeID":"100001","targetNodeID":"http_k3e6O"},{"sourceNodeID":"code_mEXyi","targetNodeID":"900001"},{"sourceNodeID":"condition_R5KCg","targetNodeID":"900001","sourcePortID":"else"},{"sourceNodeID":"http_k3e6O","targetNodeID":"condition_R5KCg"},{"sourceNodeID":"condition_R5KCg","targetNodeID":"code_mEXyi","sourcePortID":"if_mVrfL"}]}',
        null, 'sys', 'sys');
insert into `carp_cep_workflow` (`id`, `namespace`, `name`, `uuid`, `type`, `body`, `remark`, `creator`, `editor`)
VALUES (6, 'defafult', 'demo-rule', 'a4d26bee27d44c109ee88dcf849b402a', 'user',
        '{"nodes":[{"id":"100001","type":"start","meta":{"position":{"x":0,"y":0}},"data":{"title":"Start","outputs":{"type":"object","properties":{"petId":{"type":"integer","extra":{"index":1},"default":20},"result":{"type":"boolean","extra":{"index":2}},"action":{"type":"string","extra":{"index":3}}},"required":["petId"]}}},{"id":"900001","type":"end","meta":{"position":{"x":2973.4347640664196,"y":14.586512095692083}},"data":{"title":"End","inputsValues":{"result":{"type":"ref","content":["100001","result"],"extra":{"index":0}},"action":{"type":"ref","content":["100001","action"],"extra":{"index":1}}},"inputs":{"type":"object","properties":{"result":{"type":"boolean"},"action":{"type":"string"}}}}},{"id":"http_LOzxg","type":"http","meta":{"position":{"x":425.3821656050955,"y":-177.4}},"data":{"title":"HTTP_1","api":{"method":"GET","url":{"type":"template","content":"https://petstore.swagger.io/v2/pet/{{100001.petId}}"}},"body":{"bodyType":"none"},"headersValues":{},"paramsValues":{},"outputs":{"type":"object","properties":{"body":{"type":"string"},"headers":{"type":"object"},"statusCode":{"type":"integer"}}},"timeout":{"timeout":10000,"retryTimes":1},"headers":{"type":"object","properties":{}},"params":{"type":"object","properties":{}}}},{"id":"code_54HI-","type":"code","meta":{"position":{"x":851.608280254777,"y":-17.5}},"data":{"title":"Code_1","inputsValues":{"statusCode":{"type":"ref","content":["http_LOzxg","statusCode"],"extra":{"index":0}}},"script":{"language":"javascript","content":"// Here, you can retrieve input variables from the node using ''params'' and output results using ''ret''.\\n// ''params'' has been correctly injected into the environment.\\n// Here''s an example of getting the value of the parameter named ''input'' from the node input:\\n// const input = params.input;\\n// Here''s an example of outputting a ''ret'' object containing multiple data types:\\n// const ret = { \\"name\\": ''Xiaoming'', \\"hobbies\\": [\\"Reading\\", \\"Traveling\\"] };\\n\\nasync function main({ params }) {\\n  // Build the output object\\n  const ret = {\\n    success: params.statusCode == 200 ? true : false\\n  };\\n\\n  return ret;\\n}"},"outputs":{"type":"object","properties":{"success":{"type":"boolean"}},"required":[]},"inputs":{"type":"object","properties":{"statusCode":{"type":"integer"}}}}},{"id":"multi_condition_cXAcu","type":"multi-condition","meta":{"position":{"x":1287.7259047158634,"y":-192}},"data":{"title":"multi_condition_1","branch":[{"logic":"and","conditions":[{"value":{"left":{"type":"ref","content":["code_54HI-","success"]},"operator":"eq","right":{"type":"constant","content":true,"schema":{"type":"boolean"}}},"key":"condition_cupTz4"},{"value":{"left":{"type":"ref","content":["http_LOzxg","body"]},"operator":"is_not_empty"},"key":"condition_mecHXU"}]},{"logic":"and","conditions":[{"value":{"left":{"type":"ref","content":["code_54HI-","success"]},"operator":"eq","right":{"type":"constant","content":true,"schema":{"type":"boolean"}}},"key":"condition_6b-h-s"},{"value":{"left":{"type":"ref","content":["http_LOzxg","body"]},"operator":"is_empty"},"key":"condition_0oQaMC"}]}]}},{"id":"variable_yIwsy","type":"variable","meta":{"position":{"x":1724.5548427753126,"y":-100.06698394780085}},"data":{"title":"Variable_1","assign":[{"operator":"assign","left":{"type":"ref","content":["100001","result"]},"right":{"type":"constant","content":true,"schema":{"type":"boolean"}}}],"outputs":{"type":"object","required":[],"properties":{}}}},{"id":"variable_mbnKV","type":"variable","meta":{"position":{"x":1724.5548427753126,"y":171.74469809539076}},"data":{"title":"Variable_5","assign":[{"operator":"assign","left":{"type":"ref","content":["100001","result"]},"right":{"type":"constant","content":true,"schema":{"type":"boolean"}}}],"outputs":{"type":"object","required":[],"properties":{}}}},{"id":"code_cM-td","type":"code","meta":{"position":{"x":1724.5548427753126,"y":50.8312101910828}},"data":{"title":"Code_2","inputsValues":{"input":{"type":"constant","content":""}},"script":{"language":"javascript","content":"// Here, you can retrieve input variables from the node using ''params'' and output results using ''ret''.\\n// ''params'' has been correctly injected into the environment.\\n// Here''s an example of getting the value of the parameter named ''input'' from the node input:\\n// const input = params.input;\\n// Here''s an example of outputting a ''ret'' object containing multiple data types:\\n// const ret = { \\"name\\": ''Xiaoming'', \\"hobbies\\": [\\"Reading\\", \\"Traveling\\"] };\\n\\nasync function main({ params }) {\\n  // Build the output object\\n  const ret = {\\n    key0: params.input + params.input, // Concatenate the input parameter ''input'' twice\\n    key1: [\\"hello\\", \\"world\\"], // Output an array\\n    key2: { // Output an Object\\n      key21: \\"hi\\"\\n    },\\n  };\\n\\n  return ret;\\n}"},"outputs":{"type":"object","properties":{"key0":{"type":"string"},"key1":{"type":"array","items":{"type":"string"}},"key2":{"type":"object","properties":{"key21":{"type":"string"}}}}},"inputs":{"type":"object","properties":{"input":{"type":"string"}}}}},{"id":"variable_QefOy","type":"variable","meta":{"position":{"x":2143.398735306147,"y":68.3312101910828}},"data":{"title":"Variable_6","assign":[{"operator":"declare","left":"sum","right":{"type":"constant","content":0,"schema":{"type":"integer"}}}],"outputs":{"type":"object","required":[],"properties":{"sum":{"type":"integer"}}}}}],"edges":[{"sourceNodeID":"100001","targetNodeID":"http_LOzxg"},{"sourceNodeID":"variable_yIwsy","targetNodeID":"900001"},{"sourceNodeID":"variable_QefOy","targetNodeID":"900001"},{"sourceNodeID":"variable_mbnKV","targetNodeID":"900001"},{"sourceNodeID":"http_LOzxg","targetNodeID":"code_54HI-"},{"sourceNodeID":"code_54HI-","targetNodeID":"multi_condition_cXAcu"},{"sourceNodeID":"multi_condition_cXAcu","targetNodeID":"variable_yIwsy","sourcePortID":"branch.0"},{"sourceNodeID":"multi_condition_cXAcu","targetNodeID":"variable_mbnKV","sourcePortID":"else"},{"sourceNodeID":"multi_condition_cXAcu","targetNodeID":"code_cM-td","sourcePortID":"branch.1"},{"sourceNodeID":"code_cM-td","targetNodeID":"variable_QefOy"}]}',
        NULL, 'sys', 'sys');



drop table if exists carp_cep_function;
create table carp_cep_function
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `namespace`   varchar(64) not null,
    `type`        varchar(32) not null comment '类型',
    `name`        varchar(64) not null comment '名称',
    `short_name`  varchar(64) comment '短的描述',
    `uuid`        varchar(64) not null,
    `remark`      text comment '备注',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_uuid (`namespace`, `uuid`),
    key           idx_name (`namespace`, `name`)
) engine = innodb comment = 'cep function';

insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (1, 'default', '常用函数', 'add', 'add', '1',
        '用于精确数值加法的函数，使用BigDecimal进行计算以避免浮点数精度问题。', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (2, 'default', '常用函数', 'abs', 'abs', '2',
        '求绝对值', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (3, 'default', '常用函数', 'result_compare', 'result_compare', '3',
        '历史结果对比', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (4, 'default', '常用函数', 'his_result_compare', 'his_result_compare', '4',
        null, 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (5, 'default', '常用函数', 'isMobileNumber', 'isMobileNumber', '5',
        '15000530813', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (6, 'default', '常用函数', 'createJsonOBJ', 'createJsonOBJ', '6',
        '传入动态数据 拼接为一个对象数据，单数为键 双数为值，例如：craeteJsonOBJ("a",1,"b",2) 执行后为 {"a":1,"b":2}，若值为单数，则最后一个键对应的值为null',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (7, 'default', '常用函数', 'LIMS_Result_Compare', 'LIMS_Result_Compare', '7',
        null, 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (8, 'default', '常用函数', 'stringToNumberList', 'stringToNumberList', '8',
        '将以逗号分隔的字符串拆分为List<Number>', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (9, 'default', '常用函数', 'sumList', 'sumList', '9',
        '传入list数组求和，null返回0', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (10, 'default', '常用函数', 'Calculate', 'Calculate', '10',
        '', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (11, 'default', '常用函数', 'GET_TIM_BY_ID', 'GET_TIM_BY_ID', '11',
        '某两个表都有同一个字段，通过第一张表的该字段作为入参，查询另一张表中的其他需要的字段数据', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (12, 'default', '常用函数', 'MINUTEOFFSET', 'MINUTEOFFSET', '12',
        '对输入的时间字符串增加或减少指定分钟数', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (13, 'default', '常用函数', 'TO_NEGATIVE', 'TO_NEGATIVE', '13',
        '将数字转换为负数', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (14, 'default', '常用函数', 'TO_NEGATIVE_STR', 'TO_NEGATIVE_STR', '14',
        '将字符串转换为负数', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (15, 'default', '常用函数', 'daysBetween', 'daysBetween', '15',
        '计算两个日期相差天数，日期格式为yyyy-MM-dd', 'sys', 'sys');

insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (16, 'default', '加解密函数', 'Base64Tools', 'Base64Tools', '16',
        'Base64加解密 Base64Tools(加解密字符串, 是否为加密)', 'sys', 'sys');

insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (17, 'default', '脱敏函数', 'MASKING_NAME', 'MASKING_NAME', '17',
        '姓名脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_NAME("苏东坡"); 返回 苏**', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (18, 'default', '脱敏函数', 'MASKING_IDCARD', null, '18',
        '身份证脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_IDCARD("51239876432789002X"); 返回 5123************2X',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (19, 'default', '脱敏函数', 'MASKING_BANKCARD', null, '19',
        '银行卡号脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_BANKCARD("9659980368535971298"); 返回 9659 **** **** *** 1298',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (20, 'default', '脱敏函数', 'MASKING_EMAIL', null, '20',
        '邮箱脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_EMAIL("1348888166@qq.com"); 返回 1*********@qq.com',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (21, 'default', '脱敏函数', 'MASKING_PHONE', null, '21',
        '手机号脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_PHONE("13488888166");返回 134****8166',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (22, 'default', '脱敏函数', 'MASKING_ADDRESS', null, '22',
        '地址脱敏工具，第一位参数为需要脱敏的数据，第二位为敏感信息长度，返回脱敏后的数据例：MASKING_ADDRESS("重庆市渝北区龙睛路",3); 返回 重庆市渝北区***',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (23, 'default', '脱敏函数', 'MASKING_CENTER', null, '23',
        '脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_CENTER("苏东坡"); 返回 苏*坡', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (24, 'default', '脱敏函数', 'MASKING_ENTRY_CENTER', null, '24',
        '脱敏工具，第一位参数为需要脱敏的数据，返回脱敏后的数据例：MASKING_CENTER("测试企业名称"); 返回 测试*业名称',
        'sys', 'sys');

insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (25, 'default', '对象函数', 'SETVALUE', null, '25',
        '修改对象数据中指定属性的值；第二个参数为要修改的属性名，第三个参数为修改的值。例1：SETVALUE(obj,"id","20222156")注意：若属性不存在则，在数据中新增属性及值。',
        'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (26, 'default', '对象函数', 'COPYPROPER', null, '26',
        '拷贝第一个对象的属性值到第二个对象。（仅拷贝属性名相同的值）', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (27, 'default', '对象函数', 'GETVALUE', null, '27',
        '获取对象数据中指定属性的值。例1：GETVALUE(date1,''danWei'')";', 'sys', 'sys');
insert into `carp_cep_function` (`id`, `namespace`, `type`, `name`, `short_name`, `uuid`, `remark`, `creator`, `editor`)
values (28, 'default', '对象函数', 'TOJSONOBJ', null, '28',
        '将json字符串转换为对象。例1：TOJSONOBJ("{"danWei":"444","zongJia":555}")', 'sys', 'sys');

drop table if exists carp_cep_function_detail;
create table carp_cep_function_detail
(
    `id`          bigint      not null auto_increment comment '自增主键',
    `namespace`   varchar(64) not null,
    `function_id` bigint      not null comment '函数 ID',
    `body`        text comment '函数体',
    `result_type` varchar(32) not null comment '结果类型',
    `arg_infos`   text comment '入参列表',
    `remark`      text comment '备注',
    `creator`     varchar(32) comment '创建人',
    `create_time` datetime    not null default current_timestamp comment '创建时间',
    `editor`      varchar(32) comment '修改人',
    `update_time` datetime    not null default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key uniq_function (`namespace`, `function_id`)
) engine = innodb comment = 'cep function detail';


