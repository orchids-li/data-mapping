package cn.ting.er.datamapping;

import java.util.Objects;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class SourceSetting {
//    private Source source;
    private Setting setting;

//    public SourceSetting(Source source) {
//        this(source, new Setting());
//    }

    public SourceSetting(Setting setting) {
//        this.source = source;
        this.setting = Objects.requireNonNull(setting);
    }

    public Source getSource() {
        return null;
    }

//    public void setSource(Source source) {
//        this.source = source;
//    }

    public Setting getSetting() {
        return setting;
    }

    public void setSetting(Setting setting) {
        this.setting = setting;
    }
}
