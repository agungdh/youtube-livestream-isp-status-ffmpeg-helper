package id.my.agungdh.ffmpeghelper;

import java.util.*;
import java.util.stream.Collectors;

public class TargetConfig {
    public static class Entry {
        public final String name;
        public final String ip;
        public final boolean masked;
        public Entry(String name, String ip, boolean masked) {
            this.name = name; this.ip = ip; this.masked = masked;
        }
    }

    public static List<Entry> parse(StatusProperties props) {
        Set<String> masked = Arrays.stream(
                        Optional.ofNullable(props.getMaskedServices()).orElse("")
                                .split("\\s*,\\s*"))
                .filter(s -> !s.isBlank())
                .map(s -> s.toLowerCase(Locale.ROOT))
                .collect(Collectors.toSet());

        List<Entry> list = new ArrayList<>();
        String mapStr = Optional.ofNullable(props.getServiceMap()).orElse("");
        for (String pair : mapStr.split("\\s*,\\s*")) {
            if (pair.isBlank() || !pair.contains("=")) continue;
            String[] kv = pair.split("=", 2);
            String name = kv[0].trim();
            String ip = kv[1].trim();
            boolean isMasked = masked.contains(name.toLowerCase(Locale.ROOT));
            list.add(new Entry(name, ip, isMasked));
        }
        return list;
    }

    public static String maskIp(String ip) {
        return "xxx.xxx.xxx.xxx";
    }
}
