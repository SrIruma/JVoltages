package inter;

import runtime.RuntimeError;
import token.Token;

import java.util.HashMap;
import java.util.Map;

public class Environment {
    public final Environment enclosing;
    private final Map<String, Object> valuesMap = new HashMap<>();

    public Environment(){
        enclosing = null;
    }
    public Environment(Environment enclosing){
        this.enclosing = enclosing;
    }
    public Object get(Token name){
        if(valuesMap.containsKey(name.lexeme)){
            return valuesMap.get(name.lexeme);
        }
        //IF THE VARIABLE ISN'T FOUND IN THIS SCOPE, WE SIMPLY TRY THE ENCLOSING ONE
        if(enclosing != null) return enclosing.get(name);
        throw new RuntimeError(name, "Undefined variable '"+ name.lexeme + "'.");
    }
    public void define(String name, Object value){
        valuesMap.put(name, value);
    }
    public boolean isContain(String name){
        return valuesMap.containsKey(name);
    }
    public void assign(Token name, Object value){
        if(valuesMap.containsKey(name.lexeme)){
            valuesMap.put(name.lexeme, value);
            return;
        }
        //AGAIN, IF THE VARIABLE ISN'T IN THIS ENVIRONMENT, IR CHECKS THE OUTER ONE, RECURSIVELY
        if(enclosing != null){
            enclosing.assign(name, value);
            return;
        }
        throw new RuntimeError(name, "Undefined variable '"+ name.lexeme + "'.");
    }
    void assignAt(int distance, Token name, Object value){
        ancestor(distance).valuesMap.put(name.lexeme, value);
    }
    public Object getAt(int distance, String name) {
        return ancestor(distance).valuesMap.get(name);
    }
    Environment ancestor(int distance) {
        Environment environment = this;
        for (int i = 0; i < distance; i++) {
            environment = environment.enclosing;
        }

        return environment;
    }
}