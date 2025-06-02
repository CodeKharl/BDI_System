package org.isu_std.admin.admin_main.admin_account_setting.admin_manage_account;

import org.isu_std.admin.admin_main.admin_account_setting.AdminAccSettingProcess;
import org.isu_std.io.SystemInput;
import org.isu_std.io.Util;
import org.isu_std.io.collections_enum.ChoiceCollection;

public class AdminManageAccount implements AdminAccSettingProcess {
    private final AdminManageAccController adminManageAccController;

    public AdminManageAccount(AdminManageAccController adminManageAccController){
        this.adminManageAccController = adminManageAccController;
    }

    @Override
    public void run(String sectionTitle) {
        while(true){
            Util.printSectionTitle(sectionTitle);
            Util.printInformation(adminManageAccController.getAdminDetails());

            if(!isConfirmToModify()){
                return;
            }


            if(setChosenAttribute()){

            }

            if(adminManageAccController.isModifySuccess()){
                Util.printMessage("Modify Success");
            }
        }
    }

    private boolean isConfirmToModify(){
        return SystemInput.isPerformConfirmed("Confirm to Modify",
                ChoiceCollection.CONFIRM.getValue(),
                ChoiceCollection.SUB_CANCEL.getValue()
        );
    }

    private boolean setChosenAttribute(){
        String[] adminAttributeNames = adminManageAccController.getAdminAttributeNames();

        do{
            int chosenAttributeNameIndex = getChosenAttrNameIndex(adminAttributeNames);

            if(chosenAttributeNameIndex == 0){
                return false;
            }


        }while (!isModificationDetailsConfirm());

        return true;
    }

    private int getChosenAttrNameIndex(String[] adminAttributeNames){
        int backValue = adminAttributeNames.length + 1;

        Util.printSubSectionTitle("Select Account to Detail to Modify :");
        Util.printChoices(adminAttributeNames);
        Util.printChoice("%d. Return to Account Setting".formatted(backValue));

        int choice = SystemInput.getIntChoice("Enter your choice : ", backValue);

        return choice != backValue ? choice : 0;
    }


    private boolean isModificationDetailsConfirm(){
        return false;
    }
}
