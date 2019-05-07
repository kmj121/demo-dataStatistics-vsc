package com.ssm.demo.controller;

import com.ssm.demo.common.MessageCode;
import com.ssm.demo.common.ResultObject;
import com.ssm.demo.dto.DataStatisticsQueryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/7
 */
@Controller
@Api(tags = {"数据统计"})
@RequestMapping(value = "/dataStatistics")
public class DataStatisticsController {

    @ApiOperation(value = "数据查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject query(HttpServletRequest request, @RequestBody @Valid DataStatisticsQueryDto dto) {
        return new ResultObject(MessageCode.CODE_SUCCESS, null);
    }
}
