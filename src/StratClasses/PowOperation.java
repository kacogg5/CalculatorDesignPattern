package StratClasses;

import Interfaces.IBinaryOperation;

/**
 * StratClasses.SubOperation:
 * implemented by Kaleb Coggins
 * on 2/18/2021
 *
 * Extends Interfaces.IBinaryOperation, performs subtraction of a - b.
 */

public class PowOperation implements IBinaryOperation {
    public String name() { return "^"; }

    public float calculate(float a, float b) {
        return (float)Math.pow(a, b);
    }
}
