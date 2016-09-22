package edu.ycp.cs340.jsonparser;

public class JSONParser {
	private Lexer lexer;
	
	public JSONParser(Lexer lexer) {
		this.lexer = lexer;
	}
	
	public Node parseValue() {
		Node value = new Node(Symbol.VALUE);
		
		Token tok = lexer.peek();
		
		if (tok == null) {
			throw new ParserException("Unexpected end of input reading value");
		}
		
		if (tok.getSymbol() == Symbol.STRING_LITERAL) {
			// Value → StringLiteral
			value.getChildren().add(expect(Symbol.STRING_LITERAL));
		} else if (tok.getSymbol() == Symbol.INT_LITERAL) {
			// Value → IntLiteral
			value.getChildren().add(expect(Symbol.INT_LITERAL));
		} else if (tok.getSymbol() == Symbol.LBRACE) {
			// Value → Object
			value.getChildren().add(parseObject());
		} else if (tok.getSymbol() == Symbol.LBRACKET) {
			// Value → Array
			value.getChildren().add(parseArray());
		} else {
			throw new ParserException("Unexpected token looking for value: " + tok);
		}
		
		return value;
	}

	private Node parseObject() {
		Node object = new Node(Symbol.OBJECT);
		
		// Object → "{" OptFieldList "}"
		object.getChildren().add(expect(Symbol.LBRACE));
		object.getChildren().add(parseOptFieldList());
		object.getChildren().add(expect(Symbol.RBRACE));
		
		return object;
	}

	private Node parseOptFieldList() {
		//throw new UnsupportedOperationException("TODO -implement");
		Node OptFieldlist =new Node(Symbol.OPT_FIELD_LIST);

		if(lexer.peek().getSymbol() == Symbol.RBRACE) {
			//the next item is a }, epsilon transition			
		}
		else { // next symbol is a value_list
			OptFieldlist.getChildren().add(parseFieldList());
		}
		return OptFieldlist;
	}
	

	
	private Node parseFieldList() {
		//throw new UnsupportedOperationException("TODO -implement");
		Node fieldList = new Node(Symbol.FIELD_LIST);
		//System.out.println(lexer.peek());
		Node field = parseField();
		if(lexer.peek().getSymbol() == Symbol.COMMA){
			fieldList.getChildren().add(field);
			fieldList.getChildren().add(expect(Symbol.COMMA));
			fieldList.getChildren().add(parseFieldList());
		}
		else {
			fieldList.getChildren().add(field);
		}
		return fieldList;
	}
	
	private Node parseField() {
		Node field = new Node(Symbol.FIELD);
		if (lexer.peek().getSymbol() == Symbol.STRING_LITERAL) {
			// Value → StringLiteral
			field.getChildren().add(expect(Symbol.STRING_LITERAL));
			field.getChildren().add(expect(Symbol.COLON));
			field.getChildren().add(parseValue());
		}
		else {
			throw new ParserException("Unexpected token " + lexer.peek() + " Expected STRING_LITERAL ");
		}
		return field;
	}

	private Node parseArray() {
		Node array = new Node(Symbol.ARRAY);
		array.getChildren().add(expect(Symbol.LBRACKET));
		array.getChildren().add(parseOptValueList());
		array.getChildren().add(expect(Symbol.RBRACKET));
		return array;
	}
	
	private Node parseOptValueList(){
		//throw new UnsupportedOperationException("TODO -implement");
		Node value_list = new Node(Symbol.OPT_VALUE_LIST);

		if(lexer.peek().getSymbol() == Symbol.RBRACKET) {
			//the next item is a ], epsilon transition			
		}
		else { // next symbol is a value_list
			value_list.getChildren().add(parseValueList());
		}
		return value_list;
	}

	private Node parseValueList() {
		//throw new UnsupportedOperationException("TODO -implement");
		Node valueList = new Node(Symbol.VALUE_LIST);
		//System.out.println(lexer.peek());
		Node value = parseValue();
		if(lexer.peek().getSymbol() == Symbol.COMMA){
			valueList.getChildren().add(value);
			valueList.getChildren().add(expect(Symbol.COMMA));
			valueList.getChildren().add(parseValueList());
		}
		else {
			valueList.getChildren().add(value);
		}
		return valueList;
	}

	private Node expect(Symbol symbol) {
		Token tok = lexer.next();
		if (tok.getSymbol() != symbol) {
			throw new LexerException("Unexpected token " + tok + " (was expecting " + symbol + ")");
		}
		return new Node(tok);
	}
}
