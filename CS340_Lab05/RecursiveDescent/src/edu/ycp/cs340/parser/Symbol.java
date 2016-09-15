package edu.ycp.cs340.parser;

public enum Symbol {
	// Terminal symbols
	IDENTIFIER,
	INT_LITERAL,
	PLUS,
	MINUS,
	TIMES,
	DIVIDES,
	
	// Nonterminal symbols
	S,
	E,
	E_PRIME,
	T,
	T_PRIME,
	F
	;
	
	/**
	 * Convert a single character value to a (terminal) Symbol.
	 * 
	 * @param ch a character
	 * @return the Symbol
	 */
	public static Symbol fromCharacter(int ch) {
		if (Character.isDigit(ch)) {
			return INT_LITERAL;
		} else if (Character.isLetter(ch)) {
			return IDENTIFIER;
		} else {
			switch (ch) {
			case '+':
				return PLUS;
			case '-':
				return MINUS;
			case '*':
				return TIMES;
			case '/':
				return DIVIDES;
			default:
				throw new LexerException("Unknown character: " + ((char)ch));
			}
		}
	}
}
