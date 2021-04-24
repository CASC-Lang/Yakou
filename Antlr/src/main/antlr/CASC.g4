//header
grammar CASC;

@header {
package io.github.chaosunity.antlr;
}

//RULES
compilationUnit             : classDeclaration EOF;
classDeclaration            : CLASS className '{' classBody '}';
className                   : ID;
classBody                   : function* ;
function                    : functionDeclaration '{' (blockStatement)* '}' ;
functionDeclaration         : FUNC (type)? functionName '('(functionArgument)*')' ;
functionName                : ID ;
functionArgument            : type ID functionParamdefaultValue? ;
functionParamdefaultValue   : '=' expression ;
type                        : primitiveType
                            | classType ;

primitiveType   :  'boolean' ('[' ']')*
                |  'string' ('[' ']')*
                |  'char' ('[' ']')*
                |  'byte' ('[' ']')*
                |  'short' ('[' ']')*
                |  'int' ('[' ']')*
                |  'long' ('[' ']')*
                |  'float' ('[' ']')*
                |  'double' ('[' ']')*
                |  'void' ('[' ']')* ;

classType       : QUALIFIED_NAME ('[' ']')* ;

blockStatement          : variableDeclaration
                        | printStatement
                        | printlnStatement
                        | functionCall ;

variableDeclaration     : VARIABLE name EQUALS expression;
printStatement          : PRINT '('expression')';
printlnStatement        : PRINTLN '('expression')';
functionCall            : functionName '('expressionList ')';
name                    : ID ;
expressionList          : expression (',' expression)* ;
expression              : varReference
                        | value
                        | functionCall ;

varReference        : ID ;
value               : NUMBER
                    | STRING ;

//TOKENS
fragment CHAR     :  ('A'..'Z') | ('a'..'z');
fragment DIGIT    :  ('0'..'9');
fragment UNICODE  :  '\u0080'..'\uFFFF';

CLASS           : 'class' | '\u985e\u5225';             // class, 類別
FUNC            : 'func' | '\u51fd\u5f0f';
VARIABLE        : 'var' | '\u8b8a\u6578' ;              // var, 變數
PRINT           : 'print' | '\u5370\u51fa' ;            // print, 印出
PRINTLN         : 'println' | '\u5370\u51fa\u884c';     // println, 印出行
EQUALS          : '=' | '\u8ce6' ;
NUMBER          : [0-9]+ ;
STRING          : '"'.*'"' ;
ID              : (CHAR|DIGIT|UNICODE)+ ;
QUALIFIED_NAME  : ID ('.' ID)+;
WS              : [ \t\n\r]+ -> skip ;