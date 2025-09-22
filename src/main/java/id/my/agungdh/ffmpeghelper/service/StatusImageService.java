package id.my.agungdh.ffmpeghelper.service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.util.Random;

@Service
public class StatusImageService {

    public void generate() throws IOException {
        // Timezone
        ZoneId zone = ZoneId.of("Asia/Jakarta");
        ZonedDateTime now = ZonedDateTime.now(zone);

        // Buat canvas
        int width = 500, height = 300;
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();

        // Background putih
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Judul timestamp
        g2d.setPaint(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 20));
        String timestamp = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
        g2d.drawString("Timestamp: " + timestamp, 20, 40);

        // Simulasi 4 data UP/DOWN
        String[] services = {"Service A", "Service B", "Service C", "Service D"};
        Random random = new Random();

        g2d.setFont(new Font("Arial", Font.PLAIN, 18));
        int y = 80;
        for (String service : services) {
            boolean up = random.nextBoolean(); // ganti dengan logika real kalau ada
            g2d.setPaint(up ? Color.GREEN : Color.RED);
            g2d.drawString(service + " : " + (up ? "UP" : "DOWN"), 20, y);
            y += 40;
        }

        g2d.dispose();

        // Simpan PNG
        File output = new File("status.png");
        ImageIO.write(img, "png", output);

        System.out.println("Generated: " + output.getAbsolutePath());
    }
}
