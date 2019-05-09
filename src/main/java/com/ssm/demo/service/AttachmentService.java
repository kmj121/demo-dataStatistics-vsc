package com.ssm.demo.service;

import com.ssm.demo.dao.AttachmentMapper;
import com.ssm.demo.entity.Attachment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @Description
 * @Author Roge
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2019/5/5
 */
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class AttachmentService {
    @Value("${config.attachFolder}")
    private String attachFolder;
    @Autowired
    private AttachmentMapper attachmentMapper;


    /**
     * 根据文件扩展名生成文件名
     * @param extension
     * @return
     */
    public String genFileNameByExt(String extension) {
        return UUID.randomUUID().toString() + extension;
    }

    /**
     * 根据原文件名生成文件名
     * @param filenameOriginal
     * @return
     */
    private String genFileName(String filenameOriginal) {
        String extension = filenameOriginal.substring(filenameOriginal.lastIndexOf("."));
        return genFileNameByExt(extension);
    }

    /**
     * 根据文件名和目录创建该文件
     * @param filename
     * @return
     */
    public File getFile(String filename) {
        File file = new File(attachFolder + filename);
        file.getParentFile().mkdir();
        return file;
    }

    /**
     * 上传附件
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String txUpload(MultipartFile multipartFile) throws IOException {
        String filename = "";
        //保存文件
        filename = saveFile(multipartFile);
        //插入附件表
        addAttachment(filename, multipartFile);
        return filename;
    }

    /**
     * 附件信息插入附件表
     * @param filename
     * @param multipartFile
     */
    private void addAttachment(String filename, MultipartFile multipartFile) {
        Attachment attachment = new Attachment();
        attachment.setFilename(filename);
        attachment.setFilenameOriginal(multipartFile.getOriginalFilename());
        attachment.setMime(multipartFile.getContentType());
        attachment.setCreatedAt(new Date());
        attachmentMapper.insert(attachment);
    }

    /**
     * 保存附件
     * @param multipartFile
     * @return
     * @throws IOException
     */
    private String saveFile(MultipartFile multipartFile) throws IOException {
        String filenameOriginal = multipartFile.getOriginalFilename();
        String filename = genFileName(filenameOriginal);

        File target = getFile(filename);

        //将上传的附件信息填充到目标文件中
        multipartFile.transferTo(target);

        return filename;
    }
}
