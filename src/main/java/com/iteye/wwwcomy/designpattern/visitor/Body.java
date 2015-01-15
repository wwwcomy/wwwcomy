package com.iteye.wwwcomy.designpattern.visitor;

public class Body implements ICarElement {
    public void accept(ICarElementVisitor visitor) {
        visitor.visit(this);
    }
}
