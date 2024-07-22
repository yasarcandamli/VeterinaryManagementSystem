package dev.patika.VeterinaryManagementSystem.core.utility;


import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.CursorResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class ResultHelper {
    public static <T> ResultData<T> created(T data) {
        return new ResultData<>(true, Messages.CREATED, "201", data);
    }

    public static <T> ResultData<T> validateError(T data) {
        return new ResultData<>(false, Messages.VALIDATE_ERROR, "400", data);
    }

    public static <T> ResultData<T> success(T data) {
        return new ResultData<>(true, Messages.OK, "200", data);
    }

    public static <T> ResultData<List<T>> successList(List<T> data) {
        return new ResultData<>(true, Messages.OK, "200", data);
    }

    public static Result ok() {
        return new Result(true, Messages.OK, "200");
    }

    public static Result notFoundError(String message) {
        return new Result(false, message, "404");
    }

    public static Result conflictError(String message) {
        return new Result(false, message, "500");
    }

    public static Result illegalArgumentError(String message) {
        return new Result(false, message, "500");
    }

    public static Result error() {
        return new Result(false, Messages.ERROR, "400");
    }

    public static <T> ResultData<CursorResponse<T>> cursor(Page<T> pageData) {
        CursorResponse<T> cursor = new CursorResponse<>();
        cursor.setItems(pageData.getContent());
        cursor.setPageNumber(pageData.getNumber());
        cursor.setPageSize(pageData.getSize());
        cursor.setTotalElements(pageData.getTotalElements());
        return ResultHelper.success(cursor);
    }
}
