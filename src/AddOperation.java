/**
 * AddOperation:
 * implemented by Kaleb Coggins
 * on 2/18/2021
 *
 * Extends IBinaryOperation, preforms addition of a + b.
 */

public class AddOperation implements IBinaryOperation {
    public String name() { return "+"; }

    public float calculate(float a, float b) {
        return a + b;
    }
}
