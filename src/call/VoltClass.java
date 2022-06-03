package call;

import inter.Environment;
import inter.Interpreter;

import java.util.List;
import java.util.Map;

public class VoltClass implements VoltCallable{

    private final String name;
    private final VoltClass superClass;
    private final Environment environment;
    private final Map<String, VoltFunction> methods;

    public VoltClass(String name, VoltClass superClass, Environment environment, Map<String, VoltFunction> methods) {
        this.name = name;
        this.superClass = superClass;
        this.methods = methods;
        this.environment = environment;
    }

    public String getName() {
        return name;
    }

    public VoltClass getSuperClass() {
        return superClass;
    }

    public Map<String, VoltFunction> getMethods() {
        return methods;
    }

    public void addMethod(String name, VoltFunction function){
        methods.put(name, function);
    }

    public VoltFunction findMethod(String name) {
        if (methods.containsKey(name)) {
            return methods.get(name);
        }
        //if can't find this method in class check if this method is from super class
        if (superClass != null) {
            return superClass.findMethod(name);
        }
        return null;
    }

    public Environment getEnvironment(){
        return environment;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int arity() {
        VoltFunction initializer = findMethod("init");
        if (initializer == null) return 0;
        return initializer.arity();
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        VoltInstance instance = new VoltInstance(this);
        //for Constructing the class
        VoltFunction initializer = findMethod("init");
        if (initializer != null) {
            initializer.bind(instance).call(interpreter, arguments);
        }
        return instance;
    }
}