package com.kwan.springbootkwan.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

/**
 * 算法题(AlgorithmicProblem)表实体类
 *
 * @author makejava
 * @since 2023-10-07 09:15:47
 */
@Data
public class AlgorithmicProblem extends Model<AlgorithmicProblem> {
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 问题名称
     */
    private String questionName;
    /**
     * 0:'全部';
     * 1: '链表';
     * 2: '数组';
     * 3: 'JVM';
     * 4: '并发编程';
     * 5:'MySql';
     * 6: 'Redis';
     * 7: '中间件';
     * 8: 'Spring';
     * 9: '微服务';
     * 10:'分布式';
     * 11:'项目';
     * 12:'算法';
     * 99: '其他';
     */
    private Integer questionType;
    /**
     * 1~10的分值
     */
    private Integer degreeOfImportance;

    /**
     * 1:简单;2:中等;3:困难
     */
    private Integer degreeOfDifficulty;
    /**
     * 困难指数
     */
    private Integer difficultyOfScore;
    /**
     * 力扣的问题号
     */
    private Integer leetcodeNumber;
    /**
     * 力扣的问题链接
     */
    private String leetcodeLink;
    /**
     * 创建时间
     */
    private Date createTime;
    //逻辑删除,0未删除,1已删除
    private Integer isDelete;
}