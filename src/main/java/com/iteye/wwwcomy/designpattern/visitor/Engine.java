package com.iteye.wwwcomy.designpattern.visitor;

class Engine implements ICarElement {
    public void accept(ICarElementVisitor visitor) {
        visitor.visit(this);
    }
}
