package ast;

import vis.ExpressionVisitor;

public class GroupingExp extends Expression{

    private Expression expression;

    public GroupingExp(Expression expression) {
        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public <R> R accept(ExpressionVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
