package com.gogobuy.houseDA.constants;

public enum HouseStateEnum {
    SELLING("zai_shou","在售"),
    SOLD("yi_shou","已售"),
    END_SALE("ting_shou","停售");


    private String code;

    private String desc;

    HouseStateEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }



    public static HouseStateEnum getByCode(String code){
        HouseStateEnum[] values = HouseStateEnum.values();
        for (HouseStateEnum state : values) {
            if (code.equals(state.code)){
                return state;
            }
        }
        throw new IllegalStateException("没有此枚举信息");

    }

}
