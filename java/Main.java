import com.zai.AutoKomplete;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Please specify k value and filepath.");
            return;
        }

        int k = Integer.parseInt(args[0]);
        String filepath = args[1];
        
        try {
            AutoKomplete ak = new AutoKomplete(k, filepath);
            System.out.println(ak.autokomplete("ㄱ"));
            memoryStats();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public static void memoryStats() {
        int mb = 1024 * 1024;
        Runtime instance = Runtime.getRuntime();
        System.out.println("\n***** Heap utilization statistics [MB] *****");
        System.out.println("Total Memory : " + (instance.totalMemory() / mb) + "MB");
        System.out.println("Free Memory  : " + (instance.freeMemory() / mb) + "MB");
        System.out.println("Used Memory  : "
                + ((instance.totalMemory() - instance.freeMemory()) / mb) + "MB");
        System.out.println("Max Memory   : " + (instance.maxMemory() / mb) + "MB");
    }
}
