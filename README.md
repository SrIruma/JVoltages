<div align="center">

<img src="https://capsule-render.vercel.app/api?type=waving&height=120&color=0:1e1b4b,100:4c1d95"/>

# JVoltages

**"Voltage Runtime" — a from-scratch interpreter for the Volt programming
language, written entirely in Java.**

**Lexer** · **Parser** · **AST** · **Semantic resolver** · **Tree-walking interpreter**

</div>

---

## ⚠️ Project status

> **Maintenance mode / archived.** JVoltages was a hands-on language-design
> experiment and educational project. It works for its core feature set but is
> no longer actively developed — a new compiler is planned to supersede it.
> It is preserved here as a reference for compiler internals and as a record
> of the language's design.

---

## 🧩 What it is

JVoltages is an interpreter (and static checker) for **Volt**, a small
scripting language of my own design. Instead of depending on a parser
generator, every stage of the toolchain — tokenizing, parsing, semantic
analysis, and evaluation — is hand-written in Java, which makes it a useful
study of how a programming language actually works end-to-end.

## 🏗️ Architecture

```
 source (.vll)
      │
      ▼
 ┌─────────────┐   tokens   ┌────────────┐   AST   ┌──────────────┐
 │ VoltLexer   │───────────►│  Parser    │────────►│   Resolver   │  semantic analysis
 └─────────────┘            └────────────┘         └──────────────┘
                                                          │
                                                          ▼
 ┌─────────────┐    runtime error / value   ┌────────────────────────────┐
 │  Interpreter│◄───────────────────────────│  VoltCheckStyle (optional) │
 └─────────────┘                            └────────────────────────────┘
```

| Component | Package | Responsibility |
|-----------|---------|----------------|
| `VoltLexer` | `lex/` | Converts source text into tokens |
| `Parser` | `parser/` | Recursive-descent parser producing an AST |
| `Resolver` | `sem/` | Semantic analysis: variable resolution, scoping |
| `Interpreter` | `inter/` | Tree-walking evaluator |
| `VoltCheckStyle` | `style/` | Static style/sanity checker (constant folding hints, …) |
| Modules | `modules/` | Standard library bridging Java ↔ Volt |

The front-end follows the classic **lex → parse → resolve → interpret**
pipeline described in *Crafting Interpreters*, implemented here from scratch
and extended with Volt-specific features (native modules, a style checker, and
a REPL).

## 🚀 Getting started

### Requirements

- **JDK 8+**
- (Optional) an IDE with Java support

### Run the interactive REPL

```sh
# class entry point: Volt
javac -d out src/**/*.java src/*.java
java -cp out Volt
```

This launches the JVoltage terminal:

```
Welcome to JVoltage Terminal v1.2!
|> var name = "Volt";
|> print name;
```

### Run a Volt source file

```sh
# class entry point: VFile
java -cp out VFile path/to/your/file.volt
```

## ✍️ Language tour

Variables, functions, classes, arrays, control flow, ternaries, the elvis
(`?:`) operator, bitwise operations, and `repeat`/`test` constructs are all
first-class parts of the grammar.

```volt
// variables
var x = 42;
var msg = "hello";
var ok = true;

// control flow
if (x > 10) {
    print "big number";
} else {
    print "small number";
}

// a simple function
fun greet(name) {
    return "Hello, " + name;
}
print greet("Volt");

// classes + inheritance
class Animal {
    fun speak() { print "..." }
}
class Dog extends Animal {
    fun speak() { print "Woof!" }
}

// arrays
var arr = array[1, 2, 3];
print arr[0];

// test block (unit-style checks)
test "arithmetic" {
    // assertions
}
```

> The grammar is defined by the parser itself — see `src/parser/Parser.java`
> and `src/token/TokenType.java` for the authoritative token/keyword list.

## 📦 Standard library (native modules)

Volt ships standard-library modules implemented in Java and **declared in Volt
itself** `.vll` — a nice demonstration of splicing the host language and guest
language together.

| Module | Purpose | Highlights |
|--------|---------|------------|
| `system` | OS interaction | terminal, OS name/env/arch |
| `math` | Mathematics | `pow`, `sqrt`, `log`, trig, `max`/`min` |
| `string` | String manipulation | case, trim, split, regex match/replace, substring |
| `parse:json` | JSON support | parse objects/arrays, query values |

Modules are imported with the `module` directive, e.g.:

```volt
module volt.math;

print math.sqrt(16);   // 4.0
```

The Java side exposes functions via `@Library` / `@LibraryFunction`
annotations (see `src/modules/math.java`) and module resolution happens in
`src/lex/ModuleProcessor.java`.

## 🗂️ Repository layout

```
src/
├── lex/        # VoltLexer, ModuleProcessor (module resolution)
├── parser/     # recursive-descent parser
├── ast/        # abstract syntax tree node definitions
├── sem/        # Resolver (semantic analysis)
├── inter/      # Interpreter, Environment
├── style/      # VoltCheckStyle (static style checker)
├── modules/    # standard library (Java + .vll)
├── runtime/    # voltRuntime (REPL + file runner)
├── token/      # Token, TokenType
├── call/       # callables: functions, classes, instances, libraries
├── vis/        # Visitor interfaces
├── Volt.java   # REPL entry point
└── VFile.java  # file runner entry point
libs/           # packaged runtime jars
LICENSE
```

## 🛠️ Tech stack

- **Language:** Java (JDK 8+)
- **Design approach:** hand-written lexer/parser/interpreter (no generator tools)
- **Paradigms supported by Volt:** imperative, OOP (classes/inheritance),
  first-class functions, native host-language interop

## 📚 Motivation

JVoltages started as a way to understand compilers and interpreters at a deep
level. Building the whole pipeline by hand — rather than using a parser
generator — demystified how text becomes running code. It is being superseded
by a new, more ambitious compiler project, but the ideas here (native module
bridging, static style checking, full AST visitation) directly inform it.

## 📄 License

See [LICENSE](LICENSE).

---

<div align="center">

Built by [Carlos Espinoza](https://github.com/SrIruma).

</div>
