package lex;

import runtime.voltRuntime;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ModuleProcessor {
    private String currentFilePath;
    private final Stack<String> modulesStack = new Stack<>();
    private final Set<String> scannedModules = new HashSet<>();

    public String process(String filePath){
        this.modulesStack.add(filePath);
        this.scannedModules.add(filePath);
        this.currentFilePath = filePath;
        StringBuilder source = new StringBuilder();
        String currentFileSource = readFileSource(currentFilePath);
        while(!modulesStack.isEmpty()){
            currentFilePath = modulesStack.pop();
            currentFileSource = readFileSource(currentFilePath);
            source.append(currentFileSource);
        }
        return source.toString();
    }
    private String readFileSource(String path){
        StringBuilder lines = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while((line = br.readLine()) != null){
                if(line.trim().startsWith("module")) {
                    String moduleName = line.replace("module", "")
                                            .replaceAll(";", "").trim();
                    scanModuleName(moduleName);
                }else {
                    lines.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            voltRuntime.error("Invalid module path : " + path);
            System.exit(1);
        }
        return lines.toString();
    }
    private void scanModuleName(String moduleNameStr){
        String modulePath = "";
        if(moduleNameStr.startsWith("volt.")){
            String fileName = moduleNameStr.substring(5);
            fileName = fileName.replaceAll("\\.", "/");
            modulePath = "src/modules/" + fileName + ".vll";
        }else{
            String parentPath = new File(currentFilePath).getParent();
            modulePath = parentPath + "/" + moduleNameStr + ".vll";
        }
        boolean isUnique = scannedModules.add(modulePath);
        if(isUnique){
            modulesStack.add(modulePath);
        }
    }
}
