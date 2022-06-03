package call;

import ast.FunctionStatement;
import inter.Environment;
import inter.Interpreter;
import runtime.Return;

import java.util.List;

public class VoltFunction implements VoltCallable {

    private final FunctionStatement declaration;
    private final Environment closure;

    private final boolean isInitializer;

    public VoltFunction(FunctionStatement declaration, Environment closure,boolean isInitializer){
        this.closure = closure;
        this.declaration = declaration;
        this.isInitializer = isInitializer;
    }

    @Override
    public int arity() {
        return declaration.getParams().size();
    }

    public VoltFunction bind(VoltInstance instance) {
        Environment environment = new Environment(closure);
        environment.define("this", instance);
        return new VoltFunction(declaration, environment,isInitializer);
    }

    @Override
    public Object call(Interpreter interpreter, List<Object> arguments) {
        Environment environment = new Environment(closure);
        for(int i = 0 ; i < declaration.getParams().size() ; i++){
            environment.define(declaration.getParams().get(i).lexeme,arguments.get(i));
        }
        try {
            interpreter.execute(declaration.getFunctionBody(), environment);
        } catch (Return returnValue) {
            if (isInitializer) return closure.getAt(0, "this");
            return returnValue.getValue();
        }
        if (isInitializer) return closure.getAt(0, "this");
        return null;
    }
}