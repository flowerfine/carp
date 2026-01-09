package cn.sliew.carp.module.cep.workflow.flowgram.core.nodes;

import cn.hutool.core.thread.ThreadUtil;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.HTTPNodeSchema;
import cn.sliew.carp.module.cep.workflow.flowgram.api.node.NodeType;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowInputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.base.WorkflowOutputs;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.document.INode;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.executor.INodeExecutor;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.snapshot.SnapshotData;
import cn.sliew.carp.module.cep.workflow.flowgram.api.runtime.variable.IVariableParseResult;
import cn.sliew.milky.common.util.JacksonUtil;
import kotlin.Pair;
import lombok.Data;
import lombok.experimental.Accessors;
import okhttp3.*;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

public class HTTPExecutor extends INodeExecutor {

    private static final OkHttpClient CLIENT = new OkHttpClient();

    public HTTPExecutor() {
        setType(NodeType.HTTP);
    }

    @Override
    public CompletableFuture<ExecutionResult> execute(ExecutionContext context) {
        CompletableFuture<ExecutionResult> future = new CompletableFuture<>();
        ExecutionResult result = new ExecutionResult();
        WorkflowOutputs outputs = new WorkflowOutputs();
        result.setOutputs(outputs);

        try {
            HTTPExecutorInputs inputs = parseInputs(context);
            Response response = request(inputs);
            if (Objects.nonNull(response)) {
                Map<String, String> responseHeaders = new HashMap<>();
                Headers headers = response.headers();
                Iterator<Pair<String, String>> iterator = headers.iterator();
                while (iterator.hasNext()) {
                    Pair<String, String> pair = iterator.next();
                    responseHeaders.put(pair.getFirst(), pair.getSecond());
                }
                outputs.put("headers", responseHeaders);
                outputs.put("statusCode", response.code());
                outputs.put("body", response.body().byteString().toString());
            }
            future.complete(result);
        } catch (IOException e) {
            future.completeExceptionally(e);
        }
        return future;
    }

    private HTTPExecutorInputs parseInputs(ExecutionContext context) {
        INode node = context.getNode();
        HTTPNodeSchema.HTTPNodeData data = (HTTPNodeSchema.HTTPNodeData) node.getData();
        String method = data.getApi().getMethod();
        IVariableParseResult urlVariable = context.getRuntime().getState().parseTemplate(data.getApi().getUrl());
        if (Objects.isNull(urlVariable)) {
            throw new RuntimeException("HTTP url is required");
        }

        String url = String.valueOf(urlVariable.getValue());
        WorkflowInputs headers = context.getRuntime().getState().parseInputs(data.getHeadersValues(), data.getHeaders());
        WorkflowInputs params = context.getRuntime().getState().parseInputs(data.getParamsValues(), data.getParams());
        HTTPBody httpBody = parseBody(context);

        HTTPExecutorInputs inputs = new HTTPExecutorInputs()
                .setMethod(method)
                .setUrl(url)
                .setHeaders(headers)
                .setParams(params)
                .setBodyType(httpBody.getBodyType())
                .setBody(httpBody.getBody())
                .setRetryTimes(data.getTimeout().getRetryTimes())
                .setTimeout(data.getTimeout().getTimeout());

        Map<String, Object> inputMap = JacksonUtil.toMap(JacksonUtil.toJsonNode(inputs));
        context.getSnapshot().update(new SnapshotData()
                .setInputs(new WorkflowInputs(inputMap)));
        return inputs;
    }

    private HTTPBody parseBody(ExecutionContext context) {
        INode node = context.getNode();
        HTTPNodeSchema.HTTPNodeData data = (HTTPNodeSchema.HTTPNodeData) node.getData();

        HTTPBody httpBody = new HTTPBody();
        httpBody.setBodyType(data.getBody().getBodyType());
        if (StringUtils.equals(data.getBody().getBodyType(), "none")) {
            httpBody.setBody("");
        } else if (StringUtils.equals(data.getBody().getBodyType(), "JSON") || StringUtils.equals(data.getBody().getBodyType(), "raw-text")) {
            if (Objects.isNull(data.getBody().getJson())) {
                throw new RuntimeException("HTTP json body is required");
            }
            IVariableParseResult<String> jsonVariable = context.getRuntime().getState().parseTemplate(data.getBody().getJson());
            if (Objects.isNull(jsonVariable)) {
                throw new RuntimeException("HTTP json body is required");
            }
            httpBody.setBody(jsonVariable.getValue());
        } else if (StringUtils.equals(data.getBody().getBodyType(), "form-data")) {
            if (Objects.isNull(data.getBody().getFormData()) || MapUtils.isEmpty(data.getBody().getFormDataValues())) {
                throw new RuntimeException("HTTP form-data body is required");
            }
            WorkflowInputs formData = context.getRuntime().getState().parseInputs(data.getBody().getFormDataValues(), data.getBody().getFormData());
            httpBody.setBody(JacksonUtil.toJsonString(formData));
        } else if (StringUtils.equals(data.getBody().getBodyType(), "binary")) {
            if (Objects.isNull(data.getBody().getBinary())) {
                throw new RuntimeException("HTTP binary body is required");
            }
            IVariableParseResult<String> binaryVariable = context.getRuntime().getState().parseTemplate(data.getBody().getBinary());
            if (Objects.isNull(binaryVariable)) {
                throw new RuntimeException("HTTP binary body is required");
            }
            httpBody.setBody(binaryVariable.getValue());
        } else if (StringUtils.equals(data.getBody().getBodyType(), "x-www-form-urlencoded")) {
            if (Objects.isNull(data.getBody().getXWwwFormUrlencoded()) || MapUtils.isEmpty(data.getBody().getXWwwFormUrlencodedValues())) {
                throw new RuntimeException("HTTP x-www-form-urlencoded body is required");
            }
            WorkflowInputs xWwwFormUrlencoded = context.getRuntime().getState().parseInputs(data.getBody().getXWwwFormUrlencodedValues(), data.getBody().getXWwwFormUrlencoded());
            httpBody.setBody(JacksonUtil.toJsonString(xWwwFormUrlencoded));
        } else {
            throw new RuntimeException("HTTP invalid body type: " + data.getBody().getBodyType());
        }
        return httpBody;
    }

    private Response request(HTTPExecutorInputs inputs) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(buildUrlWithParams(inputs.getUrl(), inputs.getParams()));
        prepareHeaders(inputs.getHeaders(), inputs.bodyType, requestBuilder);
        prepareBody(inputs.getMethod(), inputs.getBody(), inputs.getBodyType(), requestBuilder);
        for (int i = 0; i <= inputs.getRetryTimes(); i++) {
            try {
                return CLIENT.newCall(requestBuilder.build()).execute();
            } catch (IOException e) {
                ThreadUtil.sleep(1000L);
            }
        }
        return null;
    }

    private String buildUrlWithParams(String url, Map<String, Object> params) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(url);
        if (MapUtils.isNotEmpty(params)) {
            params.forEach((param, value) -> {
                uriComponentsBuilder.queryParam(param, value);
            });
        }
        return uriComponentsBuilder.toUriString();
    }

    private void prepareHeaders(Map<String, Object> headers, String bodyType, Request.Builder requestBuilder) {
        if (MapUtils.isNotEmpty(headers)) {
            headers.forEach((header, value) -> {
                requestBuilder.header(header, String.valueOf(value));
            });
        }

        if (MapUtils.isEmpty(headers) || (!headers.containsKey("Content-Type") && !headers.containsKey("content-type"))) {
            if (StringUtils.equals(bodyType, "none")) {
                requestBuilder.header("Content-Type", "application/json");
            } else if (StringUtils.equals(bodyType, "JSON")) {
                requestBuilder.header("Content-Type", "application/json");
            } else if (StringUtils.equals(bodyType, "form-data")) {
                // do nothing
            } else if (StringUtils.equals(bodyType, "raw-text")) {
                requestBuilder.header("Content-Type", "text/plain");
            } else if (StringUtils.equals(bodyType, "binary")) {
                requestBuilder.header("Content-Type", "application/octet-stream");
            } else if (StringUtils.equals(bodyType, "x-www-form-urlencoded")) {
                requestBuilder.header("Content-Type", "application/x-www-form-urlencoded");
            }
        }
    }

    private void prepareBody(String method, String body, String bodyType, Request.Builder requestBuilder) {
        RequestBody requestBody = null;
        switch (bodyType) {
            case "none":
                requestBody = RequestBody.create(body, MediaType.get("application/json"));
                break;
            case "JSON":
                requestBody = RequestBody.create(body, MediaType.get("application/json"));
                break;
            case "form-data":
                requestBody = RequestBody.create(body, MediaType.get("application/x-www-form-urlencoded"));
                break;
            case "raw-text":
                requestBody = RequestBody.create(body, MediaType.get("text/plain"));
                break;
            case "binary":
                requestBody = RequestBody.create(body, MediaType.get("application/octet-stream"));
                break;
            case "x-www-form-urlencoded":
                requestBody = RequestBody.create(body, MediaType.get("application/x-www-form-urlencoded"));
                break;
            default:
        }
        switch (method) {
            case "GET":
                requestBuilder.get();
                break;
            case "POST":
                requestBuilder.post(requestBody);
                break;
            case "PUT":
                requestBuilder.put(requestBody);
                break;
            case "DELETE":
                requestBuilder.get();
                break;
            case "PATCH":
                requestBuilder.patch(requestBody);
                break;
            case "HEAD":
                requestBuilder.head();
                break;
            default:
        }
    }

    @Data
    @Accessors(chain = true)
    public static class HTTPExecutorInputs {
        private String method;
        private String url;
        private Map<String, Object> headers;
        private Map<String, Object> params;
        private String bodyType;
        private String body;
        private Long retryTimes;
        private Long timeout;
    }

    @Data
    @Accessors(chain = true)
    public static class HTTPBody {
        private String bodyType;
        private String body;
    }
}
