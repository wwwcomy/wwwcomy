package com.iteye.wwwcomy.designpattern.visitor;

public interface ICarElementVisitor {
    void visit(Wheel wheel);

    void visit(Engine engine);

    void visit(Body body);

    void visit(Car car);
}
