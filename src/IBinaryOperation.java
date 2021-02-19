/**
 * IBinaryOperation:
 * implemented by Kaleb Coggins
 * on 2/18/2021
 *
 * Extends IOperation, enables operations with 2 operands
 */

public interface IBinaryOperation extends IOperation {
    float calculate(float a, float b);
}
