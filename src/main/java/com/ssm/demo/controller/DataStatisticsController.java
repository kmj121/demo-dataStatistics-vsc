package com.ssm.demo.controller;

import com.google.common.io.ByteStreams;
import com.ssm.demo.common.*;
import com.ssm.demo.config.Config;
import com.ssm.demo.dto.DataStatisticsQueryDto;
import com.ssm.demo.dto.ExportDto;
import com.ssm.demo.dto.ExportDtoOut;
import com.ssm.demo.entity.UdpData;
import com.ssm.demo.service.AttachmentService;
import com.ssm.demo.service.DataStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private DataStatisticsService dataStatisticsService;
    @Autowired
    private AttachmentService attachmentService;

    @ApiOperation(value = "数据查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @ResponseBody
    public ResultObject query(HttpServletRequest request, @RequestBody @Valid DataStatisticsQueryDto dto) {
        return new ResultObject(MessageCode.CODE_SUCCESS, dataStatisticsService.query(dto));
    }

    @ApiOperation(value = "数据导出")
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    @ResponseBody
    public void export(HttpServletRequest request
            , HttpServletResponse response
            , @RequestBody @Valid ExportDto dto) throws IOException {

        List<UdpData> list = dataStatisticsService.listExport(dto);

        String[] titles = new String[] {"id","module_id","record_time","sensor_x","sensor_y","sensor_z"
                ,"uvsensor0","uvsensor1","uvsensor2","uvsensor3","uvsensor4","uvsensor5","create_time","update_time"};

        List<String[]> contents = new ArrayList<>();
        List<String> data;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        for(UdpData item : list) {
            data = new ArrayList<>();
            data.add(item.getId() == null ? "" : item.getId().toString());
            data.add(item.getModuleId() == null ? "" : item.getModuleId());
            data.add(item.getRecordTime() == null ? "" : item.getRecordTime().toString());
            data.add(item.getSensorX() == null ? "" : item.getSensorX().toString());
            data.add(item.getSensorY() == null ? "" : item.getSensorY().toString());
            data.add(item.getSensorZ() == null ? "" : item.getSensorZ().toString());
            data.add(item.getUvsensor0() == null ? "" : item.getUvsensor0().toString());
            data.add(item.getUvsensor1() == null ? "" : item.getUvsensor1().toString());
            data.add(item.getUvsensor2() == null ? "" : item.getUvsensor2().toString());
            data.add(item.getUvsensor3() == null ? "" : item.getUvsensor3().toString());
            data.add(item.getUvsensor4() == null ? "" : item.getUvsensor4().toString());
            data.add(item.getUvsensor5() == null ? "" : item.getUvsensor5().toString());
            data.add(item.getCreateTime() == null ? "" : simpleDateFormat.format(item.getCreateTime()));
            data.add(item.getUpdateTime() == null ? "" : simpleDateFormat.format(item.getUpdateTime()));

            contents.add(data.toArray(new String[] {}));
        }
        String filename = attachmentService.genFileNameByExt(".csv");
        File file = attachmentService.getFile(filename);
        ExcelUtil.buildExcel(new FileOutputStream(file), null, titles, contents, "数据分析列表");
        //ExportDtoOut exportDtoOut = new ExportDtoOut();
        //exportDtoOut.setFileName(filename);
        //exportDtoOut.setSaveName("数据分析列表.csv");
        //return new ResultObject(MessageCode.CODE_SUCCESS, exportDtoOut);
        //saveName = Util.encodeUriParam(saveName);
        String mime = Files.probeContentType(Paths.get(Config.attachFolder + filename));
        //saveName = StringUtils.isEmpty(saveName) ? filename : saveName;
        response.setContentType(mime);
        response.setHeader("Content-Dispositon", "attachment;filename=" + URLEncoder.encode("数据分析列表", "UTF-8"));
        ByteStreams.copy(new FileInputStream(Config.attachFolder + filename), response.getOutputStream());
    }
}
