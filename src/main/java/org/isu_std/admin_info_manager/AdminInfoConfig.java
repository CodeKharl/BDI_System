package org.isu_std.admin_info_manager;

import org.isu_std.io.dynamic_enum_handler.ConfigValue;
import org.isu_std.io.dynamic_enum_handler.IntValue;
import org.isu_std.io.dynamic_enum_handler.StringArrValue;

enum AdminInfoConfig {
    INFO_ATTRIBUTE_NAMES(
            new StringArrValue(
                    new String[]{"Admin_Name", "Admin_Pin"}
            )
    ),

    INFO_ATTRIBUTE_SPECIFICATIONS(
            new StringArrValue(
                    new String[]{"Min. 8", "Min. 4"}
            )
    ),


    MIN_NAME_LENGTH(new IntValue(8)),
    MIN_PIN_LENGTH(new IntValue(4));

    private final ConfigValue configValue;

    AdminInfoConfig(ConfigValue configValue){
        this.configValue = configValue;
    }

    ConfigValue getConfigValue(){
        return this.configValue;
    }
}
