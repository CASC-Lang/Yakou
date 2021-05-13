// Generated from java-escape by ANTLR 4.7.1
package io.github.chaosunity.casc;
import com.strumenta.kotlinmultiplatform.asCharArray
import com.strumenta.kotlinmultiplatform.getType
import com.strumenta.kotlinmultiplatform.TypeDeclarator
import org.antlr.v4.kotlinruntime.*
import org.antlr.v4.kotlinruntime.atn.*
import org.antlr.v4.kotlinruntime.atn.ATNDeserializer
import org.antlr.v4.kotlinruntime.atn.ParserATNSimulator
import org.antlr.v4.kotlinruntime.atn.PredictionContextCache
import org.antlr.v4.kotlinruntime.dfa.*
import org.antlr.v4.kotlinruntime.tree.ParseTreeListener
import org.antlr.v4.kotlinruntime.tree.TerminalNode
import org.antlr.v4.kotlinruntime.atn.ATN.Companion.INVALID_ALT_NUMBER
import org.antlr.v4.kotlinruntime.tree.ParseTreeVisitor
import kotlin.reflect.KClass

class CASCParser(input: TokenStream) : Parser(input) {

    object solver : TypeDeclarator {
        override val classesByName : List<KClass<*>> = listOf(CASCParser.CompilationUnitContext::class,
                                                              CASCParser.ClassDeclarationContext::class,
                                                              CASCParser.ClassNameContext::class,
                                                              CASCParser.ClassBodyContext::class,
                                                              CASCParser.FieldContext::class,
                                                              CASCParser.ConstructorContext::class,
                                                              CASCParser.ConstructorDeclarationContext::class,
                                                              CASCParser.FunctionContext::class,
                                                              CASCParser.FunctionDeclarationContext::class,
                                                              CASCParser.FunctionNameContext::class,
                                                              CASCParser.ParameterContext::class,
                                                              CASCParser.TypeContext::class,
                                                              CASCParser.PrimitiveTypeContext::class,
                                                              CASCParser.ClassTypeContext::class,
                                                              CASCParser.QualifiedNameContext::class,
                                                              CASCParser.BlockContext::class,
                                                              CASCParser.StatementContext::class,
                                                              CASCParser.VariableDeclarationContext::class,
                                                              CASCParser.AssignmentContext::class,
                                                              CASCParser.PrintStatementContext::class,
                                                              CASCParser.PrintlnStatementContext::class,
                                                              CASCParser.ReturnStatementContext::class,
                                                              CASCParser.IfStatementContext::class,
                                                              CASCParser.ForStatementContext::class,
                                                              CASCParser.ForRangedExpressionContext::class,
                                                              CASCParser.NameContext::class,
                                                              CASCParser.ArgumentContext::class,
                                                              CASCParser.ExpressionContext::class,
                                                              CASCParser.VarReferenceContext::class)
    }

	// TODO verify version of runtime is compatible

    override val grammarFileName: String
        get() = "CASC.g4"

    override val tokenNames: Array<String?>?
        get() = CASCParser.Companion.tokenNames
    override val ruleNames: Array<String>?
        get() = CASCParser.Companion.ruleNames
    override val atn: ATN
        get() = CASCParser.Companion.ATN
    override val vocabulary: Vocabulary
        get() = CASCParser.Companion.VOCABULARY

    enum class Tokens(val id: Int) {
        EOF(-1),
        T__0(1),
        T__1(2),
        T__2(3),
        T__3(4),
        T__4(5),
        T__5(6),
        T__6(7),
        T__7(8),
        T__8(9),
        T__9(10),
        T__10(11),
        T__11(12),
        T__12(13),
        T__13(14),
        T__14(15),
        T__15(16),
        T__16(17),
        T__17(18),
        T__18(19),
        T__19(20),
        T__20(21),
        CLASS(22),
        FUNC(23),
        CTOR(24),
        THIS(25),
        VARIABLE(26),
        IF(27),
        ELSE(28),
        RETURN(29),
        FOR(30),
        DOWN(31),
        TO(32),
        UNTIL(33),
        PRINT(34),
        PRINTLN(35),
        PLUS(36),
        MINUS(37),
        STAR(38),
        SLASH(39),
        EQUALS(40),
        ASSIGN_EQ(41),
        GREATER(42),
        LESS(43),
        GREATER_EQ(44),
        LESS_EQ(45),
        EQ(46),
        NOT_EQ(47),
        NUMBER(48),
        STRING(49),
        BOOL(50),
        ID(51),
        WS(52)
    }

    enum class Rules(val id: Int) {
        RULE_compilationUnit(0),
        RULE_classDeclaration(1),
        RULE_className(2),
        RULE_classBody(3),
        RULE_field(4),
        RULE_constructor(5),
        RULE_constructorDeclaration(6),
        RULE_function(7),
        RULE_functionDeclaration(8),
        RULE_functionName(9),
        RULE_parameter(10),
        RULE_type(11),
        RULE_primitiveType(12),
        RULE_classType(13),
        RULE_qualifiedName(14),
        RULE_block(15),
        RULE_statement(16),
        RULE_variableDeclaration(17),
        RULE_assignment(18),
        RULE_printStatement(19),
        RULE_printlnStatement(20),
        RULE_returnStatement(21),
        RULE_ifStatement(22),
        RULE_forStatement(23),
        RULE_forRangedExpression(24),
        RULE_name(25),
        RULE_argument(26),
        RULE_expression(27),
        RULE_varReference(28)
    }

	@ThreadLocal
	companion object {
	    protected val decisionToDFA : Array<DFA>
    	protected val sharedContextCache = PredictionContextCache()

        val ruleNames = arrayOf("compilationUnit", "classDeclaration", "className", 
                                "classBody", "field", "constructor", "constructorDeclaration", 
                                "function", "functionDeclaration", "functionName", 
                                "parameter", "type", "primitiveType", "classType", 
                                "qualifiedName", "block", "statement", "variableDeclaration", 
                                "assignment", "printStatement", "printlnStatement", 
                                "returnStatement", "ifStatement", "forStatement", 
                                "forRangedExpression", "name", "argument", 
                                "expression", "varReference")

        private val LITERAL_NAMES: List<String?> = listOf(null, "'{'", "'}'", 
                                                          "':'", "'('", 
                                                          "','", "')'", 
                                                          "'boolean'", "'['", 
                                                          "']'", "'string'", 
                                                          "'char'", "'byte'", 
                                                          "'short'", "'int'", 
                                                          "'long'", "'float'", 
                                                          "'double'", "'void'", 
                                                          "'::'", "'.'", 
                                                          "'?'", "'class'", 
                                                          "'fn'", "'ctor'", 
                                                          "'this'", "'var'", 
                                                          "'if'", "'else'", 
                                                          "'return'", "'for'", 
                                                          "'down'", "'to'", 
                                                          "'until'", "'print'", 
                                                          "'println'", "'+'", 
                                                          "'-'", "'*'", 
                                                          "'/'", "'='", 
                                                          "':='", "'>'", 
                                                          "'<'", "'>='", 
                                                          "'<='", "'=='", 
                                                          "'!='")
        private val SYMBOLIC_NAMES: List<String?> = listOf(null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, "CLASS", 
                                                           "FUNC", "CTOR", 
                                                           "THIS", "VARIABLE", 
                                                           "IF", "ELSE", 
                                                           "RETURN", "FOR", 
                                                           "DOWN", "TO", 
                                                           "UNTIL", "PRINT", 
                                                           "PRINTLN", "PLUS", 
                                                           "MINUS", "STAR", 
                                                           "SLASH", "EQUALS", 
                                                           "ASSIGN_EQ", 
                                                           "GREATER", "LESS", 
                                                           "GREATER_EQ", 
                                                           "LESS_EQ", "EQ", 
                                                           "NOT_EQ", "NUMBER", 
                                                           "STRING", "BOOL", 
                                                           "ID", "WS")

        val VOCABULARY = VocabularyImpl(LITERAL_NAMES.toTypedArray(), SYMBOLIC_NAMES.toTypedArray())

        val tokenNames: Array<String?> = Array<String?>(SYMBOLIC_NAMES.size) {
            var el = VOCABULARY.getLiteralName(it)
            if (el == null) {
                el = VOCABULARY.getSymbolicName(it)
            }

            if (el == null) {
                el = "<INVALID>"
            }
            el
        }

        private const val serializedATN : String = "\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\u0003\u0036\u01dd\u0004\u0002\u0009\u0002\u0004\u0003\u0009\u0003\u0004\u0004\u0009\u0004\u0004\u0005\u0009\u0005\u0004\u0006\u0009\u0006\u0004\u0007\u0009\u0007\u0004\u0008\u0009\u0008\u0004\u0009\u0009\u0009\u0004\u000a\u0009\u000a\u0004\u000b\u0009\u000b\u0004\u000c\u0009\u000c\u0004\u000d\u0009\u000d\u0004\u000e\u0009\u000e\u0004\u000f\u0009\u000f\u0004\u0010\u0009\u0010\u0004\u0011\u0009\u0011\u0004\u0012\u0009\u0012\u0004\u0013\u0009\u0013\u0004\u0014\u0009\u0014\u0004\u0015\u0009\u0015\u0004\u0016\u0009\u0016\u0004\u0017\u0009\u0017\u0004\u0018\u0009\u0018\u0004\u0019\u0009\u0019\u0004\u001a\u0009\u001a\u0004\u001b\u0009\u001b\u0004\u001c\u0009\u001c\u0004\u001d\u0009\u001d\u0004\u001e\u0009\u001e\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0003\u0005\u0007\u0005\u004b\u000a\u0005\u000c\u0005\u000e\u0005\u004e\u000b\u0005\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0006\u0003\u0007\u0003\u0007\u0005\u0007\u0056\u000a\u0007\u0003\u0008\u0003\u0008\u0003\u0008\u0003\u0008\u0003\u0008\u0007\u0008\u005d\u000a\u0008\u000c\u0008\u000e\u0008\u0060\u000b\u0008\u0005\u0008\u0062\u000a\u0008\u0003\u0008\u0003\u0008\u0003\u0009\u0003\u0009\u0003\u0009\u0003\u000a\u0003\u000a\u0003\u000a\u0003\u000a\u0003\u000a\u0003\u000a\u0007\u000a\u006f\u000a\u000a\u000c\u000a\u000e\u000a\u0072\u000b\u000a\u0005\u000a\u0074\u000a\u000a\u0003\u000a\u0003\u000a\u0003\u000a\u0005\u000a\u0079\u000a\u000a\u0003\u000b\u0003\u000b\u0003\u000c\u0003\u000c\u0003\u000c\u0003\u000c\u0003\u000c\u0005\u000c\u0082\u000a\u000c\u0003\u000d\u0003\u000d\u0005\u000d\u0086\u000a\u000d\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u008b\u000a\u000e\u000c\u000e\u000e\u000e\u008e\u000b\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u0093\u000a\u000e\u000c\u000e\u000e\u000e\u0096\u000b\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u009b\u000a\u000e\u000c\u000e\u000e\u000e\u009e\u000b\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u00a3\u000a\u000e\u000c\u000e\u000e\u000e\u00a6\u000b\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u00ab\u000a\u000e\u000c\u000e\u000e\u000e\u00ae\u000b\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u00b3\u000a\u000e\u000c\u000e\u000e\u000e\u00b6\u000b\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u00bb\u000a\u000e\u000c\u000e\u000e\u000e\u00be\u000b\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u00c3\u000a\u000e\u000c\u000e\u000e\u000e\u00c6\u000b\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u00cb\u000a\u000e\u000c\u000e\u000e\u000e\u00ce\u000b\u000e\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u00d3\u000a\u000e\u000c\u000e\u000e\u000e\u00d6\u000b\u000e\u0005\u000e\u00d8\u000a\u000e\u0003\u000f\u0003\u000f\u0003\u000f\u0007\u000f\u00dd\u000a\u000f\u000c\u000f\u000e\u000f\u00e0\u000b\u000f\u0003\u0010\u0003\u0010\u0003\u0010\u0007\u0010\u00e5\u000a\u0010\u000c\u0010\u000e\u0010\u00e8\u000b\u0010\u0003\u0011\u0003\u0011\u0007\u0011\u00ec\u000a\u0011\u000c\u0011\u000e\u0011\u00ef\u000b\u0011\u0003\u0011\u0003\u0011\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0005\u0012\u00fc\u000a\u0012\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0015\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0003\u0017\u0003\u0017\u0003\u0017\u0005\u0017\u0113\u000a\u0017\u0003\u0018\u0003\u0018\u0005\u0018\u0117\u000a\u0018\u0003\u0018\u0003\u0018\u0005\u0018\u011b\u000a\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0005\u0018\u0120\u000a\u0018\u0003\u0019\u0003\u0019\u0005\u0019\u0124\u000a\u0019\u0003\u0019\u0003\u0019\u0005\u0019\u0128\u000a\u0019\u0003\u0019\u0003\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0005\u001a\u0130\u000a\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001b\u0003\u001b\u0003\u001c\u0003\u001c\u0003\u001c\u0003\u001c\u0003\u001c\u0005\u001c\u013c\u000a\u001c\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0005\u001d\u0142\u000a\u001d\u0003\u001d\u0003\u001d\u0007\u001d\u0146\u000a\u001d\u000c\u001d\u000e\u001d\u0149\u000b\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0005\u001d\u014f\u000a\u001d\u0003\u001d\u0003\u001d\u0007\u001d\u0153\u000a\u001d\u000c\u001d\u000e\u001d\u0156\u000b\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0005\u001d\u015d\u000a\u001d\u0003\u001d\u0003\u001d\u0007\u001d\u0161\u000a\u001d\u000c\u001d\u000e\u001d\u0164\u000b\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0005\u001d\u0170\u000a\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0003\u001d\u0005\u001d\u01cb\u000a\u001d\u0003\u001d\u0003\u001d\u0007\u001d\u01cf\u000a\u001d\u000c\u001d\u000e\u001d\u01d2\u000b\u001d\u0003\u001d\u0003\u001d\u0007\u001d\u01d6\u000a\u001d\u000c\u001d\u000e\u001d\u01d9\u000b\u001d\u0003\u001e\u0003\u001e\u0003\u001e\u0002\u0003\u0038\u001f\u0002\u0004\u0006\u0008\u000a\u000c\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0020\u0022\u0024\u0026\u0028\u002a\u002c\u002e\u0030\u0032\u0034\u0036\u0038\u003a\u0002\u0004\u0003\u0002\u0022\u0023\u0003\u0002\u0032\u0034\u0002\u0210\u0002\u003c\u0003\u0002\u0002\u0002\u0004\u003f\u0003\u0002\u0002\u0002\u0006\u0045\u0003\u0002\u0002\u0002\u0008\u004c\u0003\u0002\u0002\u0002\u000a\u004f\u0003\u0002\u0002\u0002\u000c\u0053\u0003\u0002\u0002\u0002\u000e\u0057\u0003\u0002\u0002\u0002\u0010\u0065\u0003\u0002\u0002\u0002\u0012\u0068\u0003\u0002\u0002\u0002\u0014\u007a\u0003\u0002\u0002\u0002\u0016\u007c\u0003\u0002\u0002\u0002\u0018\u0085\u0003\u0002\u0002\u0002\u001a\u00d7\u0003\u0002\u0002\u0002\u001c\u00d9\u0003\u0002\u0002\u0002\u001e\u00e1\u0003\u0002\u0002\u0002\u0020\u00e9\u0003\u0002\u0002\u0002\u0022\u00fb\u0003\u0002\u0002\u0002\u0024\u00fd\u0003\u0002\u0002\u0002\u0026\u0101\u0003\u0002\u0002\u0002\u0028\u0105\u0003\u0002\u0002\u0002\u002a\u010a\u0003\u0002\u0002\u0002\u002c\u0112\u0003\u0002\u0002\u0002\u002e\u0114\u0003\u0002\u0002\u0002\u0030\u0121\u0003\u0002\u0002\u0002\u0032\u012b\u0003\u0002\u0002\u0002\u0034\u0134\u0003\u0002\u0002\u0002\u0036\u013b\u0003\u0002\u0002\u0002\u0038\u016f\u0003\u0002\u0002\u0002\u003a\u01da\u0003\u0002\u0002\u0002\u003c\u003d\u0005\u0004\u0003\u0002\u003d\u003e\u0007\u0002\u0002\u0003\u003e\u0003\u0003\u0002\u0002\u0002\u003f\u0040\u0007\u0018\u0002\u0002\u0040\u0041\u0005\u0006\u0004\u0002\u0041\u0042\u0007\u0003\u0002\u0002\u0042\u0043\u0005\u0008\u0005\u0002\u0043\u0044\u0007\u0004\u0002\u0002\u0044\u0005\u0003\u0002\u0002\u0002\u0045\u0046\u0005\u001e\u0010\u0002\u0046\u0007\u0003\u0002\u0002\u0002\u0047\u004b\u0005\u0010\u0009\u0002\u0048\u004b\u0005\u000c\u0007\u0002\u0049\u004b\u0005\u000a\u0006\u0002\u004a\u0047\u0003\u0002\u0002\u0002\u004a\u0048\u0003\u0002\u0002\u0002\u004a\u0049\u0003\u0002\u0002\u0002\u004b\u004e\u0003\u0002\u0002\u0002\u004c\u004a\u0003\u0002\u0002\u0002\u004c\u004d\u0003\u0002\u0002\u0002\u004d\u0009\u0003\u0002\u0002\u0002\u004e\u004c\u0003\u0002\u0002\u0002\u004f\u0050\u0005\u0034\u001b\u0002\u0050\u0051\u0007\u0005\u0002\u0002\u0051\u0052\u0005\u0018\u000d\u0002\u0052\u000b\u0003\u0002\u0002\u0002\u0053\u0055\u0005\u000e\u0008\u0002\u0054\u0056\u0005\u0020\u0011\u0002\u0055\u0054\u0003\u0002\u0002\u0002\u0055\u0056\u0003\u0002\u0002\u0002\u0056\u000d\u0003\u0002\u0002\u0002\u0057\u0058\u0007\u001a\u0002\u0002\u0058\u0061\u0007\u0006\u0002\u0002\u0059\u005e\u0005\u0016\u000c\u0002\u005a\u005b\u0007\u0007\u0002\u0002\u005b\u005d\u0005\u0016\u000c\u0002\u005c\u005a\u0003\u0002\u0002\u0002\u005d\u0060\u0003\u0002\u0002\u0002\u005e\u005c\u0003\u0002\u0002\u0002\u005e\u005f\u0003\u0002\u0002\u0002\u005f\u0062\u0003\u0002\u0002\u0002\u0060\u005e\u0003\u0002\u0002\u0002\u0061\u0059\u0003\u0002\u0002\u0002\u0061\u0062\u0003\u0002\u0002\u0002\u0062\u0063\u0003\u0002\u0002\u0002\u0063\u0064\u0007\u0008\u0002\u0002\u0064\u000f\u0003\u0002\u0002\u0002\u0065\u0066\u0005\u0012\u000a\u0002\u0066\u0067\u0005\u0020\u0011\u0002\u0067\u0011\u0003\u0002\u0002\u0002\u0068\u0069\u0007\u0019\u0002\u0002\u0069\u006a\u0005\u0014\u000b\u0002\u006a\u0073\u0007\u0006\u0002\u0002\u006b\u0070\u0005\u0016\u000c\u0002\u006c\u006d\u0007\u0007\u0002\u0002\u006d\u006f\u0005\u0016\u000c\u0002\u006e\u006c\u0003\u0002\u0002\u0002\u006f\u0072\u0003\u0002\u0002\u0002\u0070\u006e\u0003\u0002\u0002\u0002\u0070\u0071\u0003\u0002\u0002\u0002\u0071\u0074\u0003\u0002\u0002\u0002\u0072\u0070\u0003\u0002\u0002\u0002\u0073\u006b\u0003\u0002\u0002\u0002\u0073\u0074\u0003\u0002\u0002\u0002\u0074\u0075\u0003\u0002\u0002\u0002\u0075\u0078\u0007\u0008\u0002\u0002\u0076\u0077\u0007\u0005\u0002\u0002\u0077\u0079\u0005\u0018\u000d\u0002\u0078\u0076\u0003\u0002\u0002\u0002\u0078\u0079\u0003\u0002\u0002\u0002\u0079\u0013\u0003\u0002\u0002\u0002\u007a\u007b\u0007\u0035\u0002\u0002\u007b\u0015\u0003\u0002\u0002\u0002\u007c\u007d\u0007\u0035\u0002\u0002\u007d\u007e\u0007\u0005\u0002\u0002\u007e\u0081\u0005\u0018\u000d\u0002\u007f\u0080\u0007\u002a\u0002\u0002\u0080\u0082\u0005\u0038\u001d\u0002\u0081\u007f\u0003\u0002\u0002\u0002\u0081\u0082\u0003\u0002\u0002\u0002\u0082\u0017\u0003\u0002\u0002\u0002\u0083\u0086\u0005\u001a\u000e\u0002\u0084\u0086\u0005\u001c\u000f\u0002\u0085\u0083\u0003\u0002\u0002\u0002\u0085\u0084\u0003\u0002\u0002\u0002\u0086\u0019\u0003\u0002\u0002\u0002\u0087\u008c\u0007\u0009\u0002\u0002\u0088\u0089\u0007\u000a\u0002\u0002\u0089\u008b\u0007\u000b\u0002\u0002\u008a\u0088\u0003\u0002\u0002\u0002\u008b\u008e\u0003\u0002\u0002\u0002\u008c\u008a\u0003\u0002\u0002\u0002\u008c\u008d\u0003\u0002\u0002\u0002\u008d\u00d8\u0003\u0002\u0002\u0002\u008e\u008c\u0003\u0002\u0002\u0002\u008f\u0094\u0007\u000c\u0002\u0002\u0090\u0091\u0007\u000a\u0002\u0002\u0091\u0093\u0007\u000b\u0002\u0002\u0092\u0090\u0003\u0002\u0002\u0002\u0093\u0096\u0003\u0002\u0002\u0002\u0094\u0092\u0003\u0002\u0002\u0002\u0094\u0095\u0003\u0002\u0002\u0002\u0095\u00d8\u0003\u0002\u0002\u0002\u0096\u0094\u0003\u0002\u0002\u0002\u0097\u009c\u0007\u000d\u0002\u0002\u0098\u0099\u0007\u000a\u0002\u0002\u0099\u009b\u0007\u000b\u0002\u0002\u009a\u0098\u0003\u0002\u0002\u0002\u009b\u009e\u0003\u0002\u0002\u0002\u009c\u009a\u0003\u0002\u0002\u0002\u009c\u009d\u0003\u0002\u0002\u0002\u009d\u00d8\u0003\u0002\u0002\u0002\u009e\u009c\u0003\u0002\u0002\u0002\u009f\u00a4\u0007\u000e\u0002\u0002\u00a0\u00a1\u0007\u000a\u0002\u0002\u00a1\u00a3\u0007\u000b\u0002\u0002\u00a2\u00a0\u0003\u0002\u0002\u0002\u00a3\u00a6\u0003\u0002\u0002\u0002\u00a4\u00a2\u0003\u0002\u0002\u0002\u00a4\u00a5\u0003\u0002\u0002\u0002\u00a5\u00d8\u0003\u0002\u0002\u0002\u00a6\u00a4\u0003\u0002\u0002\u0002\u00a7\u00ac\u0007\u000f\u0002\u0002\u00a8\u00a9\u0007\u000a\u0002\u0002\u00a9\u00ab\u0007\u000b\u0002\u0002\u00aa\u00a8\u0003\u0002\u0002\u0002\u00ab\u00ae\u0003\u0002\u0002\u0002\u00ac\u00aa\u0003\u0002\u0002\u0002\u00ac\u00ad\u0003\u0002\u0002\u0002\u00ad\u00d8\u0003\u0002\u0002\u0002\u00ae\u00ac\u0003\u0002\u0002\u0002\u00af\u00b4\u0007\u0010\u0002\u0002\u00b0\u00b1\u0007\u000a\u0002\u0002\u00b1\u00b3\u0007\u000b\u0002\u0002\u00b2\u00b0\u0003\u0002\u0002\u0002\u00b3\u00b6\u0003\u0002\u0002\u0002\u00b4\u00b2\u0003\u0002\u0002\u0002\u00b4\u00b5\u0003\u0002\u0002\u0002\u00b5\u00d8\u0003\u0002\u0002\u0002\u00b6\u00b4\u0003\u0002\u0002\u0002\u00b7\u00bc\u0007\u0011\u0002\u0002\u00b8\u00b9\u0007\u000a\u0002\u0002\u00b9\u00bb\u0007\u000b\u0002\u0002\u00ba\u00b8\u0003\u0002\u0002\u0002\u00bb\u00be\u0003\u0002\u0002\u0002\u00bc\u00ba\u0003\u0002\u0002\u0002\u00bc\u00bd\u0003\u0002\u0002\u0002\u00bd\u00d8\u0003\u0002\u0002\u0002\u00be\u00bc\u0003\u0002\u0002\u0002\u00bf\u00c4\u0007\u0012\u0002\u0002\u00c0\u00c1\u0007\u000a\u0002\u0002\u00c1\u00c3\u0007\u000b\u0002\u0002\u00c2\u00c0\u0003\u0002\u0002\u0002\u00c3\u00c6\u0003\u0002\u0002\u0002\u00c4\u00c2\u0003\u0002\u0002\u0002\u00c4\u00c5\u0003\u0002\u0002\u0002\u00c5\u00d8\u0003\u0002\u0002\u0002\u00c6\u00c4\u0003\u0002\u0002\u0002\u00c7\u00cc\u0007\u0013\u0002\u0002\u00c8\u00c9\u0007\u000a\u0002\u0002\u00c9\u00cb\u0007\u000b\u0002\u0002\u00ca\u00c8\u0003\u0002\u0002\u0002\u00cb\u00ce\u0003\u0002\u0002\u0002\u00cc\u00ca\u0003\u0002\u0002\u0002\u00cc\u00cd\u0003\u0002\u0002\u0002\u00cd\u00d8\u0003\u0002\u0002\u0002\u00ce\u00cc\u0003\u0002\u0002\u0002\u00cf\u00d4\u0007\u0014\u0002\u0002\u00d0\u00d1\u0007\u000a\u0002\u0002\u00d1\u00d3\u0007\u000b\u0002\u0002\u00d2\u00d0\u0003\u0002\u0002\u0002\u00d3\u00d6\u0003\u0002\u0002\u0002\u00d4\u00d2\u0003\u0002\u0002\u0002\u00d4\u00d5\u0003\u0002\u0002\u0002\u00d5\u00d8\u0003\u0002\u0002\u0002\u00d6\u00d4\u0003\u0002\u0002\u0002\u00d7\u0087\u0003\u0002\u0002\u0002\u00d7\u008f\u0003\u0002\u0002\u0002\u00d7\u0097\u0003\u0002\u0002\u0002\u00d7\u009f\u0003\u0002\u0002\u0002\u00d7\u00a7\u0003\u0002\u0002\u0002\u00d7\u00af\u0003\u0002\u0002\u0002\u00d7\u00b7\u0003\u0002\u0002\u0002\u00d7\u00bf\u0003\u0002\u0002\u0002\u00d7\u00c7\u0003\u0002\u0002\u0002\u00d7\u00cf\u0003\u0002\u0002\u0002\u00d8\u001b\u0003\u0002\u0002\u0002\u00d9\u00de\u0005\u001e\u0010\u0002\u00da\u00db\u0007\u000a\u0002\u0002\u00db\u00dd\u0007\u000b\u0002\u0002\u00dc\u00da\u0003\u0002\u0002\u0002\u00dd\u00e0\u0003\u0002\u0002\u0002\u00de\u00dc\u0003\u0002\u0002\u0002\u00de\u00df\u0003\u0002\u0002\u0002\u00df\u001d\u0003\u0002\u0002\u0002\u00e0\u00de\u0003\u0002\u0002\u0002\u00e1\u00e6\u0007\u0035\u0002\u0002\u00e2\u00e3\u0007\u0015\u0002\u0002\u00e3\u00e5\u0007\u0035\u0002\u0002\u00e4\u00e2\u0003\u0002\u0002\u0002\u00e5\u00e8\u0003\u0002\u0002\u0002\u00e6\u00e4\u0003\u0002\u0002\u0002\u00e6\u00e7\u0003\u0002\u0002\u0002\u00e7\u001f\u0003\u0002\u0002\u0002\u00e8\u00e6\u0003\u0002\u0002\u0002\u00e9\u00ed\u0007\u0003\u0002\u0002\u00ea\u00ec\u0005\u0022\u0012\u0002\u00eb\u00ea\u0003\u0002\u0002\u0002\u00ec\u00ef\u0003\u0002\u0002\u0002\u00ed\u00eb\u0003\u0002\u0002\u0002\u00ed\u00ee\u0003\u0002\u0002\u0002\u00ee\u00f0\u0003\u0002\u0002\u0002\u00ef\u00ed\u0003\u0002\u0002\u0002\u00f0\u00f1\u0007\u0004\u0002\u0002\u00f1\u0021\u0003\u0002\u0002\u0002\u00f2\u00fc\u0005\u0020\u0011\u0002\u00f3\u00fc\u0005\u0024\u0013\u0002\u00f4\u00fc\u0005\u0026\u0014\u0002\u00f5\u00fc\u0005\u0028\u0015\u0002\u00f6\u00fc\u0005\u002a\u0016\u0002\u00f7\u00fc\u0005\u0030\u0019\u0002\u00f8\u00fc\u0005\u002c\u0017\u0002\u00f9\u00fc\u0005\u002e\u0018\u0002\u00fa\u00fc\u0005\u0038\u001d\u0002\u00fb\u00f2\u0003\u0002\u0002\u0002\u00fb\u00f3\u0003\u0002\u0002\u0002\u00fb\u00f4\u0003\u0002\u0002\u0002\u00fb\u00f5\u0003\u0002\u0002\u0002\u00fb\u00f6\u0003\u0002\u0002\u0002\u00fb\u00f7\u0003\u0002\u0002\u0002\u00fb\u00f8\u0003\u0002\u0002\u0002\u00fb\u00f9\u0003\u0002\u0002\u0002\u00fb\u00fa\u0003\u0002\u0002\u0002\u00fc\u0023\u0003\u0002\u0002\u0002\u00fd\u00fe\u0005\u0034\u001b\u0002\u00fe\u00ff\u0007\u002b\u0002\u0002\u00ff\u0100\u0005\u0038\u001d\u0002\u0100\u0025\u0003\u0002\u0002\u0002\u0101\u0102\u0005\u0034\u001b\u0002\u0102\u0103\u0007\u002a\u0002\u0002\u0103\u0104\u0005\u0038\u001d\u0002\u0104\u0027\u0003\u0002\u0002\u0002\u0105\u0106\u0007\u0024\u0002\u0002\u0106\u0107\u0007\u0006\u0002\u0002\u0107\u0108\u0005\u0038\u001d\u0002\u0108\u0109\u0007\u0008\u0002\u0002\u0109\u0029\u0003\u0002\u0002\u0002\u010a\u010b\u0007\u0025\u0002\u0002\u010b\u010c\u0007\u0006\u0002\u0002\u010c\u010d\u0005\u0038\u001d\u0002\u010d\u010e\u0007\u0008\u0002\u0002\u010e\u002b\u0003\u0002\u0002\u0002\u010f\u0110\u0007\u001f\u0002\u0002\u0110\u0113\u0005\u0038\u001d\u0002\u0111\u0113\u0007\u001f\u0002\u0002\u0112\u010f\u0003\u0002\u0002\u0002\u0112\u0111\u0003\u0002\u0002\u0002\u0113\u002d\u0003\u0002\u0002\u0002\u0114\u0116\u0007\u001d\u0002\u0002\u0115\u0117\u0007\u0006\u0002\u0002\u0116\u0115\u0003\u0002\u0002\u0002\u0116\u0117\u0003\u0002\u0002\u0002\u0117\u0118\u0003\u0002\u0002\u0002\u0118\u011a\u0005\u0038\u001d\u0002\u0119\u011b\u0007\u0008\u0002\u0002\u011a\u0119\u0003\u0002\u0002\u0002\u011a\u011b\u0003\u0002\u0002\u0002\u011b\u011c\u0003\u0002\u0002\u0002\u011c\u011f\u0005\u0022\u0012\u0002\u011d\u011e\u0007\u001e\u0002\u0002\u011e\u0120\u0005\u0022\u0012\u0002\u011f\u011d\u0003\u0002\u0002\u0002\u011f\u0120\u0003\u0002\u0002\u0002\u0120\u002f\u0003\u0002\u0002\u0002\u0121\u0123\u0007\u0020\u0002\u0002\u0122\u0124\u0007\u0006\u0002\u0002\u0123\u0122\u0003\u0002\u0002\u0002\u0123\u0124\u0003\u0002\u0002\u0002\u0124\u0125\u0003\u0002\u0002\u0002\u0125\u0127\u0005\u0032\u001a\u0002\u0126\u0128\u0007\u0008\u0002\u0002\u0127\u0126\u0003\u0002\u0002\u0002\u0127\u0128\u0003\u0002\u0002\u0002\u0128\u0129\u0003\u0002\u0002\u0002\u0129\u012a\u0005\u0022\u0012\u0002\u012a\u0031\u0003\u0002\u0002\u0002\u012b\u012c\u0005\u003a\u001e\u0002\u012c\u012d\u0007\u0005\u0002\u0002\u012d\u012f\u0005\u0038\u001d\u0002\u012e\u0130\u0007\u0021\u0002\u0002\u012f\u012e\u0003\u0002\u0002\u0002\u012f\u0130\u0003\u0002\u0002\u0002\u0130\u0131\u0003\u0002\u0002\u0002\u0131\u0132\u0009\u0002\u0002\u0002\u0132\u0133\u0005\u0038\u001d\u0002\u0133\u0033\u0003\u0002\u0002\u0002\u0134\u0135\u0007\u0035\u0002\u0002\u0135\u0035\u0003\u0002\u0002\u0002\u0136\u013c\u0005\u0038\u001d\u0002\u0137\u0138\u0005\u0034\u001b\u0002\u0138\u0139\u0007\u002a\u0002\u0002\u0139\u013a\u0005\u0038\u001d\u0002\u013a\u013c\u0003\u0002\u0002\u0002\u013b\u0136\u0003\u0002\u0002\u0002\u013b\u0137\u0003\u0002\u0002\u0002\u013c\u0037\u0003\u0002\u0002\u0002\u013d\u013e\u0008\u001d\u0001\u0002\u013e\u013f\u0007\u001b\u0002\u0002\u013f\u0141\u0007\u0006\u0002\u0002\u0140\u0142\u0005\u0036\u001c\u0002\u0141\u0140\u0003\u0002\u0002\u0002\u0141\u0142\u0003\u0002\u0002\u0002\u0142\u0147\u0003\u0002\u0002\u0002\u0143\u0144\u0007\u0007\u0002\u0002\u0144\u0146\u0005\u0036\u001c\u0002\u0145\u0143\u0003\u0002\u0002\u0002\u0146\u0149\u0003\u0002\u0002\u0002\u0147\u0145\u0003\u0002\u0002\u0002\u0147\u0148\u0003\u0002\u0002\u0002\u0148\u014a\u0003\u0002\u0002\u0002\u0149\u0147\u0003\u0002\u0002\u0002\u014a\u0170\u0007\u0008\u0002\u0002\u014b\u014c\u0005\u0006\u0004\u0002\u014c\u014e\u0007\u0006\u0002\u0002\u014d\u014f\u0005\u0036\u001c\u0002\u014e\u014d\u0003\u0002\u0002\u0002\u014e\u014f\u0003\u0002\u0002\u0002\u014f\u0154\u0003\u0002\u0002\u0002\u0150\u0151\u0007\u0007\u0002\u0002\u0151\u0153\u0005\u0036\u001c\u0002\u0152\u0150\u0003\u0002\u0002\u0002\u0153\u0156\u0003\u0002\u0002\u0002\u0154\u0152\u0003\u0002\u0002\u0002\u0154\u0155\u0003\u0002\u0002\u0002\u0155\u0157\u0003\u0002\u0002\u0002\u0156\u0154\u0003\u0002\u0002\u0002\u0157\u0158\u0007\u0008\u0002\u0002\u0158\u0170\u0003\u0002\u0002\u0002\u0159\u015a\u0005\u0014\u000b\u0002\u015a\u015c\u0007\u0006\u0002\u0002\u015b\u015d\u0005\u0036\u001c\u0002\u015c\u015b\u0003\u0002\u0002\u0002\u015c\u015d\u0003\u0002\u0002\u0002\u015d\u0162\u0003\u0002\u0002\u0002\u015e\u015f\u0007\u0007\u0002\u0002\u015f\u0161\u0005\u0036\u001c\u0002\u0160\u015e\u0003\u0002\u0002\u0002\u0161\u0164\u0003\u0002\u0002\u0002\u0162\u0160\u0003\u0002\u0002\u0002\u0162\u0163\u0003\u0002\u0002\u0002\u0163\u0165\u0003\u0002\u0002\u0002\u0164\u0162\u0003\u0002\u0002\u0002\u0165\u0166\u0007\u0008\u0002\u0002\u0166\u0170\u0003\u0002\u0002\u0002\u0167\u0168\u0007\u0027\u0002\u0002\u0168\u0170\u0005\u0038\u001d\u0017\u0169\u016a\u0007\u0006\u0002\u0002\u016a\u016b\u0005\u0038\u001d\u0002\u016b\u016c\u0007\u0008\u0002\u0002\u016c\u0170\u0003\u0002\u0002\u0002\u016d\u0170\u0005\u003a\u001e\u0002\u016e\u0170\u0009\u0003\u0002\u0002\u016f\u013d\u0003\u0002\u0002\u0002\u016f\u014b\u0003\u0002\u0002\u0002\u016f\u0159\u0003\u0002\u0002\u0002\u016f\u0167\u0003\u0002\u0002\u0002\u016f\u0169\u0003\u0002\u0002\u0002\u016f\u016d\u0003\u0002\u0002\u0002\u016f\u016e\u0003\u0002\u0002\u0002\u0170\u01d7\u0003\u0002\u0002\u0002\u0171\u0172\u000c\u0014\u0002\u0002\u0172\u0173\u0007\u002c\u0002\u0002\u0173\u01d6\u0005\u0038\u001d\u0015\u0174\u0175\u000c\u0013\u0002\u0002\u0175\u0176\u0007\u002d\u0002\u0002\u0176\u01d6\u0005\u0038\u001d\u0014\u0177\u0178\u000c\u0012\u0002\u0002\u0178\u0179\u0007\u0030\u0002\u0002\u0179\u01d6\u0005\u0038\u001d\u0013\u017a\u017b\u000c\u0011\u0002\u0002\u017b\u017c\u0007\u0031\u0002\u0002\u017c\u01d6\u0005\u0038\u001d\u0012\u017d\u017e\u000c\u0010\u0002\u0002\u017e\u017f\u0007\u002e\u0002\u0002\u017f\u01d6\u0005\u0038\u001d\u0011\u0180\u0181\u000c\u000f\u0002\u0002\u0181\u0182\u0007\u002f\u0002\u0002\u0182\u01d6\u0005\u0038\u001d\u0010\u0183\u0184\u000c\u000e\u0002\u0002\u0184\u0185\u0007\u002c\u0002\u0002\u0185\u0186\u0005\u0038\u001d\u0002\u0186\u0187\u0007\u0017\u0002\u0002\u0187\u0188\u0005\u0038\u001d\u0002\u0188\u0189\u0007\u0005\u0002\u0002\u0189\u018a\u0005\u0038\u001d\u000f\u018a\u01d6\u0003\u0002\u0002\u0002\u018b\u018c\u000c\u000d\u0002\u0002\u018c\u018d\u0007\u002d\u0002\u0002\u018d\u018e\u0005\u0038\u001d\u0002\u018e\u018f\u0007\u0017\u0002\u0002\u018f\u0190\u0005\u0038\u001d\u0002\u0190\u0191\u0007\u0005\u0002\u0002\u0191\u0192\u0005\u0038\u001d\u000e\u0192\u01d6\u0003\u0002\u0002\u0002\u0193\u0194\u000c\u000c\u0002\u0002\u0194\u0195\u0007\u0030\u0002\u0002\u0195\u0196\u0005\u0038\u001d\u0002\u0196\u0197\u0007\u0017\u0002\u0002\u0197\u0198\u0005\u0038\u001d\u0002\u0198\u0199\u0007\u0005\u0002\u0002\u0199\u019a\u0005\u0038\u001d\u000d\u019a\u01d6\u0003\u0002\u0002\u0002\u019b\u019c\u000c\u000b\u0002\u0002\u019c\u019d\u0007\u0031\u0002\u0002\u019d\u019e\u0005\u0038\u001d\u0002\u019e\u019f\u0007\u0017\u0002\u0002\u019f\u01a0\u0005\u0038\u001d\u0002\u01a0\u01a1\u0007\u0005\u0002\u0002\u01a1\u01a2\u0005\u0038\u001d\u000c\u01a2\u01d6\u0003\u0002\u0002\u0002\u01a3\u01a4\u000c\u000a\u0002\u0002\u01a4\u01a5\u0007\u002e\u0002\u0002\u01a5\u01a6\u0005\u0038\u001d\u0002\u01a6\u01a7\u0007\u0017\u0002\u0002\u01a7\u01a8\u0005\u0038\u001d\u0002\u01a8\u01a9\u0007\u0005\u0002\u0002\u01a9\u01aa\u0005\u0038\u001d\u000b\u01aa\u01d6\u0003\u0002\u0002\u0002\u01ab\u01ac\u000c\u0009\u0002\u0002\u01ac\u01ad\u0007\u002f\u0002\u0002\u01ad\u01ae\u0005\u0038\u001d\u0002\u01ae\u01af\u0007\u0017\u0002\u0002\u01af\u01b0\u0005\u0038\u001d\u0002\u01b0\u01b1\u0007\u0005\u0002\u0002\u01b1\u01b2\u0005\u0038\u001d\u000a\u01b2\u01d6\u0003\u0002\u0002\u0002\u01b3\u01b4\u000c\u0008\u0002\u0002\u01b4\u01b5\u0007\u0017\u0002\u0002\u01b5\u01b6\u0005\u0038\u001d\u0002\u01b6\u01b7\u0007\u0005\u0002\u0002\u01b7\u01b8\u0005\u0038\u001d\u0009\u01b8\u01d6\u0003\u0002\u0002\u0002\u01b9\u01ba\u000c\u0007\u0002\u0002\u01ba\u01bb\u0007\u0028\u0002\u0002\u01bb\u01d6\u0005\u0038\u001d\u0008\u01bc\u01bd\u000c\u0006\u0002\u0002\u01bd\u01be\u0007\u0029\u0002\u0002\u01be\u01d6\u0005\u0038\u001d\u0007\u01bf\u01c0\u000c\u0005\u0002\u0002\u01c0\u01c1\u0007\u0026\u0002\u0002\u01c1\u01d6\u0005\u0038\u001d\u0006\u01c2\u01c3\u000c\u0004\u0002\u0002\u01c3\u01c4\u0007\u0027\u0002\u0002\u01c4\u01d6\u0005\u0038\u001d\u0005\u01c5\u01c6\u000c\u0019\u0002\u0002\u01c6\u01c7\u0007\u0016\u0002\u0002\u01c7\u01c8\u0005\u0014\u000b\u0002\u01c8\u01ca\u0007\u0006\u0002\u0002\u01c9\u01cb\u0005\u0036\u001c\u0002\u01ca\u01c9\u0003\u0002\u0002\u0002\u01ca\u01cb\u0003\u0002\u0002\u0002\u01cb\u01d0\u0003\u0002\u0002\u0002\u01cc\u01cd\u0007\u0007\u0002\u0002\u01cd\u01cf\u0005\u0036\u001c\u0002\u01ce\u01cc\u0003\u0002\u0002\u0002\u01cf\u01d2\u0003\u0002\u0002\u0002\u01d0\u01ce\u0003\u0002\u0002\u0002\u01d0\u01d1\u0003\u0002\u0002\u0002\u01d1\u01d3\u0003\u0002\u0002\u0002\u01d2\u01d0\u0003\u0002\u0002\u0002\u01d3\u01d4\u0007\u0008\u0002\u0002\u01d4\u01d6\u0003\u0002\u0002\u0002\u01d5\u0171\u0003\u0002\u0002\u0002\u01d5\u0174\u0003\u0002\u0002\u0002\u01d5\u0177\u0003\u0002\u0002\u0002\u01d5\u017a\u0003\u0002\u0002\u0002\u01d5\u017d\u0003\u0002\u0002\u0002\u01d5\u0180\u0003\u0002\u0002\u0002\u01d5\u0183\u0003\u0002\u0002\u0002\u01d5\u018b\u0003\u0002\u0002\u0002\u01d5\u0193\u0003\u0002\u0002\u0002\u01d5\u019b\u0003\u0002\u0002\u0002\u01d5\u01a3\u0003\u0002\u0002\u0002\u01d5\u01ab\u0003\u0002\u0002\u0002\u01d5\u01b3\u0003\u0002\u0002\u0002\u01d5\u01b9\u0003\u0002\u0002\u0002\u01d5\u01bc\u0003\u0002\u0002\u0002\u01d5\u01bf\u0003\u0002\u0002\u0002\u01d5\u01c2\u0003\u0002\u0002\u0002\u01d5\u01c5\u0003\u0002\u0002\u0002\u01d6\u01d9\u0003\u0002\u0002\u0002\u01d7\u01d5\u0003\u0002\u0002\u0002\u01d7\u01d8\u0003\u0002\u0002\u0002\u01d8\u0039\u0003\u0002\u0002\u0002\u01d9\u01d7\u0003\u0002\u0002\u0002\u01da\u01db\u0007\u0035\u0002\u0002\u01db\u003b\u0003\u0002\u0002\u0002\u002e\u004a\u004c\u0055\u005e\u0061\u0070\u0073\u0078\u0081\u0085\u008c\u0094\u009c\u00a4\u00ac\u00b4\u00bc\u00c4\u00cc\u00d4\u00d7\u00de\u00e6\u00ed\u00fb\u0112\u0116\u011a\u011f\u0123\u0127\u012f\u013b\u0141\u0147\u014e\u0154\u015c\u0162\u016f\u01ca\u01d0\u01d5\u01d7"

        val ATN = ATNDeserializer().deserialize(serializedATN.asCharArray())
        init {
        	decisionToDFA = Array<DFA>(ATN.numberOfDecisions, {
        		DFA(ATN.getDecisionState(it)!!, it)
        	})


        }
	}

    private val T__0 = Tokens.T__0.id
    private val T__1 = Tokens.T__1.id
    private val T__2 = Tokens.T__2.id
    private val T__3 = Tokens.T__3.id
    private val T__4 = Tokens.T__4.id
    private val T__5 = Tokens.T__5.id
    private val T__6 = Tokens.T__6.id
    private val T__7 = Tokens.T__7.id
    private val T__8 = Tokens.T__8.id
    private val T__9 = Tokens.T__9.id
    private val T__10 = Tokens.T__10.id
    private val T__11 = Tokens.T__11.id
    private val T__12 = Tokens.T__12.id
    private val T__13 = Tokens.T__13.id
    private val T__14 = Tokens.T__14.id
    private val T__15 = Tokens.T__15.id
    private val T__16 = Tokens.T__16.id
    private val T__17 = Tokens.T__17.id
    private val T__18 = Tokens.T__18.id
    private val T__19 = Tokens.T__19.id
    private val T__20 = Tokens.T__20.id
    private val CLASS = Tokens.CLASS.id
    private val FUNC = Tokens.FUNC.id
    private val CTOR = Tokens.CTOR.id
    private val THIS = Tokens.THIS.id
    private val VARIABLE = Tokens.VARIABLE.id
    private val IF = Tokens.IF.id
    private val ELSE = Tokens.ELSE.id
    private val RETURN = Tokens.RETURN.id
    private val FOR = Tokens.FOR.id
    private val DOWN = Tokens.DOWN.id
    private val TO = Tokens.TO.id
    private val UNTIL = Tokens.UNTIL.id
    private val PRINT = Tokens.PRINT.id
    private val PRINTLN = Tokens.PRINTLN.id
    private val PLUS = Tokens.PLUS.id
    private val MINUS = Tokens.MINUS.id
    private val STAR = Tokens.STAR.id
    private val SLASH = Tokens.SLASH.id
    private val EQUALS = Tokens.EQUALS.id
    private val ASSIGN_EQ = Tokens.ASSIGN_EQ.id
    private val GREATER = Tokens.GREATER.id
    private val LESS = Tokens.LESS.id
    private val GREATER_EQ = Tokens.GREATER_EQ.id
    private val LESS_EQ = Tokens.LESS_EQ.id
    private val EQ = Tokens.EQ.id
    private val NOT_EQ = Tokens.NOT_EQ.id
    private val NUMBER = Tokens.NUMBER.id
    private val STRING = Tokens.STRING.id
    private val BOOL = Tokens.BOOL.id
    private val ID = Tokens.ID.id
    private val WS = Tokens.WS.id

    /* Named actions */
	init {
		interpreter = ParserATNSimulator(this, ATN, decisionToDFA, sharedContextCache)
	}
	/* Funcs */
	open class CompilationUnitContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_compilationUnit.id
	        set(value) { throw RuntimeException() }
		fun findClassDeclaration() : ClassDeclarationContext? = getRuleContext(solver.getType("ClassDeclarationContext"),0)
		fun EOF() : TerminalNode? = getToken(CASCParser.Tokens.EOF.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitCompilationUnit(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  compilationUnit() : CompilationUnitContext {
		var _localctx : CompilationUnitContext = CompilationUnitContext(context, state)
		enterRule(_localctx, 0, Rules.RULE_compilationUnit.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 58
			classDeclaration()
			this.state = 59
			match(EOF) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ClassDeclarationContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_classDeclaration.id
	        set(value) { throw RuntimeException() }
		fun CLASS() : TerminalNode? = getToken(CASCParser.Tokens.CLASS.id, 0)
		fun findClassName() : ClassNameContext? = getRuleContext(solver.getType("ClassNameContext"),0)
		fun findClassBody() : ClassBodyContext? = getRuleContext(solver.getType("ClassBodyContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitClassDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  classDeclaration() : ClassDeclarationContext {
		var _localctx : ClassDeclarationContext = ClassDeclarationContext(context, state)
		enterRule(_localctx, 2, Rules.RULE_classDeclaration.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 61
			match(CLASS) as Token
			this.state = 62
			className()
			this.state = 63
			match(T__0) as Token
			this.state = 64
			classBody()
			this.state = 65
			match(T__1) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ClassNameContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_className.id
	        set(value) { throw RuntimeException() }
		fun findQualifiedName() : QualifiedNameContext? = getRuleContext(solver.getType("QualifiedNameContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitClassName(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  className() : ClassNameContext {
		var _localctx : ClassNameContext = ClassNameContext(context, state)
		enterRule(_localctx, 4, Rules.RULE_className.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 67
			qualifiedName()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ClassBodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_classBody.id
	        set(value) { throw RuntimeException() }
		fun findFunction() : List<FunctionContext> = getRuleContexts(solver.getType("FunctionContext"))
		fun findFunction(i: Int) : FunctionContext? = getRuleContext(solver.getType("FunctionContext"),i)
		fun findConstructor() : List<ConstructorContext> = getRuleContexts(solver.getType("ConstructorContext"))
		fun findConstructor(i: Int) : ConstructorContext? = getRuleContext(solver.getType("ConstructorContext"),i)
		fun findField() : List<FieldContext> = getRuleContexts(solver.getType("FieldContext"))
		fun findField(i: Int) : FieldContext? = getRuleContext(solver.getType("FieldContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitClassBody(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  classBody() : ClassBodyContext {
		var _localctx : ClassBodyContext = ClassBodyContext(context, state)
		enterRule(_localctx, 6, Rules.RULE_classBody.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 74
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl FUNC) or (1L shl CTOR) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 72
				errorHandler.sync(this)
				when (_input!!.LA(1)) {
				FUNC  ->  /*LL1AltBlock*/{if (true){
				this.state = 69
				function()
				}}
				CTOR  ->  /*LL1AltBlock*/{if (true){
				this.state = 70
				constructor()
				}}
				ID  ->  /*LL1AltBlock*/{if (true){
				this.state = 71
				field()
				}}
				else -> throw NoViableAltException(this)
				}
				}
				this.state = 76
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FieldContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_field.id
	        set(value) { throw RuntimeException() }
		fun findName() : NameContext? = getRuleContext(solver.getType("NameContext"),0)
		fun findType() : TypeContext? = getRuleContext(solver.getType("TypeContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitField(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  field() : FieldContext {
		var _localctx : FieldContext = FieldContext(context, state)
		enterRule(_localctx, 8, Rules.RULE_field.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 77
			name()
			this.state = 78
			match(T__2) as Token
			this.state = 79
			type()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ConstructorContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_constructor.id
	        set(value) { throw RuntimeException() }
		fun findConstructorDeclaration() : ConstructorDeclarationContext? = getRuleContext(solver.getType("ConstructorDeclarationContext"),0)
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitConstructor(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  constructor() : ConstructorContext {
		var _localctx : ConstructorContext = ConstructorContext(context, state)
		enterRule(_localctx, 10, Rules.RULE_constructor.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 81
			constructorDeclaration()
			this.state = 83
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__0) {
				if (true){
				this.state = 82
				block()
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ConstructorDeclarationContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_constructorDeclaration.id
	        set(value) { throw RuntimeException() }
		fun findParameter() : List<ParameterContext> = getRuleContexts(solver.getType("ParameterContext"))
		fun findParameter(i: Int) : ParameterContext? = getRuleContext(solver.getType("ParameterContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitConstructorDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  constructorDeclaration() : ConstructorDeclarationContext {
		var _localctx : ConstructorDeclarationContext = ConstructorDeclarationContext(context, state)
		enterRule(_localctx, 12, Rules.RULE_constructorDeclaration.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 85
			match(CTOR) as Token
			this.state = 86
			match(T__3) as Token
			this.state = 95
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==ID) {
				if (true){
				this.state = 87
				parameter()
				this.state = 92
				errorHandler.sync(this);
				_la = _input!!.LA(1)
				while (_la==T__4) {
					if (true){
					if (true){
					this.state = 88
					match(T__4) as Token
					this.state = 89
					parameter()
					}
					}
					this.state = 94
					errorHandler.sync(this)
					_la = _input!!.LA(1)
				}
				}
			}

			this.state = 97
			match(T__5) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FunctionContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_function.id
	        set(value) { throw RuntimeException() }
		fun findFunctionDeclaration() : FunctionDeclarationContext? = getRuleContext(solver.getType("FunctionDeclarationContext"),0)
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFunction(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  function() : FunctionContext {
		var _localctx : FunctionContext = FunctionContext(context, state)
		enterRule(_localctx, 14, Rules.RULE_function.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 99
			functionDeclaration()
			this.state = 100
			block()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FunctionDeclarationContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_functionDeclaration.id
	        set(value) { throw RuntimeException() }
		fun FUNC() : TerminalNode? = getToken(CASCParser.Tokens.FUNC.id, 0)
		fun findFunctionName() : FunctionNameContext? = getRuleContext(solver.getType("FunctionNameContext"),0)
		fun findParameter() : List<ParameterContext> = getRuleContexts(solver.getType("ParameterContext"))
		fun findParameter(i: Int) : ParameterContext? = getRuleContext(solver.getType("ParameterContext"),i)
		fun findType() : TypeContext? = getRuleContext(solver.getType("TypeContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFunctionDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  functionDeclaration() : FunctionDeclarationContext {
		var _localctx : FunctionDeclarationContext = FunctionDeclarationContext(context, state)
		enterRule(_localctx, 16, Rules.RULE_functionDeclaration.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 102
			match(FUNC) as Token
			this.state = 103
			functionName()
			this.state = 104
			match(T__3) as Token
			this.state = 113
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==ID) {
				if (true){
				this.state = 105
				parameter()
				this.state = 110
				errorHandler.sync(this);
				_la = _input!!.LA(1)
				while (_la==T__4) {
					if (true){
					if (true){
					this.state = 106
					match(T__4) as Token
					this.state = 107
					parameter()
					}
					}
					this.state = 112
					errorHandler.sync(this)
					_la = _input!!.LA(1)
				}
				}
			}

			this.state = 115
			match(T__5) as Token
			this.state = 118
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__2) {
				if (true){
				this.state = 116
				match(T__2) as Token
				this.state = 117
				type()
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class FunctionNameContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_functionName.id
	        set(value) { throw RuntimeException() }
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFunctionName(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  functionName() : FunctionNameContext {
		var _localctx : FunctionNameContext = FunctionNameContext(context, state)
		enterRule(_localctx, 18, Rules.RULE_functionName.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 120
			match(ID) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ParameterContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_parameter.id
	        set(value) { throw RuntimeException() }
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		fun findType() : TypeContext? = getRuleContext(solver.getType("TypeContext"),0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitParameter(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  parameter() : ParameterContext {
		var _localctx : ParameterContext = ParameterContext(context, state)
		enterRule(_localctx, 20, Rules.RULE_parameter.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 122
			match(ID) as Token
			this.state = 123
			match(T__2) as Token
			this.state = 124
			type()
			this.state = 127
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==EQUALS) {
				if (true){
				this.state = 125
				match(EQUALS) as Token
				this.state = 126
				expression(0)
				}
			}

			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class TypeContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_type.id
	        set(value) { throw RuntimeException() }
		fun findPrimitiveType() : PrimitiveTypeContext? = getRuleContext(solver.getType("PrimitiveTypeContext"),0)
		fun findClassType() : ClassTypeContext? = getRuleContext(solver.getType("ClassTypeContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitType(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  type() : TypeContext {
		var _localctx : TypeContext = TypeContext(context, state)
		enterRule(_localctx, 22, Rules.RULE_type.id)
		try {
			this.state = 131
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__6 , T__9 , T__10 , T__11 , T__12 , T__13 , T__14 , T__15 , T__16 , T__17  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 129
			primitiveType()
			}}
			ID  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 130
			classType()
			}}
			else -> throw NoViableAltException(this)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class PrimitiveTypeContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_primitiveType.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitPrimitiveType(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  primitiveType() : PrimitiveTypeContext {
		var _localctx : PrimitiveTypeContext = PrimitiveTypeContext(context, state)
		enterRule(_localctx, 24, Rules.RULE_primitiveType.id)
		var _la: Int
		try {
			this.state = 213
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__6  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 133
			match(T__6) as Token
			this.state = 138
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 134
				match(T__7) as Token
				this.state = 135
				match(T__8) as Token
				}
				}
				this.state = 140
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__9  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 141
			match(T__9) as Token
			this.state = 146
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 142
				match(T__7) as Token
				this.state = 143
				match(T__8) as Token
				}
				}
				this.state = 148
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__10  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 149
			match(T__10) as Token
			this.state = 154
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 150
				match(T__7) as Token
				this.state = 151
				match(T__8) as Token
				}
				}
				this.state = 156
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__11  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 4)
			if (true){
			this.state = 157
			match(T__11) as Token
			this.state = 162
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 158
				match(T__7) as Token
				this.state = 159
				match(T__8) as Token
				}
				}
				this.state = 164
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__12  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 5)
			if (true){
			this.state = 165
			match(T__12) as Token
			this.state = 170
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 166
				match(T__7) as Token
				this.state = 167
				match(T__8) as Token
				}
				}
				this.state = 172
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__13  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 6)
			if (true){
			this.state = 173
			match(T__13) as Token
			this.state = 178
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 174
				match(T__7) as Token
				this.state = 175
				match(T__8) as Token
				}
				}
				this.state = 180
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__14  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 7)
			if (true){
			this.state = 181
			match(T__14) as Token
			this.state = 186
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 182
				match(T__7) as Token
				this.state = 183
				match(T__8) as Token
				}
				}
				this.state = 188
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__15  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 8)
			if (true){
			this.state = 189
			match(T__15) as Token
			this.state = 194
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 190
				match(T__7) as Token
				this.state = 191
				match(T__8) as Token
				}
				}
				this.state = 196
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__16  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 9)
			if (true){
			this.state = 197
			match(T__16) as Token
			this.state = 202
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 198
				match(T__7) as Token
				this.state = 199
				match(T__8) as Token
				}
				}
				this.state = 204
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__17  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 10)
			if (true){
			this.state = 205
			match(T__17) as Token
			this.state = 210
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 206
				match(T__7) as Token
				this.state = 207
				match(T__8) as Token
				}
				}
				this.state = 212
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			else -> throw NoViableAltException(this)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ClassTypeContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_classType.id
	        set(value) { throw RuntimeException() }
		fun findQualifiedName() : QualifiedNameContext? = getRuleContext(solver.getType("QualifiedNameContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitClassType(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  classType() : ClassTypeContext {
		var _localctx : ClassTypeContext = ClassTypeContext(context, state)
		enterRule(_localctx, 26, Rules.RULE_classType.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 215
			qualifiedName()
			this.state = 220
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__7) {
				if (true){
				if (true){
				this.state = 216
				match(T__7) as Token
				this.state = 217
				match(T__8) as Token
				}
				}
				this.state = 222
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class QualifiedNameContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_qualifiedName.id
	        set(value) { throw RuntimeException() }
		fun ID() : List<TerminalNode> = getTokens(CASCParser.Tokens.ID.id)
		fun ID(i: Int) : TerminalNode = getToken(CASCParser.Tokens.ID.id, i) as TerminalNode
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitQualifiedName(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  qualifiedName() : QualifiedNameContext {
		var _localctx : QualifiedNameContext = QualifiedNameContext(context, state)
		enterRule(_localctx, 28, Rules.RULE_qualifiedName.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 223
			match(ID) as Token
			this.state = 228
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__18) {
				if (true){
				if (true){
				this.state = 224
				match(T__18) as Token
				this.state = 225
				match(ID) as Token
				}
				}
				this.state = 230
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class BlockContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_block.id
	        set(value) { throw RuntimeException() }
		fun findStatement() : List<StatementContext> = getRuleContexts(solver.getType("StatementContext"))
		fun findStatement(i: Int) : StatementContext? = getRuleContext(solver.getType("StatementContext"),i)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitBlock(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  block() : BlockContext {
		var _localctx : BlockContext = BlockContext(context, state)
		enterRule(_localctx, 30, Rules.RULE_block.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 231
			match(T__0) as Token
			this.state = 235
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__0) or (1L shl T__3) or (1L shl THIS) or (1L shl IF) or (1L shl RETURN) or (1L shl FOR) or (1L shl PRINT) or (1L shl PRINTLN) or (1L shl MINUS) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl ID))) != 0L)) {
				if (true){
				if (true){
				this.state = 232
				statement()
				}
				}
				this.state = 237
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 238
			match(T__1) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class StatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_statement.id
	        set(value) { throw RuntimeException() }
		fun findBlock() : BlockContext? = getRuleContext(solver.getType("BlockContext"),0)
		fun findVariableDeclaration() : VariableDeclarationContext? = getRuleContext(solver.getType("VariableDeclarationContext"),0)
		fun findAssignment() : AssignmentContext? = getRuleContext(solver.getType("AssignmentContext"),0)
		fun findPrintStatement() : PrintStatementContext? = getRuleContext(solver.getType("PrintStatementContext"),0)
		fun findPrintlnStatement() : PrintlnStatementContext? = getRuleContext(solver.getType("PrintlnStatementContext"),0)
		fun findForStatement() : ForStatementContext? = getRuleContext(solver.getType("ForStatementContext"),0)
		fun findReturnStatement() : ReturnStatementContext? = getRuleContext(solver.getType("ReturnStatementContext"),0)
		fun findIfStatement() : IfStatementContext? = getRuleContext(solver.getType("IfStatementContext"),0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  statement() : StatementContext {
		var _localctx : StatementContext = StatementContext(context, state)
		enterRule(_localctx, 32, Rules.RULE_statement.id)
		try {
			this.state = 249
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,24,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 240
			block()
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 241
			variableDeclaration()
			}}
			3 -> {
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 242
			assignment()
			}}
			4 -> {
			enterOuterAlt(_localctx, 4)
			if (true){
			this.state = 243
			printStatement()
			}}
			5 -> {
			enterOuterAlt(_localctx, 5)
			if (true){
			this.state = 244
			printlnStatement()
			}}
			6 -> {
			enterOuterAlt(_localctx, 6)
			if (true){
			this.state = 245
			forStatement()
			}}
			7 -> {
			enterOuterAlt(_localctx, 7)
			if (true){
			this.state = 246
			returnStatement()
			}}
			8 -> {
			enterOuterAlt(_localctx, 8)
			if (true){
			this.state = 247
			ifStatement()
			}}
			9 -> {
			enterOuterAlt(_localctx, 9)
			if (true){
			this.state = 248
			expression(0)
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class VariableDeclarationContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_variableDeclaration.id
	        set(value) { throw RuntimeException() }
		fun findName() : NameContext? = getRuleContext(solver.getType("NameContext"),0)
		fun ASSIGN_EQ() : TerminalNode? = getToken(CASCParser.Tokens.ASSIGN_EQ.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitVariableDeclaration(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  variableDeclaration() : VariableDeclarationContext {
		var _localctx : VariableDeclarationContext = VariableDeclarationContext(context, state)
		enterRule(_localctx, 34, Rules.RULE_variableDeclaration.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 251
			name()
			this.state = 252
			match(ASSIGN_EQ) as Token
			this.state = 253
			expression(0)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class AssignmentContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_assignment.id
	        set(value) { throw RuntimeException() }
		fun findName() : NameContext? = getRuleContext(solver.getType("NameContext"),0)
		fun EQUALS() : TerminalNode? = getToken(CASCParser.Tokens.EQUALS.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitAssignment(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  assignment() : AssignmentContext {
		var _localctx : AssignmentContext = AssignmentContext(context, state)
		enterRule(_localctx, 36, Rules.RULE_assignment.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 255
			name()
			this.state = 256
			match(EQUALS) as Token
			this.state = 257
			expression(0)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class PrintStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_printStatement.id
	        set(value) { throw RuntimeException() }
		fun PRINT() : TerminalNode? = getToken(CASCParser.Tokens.PRINT.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitPrintStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  printStatement() : PrintStatementContext {
		var _localctx : PrintStatementContext = PrintStatementContext(context, state)
		enterRule(_localctx, 38, Rules.RULE_printStatement.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 259
			match(PRINT) as Token
			this.state = 260
			match(T__3) as Token
			this.state = 261
			expression(0)
			this.state = 262
			match(T__5) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class PrintlnStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_printlnStatement.id
	        set(value) { throw RuntimeException() }
		fun PRINTLN() : TerminalNode? = getToken(CASCParser.Tokens.PRINTLN.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitPrintlnStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  printlnStatement() : PrintlnStatementContext {
		var _localctx : PrintlnStatementContext = PrintlnStatementContext(context, state)
		enterRule(_localctx, 40, Rules.RULE_printlnStatement.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 264
			match(PRINTLN) as Token
			this.state = 265
			match(T__3) as Token
			this.state = 266
			expression(0)
			this.state = 267
			match(T__5) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ReturnStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_returnStatement.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
	 
		constructor() : super() { }
		fun copyFrom(ctx: ReturnStatementContext) {
			super.copyFrom(ctx)
		}
	}
	open class ReturnVoidContext : ReturnStatementContext {
		fun RETURN() : TerminalNode? = getToken(CASCParser.Tokens.RETURN.id, 0)
		constructor(ctx: ReturnStatementContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitReturnVoid(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ReturnWithValueContext : ReturnStatementContext {
		fun RETURN() : TerminalNode? = getToken(CASCParser.Tokens.RETURN.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(ctx: ReturnStatementContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitReturnWithValue(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  returnStatement() : ReturnStatementContext {
		var _localctx : ReturnStatementContext = ReturnStatementContext(context, state)
		enterRule(_localctx, 42, Rules.RULE_returnStatement.id)
		try {
			this.state = 272
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,25,context) ) {
			1 -> {_localctx = ReturnWithValueContext(_localctx)
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 269
			match(RETURN) as Token
			this.state = 270
			expression(0)
			}}
			2 -> {_localctx = ReturnVoidContext(_localctx)
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 271
			match(RETURN) as Token
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class IfStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_ifStatement.id
	        set(value) { throw RuntimeException() }
		var condition: ExpressionContext? = null
		var trueStatement: StatementContext? = null
		var falseStatement: StatementContext? = null
		fun IF() : TerminalNode? = getToken(CASCParser.Tokens.IF.id, 0)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		fun findStatement() : List<StatementContext> = getRuleContexts(solver.getType("StatementContext"))
		fun findStatement(i: Int) : StatementContext? = getRuleContext(solver.getType("StatementContext"),i)
		fun ELSE() : TerminalNode? = getToken(CASCParser.Tokens.ELSE.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitIfStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  ifStatement() : IfStatementContext {
		var _localctx : IfStatementContext = IfStatementContext(context, state)
		enterRule(_localctx, 44, Rules.RULE_ifStatement.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 274
			match(IF) as Token
			this.state = 276
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,26,context) ) {
			1   -> if (true){
			this.state = 275
			match(T__3) as Token
			}
			}
			this.state = 278
			(_localctx as IfStatementContext).condition = expression(0)
			this.state = 280
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__5) {
				if (true){
				this.state = 279
				match(T__5) as Token
				}
			}

			this.state = 282
			(_localctx as IfStatementContext).trueStatement = statement()
			this.state = 285
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,28,context) ) {
			1   -> if (true){
			this.state = 283
			match(ELSE) as Token
			this.state = 284
			(_localctx as IfStatementContext).falseStatement = statement()
			}
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ForStatementContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_forStatement.id
	        set(value) { throw RuntimeException() }
		fun FOR() : TerminalNode? = getToken(CASCParser.Tokens.FOR.id, 0)
		fun findForRangedExpression() : ForRangedExpressionContext? = getRuleContext(solver.getType("ForRangedExpressionContext"),0)
		fun findStatement() : StatementContext? = getRuleContext(solver.getType("StatementContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitForStatement(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  forStatement() : ForStatementContext {
		var _localctx : ForStatementContext = ForStatementContext(context, state)
		enterRule(_localctx, 46, Rules.RULE_forStatement.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 287
			match(FOR) as Token
			this.state = 289
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__3) {
				if (true){
				this.state = 288
				match(T__3) as Token
				}
			}

			this.state = 291
			forRangedExpression()
			this.state = 293
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__5) {
				if (true){
				this.state = 292
				match(T__5) as Token
				}
			}

			this.state = 295
			statement()
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ForRangedExpressionContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_forRangedExpression.id
	        set(value) { throw RuntimeException() }
		var iterator: VarReferenceContext? = null
		var startExpr: ExpressionContext? = null
		var down: Token? = null
		var range: Token? = null
		var endExpr: ExpressionContext? = null
		fun findVarReference() : VarReferenceContext? = getRuleContext(solver.getType("VarReferenceContext"),0)
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun TO() : TerminalNode? = getToken(CASCParser.Tokens.TO.id, 0)
		fun UNTIL() : TerminalNode? = getToken(CASCParser.Tokens.UNTIL.id, 0)
		fun DOWN() : TerminalNode? = getToken(CASCParser.Tokens.DOWN.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitForRangedExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  forRangedExpression() : ForRangedExpressionContext {
		var _localctx : ForRangedExpressionContext = ForRangedExpressionContext(context, state)
		enterRule(_localctx, 48, Rules.RULE_forRangedExpression.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 297
			(_localctx as ForRangedExpressionContext).iterator = varReference()
			this.state = 298
			match(T__2) as Token
			this.state = 299
			(_localctx as ForRangedExpressionContext).startExpr = expression(0)
			this.state = 301
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==DOWN) {
				if (true){
				this.state = 300
				(_localctx as ForRangedExpressionContext).down = match(DOWN) as Token
				}
			}

			this.state = 303
			(_localctx as ForRangedExpressionContext).range = _input!!.LT(1)
			_la = _input!!.LA(1)
			if ( !(_la==TO || _la==UNTIL) ) {
				(_localctx as ForRangedExpressionContext).range = errorHandler.recoverInline(this) as Token
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 304
			(_localctx as ForRangedExpressionContext).endExpr = expression(0)
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class NameContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_name.id
	        set(value) { throw RuntimeException() }
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitName(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  name() : NameContext {
		var _localctx : NameContext = NameContext(context, state)
		enterRule(_localctx, 50, Rules.RULE_name.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 306
			match(ID) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ArgumentContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_argument.id
	        set(value) { throw RuntimeException() }
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		fun findName() : NameContext? = getRuleContext(solver.getType("NameContext"),0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitArgument(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  argument() : ArgumentContext {
		var _localctx : ArgumentContext = ArgumentContext(context, state)
		enterRule(_localctx, 52, Rules.RULE_argument.id)
		try {
			this.state = 313
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,32,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 308
			expression(0)
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 309
			name()
			this.state = 310
			match(EQUALS) as Token
			this.state = 311
			expression(0)
			}}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	open class ExpressionContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_expression.id
	        set(value) { throw RuntimeException() }
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
	 
		constructor() : super() { }
		fun copyFrom(ctx: ExpressionContext) {
			super.copyFrom(ctx)
		}
	}
	open class AddContext : ExpressionContext {
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun PLUS() : TerminalNode? = getToken(CASCParser.Tokens.PLUS.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitAdd(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class NegativeExpressionContext : ExpressionContext {
		public var NEG: Token? = null
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		fun MINUS() : TerminalNode? = getToken(CASCParser.Tokens.MINUS.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitNegativeExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ConditionalExpressionContext : ExpressionContext {
		public var cmp: Token? = null
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun GREATER() : TerminalNode? = getToken(CASCParser.Tokens.GREATER.id, 0)
		fun LESS() : TerminalNode? = getToken(CASCParser.Tokens.LESS.id, 0)
		fun EQ() : TerminalNode? = getToken(CASCParser.Tokens.EQ.id, 0)
		fun NOT_EQ() : TerminalNode? = getToken(CASCParser.Tokens.NOT_EQ.id, 0)
		fun GREATER_EQ() : TerminalNode? = getToken(CASCParser.Tokens.GREATER_EQ.id, 0)
		fun LESS_EQ() : TerminalNode? = getToken(CASCParser.Tokens.LESS_EQ.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitConditionalExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class SubtractContext : ExpressionContext {
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun MINUS() : TerminalNode? = getToken(CASCParser.Tokens.MINUS.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitSubtract(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ConstructorCallContext : ExpressionContext {
		fun findClassName() : ClassNameContext? = getRuleContext(solver.getType("ClassNameContext"),0)
		fun findArgument() : List<ArgumentContext> = getRuleContexts(solver.getType("ArgumentContext"))
		fun findArgument(i: Int) : ArgumentContext? = getRuleContext(solver.getType("ArgumentContext"),i)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitConstructorCall(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class VarRefContext : ExpressionContext {
		fun findVarReference() : VarReferenceContext? = getRuleContext(solver.getType("VarReferenceContext"),0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitVarRef(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class FunctionCallContext : ExpressionContext {
		public var owner: ExpressionContext? = null
		fun findFunctionName() : FunctionNameContext? = getRuleContext(solver.getType("FunctionNameContext"),0)
		fun findArgument() : List<ArgumentContext> = getRuleContexts(solver.getType("ArgumentContext"))
		fun findArgument(i: Int) : ArgumentContext? = getRuleContext(solver.getType("ArgumentContext"),i)
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitFunctionCall(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class SuperCallContext : ExpressionContext {
		public var superCall: Token? = null
		fun THIS() : TerminalNode? = getToken(CASCParser.Tokens.THIS.id, 0)
		fun findArgument() : List<ArgumentContext> = getRuleContexts(solver.getType("ArgumentContext"))
		fun findArgument(i: Int) : ArgumentContext? = getRuleContext(solver.getType("ArgumentContext"),i)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitSuperCall(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class DivideContext : ExpressionContext {
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun SLASH() : TerminalNode? = getToken(CASCParser.Tokens.SLASH.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitDivide(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class IfExpressionContext : ExpressionContext {
		public var left: ExpressionContext? = null
		public var condition: ExpressionContext? = null
		public var cmp: Token? = null
		public var right: ExpressionContext? = null
		public var trueExpression: ExpressionContext? = null
		public var falseExpression: ExpressionContext? = null
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun GREATER() : TerminalNode? = getToken(CASCParser.Tokens.GREATER.id, 0)
		fun LESS() : TerminalNode? = getToken(CASCParser.Tokens.LESS.id, 0)
		fun EQ() : TerminalNode? = getToken(CASCParser.Tokens.EQ.id, 0)
		fun NOT_EQ() : TerminalNode? = getToken(CASCParser.Tokens.NOT_EQ.id, 0)
		fun GREATER_EQ() : TerminalNode? = getToken(CASCParser.Tokens.GREATER_EQ.id, 0)
		fun LESS_EQ() : TerminalNode? = getToken(CASCParser.Tokens.LESS_EQ.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitIfExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class MultiplyContext : ExpressionContext {
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
		fun STAR() : TerminalNode? = getToken(CASCParser.Tokens.STAR.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitMultiply(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class ValueContext : ExpressionContext {
		fun NUMBER() : TerminalNode? = getToken(CASCParser.Tokens.NUMBER.id, 0)
		fun BOOL() : TerminalNode? = getToken(CASCParser.Tokens.BOOL.id, 0)
		fun STRING() : TerminalNode? = getToken(CASCParser.Tokens.STRING.id, 0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitValue(this)
			else return visitor.visitChildren(this)!!
		}
	}
	open class WrappedExpressionContext : ExpressionContext {
		fun findExpression() : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),0)
		constructor(ctx: ExpressionContext) { copyFrom(ctx) }
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitWrappedExpression(this)
			else return visitor.visitChildren(this)!!
		}
	}

	 fun expression() : ExpressionContext {
		return expression(0);
	}

	private fun expression(_p: Int) : ExpressionContext {
		var _parentctx : ParserRuleContext? = context
		var _parentState : Int = state
		var _localctx : ExpressionContext= ExpressionContext(context, _parentState)
		var _prevctx : ExpressionContext= _localctx
		var _startState : Int = 54
		enterRecursionRule(_localctx, 54, Rules.RULE_expression.id, _p)
		var _la: Int
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 365
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,39,context) ) {
			1 -> {if (true){
			_localctx = SuperCallContext(_localctx)
			context = _localctx
			_prevctx = _localctx

			this.state = 316
			(_localctx as SuperCallContext).superCall = match(THIS) as Token
			this.state = 317
			match(T__3) as Token
			this.state = 319
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__3) or (1L shl THIS) or (1L shl MINUS) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 318
				argument()
				}
			}

			this.state = 325
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__4) {
				if (true){
				if (true){
				this.state = 321
				match(T__4) as Token
				this.state = 322
				argument()
				}
				}
				this.state = 327
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 328
			match(T__5) as Token
			}}
			2 -> {if (true){
			_localctx = ConstructorCallContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 329
			className()
			this.state = 330
			match(T__3) as Token
			this.state = 332
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__3) or (1L shl THIS) or (1L shl MINUS) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 331
				argument()
				}
			}

			this.state = 338
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__4) {
				if (true){
				if (true){
				this.state = 334
				match(T__4) as Token
				this.state = 335
				argument()
				}
				}
				this.state = 340
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 341
			match(T__5) as Token
			}}
			3 -> {if (true){
			_localctx = FunctionCallContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 343
			functionName()
			this.state = 344
			match(T__3) as Token
			this.state = 346
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__3) or (1L shl THIS) or (1L shl MINUS) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl ID))) != 0L)) {
				if (true){
				this.state = 345
				argument()
				}
			}

			this.state = 352
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__4) {
				if (true){
				if (true){
				this.state = 348
				match(T__4) as Token
				this.state = 349
				argument()
				}
				}
				this.state = 354
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 355
			match(T__5) as Token
			}}
			4 -> {if (true){
			_localctx = NegativeExpressionContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 357
			(_localctx as NegativeExpressionContext).NEG = match(MINUS) as Token
			this.state = 358
			expression(21)
			}}
			5 -> {if (true){
			_localctx = WrappedExpressionContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 359
			match(T__3) as Token
			this.state = 360
			expression(0)
			this.state = 361
			match(T__5) as Token
			}}
			6 -> {if (true){
			_localctx = VarRefContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 363
			varReference()
			}}
			7 -> {if (true){
			_localctx = ValueContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 364
			_la = _input!!.LA(1)
			if ( !((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL))) != 0L)) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			}}
			}
			this.context!!.stop = _input!!.LT(-1)
			this.state = 469
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,43,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent()
					    _prevctx = _localctx
					if (true){
					this.state = 467
					errorHandler.sync(this)
					when ( interpreter!!.adaptivePredict(_input!!,42,context) ) {
					1 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 367
					if (!(precpred(context!!, 18))) throw FailedPredicateException(this, "precpred(context!!, 18)")
					this.state = 368
					(_localctx as ConditionalExpressionContext).cmp = match(GREATER) as Token
					this.state = 369
					expression(19)
					}}
					2 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 370
					if (!(precpred(context!!, 17))) throw FailedPredicateException(this, "precpred(context!!, 17)")
					this.state = 371
					(_localctx as ConditionalExpressionContext).cmp = match(LESS) as Token
					this.state = 372
					expression(18)
					}}
					3 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 373
					if (!(precpred(context!!, 16))) throw FailedPredicateException(this, "precpred(context!!, 16)")
					this.state = 374
					(_localctx as ConditionalExpressionContext).cmp = match(EQ) as Token
					this.state = 375
					expression(17)
					}}
					4 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 376
					if (!(precpred(context!!, 15))) throw FailedPredicateException(this, "precpred(context!!, 15)")
					this.state = 377
					(_localctx as ConditionalExpressionContext).cmp = match(NOT_EQ) as Token
					this.state = 378
					expression(16)
					}}
					5 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 379
					if (!(precpred(context!!, 14))) throw FailedPredicateException(this, "precpred(context!!, 14)")
					this.state = 380
					(_localctx as ConditionalExpressionContext).cmp = match(GREATER_EQ) as Token
					this.state = 381
					expression(15)
					}}
					6 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 382
					if (!(precpred(context!!, 13))) throw FailedPredicateException(this, "precpred(context!!, 13)")
					this.state = 383
					(_localctx as ConditionalExpressionContext).cmp = match(LESS_EQ) as Token
					this.state = 384
					expression(14)
					}}
					7 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 385
					if (!(precpred(context!!, 12))) throw FailedPredicateException(this, "precpred(context!!, 12)")
					this.state = 386
					(_localctx as IfExpressionContext).cmp = match(GREATER) as Token
					this.state = 387
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 388
					match(T__20) as Token
					this.state = 389
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 390
					match(T__2) as Token
					this.state = 391
					(_localctx as IfExpressionContext).falseExpression = expression(13)
					}}
					8 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 393
					if (!(precpred(context!!, 11))) throw FailedPredicateException(this, "precpred(context!!, 11)")
					this.state = 394
					(_localctx as IfExpressionContext).cmp = match(LESS) as Token
					this.state = 395
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 396
					match(T__20) as Token
					this.state = 397
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 398
					match(T__2) as Token
					this.state = 399
					(_localctx as IfExpressionContext).falseExpression = expression(12)
					}}
					9 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 401
					if (!(precpred(context!!, 10))) throw FailedPredicateException(this, "precpred(context!!, 10)")
					this.state = 402
					(_localctx as IfExpressionContext).cmp = match(EQ) as Token
					this.state = 403
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 404
					match(T__20) as Token
					this.state = 405
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 406
					match(T__2) as Token
					this.state = 407
					(_localctx as IfExpressionContext).falseExpression = expression(11)
					}}
					10 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 409
					if (!(precpred(context!!, 9))) throw FailedPredicateException(this, "precpred(context!!, 9)")
					this.state = 410
					(_localctx as IfExpressionContext).cmp = match(NOT_EQ) as Token
					this.state = 411
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 412
					match(T__20) as Token
					this.state = 413
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 414
					match(T__2) as Token
					this.state = 415
					(_localctx as IfExpressionContext).falseExpression = expression(10)
					}}
					11 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 417
					if (!(precpred(context!!, 8))) throw FailedPredicateException(this, "precpred(context!!, 8)")
					this.state = 418
					(_localctx as IfExpressionContext).cmp = match(GREATER_EQ) as Token
					this.state = 419
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 420
					match(T__20) as Token
					this.state = 421
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 422
					match(T__2) as Token
					this.state = 423
					(_localctx as IfExpressionContext).falseExpression = expression(9)
					}}
					12 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).left = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 425
					if (!(precpred(context!!, 7))) throw FailedPredicateException(this, "precpred(context!!, 7)")
					this.state = 426
					(_localctx as IfExpressionContext).cmp = match(LESS_EQ) as Token
					this.state = 427
					(_localctx as IfExpressionContext).right = expression(0)
					this.state = 428
					match(T__20) as Token
					this.state = 429
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 430
					match(T__2) as Token
					this.state = 431
					(_localctx as IfExpressionContext).falseExpression = expression(8)
					}}
					13 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).condition = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 433
					if (!(precpred(context!!, 6))) throw FailedPredicateException(this, "precpred(context!!, 6)")
					this.state = 434
					match(T__20) as Token
					this.state = 435
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 436
					match(T__2) as Token
					this.state = 437
					(_localctx as IfExpressionContext).falseExpression = expression(7)
					}}
					14 -> {if (true){
					_localctx = MultiplyContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 439
					if (!(precpred(context!!, 5))) throw FailedPredicateException(this, "precpred(context!!, 5)")
					this.state = 440
					match(STAR) as Token
					this.state = 441
					expression(6)
					}}
					15 -> {if (true){
					_localctx = DivideContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 442
					if (!(precpred(context!!, 4))) throw FailedPredicateException(this, "precpred(context!!, 4)")
					this.state = 443
					match(SLASH) as Token
					this.state = 444
					expression(5)
					}}
					16 -> {if (true){
					_localctx = AddContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 445
					if (!(precpred(context!!, 3))) throw FailedPredicateException(this, "precpred(context!!, 3)")
					this.state = 446
					match(PLUS) as Token
					this.state = 447
					expression(4)
					}}
					17 -> {if (true){
					_localctx = SubtractContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 448
					if (!(precpred(context!!, 2))) throw FailedPredicateException(this, "precpred(context!!, 2)")
					this.state = 449
					match(MINUS) as Token
					this.state = 450
					expression(3)
					}}
					18 -> {if (true){
					_localctx = FunctionCallContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as FunctionCallContext).owner = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 451
					if (!(precpred(context!!, 23))) throw FailedPredicateException(this, "precpred(context!!, 23)")
					this.state = 452
					match(T__19) as Token
					this.state = 453
					functionName()
					this.state = 454
					match(T__3) as Token
					this.state = 456
					errorHandler.sync(this)
					_la = _input!!.LA(1)
					if ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__3) or (1L shl THIS) or (1L shl MINUS) or (1L shl NUMBER) or (1L shl STRING) or (1L shl BOOL) or (1L shl ID))) != 0L)) {
						if (true){
						this.state = 455
						argument()
						}
					}

					this.state = 462
					errorHandler.sync(this);
					_la = _input!!.LA(1)
					while (_la==T__4) {
						if (true){
						if (true){
						this.state = 458
						match(T__4) as Token
						this.state = 459
						argument()
						}
						}
						this.state = 464
						errorHandler.sync(this)
						_la = _input!!.LA(1)
					}
					this.state = 465
					match(T__5) as Token
					}}
					}
					} 
				}
				this.state = 471
				errorHandler.sync(this)
				_alt = interpreter!!.adaptivePredict(_input!!,43,context)
			}
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			unrollRecursionContexts(_parentctx)
		}
		return _localctx
	}

	open class VarReferenceContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_varReference.id
	        set(value) { throw RuntimeException() }
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
		constructor(parent: ParserRuleContext?, invokingState: Int) : super(parent, invokingState){
		}
		override fun <T> accept(visitor : ParseTreeVisitor<out T>) : T {
			if ( visitor is CASCVisitor ) return (visitor as CASCVisitor<out T>).visitVarReference(this)
			else return visitor.visitChildren(this)!!
		}
	}

	fun  varReference() : VarReferenceContext {
		var _localctx : VarReferenceContext = VarReferenceContext(context, state)
		enterRule(_localctx, 56, Rules.RULE_varReference.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 472
			match(ID) as Token
			}
		}
		catch (re: RecognitionException) {
			_localctx.exception = re
			errorHandler.reportError(this, re)
			errorHandler.recover(this, re)
		}
		finally {
			exitRule()
		}
		return _localctx
	}

	override fun sempred(_localctx: RuleContext?, ruleIndex: Int, predIndex: Int) : Boolean {
		when (ruleIndex) {
		27 -> return expression_sempred(_localctx as ExpressionContext, predIndex)
		}
		return true
	}
	private fun expression_sempred( _localctx : ExpressionContext, predIndex: Int) : Boolean {
		when (predIndex) {
		    0 -> return precpred(context!!, 18)
		    1 -> return precpred(context!!, 17)
		    2 -> return precpred(context!!, 16)
		    3 -> return precpred(context!!, 15)
		    4 -> return precpred(context!!, 14)
		    5 -> return precpred(context!!, 13)
		    6 -> return precpred(context!!, 12)
		    7 -> return precpred(context!!, 11)
		    8 -> return precpred(context!!, 10)
		    9 -> return precpred(context!!, 9)
		    10 -> return precpred(context!!, 8)
		    11 -> return precpred(context!!, 7)
		    12 -> return precpred(context!!, 6)
		    13 -> return precpred(context!!, 5)
		    14 -> return precpred(context!!, 4)
		    15 -> return precpred(context!!, 3)
		    16 -> return precpred(context!!, 2)
		    17 -> return precpred(context!!, 23)
		}
		return true
	}

}