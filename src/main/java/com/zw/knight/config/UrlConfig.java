/*
 * Copyright (c) 2019.掌阅科技
 * All rights reserved.
 */

package com.zw.knight.config;

import com.zhangyue.arch.nsc.NSClient;
import com.zhangyue.common2.http.HttpUtilsV2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * url配置
 *
 * @author zw
 * @date 2019/9/2
 */
@Component
@PropertySource("classpath:config/url.properties")
public class UrlConfig {

    /**
     * fee 名称空间
     */
    @Value("${dejian.ngx.fee.namespace}")
    private String ngxFeeNamespace;

    /**
     * ngx 名称空间
     */
    @Value("${dejian.ngx.namespace}")
    private String ngxNamespace;

    /**
     * 充值链接
     */
    @Value("${dj.knight.recharge.url}")
    private String knightRechargeUrl;

    /**
     * 金币充值链接
     */
    @Value("${dj.gold.add.url}")
    private String goldAddUrl;

    /**
     * 连续阅读任务配置推送到reading链接
     */
    @Value("${continuous.read.to.reading.url}")
    private String crToReadingUrl;

    @Value("${dj.admin.namespace}")
    private String adminNamespace;

    @Value("${admin.coupon.update.url}")
    private String adminCouponUpdateUrl;

    /**
     * 查询金币提现配置
     */
    @Value("${config.gold.task.url}")
    private String goldTaskUrl;

    /**
     * 查询用户设备是否完成首登任务
     */
    @Value("${login.task.V2.url}")
    private String loginTaskV2Url;

    /**
     * 查询红包任务
     */
    @Value("${red.pocket.task.url}")
    private String redPocketTaskUrl;

    /**
     * 根据id查询金币提现配置
     */
    @Value("${config.gold.task.by.id.url}")
    private String goldTaskByIdUrl;


    @Value("${php.topic.praise.url}")
    private String phpTopicPraiseUrl;

    @Resource
    private NSClient nsClient;

    @Resource
    private Environment env;

    public String getKnightRechargeUrl() {
        if (isDev()) {
            return "http://localhost:40130/dj_knight/recharge/slaveSave";
        } else {
            return HttpUtilsV2.getUrlForNamespace(knightRechargeUrl, ngxFeeNamespace, nsClient);
        }
    }

    public String goldAddUrl() {
        if (isDev()) {
            return "http://localhost:40130/dj_vc/gold/coin/saveTask";
        } else {
            return HttpUtilsV2.getUrlForNamespace(goldAddUrl, ngxFeeNamespace, nsClient);
        }
    }

    public String crToReadingUrl() {
        if (isDev()) {
            return "http://localhost:40090/dj_reading/config/gold/crConfig";
        } else {
            return HttpUtilsV2.getUrlForNamespace(crToReadingUrl, ngxNamespace, nsClient);
        }
    }

    public String getAdminCouponUpdateUrl() {
        return HttpUtilsV2.getUrlForNamespace(adminCouponUpdateUrl, adminNamespace, nsClient);
    }

    private boolean isDev() {
        return Objects.equals(env.getProperty("environment"), "dev");
    }

    public String getGoldTaskFromConfig() {
        if (isDev()) {
            return "http://localhost:40060/dj_config/gold/withdrawal/get";
        } else {
            return HttpUtilsV2.getUrlForNamespace(goldTaskUrl, ngxNamespace, nsClient);
        }
    }

    public String queryLoginTaskV2FromUser() {
        if (isDev()) {
            return "http://localhost:47000/dj_user/inner/info/loginTaskV2";
        } else {
            return HttpUtilsV2.getUrlForNamespace(loginTaskV2Url, ngxNamespace, nsClient);
        }
    }

    public String getRedPocketTaskFromConfig() {
        if (isDev()) {
            return "http://localhost:40060/dj_config/gold/task/get";
        } else {
            return HttpUtilsV2.getUrlForNamespace(redPocketTaskUrl, ngxNamespace, nsClient);
        }
    }

    public String getGoldTaskByIdFromConfig() {
        if (isDev()) {
            return "http://localhost:40060/dj_config/gold/withdrawal/getById";
        } else {
            return HttpUtilsV2.getUrlForNamespace(goldTaskByIdUrl, ngxNamespace, nsClient);
        }
    }


    public String getPhpTopicPraiseUrl() {
        return phpTopicPraiseUrl;
    }
}
