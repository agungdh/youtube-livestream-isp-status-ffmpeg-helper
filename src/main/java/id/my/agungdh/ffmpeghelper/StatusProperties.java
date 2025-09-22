package id.my.agungdh.ffmpeghelper;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "status")
public class StatusProperties {
    private String timezone = "Asia/Jakarta";
    private int width = 1280;
    private int height = 720;
    private String outputPath = "./current.png";
    private long fixedRateMs = 60_000;

    private String serviceMap = "google=8.8.8.8,cloudflare=1.1.1.1,router=192.168.1.1,modem=10.0.0.1";
    private String maskedServices = "router,modem";

    private int pingTimeoutMs = 1000;
    private boolean useShellPing = false;
    private int pingCount = 1;

    // getters/setters ...
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    public String getOutputPath() { return outputPath; }
    public void setOutputPath(String outputPath) { this.outputPath = outputPath; }
    public long getFixedRateMs() { return fixedRateMs; }
    public void setFixedRateMs(long fixedRateMs) { this.fixedRateMs = fixedRateMs; }

    public String getServiceMap() { return serviceMap; }
    public void setServiceMap(String serviceMap) { this.serviceMap = serviceMap; }
    public String getMaskedServices() { return maskedServices; }
    public void setMaskedServices(String maskedServices) { this.maskedServices = maskedServices; }

    public int getPingTimeoutMs() { return pingTimeoutMs; }
    public void setPingTimeoutMs(int pingTimeoutMs) { this.pingTimeoutMs = pingTimeoutMs; }
    public boolean isUseShellPing() { return useShellPing; }
    public void setUseShellPing(boolean useShellPing) { this.useShellPing = useShellPing; }
    public int getPingCount() { return pingCount; }
    public void setPingCount(int pingCount) { this.pingCount = pingCount; }
}
