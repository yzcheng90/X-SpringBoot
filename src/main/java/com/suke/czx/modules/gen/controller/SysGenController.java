package com.suke.czx.modules.gen.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.suke.czx.common.annotation.AuthIgnore;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.R;
import com.suke.czx.modules.gen.entity.GenConfig;
import com.suke.czx.modules.gen.entity.InfoRmationSchema;
import com.suke.czx.modules.gen.service.SysGenService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/sys/gen")
@AllArgsConstructor
@Api(value = "SysGenController" ,tags = "代码生成")
public class SysGenController extends AbstractController {

    private final SysGenService sysGenService;

    /**
     * 列表
     */
    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public R list(@RequestParam Map<String, Object> params){
        //查询列表数据
        QueryWrapper<InfoRmationSchema> queryWrapper = new QueryWrapper<>();
        if(MapUtil.getStr(params,"tableName") != null){
            queryWrapper
                    .like("tableName",MapUtil.getStr(params,"tableName"));
        }
        IPage<InfoRmationSchema> sysConfigList = sysGenService.queryTableList(mpPageConvert.<InfoRmationSchema>pageParamConvert(params),queryWrapper);
        return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
    }

    /**
     * 生成代码
     */
    @AuthIgnore
    @RequestMapping(value = "/code",method = RequestMethod.GET)
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String data = request.getParameter("data");
        GenConfig config = new Gson().fromJson(data,GenConfig.class);
        byte[] zipData = sysGenService.generatorCode(config);
        response.reset();
        response.addHeader("Content-Disposition", "attachment; filename=\"x-springboot.zip\"");
        response.addHeader("X-Frame-Options", "SAMEORIGIN");
        response.addHeader("Content-Length", "" + zipData.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(zipData, response.getOutputStream());
    }
}
