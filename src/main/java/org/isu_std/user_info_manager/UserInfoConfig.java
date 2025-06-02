package org.isu_std.user_info_manager;

import org.isu_std.io.dynamic_enum_handler.ConfigValue;
import org.isu_std.io.dynamic_enum_handler.IntValue;
import org.isu_std.io.dynamic_enum_handler.StringArrValue;

public enum UserInfoConfig{
    USER_ATTRIBUTE_NAMES(
            new StringArrValue(
                    new String[]{"Username", "Password"}
            )
    ),

    USER_ATTRIBUTES_SPECIFICATIONS(
            new StringArrValue(
                    new String[]{"Min. 8", "Min. 6"}
            )
    ),

    MIN_USERNAME_LENGTH(new IntValue(8)),
    MIN_PASSWORD_LENGTH(new IntValue(6));

    private final ConfigValue value;

    UserInfoConfig(ConfigValue configValue){
        this.value = configValue;
    }

    public ConfigValue getValue(){
        return this.value;
    }
}
