import Devices.Device;

public class Main {
    public static void main(String[] args) {
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        int q = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < q; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            String command = parts[0];
            String result = "";

            try {
                switch (command) {
                    case "add_device":
                        result = Device.addDevice(parts[1], parts[2], parts[3]);
                        break;
                    case "set_device":
                        result = Device.setDevice(parts[1], parts[2], parts[3]);
                        break;
                    case "remove_device":
                        result = Device.removeDevice(parts[1]);
                        break;
                    case "list_devices":
                        result = Device.listDevices();  // فراخوانی صحیح
                        break;
                    case "add_rule":
                        result = Device.addRule(parts[1], parts[2], parts[3]);
                        break;
                    case "check_rules":
                        result = Device.checkRules(parts[1]);
                        break;
                    case "list_rules":
                        result = Device.listRules();
                        break;
                    default:
                        result = "invalid input";
                }
            } catch (Exception e) {
                result = "invalid input";
            }

            System.out.println(result);
        }
    }
}