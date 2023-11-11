package com.kwan.springbootkwan.entity.pic;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class BaiduPic implements Serializable {
    private Integer listNum;
    private String gsm;
    private Integer isNeedAsyncRequest;
    private List<PicDataInfo> data;
    private List<Object> middlers;
    private Object fcadData;
    private String queryEnc;
    private String bdSearchTime;
    private Integer displayNum;
    private String queryExt;
    private String bdFmtDispNum;
    private String bdIsClustered;
}