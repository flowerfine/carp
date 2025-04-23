package cn.sliew.carp.module.persistence.demo.controller;

import cn.sliew.carp.framework.common.model.PageParam;
import cn.sliew.carp.framework.common.model.PageResult;
import cn.sliew.carp.framework.common.security.annotations.AnonymousAccess;
import cn.sliew.carp.module.persistence.demo.repository.entity.DemoEntity;
import cn.sliew.carp.module.persistence.demo.service.DemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AnonymousAccess
@RestController
@RequestMapping("/api/carp/persistence/demo")
@RequiredArgsConstructor
@Tag(name = "Persistence模块-Demo")
public class DemoController {

    private final DemoService demoService;

    @GetMapping("page")
    @Operation(summary = "查询-分页", description = "查询-分页")
    public PageResult<DemoEntity> page(@Valid PageParam param) {
        return demoService.page(param);
    }

    @GetMapping("{id}")
    @Operation(summary = "查询-详情", description = "查询-详情")
    public DemoEntity get(@PathVariable("id") Long id) {
        return demoService.get(id);
    }

    @PutMapping
    @Operation(summary = "新增", description = "新增")
    public void add(@Valid @RequestBody DemoEntity param) {
        demoService.add(param);
    }

    @PostMapping
    @Operation(summary = "更新", description = "更新")
    public void update(@Valid @RequestBody DemoEntity param) {
        demoService.update(param);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "删除", description = "删除")
    public void delete(@PathVariable("id") Long id) {
        demoService.delete(id);
    }

    @DeleteMapping("batch")
    @Operation(summary = "批量删除", description = "批量删除")
    public void deleteBatch(@RequestBody List<Long> ids) {
        demoService.deleteBatch(ids);
    }

}
