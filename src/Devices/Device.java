package Devices;

import java.util.ArrayList;


public class Device {
    private String type;
    private String name;
    private int brightness;
    private int temperature;
    private protocol deviceProtocol;
    private status deviceStatus;
    private enum protocol {
        Wifi,
        Bluetooth;
    }
    private enum status {
        on,
        off;
    }
    private ArrayList<Device> devices = new ArrayList<>();

    public Device(String type, String name, protocol deviceProtocol){
        this.type = type;
        this.name =name;
        this.deviceProtocol = deviceProtocol;
        this.deviceStatus = status.off;
    }

    public void addDevice(String type, String name, protocol deviceProtocol) {
        if (!(type.equals("light") || type.equals("thermostat"))) {
            throw new InvalidOperationException("invalid input");
        }
        if (deviceProtocol != protocol.Wifi && deviceProtocol != protocol.Bluetooth) {
            throw new InvalidOperationException("invalid protocol");
        }
        for (Device device : devices){
            if (device.name == name){
                throw new InvalidOperationException("duplicate device name");
            }
        }
        Device device = new Device(type, name, deviceProtocol);

        devices.add(device);

    }

    public void setDevice(String name, String property, Object value) {
        for (Device device : devices) {
            if (device.name.equals(name)) {
                switch (device.type) {
                    case "light":
                        if (property.equals("status")) {
                            device.deviceStatus = status.valueOf(value.toString().toUpperCase());
                        } else if (property.equals("brightness")) {
                            int brightness = Integer.parseInt(value.toString());
                            if (brightness < 0 || brightness > 100) {
                                throw new InvalidOperationException("invalid value");
                            }
                            device.brightness = brightness;
                        } else {
                            throw new InvalidOperationException("invalid property");
                        }
                        break;
                    case "thermostat":
                        if (property.equals("status")) {
                            device.deviceStatus = status.valueOf(value.toString().toUpperCase());
                        } else if (property.equals("temperature")) {
                            int temperature = Integer.parseInt(value.toString());
                            if (temperature < 10 || temperature > 30) {
                                throw new InvalidOperationException("invalid value");
                            }
                            device.temperature = temperature;
                        } else {
                            throw new InvalidOperationException("invalid property");
                        }
                        break;
                    default:
                        throw new InvalidOperationException("invalid device type");
                }
                return;
            }
        }
        throw new InvalidOperationException("device not found");
    }
    public void removeDevice(String name){
        boolean found = false;
        for (Device device : devices){
            if (device.name.equals(name)){
                devices.remove(device);
                found = true;
            }
        }
        if (!found){
            throw new InvalidOperationException("device not found");
        }
    }

}
