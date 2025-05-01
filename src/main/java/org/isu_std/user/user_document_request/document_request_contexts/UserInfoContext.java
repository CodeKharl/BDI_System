package org.isu_std.user.user_document_request.document_request_contexts;

import org.isu_std.models.User;
import org.isu_std.models.UserPersonal;

public record UserInfoContext(User user, UserPersonal userPersonal) {
}
