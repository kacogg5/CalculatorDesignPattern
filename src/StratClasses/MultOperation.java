package StratClasses;

import Interfaces.IBinaryOperation;

/**
 * StratClasses.MultOperation:
 * implemented by Kaleb Coggins
 * on 2/18/2021
 *
 * Extends Interfaces.IBinaryOperation, preforms multiplication of a * b.
 */

public class MultOperation implements IBinaryOperation {
    public String name() { return "*"; }

    public float calculate(float a, float b) { return a * b; }
}
