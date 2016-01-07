package com.sensetime.stapi.constants;

/**
 * 返回JSON的基本KEY以及STATUS说明(部分)
 *
 */
public class STJsonStatus {

	public static final String DOWNLOAD_TIMEOUT = "网络地址图片获取超时";
	public static final String CORRUPT_IMAGE = "文件不是图片文件或已经损坏";
	public static final String DOWNLOAD_ERROR = "网络地址图片获取失败";
	public static final String IMAGE_FILE_SIZE_TOO_BIG = "图片体积过大";
	public static final String INVALID_IMAGE_FORMAT_OR_SIZE = "图片大小或格式不符合要求";
	public static final String INVALID_ARGUMENT = "请求参数错误，具体原因见 reason 字段内容";
	public static final String UNAUTHORIZED = "账号或密钥错误";
	public static final String KEY_EXPIRED = "账号过期，具体情况见 reason 字段内容";
	public static final String RATE_LIMIT_EXCEEDED = "调用频率超出限额";
	public static final String NO_PERMISSION = "无调用权限";
	public static final String NOT_FOUND = "请求路径错误";
	public static final String INTERNAL_ERROR = "服务器内部错误";
	public static final String STATUS_OK = "OK";
	public static final String STATUS_KEY = "status";
	public static final String REASON_KEY = "reason";
	public static final String NO_RESPONSE = "没有响应或者没有使用linkface服务器";
}
