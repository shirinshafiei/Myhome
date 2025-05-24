package Devices;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Device {
    private String type;
    private String name;
    private int brightness;
    private int temperature;
    private Protocol deviceProtocol;
    private Status deviceStatus;

    private enum Protocol {
        WiFi,
        Bluetooth
    }

    private enum Status {
        on,
        off
    }

    private static Map<String, Device> devices = new LinkedHashMap<>();
    private static List<Rule> rules = new ArrayList<>();

    public Device(String type, String name, Protocol deviceProtocol) {
        this.type = type;
        this.name = name;
        this.deviceProtocol = deviceProtocol;
        this.deviceStatus = Status.off;
        this.brightness = 50;
        this.temperature = 20;
    }

    public static String addDevice(String type, String name, String protocol) {
        if (!type.equals("light") && !type.equals("thermostat")) {
            return "invalid input";
        }

        Protocol proto;
        try {
            proto = Protocol.valueOf(protocol);
        } catch (IllegalArgumentException e) {
            return "invalid input";
        }

        if (devices.containsKey(name)) {
            return "duplicate device name";
        }

        Device device;
        if (type.equals("light")) {
            device = new Device(type, name, proto);
        } else {
            device = new Device(type, name, proto);
        }
        devices.put(name, device);
        return "device added successfully";
    }

    public static String setDevice(String name, String property, String value) {
        if (!devices.containsKey(name)) {
            return "device not found";
        }

        Device device = devices.get(name);
        try {
            if (device.type.equals("light")) {
                if (property.equals("status")) {
                    device.deviceStatus = Status.valueOf(value);
                } else if (property.equals("brightness")) {
                    int brightness = Integer.parseInt(value);
                    if (brightness < 0 || brightness > 100) {
                        return "invalid value";
                    }
                    device.brightness = brightness;
                } else {
                    return "invalid property";
                }
            } else if (device.type.equals("thermostat")) {
                if (property.equals("status")) {
                    device.deviceStatus = Status.valueOf(value);
                } else if (property.equals("temperature")) {
                    int temp = Integer.parseInt(value);
                    if (temp < 10 || temp > 30) {
                        return "invalid value";
                    }
                    device.temperature = temp;
                } else {
                    return "invalid property";
                }
            }
            return "device updated successfully";
        } catch (IllegalArgumentException e) {
            return "invalid value";
        }
    }

    public static String removeDevice(String name) {
        if (!devices.containsKey(name)) {
            return "device not found";
        }

        rules.removeIf(rule -> rule.getDeviceName().equals(name));
        devices.remove(name);
        return "device removed successfully";
    }

    public static String listDevices() {
        if (devices.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Device device : devices.values()) {
            if (device.type.equals("light")) {
                sb.append(device.name).append(" ")
                        .append(device.deviceStatus).append(" ")
                        .append(device.brightness).append("% ")
                        .append(device.deviceProtocol).append("\n");
            } else {
                sb.append(device.name).append(" ")
                        .append(device.deviceStatus).append(" ")
                        .append(device.temperature).append("C ")
                        .append(device.deviceProtocol).append("\n");
            }
        }
        return sb.toString().trim();
    }

    public static String addRule(String name, String time, String action) {
        if (!devices.containsKey(name)) {
            return "device not found";
        }

        if (!time.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            return "invalid time";
        }

        if (!action.equals("on") && !action.equals("off")) {
            return "invalid action";
        }

        for (Rule rule : rules) {
            if (rule.getDeviceName().equals(name) && rule.getTime().equals(time)) {
                return "duplicate rule";
            }
        }

        rules.add(new Rule(name, time, action));
        return "rule added successfully";
    }

    public static String checkRules(String time) {
        if (!time.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
            return "invalid time";
        }

        for (Rule rule : rules) {
            if (rule.getTime().equals(time)) {
                Device device = devices.get(rule.getDeviceName());
                if (device != null) {
                    device.deviceStatus = Status.valueOf(rule.getAction());
                }
            }
        }

        return "rules checked";
    }

    public static String listRules() {
        if (rules.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Rule rule : rules) {
            sb.append(rule.getDeviceName()).append(" ")
                    .append(rule.getTime()).append(" ")
                    .append(rule.getAction()).append("\n");
        }
        return sb.toString().trim();
    }
}

class Rule {
    private String deviceName;
    private String time;
    private String action;

    public Rule(String deviceName, String time, String action) {
        this.deviceName = deviceName;
        this.time = time;
        this.action = action;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getTime() {
        return time;
    }

    public String getAction() {
        return action;
    }
}
