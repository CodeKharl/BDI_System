package org.isu_std.models.model_builders;

public class BuilderFactory {
    private BuilderFactory(){}

    public static AdminBuilder createAdminBuilder(){
        return new AdminBuilder();
    }

    public static BarangayBuilder createBarangayBuilder(){
        return new BarangayBuilder();
    }

    public static UserBuilder createUserBuilder(){
        return new UserBuilder();
    }

    public static DocumentBuilder createDocumentBuilder(){
        return new DocumentBuilder();
    }

    public static PaymentBuilder createPaymentBuilder(){
        return new PaymentBuilder();
    }

    public static UserPersonalBuilder createUserPersonalBuilder(){
        return new UserPersonalBuilder();
    }
}
