package com.zw.knight.exception;

/**
 * @author chenchao
 * @date 2016年4月5日 下午9:05:45
 * @version 1.0
 * @throws
 * @see
 */
public enum ErrorStatus {
    /**
     * HttpStatus BAD_REQUEST
     */
    BAD_REQUEST(400, 400, "Bad Request"),

    /**
     * HttpStatus UNAUTHORIZED
     */
    UNAUTHORIZED(401, 401, "Unauthorized"),

    /**
     * HttpStatus FORBIDDEN
     */
    FORBIDDEN(403, 403, "Forbidden"),

    /**
     * HttpStatus NOT_FOUND
     */
    NOT_FOUND(404, 404, "Not Found"),

    /**
     * HttpStatus INTERNAL_SERVER_ERROR
     */
    INTERNAL_SERVER_ERROR(500, 500, "Internal Server Error"),

    /**
     * paramter usr invalid
     */
    USR_INVALID(1100, "usr invalid"),

    /**
     * net connection exception
     */
    NET_CONNECTION_EXCEPTION(10007, "net connection exception"),

    /**
     * qconf connection exception
     */
    QCONF_CONNECTION_EXCEPTION(10008, "qconf connection exception"),

    /**
     * zk path parse exception
     */
    ZK_PATH_PARSE_EXCEPTION(10009, "zk path parse exception"),

    /**
     * AccountInfo save redis error
     */
    ACCOUNT_SAVE_REDIS_ERROR(11000, "AccountInfo save redis error!"),

    /**
     * parameter is required exception
     */
    PARAMETER_NOT_FOUND(20099, "parameter is required!"),

    /**
     * default service exception
     */
    DEFAULT_SERVICE_EXCEPTION(10000, "default service exception!"),


    /**
     * return error
     */
    RETURN_ERROR(10001,"return error"),

    /**
     * 高价书抢购订单超时
     */
    BOOK_CRAB_ORDER_EXPIRE(22001,"crab book order expire!"),
    /**
     * 书籍已经是资产
     */
    BOOK_CRAB_ASSET_ERROR(22002,"book has been asset!"),

    /**
     * 游戏扣费失败
     */
    ORDER_GAME_REDUCE_ERROR(21002,"game reduce error!"),
    /**
     *
     */
    ORDER_GAME_EXPIRED(21003,"game order expired!"),
    /**
     * 游戏订单产品校验错误
     */
    ORDER_GAME_INVALID_PRODUCT(21005,"game product invalid error!"),
    /**
     * 游戏订单余额不足
     */
    ORDER_GAME_NOT_ENOUGH_MONEY(21006,"not enough money!"),
    /**
     * 杂志订单超时
     */
    MAGAZINE_BUSINESS_ORDER_EXPIRE(21101,"magazine order expire!"),
    /**
     * 杂志资产校验错误
     */
    MAGAZINE_BUSINESS_ASSET_ERROR(21102,"magazine is asset!"),
    /**
     * 杂志订单书籍为空
     */
    MAGAZINE_BUSINESS_EMPTY_ORDER(21103,"order magazine is empty!"),
    /**
     * 杂志购买余额不足
     */
    MAGAZINE_BUSINESS_NO_ENOUGH_MONEY(21104,"not enough money!"),
    /**
     * 杂志列表为空
     */
    MAGAZINE_LIST_IS_EMPTY(21105,"magazine list is empty!"),

    BOOK_REFUND_NO_ENOUGH_MONEY(21201,"not enough money!"),

    /**
     * yuebing is not enough code
     */
    DEFAULT_ACCOUNT_INSUFFICIENT(22003, "default account is not enough"),

    EC_BUSINESS_PARAMS_REQUIRED(20099,"params is required"),
    /**
     * 高价书不存在
     */
    BOOK_CRAB_NOT_EXISTS(22004,"book info not exists!"),

    /**
     * 低价书订单超时
     */
    BOOK_CHEAP_ORDER_EXPIRE(23001,"cheap book order expire!"),
    /**
     * 低价书已经是资产
     */
    BOOK_CHEAP_ASSET(23002,"book has been asset!"),
    /**
     * 账户金额不足
     */
    BOOK_CHEAP_NO_ENOUGH_MONEY(23003,"not enough money!"),
    /**
     * 低价书不存在
     */
    BOOK_CHEAP_NOT_EXISTS(23004,"book is not exists!"),

    /**
     * 书籍章节信息不存在
     */
    BOOK_CHAPTERS_NOT_EXISTS(23005, "book chapters don't exist!"),

    /**
     * 订单超时
     */
    ORDER_REMIT_ORDER_EXPIRE(25001, "order expire!"),

    /**
     * 书籍已经是资产
     */
    ORDER_REMIT_ASSET(25002,"book is asset!"),

    /**
     * 账户余额不足
     */
    ORDER_REMIT_NO_ENOUGH_MONEY(25003,"not enough money!"),

    /**
     * 书包信息错误
     */
    ORDER_REMIT_BOOK_ERROR(25004,"book error!"),

    /**
     * 优惠劵不可用
     */
    COUPONS_NOT_AVAILABLE(25008,"coupon is not available!"),

    /***
     * 特权商品 全场免费
     */
    COMBO_USER_NOT_AVAILABLE(25009,"该账号已领取过奖品"),

    COMBO_P1_NOT_AVAILABLE(25010,"该设备已领取过奖品"),

    COMBO_NOT_MANY_TIMES_IN_A_SHORT_TIME(25011,"为了您的利益,短时间内不可多次参加"),

    COMBO_TIMESTAMP_CONVERSION_ERROR(25012,"时间戳转换错误"),

    COMBO_JSON_DATA_TIME_FORMAT_CONVERSION_ERROR(25013,"JSON数据时间格式转换错误"),

    ORDER_SUBMIT_ERROR(25014, "order submit error"),

    ORDER_SUBMIT_REPEAT(25015, "order submit repeat"),

    USER_NOT_ENOUGH_MONEY(25016, "user not enough money!"),

    SAVE_BOOK_ASSET_ERROR(25017, "save book asset error"),


    /**
     * 连载书籍整本特价 预购
     */
    PRE_ORDER_TIME_ERROR(20001, "当前时间不在活动时间范围内"),
    PRE_ORDER_CHAPTER_ERROR(20002, "后台配置的未购章节数大于用户后续未购章节数"),

    /***
     * vip折扣优惠券
     */
    VIP_COUPONS_NOT_MANY_TIMES_IN_A_SHORT_TIME(25021,"为了您的利益,短时间内不可多次参加"),

    VIP_COUPON_PRODUCT_IS_NOT_EXIST(25022,"vip coupon product is not available!"),

    EXPTIME_FORMAT_ERROR(25023,"expTime format error!"),

    PUT_TO_QUEUE_ERROR(25024,"vip coupons put to queue error,msg is :"),

    JSON_FORMAT_ERROR(25025,"json format error"),

    COUPONSID_EMPTY_ERROR(25026,"couponsId is empty!"),

    VIP_COUPONSID_ERROR(25027,"优惠券不属于当前用户"),

    VIP_COUPONSID_ISUSED(25028,"优惠券已使用"),
    PAGE_ERROR(25029,"页码错误"),

    THE_ACTIVITY_TIME_HAS_NOT_BEGUN(25109,"活动时间未开始"),

    SAVE_THE_NUMBER_OF_SINGLE_RELEASES(25110,"保存单次发行数量redis异常"),

    SET_EXCEPTIONS_DAILY(25111,"每天发放次数设置异常"),

    EC_VP_GET_COUPON(206006,"超出用户可领取次数限制"),

    DO_NOT_OPERATE_FREQUENTLY(25112,"您点太快了，请稍候再试"),

    INVALID_PRODUCT(20601,"无效产品"),

    COUPON_HAS_EXPIRED(10002,"优惠券已过期"),

    UPCOMING_EVENTS(10003,"活动预告"),

    GET_THE_SUCCESS(10004,"恭喜你，领取成功"),

    THE_USER_HAS_RECEIVED_PRIVILEGES(25014,"查询用户已领取特权天数异常"),

    /***
     * 兑换码相关
     */
    BOOK_INFORMATION_DOES_NOT_EXIST(25301,"书籍信息不存在"),

    THE_BOOK_TYPE_IS_WRONG(25302,"该书籍类型错误"),
    /**
     * 购物车错误码
     */
    BOOK_BUSINESS_ALREADY_EXIST(20701,"book is exists"),

    BOOK_BUSINESS_ALREADY_BUY(20702,"book is asset"),
    BOOK_BUSINESS_NUM_EXCEED_MAX(20703,"shopping cart is full!"),
    CART_BUSINESS_HTTP_ERROR(20704,"http error!"),
    CART_BUSINESS_SALE_CHECK_ERROR(20705,"sale check error!"),
    CART_BUSINESS_SALE_ERROR(20706,"sale error!"),
    CART_BUSINESS_ASSET_ERROR(20707,"asset error!"),
    CART_BUSINESS_ENCODE_ERROR(20708,"encode error!"),
    CART_BUSINESS_CPS_ERROR(20709,"cps error!"),
    CART_BUSINESS_ORDER_EXPIRE(20710,"order expire!"),
    CART_BUSINESS_NO_ENOUGH_MONEY(20711,"not enough money!"),
    CART_BUSINESS_REDIS_OP_ERROR(20712,"redis error!"),
    CART_BUSINESS_QUERY_ACTIVITY_ERROR(20713,"get activity error!"),
    CART_BUSINESS_EMPTY_ORDER(20714,"order is empty!"),
    CART_BUSINESS_DEAL_EMPTY_ORDER(20715,"deal empty order!"),

    CART_BUSINESS_BOOK_NUMBER_OVERRUN_ORDER(20716,"book number overrun error!"),
    CART_BUSINESS_FEE_UNIT_NO_SUPPORT_ORDER(20718,"book fee unit no support!"),
    CART_BUSINESS_VP_FREE_BOOK_ORDER(20719,"book fee for vip!"),
    CART_BUSINESS_COUPONS_CODE_UNAVAILABLE(20720,"coupons_code_unavailable"),
    CART_BUSINESS_BOOK_PRICE_TYPE_ERROR(20721,"book_price_type_error"),
    CART_BUSINESS_ORDER_USR_ERROR(20722,"order usr error"),
    CART_BUSINESS_BOOK_IS_EXIST_CART(20723,"book_is_exist_cart"),

    SHOPPING_CART_EMPTY(20716,"cart is empty!"),

    SMS_SEND_WHITE_CODE(30000,"black usr"),
    SMS_SEND_DAY_LIMIT_CODE(30001,"day limit "),
    SMS_SEND_MONTH_LIMIT_CODE(30002,"month limit"),
    SMS_SEND_DEFAULT_CODE(30003,"unknown error"),
    SMS_CODE_CHECK_ERROR(30004,"code is error"),
    SMS_CODE_TIMES_ERROR(30005,"times is error"),
    SYSTEM_ERROR(30030,"system error"),

    PHONE_NUMBER_FALSE(30401,"phone number false"),
    LIMIT_LIST_USER(30402,"limit list user"),
    DAY_LIMIT(30403,"day limit"),
    MONTH_LIMIT(30404,"month limit"),
    NO_SIGN(30101,"no sign"),
    TEMPLATE_UPT_FAIL(30509,"template update fail"),

    OAUTH_NOT_SUPPORT(31000,"oauth fail"),
    USER_NOT_EXISTS(31001,"user is not exists"),
    THIRD_USER_NOT_EXISTS(31002,"third user is not exists"),
    PLATFORM_NOT_SUPPORT(31003,"platform not support "),
    WECHAT_ACCESS_TOKEN_ERROR(31004,"get weixin accesstoken is error "),
    TOKEN_EXPIRE(31005,"accesstoken is expire "),
    TOKEN_REFRESH_TO0_OFTEN(31006,"token refresh to often "),
    LOGIN_CREATE_LOCK_PHONE_ERROR(31007,"create usr lock phone is error"),
    LOGIN_CREATE_USR_ERROR(31008,"create usr  is error"),
    LOGIN_GET_TOKEN_ERROR(31009,"create token is error"),
    LOGIN_TOKEN_EXPIRE_TIME(31010,"token  is expire time"),
    LOGIN_CREATE_LOCK_UID_ERROR(31011,"create usr lock uid is error"),
    LOGIN_ERROR(31012,"login is error"),
    LOGIN_CODE_ERROR(31013,"login code is error"),


    BIND_USR_PHONE_ERROR(33001,"phone already bind usr"),
    BIND_USR_NOT_FOUND(33002,"bind usr not found"),
    BIND_USR_BIND_ERROR(33003,"bind usr already bind phone"),
    BIND_CHECK_USR_BIND_PHONE_ERROR(33004,"check usr bind phone"),
    BIND_NEW_PHONE_CHECK_ERROR(33005,"check usr bind phone is error"),
    BIND_AUTH_UID_CHECK_ERROR(33006,"uid always bind usr check error"),
    BIND_USR_UID_CHECK_ERROR(33007,"usr bind uid check error"),
    BIND_USR_UNIQ_CHECK_ERROR(33008,"check usr uniq bind"),
    BIND_USR_GET_UID_CHECK_ERROR(33008,"get usr uid by platform is error"),
    BIND_USR_BY_UID_CHECK_ERROR(33009,"get usr msg by uid is error"),
    BIND_CHANGE_ERROR(33011,"change bind is error"),
    USR_MSG_ERROR(33010,"usr is null"),

    UPLOAD_PIC_NULL_ERROR(34001,"upload file is null"),
    UPLOAD_PIC_SIZE_ERROR(34002," file size > 10M "),
    UPLOAD_PIC_ERROR(34003," upload is error"),


    SIGN_CHECK_ERROR(35000,"sign check is error"),

    IDENTIFY_CHECK_APPID_ERROR(32001,"identify is null"),
    APP_AUTHORIZE(32002,"identify check is error"),
    APP_SCOPE_NOT_MATCH(32003,"scope not match"),
    APP_CODE_NOT_MATCH(32004,"code not match"),
    APP_REFRESHTOKEN_NOT_MATCH(32005,"refreshToken not match"),
    APP_REFRESHTOKEN_EXPIRE_TIME(32006,"refreshToken expire time"),


    TEACHER_MSG_CHECK(33001,"老师信息校验"),


    CHECK_PARAMS_ERROR(36001,"params is error"),

    CHECK_USERNAME_LENGTH_ERROR(36002,"username length is error"),
    CHECK_USERNAME_CHAR_ERROR(36003,"char is error"),
    CHECK_USERNAME_BLACK_ERROR(36004,"userName is black"),
    CHECK_USERNAME_ERROR(36005,"userName is error"),
    ROLE_USERNAME_lOCK_ERROR(36006,"lock role usr  is error"),
    ROLE_CREATE_ERROR(36007,"create role  is error"),

    READING_COUPONS_DB_ERROR(36100,"mongodb is error"),
    READING_COUPONS_ASSET_CHECK(36101,"check asset"),
    READING_COUPONS_USED_FAIL(36102,"read coupons is fail"),

    READING_COUPONS_LOCK_ERROR(36102,"lock error"),
    READING_COUPONS_SAVE_ASSET_ERROR(36103,"save asset error"),

    LOCAL_SHOPPING_CART_FEE_UNIT(1000,"feeUnit is error"),

    LOCAL_SHOPPING_CART_PRICE(1002,"price is error"),

    LOCAL_SHOPPING_CART_WEB_SITE(1001,"web site is error"),

    LOCAL_SHOPPING_CART_OFF_SELF(1003,"off self is error"),

    LOCAL_SHOPPING_CART_ASSET(1004," book is asset"),

    DOWNLOAD_BUSINESS_CHECK_SUPPORT_TYPE(30000,"Clients do not support this type of books"),

    DOWN_PARAMS_USR_ERROR(30001,"usr is error"),
    DOWN_PARAMS_ERROR_REQUEST(30002," request params is error"),
    DOWN_SERVICE_NO_DATA(699,"No data"),
    DOWN_SERVICE_DATA_ERROR(10000,"data is error"),
    DOWN_SERVICE_URL_EXPIRE(8888,"download url is expire out"),
    EC_Illegal_Properties_Param(20002,"error params"),
    EC_DLD_SDB_LAST(27201, " Sidebar is the new version"),
    BOOK_TOKEN_ERROR(25000,"token is error"),

    DRM_NOT_SUPPORT_ERROR(20706,"not support version"),

    DRM_NOT_DOWNLOAD_ERROR(20707,"not download books"),
    DRM_NOT_BUY_ERROR(20708,"not buy books"),
    DRM_NOT_CLASS_BUY_ERROR(20709,"not buy books"),
    DRM_READ_VIP_COUPON_ERROR(20710,"not buy books"),
    DRM_READ_VIP_NO_COUPON_ERROR(20711,"not buy books"),
    DRM_READ_NOT_VIP_ERROR(20712,"not buy books"),



    EC_DATA_NOT_FOUND(10005,"DATA not found"),

    EC_RUNTIME (10099,"inner error"),

    EC_VP_USER_BALANCE_NOT_ENOUGH(206003,"vp charging error!"),

    /**
     * 微信数据校验
     */

    WECHAT_TEMPLATE_DATA(207001,"wechat template set is error"),
    WECHAT_USR_DATA(207002,"wechat usr is error"),
    WECHAT_BOOK_LIST_DATA(207003,"wechat bookList  is error"),
    WECHAT_ESSAY_LIST_DATA(207004,"wechat EssayList list  is error"),


    EPUB_INNER_BOOK_CHAPTER_ERROR(30000,"get inner books msg  is error"),

    EPUB_BOOK_CHAPTER_ERROR(30001,"get books msg is error"),

    EPUB_BOOK_CHAPTER_REQUEST(30002,"request chapter is error"),

    EPUB_BOOK_PUBLIC_PACKAGE(30003,"get books public package is null"),

    EPUB_BOOK_PUBLIC_PACKAGE_REDIRECT(30004,"302跳转错误"),

    /**
     * VP 产品校验错误
     */
    EC_BUSINESS_PRODUCT_INVALID(20601,"product invalid error!"),

    /**
     * user info is null
     */
    EC_BUSINESS_NO_SUCH_USER(20606,"user info is null"),


    EC_BUSINESS_PARAM_ERROR(20607,"param is error"),

    READ_BUSINESS_BOOK_URL(15000,"  url is null"),

    READ_BUSINESS_SIGN_CHECK(15001,"drm sign check is error"),

    READ_BUSINESS_ASSET_ERROR(15002,"book asset is not exists"),

    READ_BUSINESS_BOOK_ERROR(15003,"book msg is error"),

    /**
     * 操作频繁
     */
    OP_FREQUENT(26001,"operation frequent"),


    /**
     * 用户token错误
     */
    TOKEN_ERROR(26002,"token error"),


    /**
     * 用户密码错误
     */
    PWD_ERROR(26004,"password error"),


    /**
     * 用户被禁用
     */
    USER_IS_BANNED(26003,"user is banned"),

    TING_BOOK_NOT_ENOUGH_MONEY(30001,"not enough money"),
    TING_BOOK_ASSET_ERROR(30002,"is asset"),
    TING_TEMPLATE_ORDER_EXPIRE(30003,"order expired"),
    TING_TEMPLATE_BOOK_FREE(30004,"book is free"),
    TING_TEMPLATE_NO_BOOK_NEED_BUY(30005,"no book need buy"),
    TING_BOOK_GET_BOOK_PRICE_ERROR(30006,"get book price error"),

    /**
     * 获取听书文件解密key错误
     */
    TING_BOOK_GET_BOOK_KEY_ERROR(30007,"get key error!"),

    /**
     * 听书token签名验证错误
     */
    TING_BOOK_TOKEN_SIGN_ERROR(30009,"token sign error!"),

    /**
     * 不是听书资产
     */
    TING_BOOK_IS_NOT_ASSET(30008,"book is not asset"),


    /**
     * 读书会
     */
    READING_CLUB_ORDER_EXPIRE(40001,"order expired"),
    READING_CLUB_INFO_NULL(40002,"get readingClub null"),
    PROGRAM_INFO_NULL(40003,"get program null"),

    /**
     * res exchange 项目code   60XXX
     */
    RES_EXCHANGE_REFRESH_TOKEN_ERROR(60001 ,"refresh token error"),

    /**
     * 统一接口的报文中缺少必要的参数
     */
    EC_BUSINESS_MESSAGE_INVALID_MISS_REQIERED(20604,"msg lack of param"),

    EC_BUSINESS_PARAM(20607,"param business error"),

    /**
     * 删除用户金额受限码
     */
    DELETE_USER_MONEY_LIMIT(21301,"usr money is limited"),

    /**
     * 阅历错误码:用户不存在
     */
    READ_EXP_USR_NOTEXIST(40001,"read exp usr not exist"),


    /**
    * 向服务器请求的数据格式不正确，无法解析
    */
    REQUEST_ERROR(20701,"request server error"),

    /**
     * 多本折扣必买书籍没有选择
     */

    MULTI_BOOK_DIS_MUST_BUY(1005," must buy book not choose"),

    /**
     * 书籍价格信息错误
     */
    MULTI_BOOK_DIS_BOOK_PRICE(1006, "book price is not exists"),

    /**
     * 多本折扣活动类型错误
     */
    MULTI_BOOK_DIS_TYPE_WRONG(1007, "multi book discount type is wrong"),

    /**
     * 单次购买书籍超过最大书籍数
     */
    BOOK_COUNT_ERROR(1008, "book count over max count"),

    /**
     * 用户名错误
     */
    USER_INFO_ERROR(1009, "usr is wrong"),

    /**
     * 进度点数超大
     */
    BOOK_MAX_NUM_ERROR(2001, "Spot nodeNum over max count"),
    /**
     * kafka推送出错
     */
    BOOK_KAFKA_ERROR(2002, "kafka error"),

    /**
     * 阿拉丁领取码，领取失败。重复领取
     */
    ALADDIN_REDEEM_CODE_FAIL(26101, "领取失败"),

    /**
     * 重新获取兑换码。兑换码错误、过期
     */
    ALADDIN_GET_REDEEM_CODE(26102, "重新领取"),

    /**
     * 书籍过期。
     */
    ALADDIN_BOOK_EXPIRE(26103, "书籍已过期"),

    /**
     * vip每日订单次数限制
     */
    FREE_VIP_ORDER_OVERLOAD(50001, "免费书vip订单次数过多"),

    /**
     * vip最多购买限制
     */
    FREE_VIP_ORDER_TIME_LIMIT(50002, "免费书vip订单次数过多"),

    /**
     * vip订单重复
     */
    FREE_VIP_ORDER_REPEAT(50003, "vip订单重复"),

    /**
     * vip自动续费重复
     */
    FREE_VIP_CONTINUE_REPEAT(50004, "vip自动续费重复"),

    /**
     * 订单已处理
     */
    ORDER_SUCCESS(5000, "订单已处理");


    /**
     * http 状态码
     */
    private final int httpStatus;

    /**
     * 业务状态码
     */
    private final int code;

    /**
     * 状态信息
     */
    private final String message;

    private ErrorStatus(int httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }

    private ErrorStatus(int code, String message) {
        this.httpStatus = 200;
        this.code = code;
        this.message = message;
    }

    /**
     * Return the integer value of this httpStatus.
     */
    public int httpStatus() {
        return this.httpStatus;
    }

    /**
     * Return the integer value of this code.
     */
    public int code() {
        return this.code;
    }

    /**
     * Return the advice of this status code.
     */
    public String message() {
        return this.message;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
