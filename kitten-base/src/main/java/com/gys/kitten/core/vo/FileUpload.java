package com.gys.kitten.core.vo;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;


public class FileUpload {
//	上传文件名称
	private String fileName;
//	上传文件
	private File file;
//	上传到服务器路径
	private String fileSavePath;
//	上传文件类型
	private String contentType;
//	允许上传内容类型列表
	private String allowedTypes;
//	上传到服务器后的名称(系统生成)
	private String uploadName;
//	是否成功
	private boolean isSuccess;
//	失败信息
	private String errorMessage;
//	上传文件输入流
	private InputStream inputStream;
//	上传文件输出流
	private OutputStream outputStream;
//	允许上传文件大小
	private long maxinumSize;
	
	public long getMaxinumSize() {
		return maxinumSize;
	}
	public void setMaxinumSize(long maxinumSize) {
		this.maxinumSize = maxinumSize;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public OutputStream getOutputStream() {
		return outputStream;
	}
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}
	public String getFileSavePath() {
		return fileSavePath;
	}
	public void setFileSavePath(String fileSavePath) {
		this.fileSavePath = fileSavePath;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getAllowedTypes() {
		return allowedTypes;
	}
	public void setAllowedTypes(String allowedTypes) {
		this.allowedTypes = allowedTypes;
	}
	public String getUploadName() {
		return uploadName;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	
}
