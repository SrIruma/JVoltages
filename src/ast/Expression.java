package ast;

import vis.ExpressionVisitor;

public abstract class Expression {
    public abstract <R> R accept(ExpressionVisitor<R> visitor);
}
