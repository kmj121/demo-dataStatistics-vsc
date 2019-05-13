package com.ssm.demo.controller;


import com.google.common.io.ByteStreams;
import com.ssm.demo.common.MessageCode;
import com.ssm.demo.common.ResultObject;
import com.ssm.demo.common.Util;
import com.ssm.demo.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/6
 */
@Controller
@RequestMapping(value = "/commons/attachments")
@Api(tags = {"共通-附件"})
public class AttachmentController {
    @Autowired
    private AttachmentService attachmentService;
    @Value("${config.attachFolder}")
    private String attachFolder;

    @ApiOperation(value = "上传附件")
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void upload(HttpServletResponse response
            , @ApiParam(value = "附件") @RequestPart MultipartFile multipartFile) throws IOException {
        response.setContentType("text/html");
        String filename = attachmentService.txUpload(multipartFile);

        response.getWriter().println(JSONObject.fromObject(new ResultObject<>(MessageCode.CODE_SUCCESS, filename)).toString());
    }

    @ApiOperation(value = "附件下载")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@ApiParam(value = "附件名称") @RequestParam String filename
            , @ApiParam(value = "保存文件名") @RequestParam(required = false) String saveName
            , HttpServletResponse response) throws IOException {
        saveName = Util.encodeUriParam(saveName);
        String mime = Files.probeContentType(Paths.get(attachFolder + filename));
        saveName = StringUtils.isEmpty(saveName) ? filename : saveName;
        response.setContentType(mime);
        response.setHeader("Content-Dispositon", "attachment;filename=" + URLEncoder.encode(saveName, "UTF-8"));
        ByteStreams.copy(new FileInputStream(attachFolder + filename), response.getOutputStream());
    }
}
