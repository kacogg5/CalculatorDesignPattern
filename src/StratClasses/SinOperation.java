package StratClasses;

import Interfaces.IUnaryOperation;

/**
 * StratClasses.SinOperation:
 * implemented by Kaleb Coggins
 * on 2/18/2021
 *
 * Implements Interfaces.IUnaryOperation, performs sine operation sin(a).
 */

public class SinOperation implements IUnaryOperation {
    public String name() { return "sin"; }

    public float calculate(float a) {
        return (float)Math.sin(a);
    }
}
