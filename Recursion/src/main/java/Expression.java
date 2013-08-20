import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * EXPR     = LITERAL  |  VARIABLE  |  EXPR OPERATOR EXPR  |  OPAREN EXPR CPAREN
 * OPERATOR = [+,-,*,/,&,|,^]
 * VARIABLE = ([a-z]|[A-Z])*
 * LITERAL  = [0-9]*
 * OPAREN   = (
 * CPAREN   = )
 * 
 * 
 * =====  1 Term   =====
 * Size	  Grouping
 * 1      A
 *
 * =====  2 Terms  =====
 * 1      A+B
 *
 * =====  3 Terms  =====
 * 1      A+B+C
 * 2      (A+B)+C
 * 2      A+(B+C)
 * 
 * =====  4 Terms  =====
 * 1	  A+B+C+D 		Y		1
 * 2	  (A+B)+C+D			
 * 2	  A+(B+C)+D             5
 * 2	  A+B+(C+D)				2
 * 2	  (A+B)+(C+D) 
 * 3      (A+B+C)+D		Y
 * 3      ((A+B)+C)+D	Y
 * 3      (A+(B+C))+D	Y
 * 3	  A+(B+C+D)		Y		4
 * 3	  A+((B+C)+D)	Y       6
 * 3	  A+(B+(C+D))	Y		3
 * 
 * 
 *
 * With Left Terms:  Make combinations using just "Left" if Left has 1 term; otherwise, make combinations using "Left" and "(Left)"
 * With Right Terms: Always put parenthesis around "Right" if Right has more than 1 term
 * 
 *
 * A+B+C+D
 * MP(A)     + MP(B+C+D):  A+(B+C+D)	A+(B+(C+D))   A+((B+C)+D)
 * MP(A+B)   + MP(C+D):    A+B+(C+D)   (A+B)+(C+D)
 * MP(A+B+C) + MP(D):      A+(B+C)+D   (A+B)+C+D      A+B+C+D		(A+(B+C))+D    ((A+B)+C)+D    (A+B+C)+D
 * 
 * A+B+C
 * MP(A)     + MP(B+C):	   A+(B+C)
 * MP(A+B)   + MP(C):      A+B+C,  (A+B)+C
 * 
 * B+C+D
 * MP(B)     + MP(C+D):	   B+(C+D)
 * MP(B+C)   + MP(D):      B+C+D,  (B+C)+D
 * 
 * 
 * A+B		A+B
 * B+C		B+C
 * C+D		C+D
 * A,B,C,D	A,B,C,D
 */
public class Expression {
	
	
	public static final String DIGITS      = "0123456789";
	public static final String OPERATORS   = "+-/*|^&";
	public static final String OP_MULT_DIV = "*/";
	public static final String OPEN_PAREN  = "(";
	public static final String CLOSE_PAREN = ")";
	public static final String TRUE        = "true";
	public static final String FALSE       = "false";
	public static final String DIV_BY_ZERO = "DIV_BY_ZERO";
	
	/**
	 * 
	 * @param expression
	 * @return
	 */
	public static List<String> makeExpressions(String expression) {

		// Validate input and cleanse 
		if (expression == null || expression.length() <= 0) {
			return new ArrayList<String>();
		}
		expression = expression.replaceAll(" ", "");
		return makeExpressionsHelper(expression);
	}

	
	
	
	private static List<String> makeExpressionsHelper(String expression) {

		List<String> expressions = new ArrayList<String>();
		
		// Validate input and cleanse 
		if (expression == null || expression.length() <= 0) {
			System.out.println("Should Never Be Here [" + expression + "]");
			return new ArrayList<String>();
		}
		
		// Base Case - return when we can't make any more combinations
		int numTerms = new StringTokenizer(expression, OPERATORS).countTokens();
		if (numTerms <= 2) {
			expressions.add(expression);
			return expressions;
		}

		// Recurse with subexpressions on the left and right
		for (int termsToGet=1; termsToGet < numTerms; termsToGet++) {
			
			String[] terms = splitExpression(termsToGet, expression);
			String lExpr = terms[0];
			String op    = terms[1];
			String rExpr = terms[2];

			List<String> lExprs = makeExpressionsHelper(lExpr);
			List<String> rExprs = makeExpressionsHelper(rExpr);
			expressions.addAll(makeCombinations(lExprs, op, rExprs));
		}
		
		return expressions;
	}


	/**
	 * With Left Terms:  Make combinations using just "Left" if Left has 1 term; otherwise, make combinations using "Left" and "(Left)"
	 * With Right Terms: Always put parenthesis around "Right" if Right has more than 1 term
	 */
	private static List<String> makeCombinations(List<String> lExprs, String op, List<String> rExprs) {
	
		List<String> combos = new ArrayList<String>();
		
		int lSize = lExprs.size();
		int rSize = rExprs.size();
		
		for (int lIdx=0; lIdx < lSize; lIdx++) {

			String lExpr   = lExprs.get(lIdx);
			int lTermCount = new StringTokenizer(lExpr, OPERATORS).countTokens();

			for (int rIdx=0; rIdx < rSize; rIdx++) {
			
				String rExpr = rExprs.get(rIdx);
				int rTermCount = new StringTokenizer(rExpr, OPERATORS).countTokens();
				
				if (lTermCount > 1 && rTermCount > 1) {
					combos.add(      lExpr       + op + "(" + rExpr + ")");
					combos.add("(" + lExpr + ")" + op + "(" + rExpr + ")");
				}
				else if (lTermCount > 1) {
					combos.add(      lExpr       + op + rExpr);
					combos.add("(" + lExpr + ")" + op + rExpr);
				}
				else if (rTermCount > 1) {
					combos.add(lExpr + op + "(" + rExpr + ")");
				}
				else {
					combos.add(lExpr + op + rExpr);
				}
			}
		}
		
		return combos;
	}
	

	private static String[] splitExpression(int termsOnLeft, String expression) {
		
		String[] split = new String[3];
		StringTokenizer st = new StringTokenizer(expression, OPERATORS, true);

		// left terms
		split[0] = st.hasMoreTokens() ? st.nextToken() : "";
		for (int i=1; i < termsOnLeft; i++) {
			split[0] += st.nextToken();
			split[0] += st.nextToken();
		}

		// operator
		split[1] = st.hasMoreTokens() ? st.nextToken() : "";

		// right terms
		split[2] = "";
		while (st.hasMoreTokens()) {
			split[2] += st.nextToken();
		}
		
		return split;
	}
	
	

	
	public static String solve(String expression) {

		StringTokenizer st = new StringTokenizer(expression, OPERATORS + OPEN_PAREN + CLOSE_PAREN, true);
		LinkedList<String> symbols = new LinkedList<String>();
		while (st.hasMoreTokens()) {
			symbols.add(st.nextToken());
		}

		return solve(symbols);
	}

	/**
	 * A full reduction is triggered when we encounter:
	 * 1) end of symbols
	 * 2) a closing parenthesis
	 * 
	 * A partial reduction is triggered when we encounter:
	 * 1) a multiplication (use left term)
	 * 
	 * A recursion is triggered when we encounter
	 * 1) an open parenthesis
	 * 
	 * @param symbols
	 * @return
	 */
	private static String solve(LinkedList<String> symbols) {

		String lastOp = "";
		LinkedList<String> results = new LinkedList<String>(); // stack to track nested results
		while (!symbols.isEmpty()) {
			
			String symbol = symbols.remove();
			if (isOpenParen(symbol)) {
				results.push(solve(symbols));
			}
			else if (isCloseParen(symbol)) {
				break;
			}
			else if (isLiteral(symbol)) {
				results.push(symbol);
			}
			else if (isOperator(symbol)) {
				lastOp = symbol;
				results.push(symbol);
			}
			else {
				System.out.println("Error: Unexpected symbol in expression[" + symbol + "]");
				return null;
			}

			// Partial reduction if multiply or divide op
			if (isMultiplyOrDivideOp(lastOp) && !symbol.equals(lastOp) && results.size() > 2) {
				lastOp = "";
				String rTerm = results.pop();
				String op    = results.pop();
				String lTerm = results.pop();
				results.push(eval(lTerm, op, rTerm));
			}
		}
		
		// perform full reduction - should contain just addition and subtraction
		String result = results.isEmpty() ? null : results.pop();
		while (!results.isEmpty()) {
			String op    = results.pop();
			String lTerm = results.pop();
			result = eval(lTerm, op, result);
		}

		return result;
	}
	
	
	private static String eval(String lTerm, String op, String rTerm) {

		String result = "";
		if (lTerm == null || op == null || rTerm == null) {
			return null;
		}
		else if (lTerm.equals(DIV_BY_ZERO) || rTerm.equals(DIV_BY_ZERO)) {
			return DIV_BY_ZERO;
		}
		else if (op.equals("+")) { 
			return String.valueOf(Integer.valueOf(lTerm) + Integer.valueOf(rTerm));
		}
		else if (op.equals("-")) {
			return String.valueOf(Integer.valueOf(lTerm) - Integer.valueOf(rTerm));
		}
		else if (op.equals("*")) {
			return String.valueOf(Integer.valueOf(lTerm) * Integer.valueOf(rTerm));
		}
		else if (op.equals("/")) {
			int rInt = Integer.valueOf(rTerm);
			if (rInt == 0) {
				return DIV_BY_ZERO;
			}
			else {
				return String.valueOf(Integer.valueOf(lTerm) / rInt);
			}
		}
		else if (op.equals("|")) {
			boolean lBool = lTerm.equals("1") || Boolean.valueOf(lTerm);
			boolean rBool = rTerm.equals("1") || Boolean.valueOf(rTerm);
			return String.valueOf(lBool | rBool);
		}
		else if (op.equals("&")) {
			boolean lBool = lTerm.equals("1") || Boolean.valueOf(lTerm);
			boolean rBool = rTerm.equals("1") || Boolean.valueOf(rTerm);
			return String.valueOf(lBool & rBool);
		}
		else if (op.equals("^")) {
			boolean lBool = lTerm.equals("1") || Boolean.valueOf(lTerm);
			boolean rBool = rTerm.equals("1") || Boolean.valueOf(rTerm);
			return String.valueOf(lBool ^ rBool);
		}
		
		return result;
	}
	
	

	public static final boolean isLiteral(String s) {
		return isNumber(s) || isBoolean(s);
	}
	
	public static final boolean isBoolean(String s) {
		return TRUE.equalsIgnoreCase(s) || FALSE.equalsIgnoreCase(s);
	}
	
	public static final boolean isNumber(String s) {
		
		for (char c : s.toCharArray()) {
			if (!isDigit(c)) {
				return false;
			}
		}
		
		return true;
	}	

	public static final boolean isDigit(char c) {
		return DIGITS.indexOf(c) >= 0;
	}
	
	public static final boolean isOperator(String s) {
		return OPERATORS.indexOf(s) >= 0;
	}	

	public static final boolean isMultiplyOrDivideOp(String s) {
		return OP_MULT_DIV.indexOf(s) >= 0;
	}	

	public static final boolean isOpenParen(String s) {
		return OPEN_PAREN.equals(s);
	}	
	
	public static final boolean isCloseParen(String s) {
		return CLOSE_PAREN.equals(s);
	}	

	
	public static void main(String[] args) {

		String expr = String.valueOf((int)(Math.random() * 50));
		for (int i=0; i < 4; i++) {
			String op  = String.valueOf(OPERATORS.charAt((int)(Math.random() * 4)));
			String val = String.valueOf((int)(Math.random() * 100));
			expr += op + val;
		}
		
		List<String> expressions = Expression.makeExpressions(expr);
		for (String expression : expressions) {
			System.out.println(expression);// + " = " + Expression.solve(expression));
		}
		System.out.println();
		System.out.println("Total Expressions: " + expressions.size());
	}
	
}
