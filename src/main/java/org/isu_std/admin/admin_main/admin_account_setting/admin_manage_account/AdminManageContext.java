package org.isu_std.admin.admin_main.admin_account_setting.admin_manage_account;

import org.isu_std.client_context.AdminContext;
import org.isu_std.models.model_builders.AdminBuilder;

class AdminManageContext {
    private final AdminContext adminContext;
    private final String[] adminAttributeNames;
    private final String[] adminAttributeSpecs;
    private final AdminBuilder adminBuilder;

    private String chosenAdminAttrName;

    protected AdminManageContext(
            AdminContext adminContext,
            AdminBuilder adminBuilder,
            String[] adminAttributeNames,
            String[] adminAttributeSpecs
    ){
        this.adminContext = adminContext;
        this.adminBuilder = adminBuilder;
        this.adminAttributeNames = adminAttributeNames;
        this.adminAttributeSpecs = adminAttributeSpecs;
    }

    protected AdminContext getAdminContext(){
        return this.adminContext;
    }

    protected AdminBuilder getAdminBuilder(){
        return this.adminBuilder;
    }

    protected String[] getAdminAttributeNames(){
        return this.adminAttributeNames;
    }

    protected String getChosenAdminAttributeSpec(int index){
        return adminAttributeSpecs[index];
    }

    protected String getChosenAdminAttrName(){
        return this.chosenAdminAttrName;
    }

    protected void setChosenAdminAttrName(String chosenAdminAttrName){
        this.chosenAdminAttrName = chosenAdminAttrName;
    }

}
