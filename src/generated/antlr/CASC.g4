//header
grammar CASC;

//RULES
compilationUnit                 : classDeclaration EOF;
classDeclaration                : CLASS className '{' classBody '}';
className                       : qualifiedName;
classBody                       : (function | constructor | field)* ;
field                           : name ':' type ;
constructor                     : constructorDeclaration block? ;
constructorDeclaration          : 'ctor' '('(parameter (',' parameter)*)?')' ;
function                        : functionDeclaration block ;
functionDeclaration             : FUNC functionName '('(parameter (',' parameter)*)?')' (':' type)? ;
functionName                    : ID ;
parameter                       : ID ':' type ('=' expression)? ;
type                            : primitiveType
                                | classType
                                ;

primitiveType   :  'boolean' ('[' ']')*
                |  'string'  ('[' ']')*
                |  'char'    ('[' ']')*
                |  'byte'    ('[' ']')*
                |  'short'   ('[' ']')*
                |  'int'     ('[' ']')*
                |  'long'    ('[' ']')*
                |  'float'   ('[' ']')*
                |  'double'  ('[' ']')*
                |  'void'    ('[' ']')*
                ;

classType       : qualifiedName ('[' ']')* ;
qualifiedName   : ID ('::' ID)* ;

block           : '{' statement* '}' ;

statement       : block
                | variableDeclaration
                | assignment
                | printStatement
                | printlnStatement
                | forStatement
                | returnStatement
                | ifStatement
                | expression
                ;

variableDeclaration     : name ASSIGN_EQ expression                              ;
assignment              : name EQUALS expression                               ;
printStatement          : PRINT '('expression')'                            ;
printlnStatement        : PRINTLN '('expression')'                          ;
returnStatement         : RETURN expression                                 #ReturnWithValue
                        | RETURN                                            #ReturnVoid
                        ;
ifStatement             : IF ('(')? condition=expression (')')? trueStatement=statement (ELSE falseStatement=statement)?;
forStatement            : FOR ('(')? forRangedExpression (')')? statement ;
forRangedExpression     : iterator=varReference ':' startExpr=expression down=DOWN? range=(TO | UNTIL) endExpr=expression ;
name                    : ID ;
argument                : expression
                        | name '=' expression ;

expression              : superCall=THIS '('argument? (',' argument)*')'                                      #superCall
                        | className '('argument? (',' argument)*')'                                             #constructorCall
                        | owner=expression '.' functionName '('argument? (',' argument)*')'                     #functionCall
                        | functionName '('argument? (',' argument)*')'                                          #functionCall
                        | NEG=MINUS expression                                                       #negativeExpression
                        | '(' expression ')'                                                                    #wrappedExpression
                        | varReference                                                                          #varRef
                        | expression cmp=GREATER expression                                                     #conditionalExpression
                        | expression cmp=LESS expression                                                        #conditionalExpression
                        | expression cmp=EQ expression                                                          #conditionalExpression
                        | expression cmp=NOT_EQ expression                                                      #conditionalExpression
                        | expression cmp=GREATER_EQ expression                                                  #conditionalExpression
                        | expression cmp=LESS_EQ expression                                                     #conditionalExpression
                        | left=expression cmp=GREATER right=expression '?' trueExpression=expression ':' falseExpression=expression     #ifExpression
                        | left=expression cmp=LESS right=expression '?' trueExpression=expression ':' falseExpression=expression        #ifExpression
                        | left=expression cmp=EQ right=expression '?' trueExpression=expression ':' falseExpression=expression          #ifExpression
                        | left=expression cmp=NOT_EQ right=expression '?' trueExpression=expression ':' falseExpression=expression      #ifExpression
                        | left=expression cmp=GREATER_EQ right=expression '?' trueExpression=expression ':' falseExpression=expression  #ifExpression
                        | left=expression cmp=LESS_EQ right=expression '?' trueExpression=expression ':' falseExpression=expression     #ifExpression
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

CLASS           : 'class'                           ;
FUNC            : 'fn'                              ;
CTOR            : 'ctor'                            ;
THIS            : 'this'                            ;
VARIABLE        : 'var'                             ;
IF              : 'if'                              ;
ELSE            : 'else'                            ;
RETURN          : 'return'                          ;
FOR             : 'for'                             ;
DOWN            : 'down'                            ;
TO              : 'to'                              ;
UNTIL           : 'until'                           ;

PRINT           : 'print'                           ;
PRINTLN         : 'println'                         ;

PLUS            : '+'                               ;
MINUS           : '-'                               ;
STAR            : '*'                               ;
SLASH           : '/'                               ;
EQUALS          : '='                               ;
ASSIGN_EQ       : ':='                              ;

GREATER         : '>'                               ;
LESS            : '<'                               ;
GREATER_EQ      : '>='                              ;
LESS_EQ         : '<='                              ;
EQ              : '=='                              ;
NOT_EQ          : '!='                              ;

NUMBER          : [0-9.]+                                   ;
STRING          : '"'~('\r' | '\n' | '"')*'"'               ;
BOOL            : 'true' | 'false' | '\u771f' | '\u5047'    ;
ID              : (CHAR|DIGIT|UNICODE)+                     ;
WS              : [ \t\n\r]+ -> skip                        ;