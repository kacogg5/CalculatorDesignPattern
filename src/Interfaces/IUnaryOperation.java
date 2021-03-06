package Interfaces;

/**
 * Interfaces.IUnaryOperation:
 * implemented by Kaleb Coggins
 * on 2/18/2021
 *
 * Extends Interfaces.IOperation, enables operations with a single operand
 */

public interface IUnaryOperation extends IOperation {
    float calculate(float a);
}
