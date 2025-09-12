package ecommerceautomation.base;

import java.io.InputStream;
import java.util.Properties;

/**
 * ConfigReader:
 * - Loads values from src/main/resources/config.properties
 * - Allows overriding via system property (-Dkey=value)
 */
public final class ConfigReader {

    private static final String CONFIG_FILE = "config.properties";
    private static final Properties props = new Properties();

    static {
        try (InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (is == null) {
                throw new RuntimeException("Configuration file '" + CONFIG_FILE + "' not found in classpath");
            }
            props.load(is);
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Failed to load " + CONFIG_FILE + ": " + e.getMessage());
        }
    }

    private ConfigReader() { /* prevent instantiation */ }

    public static String get(String key) {
        // System property override > config.properties
        String sys = System.getProperty(key);
        if (sys != null && !sys.isEmpty()) {
            return sys.trim();
        }
        String val = props.getProperty(key);
        return val == null ? null : val.trim();
    }

    public static String get(String key, String defaultValue) {
        String v = get(key);
        return v == null ? defaultValue : v;
    }

    public static int getInt(String key, int defaultValue) {
        String v = get(key);
        if (v == null) return defaultValue;
        try {
            return Integer.parseInt(v);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String v = get(key);
        if (v == null) return defaultValue;
        return Boolean.parseBoolean(v);
    }
}
