package StratClasses;

import Interfaces.IUnaryOperation;

/**
 * StratClasses.CosOperation:
 * implemented by Kaleb Coggins
 * on 2/18/2021
 *
 * Implements Interfaces.IUnaryOperation, performs cosine operation cos(a).
 */

public class LnOperation implements IUnaryOperation {
    public String name() { return "ln"; }

    public float calculate(float a) {
        return (float)Math.log(a);
    }
}
