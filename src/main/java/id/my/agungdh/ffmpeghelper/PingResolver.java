package id.my.agungdh.ffmpeghelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.time.Duration;

public class PingResolver {

    public static boolean isUp(String ip, StatusProperties props) {
        return props.isUseShellPing() ? shellPing(ip, props) : jrePing(ip, props);
    }

    private static boolean jrePing(String ip, StatusProperties props) {
        try {
            InetAddress addr = InetAddress.getByName(ip);
            return addr.isReachable(props.getPingTimeoutMs());
        } catch (Exception e) {
            return false;
        }
    }

    private static boolean shellPing(String ip, StatusProperties props) {
        try {
            int count = Math.max(1, props.getPingCount());
            int timeoutSec = Math.max(1, (int)Math.ceil(props.getPingTimeoutMs() / 1000.0));
            String[] cmd = new String[]{"sh", "-c", "ping -c " + count + " -W " + timeoutSec + " " + ip};

            Process p = new ProcessBuilder(cmd).start();
            boolean finished = p.waitFor(Duration.ofSeconds(timeoutSec + count + 2).toMillis(),
                    java.util.concurrent.TimeUnit.MILLISECONDS);
            if (!finished) {
                p.destroyForcibly();
                return false;
            }
            return p.exitValue() == 0;
        } catch (Exception e) {
            return false;
        }
    }
}
