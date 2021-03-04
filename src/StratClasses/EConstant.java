package StratClasses;

import Interfaces.IConstant;

public class EConstant implements IConstant {
    public String name() { return "e"; }

    public float calculate() { return (float)Math.E; }
}
