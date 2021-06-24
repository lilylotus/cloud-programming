package cn.nihiltiy.diversity.multiple.datasource.config;

public enum DataSourceEnum {
    WRITE("write"),
    READ("read");

    private String datasourceName;

    DataSourceEnum(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getDatasourceName() {
        return datasourceName;
    }
}
