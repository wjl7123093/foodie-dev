package com.snow.exception;

import com.snow.utils.IMOOCJSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 自定义异常处理工具类
 *
 * 【不起作用！！？？原因未找到！！！】
 */
@RestControllerAdvice
public class CustomExceptionHandler {

    // 上传文件超过 500k，捕获异常：MaxUploadSizeExceededException
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public IMOOCJSONResult handlerMaxUploadFile(MaxUploadSizeExceededException excepiton) {
        return IMOOCJSONResult.errorMsg("文件上传大小不能超过500k，请压缩图片或降低图片质量后再上传！");
    }

}
