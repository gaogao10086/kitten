/***********************************************************************
 * FileName：FileUtil.java 
 * Date：2013-4-23   
 * Copyright 版权所有：GaoYuanSheng
 ***********************************************************************/
package com.gys.kitten.core.util;

import com.gys.kitten.core.vo.FileUpload;

import java.io.*;
import java.net.URL;
import java.util.Random;

/**
 * 该类描述的是：IO工具类
 *
 * @Author: kitten
 * @version: 2013-4-23 上午11:54:19
 */
public class FileUtil {
    // 上传后生成文件名称随机数范围
    private final static int RANDOM_RANGE = 1000000;

    /**
     * Description 上传文件到服务器
     *
     * @param fileUpload (传入参数allowedTypes(允许文件类型)、contentType(文件类型)、file(文件)、
     *                   fileName(文件名称)不为空)
     * @return FileUpload
     * @author GaoYS
     * @CreateData 20120327
     */
    public FileUpload upload(FileUpload fileUpload) {
        String[] allowedTypes = null;
        String contentType = "";
        File file = null;
        String fileName = "";
        //TODO 上传文件保存地址
        String fileSavePath = "";
        // 系统默认允许大小为1.5M左右
        long maxinumSize = 1500000;
        if (fileUpload.getAllowedTypes() == null) {
            fileUpload.setSuccess(false);
            fileUpload.setErrorMessage("允许上传内容类型列表为空");
            return fileUpload;
        } else {
            try {
                allowedTypes = fileUpload.getAllowedTypes().split(",");
            } catch (RuntimeException e) {
                allowedTypes[0] = fileUpload.getAllowedTypes();
            }
        }
        if (fileUpload.getContentType() == null) {
            fileUpload.setSuccess(false);
            fileUpload.setErrorMessage("上传文件类型为空");
            return fileUpload;
        } else {
            contentType = fileUpload.getContentType();
        }
        if (!this.isValid(contentType, allowedTypes)) {
            fileUpload.setSuccess(false);
            fileUpload.setErrorMessage("上传文件类型不允许");
            return fileUpload;
        }
        if (fileUpload.getFile() == null) {
            fileUpload.setSuccess(false);
            fileUpload.setErrorMessage("上传文件为空");
            return fileUpload;
        } else {
            file = fileUpload.getFile();
        }
        if (fileUpload.getFileName() == null) {
            fileUpload.setSuccess(false);
            fileUpload.setErrorMessage("上传文件名字为空");
            return fileUpload;
        } else {
            fileName = fileUpload.getFileName();
        }

        if (fileUpload.getMaxinumSize() == 0) {
            fileUpload.setMaxinumSize(maxinumSize);
        } else {
            maxinumSize = fileUpload.getMaxinumSize();
        }
        if (file.length() > maxinumSize) {
            fileUpload.setSuccess(false);
            fileUpload.setErrorMessage("上传文件大小超过限制");
            return fileUpload;
        }
        if (fileUpload.getFileSavePath() == null) {
            fileUpload.setFileSavePath(fileSavePath);
        } else {
            fileSavePath = fileUpload.getFileSavePath();
        }
        mkDirectory(fileSavePath);
        String uploadName = this.getRandomName(fileName, fileSavePath);
        fileUpload.setUploadName(uploadName);
        try {
            FileOutputStream fos = new FileOutputStream(fileSavePath + "/"
                    + uploadName);
            FileInputStream fis = new FileInputStream(file);
            fileUpload.setInputStream(fis);
            fileUpload.setOutputStream(fos);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            fis.close();
            fileUpload.setSuccess(true);
        } catch (Exception e) {
            fileUpload.setSuccess(false);
            fileUpload.setErrorMessage("上传文件失败");
            e.printStackTrace();
        }
        return fileUpload;
    }

    /**
     * 根据路径创建一系列的目录
     *
     * @param path
     */
    public static void mkDirectory(String path) {
        File file;
        try {
            file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        } finally {
            file = null;
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param fileName
     * @param path
     * @return
     */
    public boolean isFileExist(String fileName, String path) {
        File files = new File(path + fileName);
        return (files.exists()) ? true : false;
    }

    /**
     * 获得随机文件名,保证在同一个文件夹下不同名
     *
     * @param fileName
     * @param path
     * @return
     */
    public String getRandomName(String fileName, String path) {
        // 将文件名已.的形式拆分
        String[] split;
        // 获文件的有效后缀
        String extendFile = "";
        try {
            split = fileName.split("\\.");
            extendFile = "." + split[split.length - 1].toLowerCase();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        Random random = new Random();
        // 产生随机数
        int add = random.nextInt(RANDOM_RANGE);
        String ret = add + extendFile;
        while (this.isFileExist(ret, path)) {
            add = random.nextInt(RANDOM_RANGE);
            ret = fileName + add + extendFile;
        }
        return ret;
    }

    /**
     * 判断文件类型是否是合法的,就是判断allowTypes中是否包含contentType
     *
     * @param contentType 文件类型
     * @param allowTypes  文件类型列表
     * @return 是否合法
     */
    public boolean isValid(String contentType, String[] allowTypes) {
        if (null == contentType || "".equals(contentType)) {
            return false;
        }
        for (String type : allowTypes) {
            if (contentType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Description 输入流转换为字节
     *
     * @param inputStream 输入流
     * @return 字节
     * @author GaoYS
     * @CreateData 20120327
     */
    public byte[] inputStreamToByte(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteOutStream = new ByteArrayOutputStream();
        int read;
        while ((read = inputStream.read()) != -1) {
            byteOutStream.write(read);
        }
        return byteOutStream.toByteArray();
    }

    /**
     * Description 字节转换为输入流
     *
     * @param b 字节
     * @return 输入流
     * @author GaoYS
     * @CreateData 20120327
     */
    public InputStream byteToInputStream(byte[] b) {
        return new ByteArrayInputStream(b);
    }

    /**
     * Description 取得当前类所在的文件
     *
     * @param clazz 类
     * @return 当前类所在的文件
     * @author GaoYS
     * @CreateData 20120323
     */
    public static File getClassFile(Class<?> clazz) {
        URL path = clazz.getResource(clazz.getName().substring(
                clazz.getName().lastIndexOf(".") + 1) + ".classs");
        if (path == null) {
            String name = clazz.getName().replaceAll("[.]", "/");
            path = clazz.getResource("/" + name + ".class");
        }
        return new File(path.getFile());
    }

    /**
     * Description 得到当前类的完整路径
     *
     * @param clazz 类
     * @return 当前类的完整路径
     * @author GaoYS
     * @CreateData 20120323
     */
    public static String getClassFilePath(Class<?> clazz) {
        try {
            return java.net.URLDecoder.decode(getClassFile(clazz).getAbsolutePath(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * Description 取得当前类所在的ClassPath目录，比如tomcat下的classes路径
     *
     * @param clazz 类
     * @return 当前类所在的ClassPath目录
     * @author GaoYS
     * @CreateData 20120323
     */
    public static File getClassPathFile(Class<?> clazz) {
        File file = getClassFile(clazz);
        for (int i = 0, count = clazz.getName().split("[.]").length; i < count; i++)
            file = file.getParentFile();
        if (file.getName().toUpperCase().endsWith(".JAR!")) {
            file = file.getParentFile();
        }
        return file;
    }

    /**
     * Description 取得当前类所在的ClassPath路径
     *
     * @param clazz 类
     * @return 当前类所在的ClassPath路径
     * @author GaoYS
     * @CreateData 20120323
     */
    public static String getClassPath(Class<?> clazz) {
        try {
            return java.net.URLDecoder.decode(getClassPathFile(clazz).getAbsolutePath(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
