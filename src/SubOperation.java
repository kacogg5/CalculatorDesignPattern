/**
 * SubOperation:
 * implemented by Kaleb Coggins
 * on 2/18/2021
 *
 * Extends IBinaryOperation, performs subtraction of a - b.
 */

public class SubOperation implements IBinaryOperation {
    public String name() { return "-"; }

    public float calculate(float a, float b) {
        return a - b;
    }
}
