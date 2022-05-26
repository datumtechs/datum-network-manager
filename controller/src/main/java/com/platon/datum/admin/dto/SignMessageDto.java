package com.platon.datum.admin.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author juzix
 */
@Getter
@Setter
@ToString
public class SignMessageDto {

    public Domain domain;
    public Message message;
    public String primaryType;
    public Types types;

    @Getter
    @Setter
    @ToString
    public static class  Domain {
        String name;
    }

    @Getter
    @Setter
    @ToString
    public static class Message {
        String key;
        String desc;
    }

    @Getter
    @Setter
    @ToString
    public static class Types {
    }


}



