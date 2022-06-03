import runtime.voltRuntime;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

public class VFile {
    public static void main(String[] args) throws IOException{
        voltRuntime.runVoltFile(args[0]);
    }
}
