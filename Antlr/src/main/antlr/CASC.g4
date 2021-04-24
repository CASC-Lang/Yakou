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
functionDeclaration         : FUNC functionName '('(functionArgument)*')' (':' type)? ;
functionName                : ID ;
functionArgument            : ID ':' type functionParamdefaultValue? ;
functionParamdefaultValue   : '=' expression ;
type                        : primitiveType
                            | classType
                            ;

primitiveType   :  ('boolean'   | '\u5e03\u6797') ('[' ']')*
                |  ('string'    | '\u5b57\u4e32') ('[' ']')*
                |  ('char'      | '\u5b57\u5143') ('[' ']')*
                |  ('byte'      | '\u4f4d\u5143') ('[' ']')*
                |  ('short'     | '\u77ed\u6574\u6578') ('[' ']')*
                |  ('int'       | '\u6574\u6578') ('[' ']')*
                |  ('long'      | '\u9577\u6574\u6578') ('[' ']')*
                |  ('float'     | '\u6d6e\u9ede\u6578') ('[' ']')*
                |  ('double'    | '\u500d\u6d6e\u9ede\u6578') ('[' ']')*
                |  ('void'      | '\u7a7a') ('[' ']')*
                ;

classType       : QUALIFIED_NAME ('[' ']')* ;

blockStatement          : variableDeclaration
                        | printStatement
                        | printlnStatement
                        | functionCall
                        ;

variableDeclaration     : VARIABLE name ('=' | '\u8ce6') expression;
printStatement          : PRINT '('expression')';
printlnStatement        : PRINTLN '('expression')';
functionCall            : functionName '('expressionList ')';
name                    : ID ;
expressionList          : expression (',' expression)* ;
expression              : varReference                          #VarRef
                        | value                                 #Val
                        | functionCall                          #FuncCall
                        |  '('expression STAR expression')'     #Multiply       // The order of arithmetic expression are related to its actual operator precedence.
                        | expression STAR expression            #Multiply
                        | '(' expression SLASH expression ')'   #Divide
                        | expression SLASH expression           #Divide
                        | '(' expression PLUS expression ')'    #Add
                        | expression PLUS expression            #Add
                        | '(' expression MINUS expression ')'   #Subtract
                        | expression MINUS expression           #Subtract
                        ;

varReference        : ID ;
value               : NUMBER
                    | STRING
                    ;

//TOKENS
fragment CHAR     :  ('A'..'Z') | ('a'..'z');
fragment DIGIT    :  ('0'..'9');
fragment UNICODE  :  '\u0080'..'\uFFFF';

CLASS           : 'class' | '\u985e\u5225';             // class, 類別
FUNC            : 'func' | '\u51fd\u5f0f';              // func, 函式
VARIABLE        : 'var' | '\u8b8a\u6578' ;              // var, 變數
PRINT           : 'print' | '\u5370\u51fa' ;            // print, 印出
PRINTLN         : 'println' | '\u5370\u51fa\u884c';     // println, 印出行

PLUS            : '+' | '\u52a0' ; // +, 加
MINUS           : '-' | '\u6e1b' ; // -, 減
STAR            : '*' | '\u4e58' ; // *, 乘
SLASH           : '/' | '\u9664' ; // /, 除
EQUALS          : '=' | '\u8ce6' ; // =, 賦          THIS EQUALS IS NOT WORKING PROPERLY SOMEHOW

NUMBER          : [0-9]+ ;
STRING          : '"'.*?'"' ;
ID              : (CHAR|DIGIT|UNICODE)+ ;
QUALIFIED_NAME  : ID ('.' ID)+;
WS              : [ \t\n\r]+ -> skip ;