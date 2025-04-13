package org.isu_std.config;

public enum SQLDatabaseConfig {
    URL("jdbc:mysql://localhost:3306/bdis_db"),
    ROOT_NAME("root"),
    PASSWORD("Kharl12");

    private final String value;

    SQLDatabaseConfig(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
