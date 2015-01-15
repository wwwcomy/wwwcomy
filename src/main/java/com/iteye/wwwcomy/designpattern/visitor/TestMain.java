package com.iteye.wwwcomy.designpattern.visitor;

/**
 * 访问者模式的目的是封装一些施加于某种数据结构元素之上的操作。
 * 一旦这些操作需要修改的话，接受这个操作的数据结构则可以保持不变。
 * 如本例：汽车作为结构元素保持不变，但是对车的操作可以是动态的，这些操作就是访问者。
 * 几个元素的对应：
 * 抽象访问者：ICarElementVisitor(接口)
 * 具体访问者：CarElementDoVisitor&CarElementPrintVisitor(访问者可以针对具体结构中的每个具体元素做出不同的事情)
 * 抽象元素：ICarElement(被访问的元素,接口)
 * 具体元素：Engine,Body...(被访问的具体类，抽象元素的实现类)
 * 结构对象：Car(实际上这货也可以是个具体元素，他其中包含了基本数据结构不会改变的元素，比如车肯定由轮子、发动机什么的构成)
 * 
 * @author xingnan.liu, Wincor Nixdorf International GmbH
 * @version $Revision$
 */
public class TestMain {
    public static void main(String[] args) {
        ICarElement car = new Car();
        car.accept(new CarElementPrintVisitor());
        car.accept(new CarElementDoVisitor());
    }
}
