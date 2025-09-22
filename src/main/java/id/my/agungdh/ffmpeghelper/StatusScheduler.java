package id.my.agungdh.ffmpeghelper;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatusScheduler {
    private final StatusImageService svc;
    private final StatusProperties props;

    public StatusScheduler(StatusImageService svc, StatusProperties props) {
        this.svc = svc;
        this.props = props;
    }

    @Scheduled(fixedRateString = "#{@statusProperties.fixedRateMs}")
    public void run() {
        try {
            svc.generate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
