package org.isu_std.user.userDocumentRequest.docReqManager;

import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;

public record UserInfoManager(User user, UserPersonal userPersonal) {
}
