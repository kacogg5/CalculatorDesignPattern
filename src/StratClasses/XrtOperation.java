package StratClasses;

import Interfaces.IBinaryOperation;

/**
 * StratClasses.SubOperation:
 * implemented by Kaleb Coggins
 * on 2/18/2021
 *
 * Extends Interfaces.IBinaryOperation, performs subtraction of a - b.
 */

public class XrtOperation implements IBinaryOperation {
    public String name() { return "xrt"; }

    public float calculate(float a, float b) {
        return (float)Math.pow(b, 1/a);
    }
}
