/**
 *
 */
 //
 // <expression> := <open-paren>*(<literal> | (<expression>|<literal>)<operator>(<expression>|<literal>))<clos-paren>*
 //
 // <operator>  := *|-|/|+
 // <literal>    := 0-9
 // <open-paren> := "("
 // <clos-paren> := ")"
 //
public class Calculator {
	
// 5 * 4 + 3 / 2
// 5 * (4+3) / 2
// 5 * 4 + (3/2)
// 5 * 4 + 3 / (2)
// (5*4) + (3/2)
	
}