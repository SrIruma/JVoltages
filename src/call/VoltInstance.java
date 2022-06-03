package call;

import runtime.RuntimeError;
import token.Token;

import java.util.HashMap;
import java.util.Map;

public class VoltInstance {

    private VoltClass voltClass;
    private final Map<String, Object> fields = new HashMap<>();

    public VoltInstance(VoltClass voltClass) {
        this.voltClass = voltClass;
    }

    public VoltClass getTankClass() {
        return voltClass;
    }

    public void set(Token name,Object value){
        fields.put(name.lexeme,value);
    }

    public Object get(Token name){
        if (fields.containsKey(name.lexeme)) {
            return fields.get(name.lexeme);
        }
        VoltFunction method = voltClass.findMethod(name.lexeme);
        if (method != null) return method.bind(this);
        throw new RuntimeError(name,"Undefined property '" + name.lexeme + "'.");
    }

    @Override
    public String toString() {
        return voltClass.getName() + " instance";
    }
}

