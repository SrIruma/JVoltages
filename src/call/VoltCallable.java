package call;

import inter.Interpreter;
import java.util.List;

public interface VoltCallable {
    int arity();
    Object call(Interpreter interpreter, List<Object> arguments);
}

