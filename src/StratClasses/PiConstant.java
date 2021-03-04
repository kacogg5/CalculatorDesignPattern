package StratClasses;

import Interfaces.IConstant;

public class PiConstant implements IConstant {
    public String name() { return "pi"; }

    public float calculate() { return (float)Math.PI; }
}
