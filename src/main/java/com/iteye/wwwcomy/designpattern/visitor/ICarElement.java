package com.iteye.wwwcomy.designpattern.visitor;

public interface ICarElement {
    void accept(ICarElementVisitor visitor); // CarElements have to provide accept().
}
