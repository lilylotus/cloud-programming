package cn.nihiltiy.diversity.multiple.datasource.config;


public class DataSourceContextHolder {

    private static final ThreadLocal<String> CTX = new ThreadLocal<>();

    /**
     * 赋值
     *
     */
    public static void set(String datasourceType) {
        CTX.set(datasourceType);
    }

    /**
     * 获取值
     */
    public static String get() {
        return CTX.get();
    }

    public static void clear() {
        CTX.remove();
    }

    private DataSourceContextHolder() {}

}
