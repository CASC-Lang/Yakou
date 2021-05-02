//header
grammar CASC;

//RULES
compilationUnit                 : classDeclaration EOF;
classDeclaration                : CLASS className '{' classBody '}';
className                       : ID;
classBody                       : (function | constructor)* ;
constructor                     : constructorDeclaration block? ;
constructorDeclaration          : ('ctor') '('(parameter (',' parameter)*)?')' ;
function                        : functionDeclaration block ;
functionDeclaration             : 'fn' functionName '('(parameter (',' parameter)*)?')' (':' type)? ;
functionName                    : ID ;
parameter                       : ID ':' type ('=' expression)? ;
type                            : primitiveType
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

classType       : qualifiedName ('[' ']')* ;
qualifiedName   : ID ('::' ID)+ ;

block           : '{' statement* '}' ;

statement       : block
                | variableDeclaration
                | printStatement
                | printlnStatement
                | forStatement
                | returnStatement
                | ifStatement
                | expression
                ;

variableDeclaration     : name ':=' expression                           ;
printStatement          : PRINT '('expression')'                         ;
printlnStatement        : PRINTLN '('expression')'                       ;
returnStatement         : RETURN expression                                 #ReturnWithValue
                        | RETURN                                            #ReturnVoid
                        ;
ifStatement             : IF ('(')? condition=expression (')')? trueStatement=statement (ELSE falseStatement=statement)?;
forStatement            : FOR ('(')? forRangedExpression (')')? statement ;
forRangedExpression     : iterator=varReference ':' startExpr=expression down=DOWN? range=(TO | UNTIL) endExpr=expression ;
name                    : ID ;
argument                : expression
                        | name '=' expression ;

expression              : NEG=('\u8ca0' | '-') expression                                                       #negativeExpression
                        | '(' expression ')'                                                                    #wrappedExpression
                        | varReference                                                                          #varRef
                        | superCall='this' '('argument? (',' argument)*')'                                      #superCall
                        | className '('argument? (',' argument)*')'                                             #constructorCall
                        | owner=expression '.' functionName '('argument? (',' argument)*')'                     #functionCall
                        | functionName '('argument? (',' argument)*')'                                          #functionCall
                        | expression cmp=GREATER expression                                                     #conditionalExpression
                        | expression cmp=LESS expression                                                        #conditionalExpression
                        | expression cmp=EQ expression                                                          #conditionalExpression
                        | expression cmp=NOT_EQ expression                                                      #conditionalExpression
                        | expression cmp=GREATER_EQ expression                                                  #conditionalExpression
                        | expression cmp=LESS_EQ expression                                                     #conditionalExpression
                        | condition=expression '?' trueExpression=expression ':' falseExpression=expression     #ifExpression
                        | expression STAR expression                                                            #multiply               // The order of arithmetic expression are related to its actual operator precedence.
                        | expression SLASH expression                                                           #divide
                        | expression PLUS expression                                                            #add
                        | expression MINUS expression                                                           #subtract
                        | (NUMBER | BOOL | STRING)                                                              #value
                        ;

varReference        : ID ;

//TOKENS
fragment CHAR     :  ('A'..'Z') | ('a'..'z')        ;
fragment DIGIT    :  ('0'..'9')                     ;
fragment UNICODE  :  '\u0080'..'\uFFFF'             ;

CLASS           : 'class'   | '\u985e\u5225'        ;       // class, 類別
FUNC            : 'fn'      | '\u51fd\u5f0f'        ;       // fn, 函式
VARIABLE        : 'var'     | '\u8b8a\u6578'        ;       // var, 變數
IF              : 'if'      | '\u5982\u679c'        ;       // if, 如果
ELSE            : 'else'    | '\u5426\u5247'        ;       // else, 否則
RETURN          : 'return'  | '\u8fd4\u56de'        ;       // return, 返回
FOR             : 'for'     | '\u8fed\u4ee3'        ;       // for, 迭代
DOWN            : 'down'                            ;       // down
TO              : 'to'      | '\u81f3'              ;       // to, 至
UNTIL           : 'until'   | '\u76f4\u5230'        ;       // until, 直到

PRINT           : 'print'   | '\u5370\u51fa'        ;       // print, 印出
PRINTLN         : 'println' | '\u5370\u51fa\u884c'  ;       // println, 印出行

PLUS            : '+' | '\u52a0'                    ;       // +, 加
MINUS           : '-' | '\u6e1b'                    ;       // -, 減, 負        (負 can only be applied to unary part but binary part)
STAR            : '*' | '\u4e58'                    ;       // *, 乘
SLASH           : '/' | '\u9664'                    ;       // /, 除
EQUALS          : '=' | '\u8ce6'                    ;       // =, 賦           THIS EQUALS IS NOT WORKING PROPERLY SOMEHOW

GREATER         : '>'       | '\u5927\u65bc'        ;       // >, 大於
LESS            : '<'       | '\u5c0f\u65bc'        ;       // <, 小於
GREATER_EQ      : '>='      | '\u5927\u7b49\u65bc'  ;       // >=, 大等於
LESS_EQ         : '<='      | '\u5c0f\u7b49\u65bc'  ;       // <=, 小等於
EQ              : '=='      | '\u662f'              ;       // ==, 等於
NOT_EQ          : '!='      | '\u4e0d\u662f'        ;       // !=, 不等於

NUMBER          : [0-9.]+                                   ;
STRING          : '"'~('\r' | '\n' | '"')*'"'               ;
BOOL            : 'true' | '\u771f' | 'false' | '\u5047'    ;
ID              : (CHAR|DIGIT|UNICODE)+                     ;
WS              : [ \t\n\r]+ -> skip                        ;