package com.kwan.springbootkwan.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel
public abstract class BaseQuery extends AbstractObject implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty("第几页")
    @NotNull(
            message = "page不能为空"
    )
    @Min(
            value = -1L,
            message = "page不能小于{value}"
    )
    private Long page = 1L;
    @ApiModelProperty("每页显示多少条记录")
    @Min(
            value = 1L,
            message = "size不能小于{value}"
    )
    private Long size = 10L;

    public BaseQuery() {
    }

    public Long getPage() {
        return this.page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return this.size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}