# Calculator Strategy Pattern
A demonstration of the composition and strategy design patterns as used in a basic calculator app.

## Composition Design Pattern
A complex problem is solved by dividing it into a series of simple subproblems. In our calculator, each mathematical expression is divided into individiual operations and evaluated in a tree structure.

## Strategy Design Pattern
In a context where the primary problem can change, multiple strategies are implemented and chosen at runtime. In our calculator, mathematical expressions can contain varying combinations of operators and functions. Therefore each operator, function, or mathematical constant is defined as a strategy class which can be resolved to a numeric solution by calling its evaluate method.
