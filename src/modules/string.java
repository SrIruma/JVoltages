package modules;

import ast.Array;

@Library
public class string {
    @LibraryFunction
    public int Length(String string){
        return string.length();
    }
    @LibraryFunction
    public boolean Match(String string, String regex){
        return string.matches(regex);
    }
    @LibraryFunction
    public String Concat(String first, String second){
        return first.concat(second);
    }
    @LibraryFunction
    public boolean Contains(String string, String sequence){
        return string.contains(sequence);
    }
    @LibraryFunction
    public String Trim(String string){
        return string.trim();
    }
    @LibraryFunction
    public String ToUpperCase(String string){
        return string.toUpperCase();
    }
    @LibraryFunction
    public String ToLowerCase(String string){
        return string.toLowerCase();
    }
    @LibraryFunction
    public boolean StartWith(String string, String sequence) {
        return string.startsWith(sequence);
    }
    @LibraryFunction
    public boolean EndWith(String string, String sequence){
        return string.endsWith(sequence);
    }
    @LibraryFunction
    public Integer IndexOf(String string, String sequence){
        return string.indexOf(sequence);
    }
    @LibraryFunction
    public Integer LastIndexOf(String string, String sequence){
        return string.lastIndexOf(sequence);
    }
    @LibraryFunction
    public String ReplaceFirst(String string, String regex, String replacement){
        return string.replaceFirst(regex, replacement);
    }
    @LibraryFunction
    public String ReplaceAll(String string, String regex, String replacement){
        return string.replaceAll(regex,replacement);
    }
    @LibraryFunction
    public Character CharAt(String string, Double index) {
        return string.charAt(index.intValue());
    }
    @LibraryFunction
    public String Substring(String string, Double start, Double end){
        return string.substring(start.intValue(), end.intValue());
    }
    @LibraryFunction
    public boolean IsEmpty(String string){
        return string.isEmpty();
    }
    @LibraryFunction
    public Array Split(String string, String regex){
        return new Array(string.split(regex));
    }
    @LibraryFunction
    public Integer CompareTo(String first, String second){
        return first.compareTo(second);
    }
}
