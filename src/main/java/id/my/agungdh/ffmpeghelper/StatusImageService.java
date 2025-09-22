package id.my.agungdh.ffmpeghelper;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class StatusImageService {

    private final StatusProperties props;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

    public StatusImageService(StatusProperties props) {
        this.props = props;
        System.setProperty("java.awt.headless", "true");
    }

    public void generate() throws IOException {
        int W = props.getWidth(), H = props.getHeight();
        BufferedImage img = new BufferedImage(W, H, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = img.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g.setPaint(Color.WHITE);
        g.fillRect(0, 0, W, H);

        // Header
        g.setPaint(new Color(245, 245, 245));
        g.fillRect(0, 0, W, 90);

        g.setPaint(Color.BLACK);
        g.setFont(new Font("SansSerif", Font.BOLD, 36));
        g.drawString("Status Dashboard", 40, 55);

        ZoneId zone = ZoneId.of(props.getTimezone());
        String timestamp = ZonedDateTime.now(zone).format(fmt);
        g.setFont(new Font("SansSerif", Font.PLAIN, 20));
        g.drawString(timestamp, 40, 80);

        // Targets
        List<TargetConfig.Entry> targets = TargetConfig.parse(props);
        int startY = 150, lineH = 60;

        for (int i = 0; i < targets.size(); i++) {
            TargetConfig.Entry t = targets.get(i);
            boolean up = PingResolver.isUp(t.ip, props);
            int y = startY + i * lineH;

            // bullet
            g.setPaint(up ? new Color(34, 197, 94) : new Color(239, 68, 68));
            g.fillOval(40, y - 18, 16, 16);

            // name + ip
            g.setPaint(Color.DARK_GRAY);
            g.setFont(new Font("SansSerif", Font.BOLD, 24));
            String ipShown = t.masked ? TargetConfig.maskIp(t.ip) : t.ip;
            g.drawString(t.name + " (" + ipShown + ")", 70, y);

            // status
            String label = up ? "UP" : "DOWN";
            g.setPaint(up ? new Color(34, 197, 94) : new Color(239, 68, 68));
            int textW = g.getFontMetrics().stringWidth(label);
            g.drawString(label, W - 40 - textW, y);
        }

        g.dispose();

        // Atomic write
        Path target = Paths.get(props.getOutputPath());
        Path tmp = target.resolveSibling(target.getFileName().toString() + ".tmp");
        Files.createDirectories(target.getParent() == null ? Paths.get(".") : target.getParent());
        ImageIO.write(img, "png", tmp.toFile());
        Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
    }
}
