parser grammar CarbonParser;

options { tokenVocab=CarbonLexer; }

compilationUnit
    : (statement ';')*
    ;

typeLiteral
    : '{' ((members+=parameter | derivedMembers+=statement) (',' (members+=parameter | derivedMembers+=statement))* ','?) ? '}'
    //| enum_list
    ;

statement
    // Can this grammar rule be simplified?
    // hasParameterList is a horrible solution to determining if parens were used, but the only one I can think of
    : declaration (hasParameterList='(' (parameters+=parameter (',' parameters+=parameter)*)? ')')? '=' expression
    ;

expression
    : base=expression '(' first_argument=expression? other_arguments+=expression_item* ')' # ApplicationExpression
    | lhs=expression op=SYMBOL1 rhs=expression # InfixExpression
    | lhs=expression op=(':' | SYMBOL2) rhs=expression # InfixExpression
    | base=expression '.' identifier # DotExpression
    | terminalExpression # Terminal
    ;

expression_item
    : ',' expression
    | ','
    ;

terminalExpression
    : typeLiteral
    | numberLiteral
    | stringLiteral
    | identifier
    ;

numberLiteral: '-'? NUMBER;

stringLiteral: STRING;

//expression
//    : value_expression
//    //| compound_expression
//    ;

//value_expression
//    : term_expression (SYMBOL term_expression)*
//    ;
//
//term_expression
//    : NUMBER
//    | '(' value_expression ')'
//    | identifier
//    | term_expression '.' identifier
//    | term_expression '(' expression_list ')'
//    ;

parameter
    : name=LABEL ':' type
    | type
    ;

declaration
    : LABEL ':' type
    | LABEL
    ;

type
    : identifier ('[' refinement_list ']')?
    ;

refinement_list
    : refinement (',' refinement)* // that must typecheck to bool
    ;

refinement         // What about Size == 5
    : //SYMBOL expression
    | identifier
//    | identifier '(' expression_list ')'
    ;


//compound_expression
//    : '{' statement_list '}'
//    ;

//statement_list
//    : statement*
//    ;

//parameterization
//    : '(' parameter_list ')'
//    ;

//parameter_list
//    : parameter (',' parameter)*
//    ;


identifier
    : LABEL
    | SYMBOL1 | SYMBOL2 // Is this even an identifier?
    //: LABEL ('.' LABEL)* // Just one level?
    ;