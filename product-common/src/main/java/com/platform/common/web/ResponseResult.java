//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.platform.common.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.platform.common.enums.StatusEnum;
import com.platform.common.util.I18NUtils;

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {
    private static final long serialVersionUID = -5945988848945139985L;
    private static PageParam pageParam;
    private String code;
    private String message;
    private T data;

    private ResponseResult() {
    }

    public ResponseResult(String code, String message, Object data) {
        this.code = code;
        this.message = I18NUtils.getMessage(message, new Object[0]);
        if (null == data) {
            this.data = (T) new Object();
        } else {
            this.data = (T) data;
        }

    }

    public ResponseResult(String code, String message) {
        this.code = code;
        this.message = I18NUtils.getMessage(message, new Object[0]);
    }

    public ResponseResult(T obj) {
        this(StatusEnum.SUCCESS.getCode(), (String)null, obj);
    }

    public ResponseResult(IPage<?> iPage) {
        this(StatusEnum.SUCCESS.getCode(), (String)null, iPage.getRecords());
        this.pageParam = initPageParam(iPage);
    }

    private static PageParam initPageParam(IPage<?> iPage) {
        PageParam pageParam = new PageParam();
        pageParam.setPageNumber(iPage.getCurrent());
        pageParam.setPageSize(iPage.getSize());
        pageParam.setTotal(iPage.getTotal());
        pageParam.setTotalPage(iPage.getPages());
        return pageParam;
    }

    public static <T> ResponseResult<T> response(String code, String message) {
        ResponseResult<T> result = new ResponseResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ResponseResult<T> newInstance() {
        return new ResponseResult();
    }

    public static <T> ResponseResult<T> success(IPage<?> iPage) {
        ResponseResult<T> result = newInstance();
        result.setCode(StatusEnum.SUCCESS.getCode());
        result.setData((T) iPage.getRecords());
        result.setPageParam(initPageParam(iPage));
        return result;
    }

    public static <T> ResponseResult<T> success(T obj) {
        ResponseResult<T> result = new ResponseResult();
        result.setCode(StatusEnum.SUCCESS.getCode());
        result.setData(obj);
        return result;
    }

    public static <T> ResponseResult<T> success() {
        ResponseResult<T> result = new ResponseResult();
        result.setCode(StatusEnum.SUCCESS.getCode());
        result.setMessage(I18NUtils.getMessage("product.info.00006", new Object[0]));
        return result;
    }

    public static <T> ResponseResult<T> error(String msg, Object... args) {
        ResponseResult<T> result = new ResponseResult();
        result.setCode(StatusEnum.FAIL.getCode());
        result.setMessage(I18NUtils.getMessage(msg, args));
        return result;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }

    public PageParam getPageParam() {
        return this.pageParam;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public void setData(final T data) {
        this.data = data;
    }

    public void setPageParam(final PageParam pageParam) {
        this.pageParam = pageParam;
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof ResponseResult)) {
            return false;
        } else {
            ResponseResult<?> other = (ResponseResult)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label59: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label59;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label59;
                    }

                    return false;
                }

                Object this$message = this.getMessage();
                Object other$message = other.getMessage();
                if (this$message == null) {
                    if (other$message != null) {
                        return false;
                    }
                } else if (!this$message.equals(other$message)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                Object this$pageParam = this.getPageParam();
                Object other$pageParam = other.getPageParam();
                if (this$pageParam == null) {
                    if (other$pageParam != null) {
                        return false;
                    }
                } else if (!this$pageParam.equals(other$pageParam)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(final Object other) {
        return other instanceof ResponseResult;
    }

    public int hashCode() {
        int result = 1;
        Object $code = this.getCode();
        result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $message = this.getMessage();
        result = result * 59 + ($message == null ? 43 : $message.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        Object $pageParam = this.getPageParam();
        result = result * 59 + ($pageParam == null ? 43 : $pageParam.hashCode());
        return result;
    }

    public String toString() {
        return "ResponseResult(code=" + this.getCode() + ", message=" + this.getMessage() + ", data=" + this.getData() + ", pageParam=" + this.getPageParam() + ")";
    }
}
