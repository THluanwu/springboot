package com.neuedu.common;

public class Const {
    public static final String CURRENTUSER="current_user";
    public static final String TRADE_SUCCESS="TRADE_SUCCESS";
    public enum  productStatusEnum{

        PRODUCT_ONLINE(1,"在售"),
        PRODUCT_OFFLINE(2,"下线"),
        PRODUCT_DELETE(3,"删除")
        ;
        private int code;
        private String desc;

       private productStatusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum  CartCheckEnum{

        CART_CHECKED(1,"已勾选"),
        CART_UNCHECKED(0,"未勾选")
        ;
        private int code;
        private String desc;

       private CartCheckEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    public enum  OrderStatusEnum{

        ORDER_CANCELED(0,"已取消"),
        ORDER_UNPAY(10,"未付款"),
        ORDER_PAYED(20,"已支付"),
        ORDER_SEND(40,"已发货"),
        ORDER_SUCESS(50,"交易成功"),
        ORDER_CLOSED(60,"交易关闭")
        ;
        private int code;
        private String desc;

       private OrderStatusEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static OrderStatusEnum codeOf(Integer code){
           for (OrderStatusEnum orderStatusEnum:values()){
               if (code==orderStatusEnum.getCode()){
                   return orderStatusEnum;
               }
           }
           return null;
        }
    }

    public enum  PaymentEnum{

        PAYMENT_ONLINE(1,"在线支付"),

        ;
        private int code;
        private String desc;

       private PaymentEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static PaymentEnum codeOf(Integer code){
            for (PaymentEnum paymentEnum:values()){
                if (code==paymentEnum.getCode()){
                    return paymentEnum;
                }
            }
            return null;
        }
    }

    public enum PaymentPlatformEnum{

        ALIPAY(1,"支付宝"),
        ;
        private int code;
        private String desc;

        private PaymentPlatformEnum(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }


    }
}
