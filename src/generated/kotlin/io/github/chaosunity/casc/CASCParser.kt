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
        T__21(22),
        T__22(23),
        T__23(24),
        T__24(25),
        T__25(26),
        T__26(27),
        T__27(28),
        T__28(29),
        T__29(30),
        T__30(31),
        T__31(32),
        T__32(33),
        T__33(34),
        T__34(35),
        T__35(36),
        T__36(37),
        T__37(38),
        CLASS(39),
        FUNC(40),
        VARIABLE(41),
        IF(42),
        ELSE(43),
        RETURN(44),
        FOR(45),
        DOWN(46),
        TO(47),
        UNTIL(48),
        PRINT(49),
        PRINTLN(50),
        PLUS(51),
        MINUS(52),
        STAR(53),
        SLASH(54),
        EQUALS(55),
        GREATER(56),
        LESS(57),
        GREATER_EQ(58),
        LESS_EQ(59),
        EQ(60),
        NOT_EQ(61),
        NUMBER(62),
        STRING(63),
        BOOL(64),
        ID(65),
        WS(66)
    }

    enum class Rules(val id: Int) {
        RULE_compilationUnit(0),
        RULE_classDeclaration(1),
        RULE_className(2),
        RULE_classBody(3),
        RULE_constructor(4),
        RULE_constructorDeclaration(5),
        RULE_function(6),
        RULE_functionDeclaration(7),
        RULE_functionName(8),
        RULE_parameter(9),
        RULE_type(10),
        RULE_primitiveType(11),
        RULE_classType(12),
        RULE_qualifiedName(13),
        RULE_block(14),
        RULE_statement(15),
        RULE_variableDeclaration(16),
        RULE_printStatement(17),
        RULE_printlnStatement(18),
        RULE_returnStatement(19),
        RULE_ifStatement(20),
        RULE_forStatement(21),
        RULE_forRangedExpression(22),
        RULE_name(23),
        RULE_argument(24),
        RULE_expression(25),
        RULE_varReference(26)
    }

	@ThreadLocal
	companion object {
	    protected val decisionToDFA : Array<DFA>
    	protected val sharedContextCache = PredictionContextCache()

        val ruleNames = arrayOf("compilationUnit", "classDeclaration", "className", 
                                "classBody", "constructor", "constructorDeclaration", 
                                "function", "functionDeclaration", "functionName", 
                                "parameter", "type", "primitiveType", "classType", 
                                "qualifiedName", "block", "statement", "variableDeclaration", 
                                "printStatement", "printlnStatement", "returnStatement", 
                                "ifStatement", "forStatement", "forRangedExpression", 
                                "name", "argument", "expression", "varReference")

        private val LITERAL_NAMES: List<String?> = listOf(null, "'{'", "'}'", 
                                                          "'ctor'", "'('", 
                                                          "','", "')'", 
                                                          "'fn'", "':'", 
                                                          "'='", "'boolean'", 
                                                          "'\u5E03\u6797'", 
                                                          "'['", "']'", 
                                                          "'string'", "'\u5B57\u4E32'", 
                                                          "'char'", "'\u5B57\u5143'", 
                                                          "'byte'", "'\u4F4D\u5143'", 
                                                          "'short'", "'\u77ED\u6574\u6578'", 
                                                          "'int'", "'\u6574\u6578'", 
                                                          "'long'", "'\u9577\u6574\u6578'", 
                                                          "'float'", "'\u6D6E\u9EDE\u6578'", 
                                                          "'double'", "'\u500D\u6D6E\u9EDE\u6578'", 
                                                          "'void'", "'\u7A7A'", 
                                                          "'::'", "':='", 
                                                          "'\u8CA0'", "'-'", 
                                                          "'this'", "'.'", 
                                                          "'?'", null, null, 
                                                          null, null, null, 
                                                          null, null, "'down'")
        private val SYMBOLIC_NAMES: List<String?> = listOf(null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           null, null, null, 
                                                           "CLASS", "FUNC", 
                                                           "VARIABLE", "IF", 
                                                           "ELSE", "RETURN", 
                                                           "FOR", "DOWN", 
                                                           "TO", "UNTIL", 
                                                           "PRINT", "PRINTLN", 
                                                           "PLUS", "MINUS", 
                                                           "STAR", "SLASH", 
                                                           "EQUALS", "GREATER", 
                                                           "LESS", "GREATER_EQ", 
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

        private const val serializedATN : String = "\u0003\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\u0003\u0044\u019e\u0004\u0002\u0009\u0002\u0004\u0003\u0009\u0003\u0004\u0004\u0009\u0004\u0004\u0005\u0009\u0005\u0004\u0006\u0009\u0006\u0004\u0007\u0009\u0007\u0004\u0008\u0009\u0008\u0004\u0009\u0009\u0009\u0004\u000a\u0009\u000a\u0004\u000b\u0009\u000b\u0004\u000c\u0009\u000c\u0004\u000d\u0009\u000d\u0004\u000e\u0009\u000e\u0004\u000f\u0009\u000f\u0004\u0010\u0009\u0010\u0004\u0011\u0009\u0011\u0004\u0012\u0009\u0012\u0004\u0013\u0009\u0013\u0004\u0014\u0009\u0014\u0004\u0015\u0009\u0015\u0004\u0016\u0009\u0016\u0004\u0017\u0009\u0017\u0004\u0018\u0009\u0018\u0004\u0019\u0009\u0019\u0004\u001a\u0009\u001a\u0004\u001b\u0009\u001b\u0004\u001c\u0009\u001c\u0003\u0002\u0003\u0002\u0003\u0002\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0003\u0004\u0003\u0004\u0003\u0005\u0003\u0005\u0007\u0005\u0046\u000a\u0005\u000c\u0005\u000e\u0005\u0049\u000b\u0005\u0003\u0006\u0003\u0006\u0005\u0006\u004d\u000a\u0006\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0007\u0003\u0007\u0007\u0007\u0054\u000a\u0007\u000c\u0007\u000e\u0007\u0057\u000b\u0007\u0005\u0007\u0059\u000a\u0007\u0003\u0007\u0003\u0007\u0003\u0008\u0003\u0008\u0003\u0008\u0003\u0009\u0003\u0009\u0003\u0009\u0003\u0009\u0003\u0009\u0003\u0009\u0007\u0009\u0066\u000a\u0009\u000c\u0009\u000e\u0009\u0069\u000b\u0009\u0005\u0009\u006b\u000a\u0009\u0003\u0009\u0003\u0009\u0003\u0009\u0005\u0009\u0070\u000a\u0009\u0003\u000a\u0003\u000a\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0003\u000b\u0005\u000b\u0079\u000a\u000b\u0003\u000c\u0003\u000c\u0005\u000c\u007d\u000a\u000c\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u0082\u000a\u000d\u000c\u000d\u000e\u000d\u0085\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u008a\u000a\u000d\u000c\u000d\u000e\u000d\u008d\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u0092\u000a\u000d\u000c\u000d\u000e\u000d\u0095\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u009a\u000a\u000d\u000c\u000d\u000e\u000d\u009d\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u00a2\u000a\u000d\u000c\u000d\u000e\u000d\u00a5\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u00aa\u000a\u000d\u000c\u000d\u000e\u000d\u00ad\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u00b2\u000a\u000d\u000c\u000d\u000e\u000d\u00b5\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u00ba\u000a\u000d\u000c\u000d\u000e\u000d\u00bd\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u00c2\u000a\u000d\u000c\u000d\u000e\u000d\u00c5\u000b\u000d\u0003\u000d\u0003\u000d\u0003\u000d\u0007\u000d\u00ca\u000a\u000d\u000c\u000d\u000e\u000d\u00cd\u000b\u000d\u0005\u000d\u00cf\u000a\u000d\u0003\u000e\u0003\u000e\u0003\u000e\u0007\u000e\u00d4\u000a\u000e\u000c\u000e\u000e\u000e\u00d7\u000b\u000e\u0003\u000f\u0003\u000f\u0003\u000f\u0006\u000f\u00dc\u000a\u000f\u000d\u000f\u000e\u000f\u00dd\u0003\u0010\u0003\u0010\u0007\u0010\u00e2\u000a\u0010\u000c\u0010\u000e\u0010\u00e5\u000b\u0010\u0003\u0010\u0003\u0010\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0003\u0011\u0005\u0011\u00f1\u000a\u0011\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0012\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0013\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0014\u0003\u0015\u0003\u0015\u0003\u0015\u0005\u0015\u0104\u000a\u0015\u0003\u0016\u0003\u0016\u0005\u0016\u0108\u000a\u0016\u0003\u0016\u0003\u0016\u0005\u0016\u010c\u000a\u0016\u0003\u0016\u0003\u0016\u0003\u0016\u0005\u0016\u0111\u000a\u0016\u0003\u0017\u0003\u0017\u0005\u0017\u0115\u000a\u0017\u0003\u0017\u0003\u0017\u0005\u0017\u0119\u000a\u0017\u0003\u0017\u0003\u0017\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0005\u0018\u0121\u000a\u0018\u0003\u0018\u0003\u0018\u0003\u0018\u0003\u0019\u0003\u0019\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0003\u001a\u0005\u001a\u012d\u000a\u001a\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0005\u001b\u013a\u000a\u001b\u0003\u001b\u0003\u001b\u0007\u001b\u013e\u000a\u001b\u000c\u001b\u000e\u001b\u0141\u000b\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0005\u001b\u0147\u000a\u001b\u0003\u001b\u0003\u001b\u0007\u001b\u014b\u000a\u001b\u000c\u001b\u000e\u001b\u014e\u000b\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0005\u001b\u0155\u000a\u001b\u0003\u001b\u0003\u001b\u0007\u001b\u0159\u000a\u001b\u000c\u001b\u000e\u001b\u015c\u000b\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0005\u001b\u0161\u000a\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0003\u001b\u0005\u001b\u018c\u000a\u001b\u0003\u001b\u0003\u001b\u0007\u001b\u0190\u000a\u001b\u000c\u001b\u000e\u001b\u0193\u000b\u001b\u0003\u001b\u0003\u001b\u0007\u001b\u0197\u000a\u001b\u000c\u001b\u000e\u001b\u019a\u000b\u001b\u0003\u001c\u0003\u001c\u0003\u001c\u0002\u0003\u0034\u001d\u0002\u0004\u0006\u0008\u000a\u000c\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0020\u0022\u0024\u0026\u0028\u002a\u002c\u002e\u0030\u0032\u0034\u0036\u0002\u000f\u0003\u0002\u000c\u000d\u0003\u0002\u0010\u0011\u0003\u0002\u0012\u0013\u0003\u0002\u0014\u0015\u0003\u0002\u0016\u0017\u0003\u0002\u0018\u0019\u0003\u0002\u001a\u001b\u0003\u0002\u001c\u001d\u0003\u0002\u001e\u001f\u0003\u0002\u0020\u0021\u0003\u0002\u0031\u0032\u0003\u0002\u0024\u0025\u0003\u0002\u0040\u0042\u0002\u01cb\u0002\u0038\u0003\u0002\u0002\u0002\u0004\u003b\u0003\u0002\u0002\u0002\u0006\u0041\u0003\u0002\u0002\u0002\u0008\u0047\u0003\u0002\u0002\u0002\u000a\u004a\u0003\u0002\u0002\u0002\u000c\u004e\u0003\u0002\u0002\u0002\u000e\u005c\u0003\u0002\u0002\u0002\u0010\u005f\u0003\u0002\u0002\u0002\u0012\u0071\u0003\u0002\u0002\u0002\u0014\u0073\u0003\u0002\u0002\u0002\u0016\u007c\u0003\u0002\u0002\u0002\u0018\u00ce\u0003\u0002\u0002\u0002\u001a\u00d0\u0003\u0002\u0002\u0002\u001c\u00d8\u0003\u0002\u0002\u0002\u001e\u00df\u0003\u0002\u0002\u0002\u0020\u00f0\u0003\u0002\u0002\u0002\u0022\u00f2\u0003\u0002\u0002\u0002\u0024\u00f6\u0003\u0002\u0002\u0002\u0026\u00fb\u0003\u0002\u0002\u0002\u0028\u0103\u0003\u0002\u0002\u0002\u002a\u0105\u0003\u0002\u0002\u0002\u002c\u0112\u0003\u0002\u0002\u0002\u002e\u011c\u0003\u0002\u0002\u0002\u0030\u0125\u0003\u0002\u0002\u0002\u0032\u012c\u0003\u0002\u0002\u0002\u0034\u0160\u0003\u0002\u0002\u0002\u0036\u019b\u0003\u0002\u0002\u0002\u0038\u0039\u0005\u0004\u0003\u0002\u0039\u003a\u0007\u0002\u0002\u0003\u003a\u0003\u0003\u0002\u0002\u0002\u003b\u003c\u0007\u0029\u0002\u0002\u003c\u003d\u0005\u0006\u0004\u0002\u003d\u003e\u0007\u0003\u0002\u0002\u003e\u003f\u0005\u0008\u0005\u0002\u003f\u0040\u0007\u0004\u0002\u0002\u0040\u0005\u0003\u0002\u0002\u0002\u0041\u0042\u0007\u0043\u0002\u0002\u0042\u0007\u0003\u0002\u0002\u0002\u0043\u0046\u0005\u000e\u0008\u0002\u0044\u0046\u0005\u000a\u0006\u0002\u0045\u0043\u0003\u0002\u0002\u0002\u0045\u0044\u0003\u0002\u0002\u0002\u0046\u0049\u0003\u0002\u0002\u0002\u0047\u0045\u0003\u0002\u0002\u0002\u0047\u0048\u0003\u0002\u0002\u0002\u0048\u0009\u0003\u0002\u0002\u0002\u0049\u0047\u0003\u0002\u0002\u0002\u004a\u004c\u0005\u000c\u0007\u0002\u004b\u004d\u0005\u001e\u0010\u0002\u004c\u004b\u0003\u0002\u0002\u0002\u004c\u004d\u0003\u0002\u0002\u0002\u004d\u000b\u0003\u0002\u0002\u0002\u004e\u004f\u0007\u0005\u0002\u0002\u004f\u0058\u0007\u0006\u0002\u0002\u0050\u0055\u0005\u0014\u000b\u0002\u0051\u0052\u0007\u0007\u0002\u0002\u0052\u0054\u0005\u0014\u000b\u0002\u0053\u0051\u0003\u0002\u0002\u0002\u0054\u0057\u0003\u0002\u0002\u0002\u0055\u0053\u0003\u0002\u0002\u0002\u0055\u0056\u0003\u0002\u0002\u0002\u0056\u0059\u0003\u0002\u0002\u0002\u0057\u0055\u0003\u0002\u0002\u0002\u0058\u0050\u0003\u0002\u0002\u0002\u0058\u0059\u0003\u0002\u0002\u0002\u0059\u005a\u0003\u0002\u0002\u0002\u005a\u005b\u0007\u0008\u0002\u0002\u005b\u000d\u0003\u0002\u0002\u0002\u005c\u005d\u0005\u0010\u0009\u0002\u005d\u005e\u0005\u001e\u0010\u0002\u005e\u000f\u0003\u0002\u0002\u0002\u005f\u0060\u0007\u0009\u0002\u0002\u0060\u0061\u0005\u0012\u000a\u0002\u0061\u006a\u0007\u0006\u0002\u0002\u0062\u0067\u0005\u0014\u000b\u0002\u0063\u0064\u0007\u0007\u0002\u0002\u0064\u0066\u0005\u0014\u000b\u0002\u0065\u0063\u0003\u0002\u0002\u0002\u0066\u0069\u0003\u0002\u0002\u0002\u0067\u0065\u0003\u0002\u0002\u0002\u0067\u0068\u0003\u0002\u0002\u0002\u0068\u006b\u0003\u0002\u0002\u0002\u0069\u0067\u0003\u0002\u0002\u0002\u006a\u0062\u0003\u0002\u0002\u0002\u006a\u006b\u0003\u0002\u0002\u0002\u006b\u006c\u0003\u0002\u0002\u0002\u006c\u006f\u0007\u0008\u0002\u0002\u006d\u006e\u0007\u000a\u0002\u0002\u006e\u0070\u0005\u0016\u000c\u0002\u006f\u006d\u0003\u0002\u0002\u0002\u006f\u0070\u0003\u0002\u0002\u0002\u0070\u0011\u0003\u0002\u0002\u0002\u0071\u0072\u0007\u0043\u0002\u0002\u0072\u0013\u0003\u0002\u0002\u0002\u0073\u0074\u0007\u0043\u0002\u0002\u0074\u0075\u0007\u000a\u0002\u0002\u0075\u0078\u0005\u0016\u000c\u0002\u0076\u0077\u0007\u000b\u0002\u0002\u0077\u0079\u0005\u0034\u001b\u0002\u0078\u0076\u0003\u0002\u0002\u0002\u0078\u0079\u0003\u0002\u0002\u0002\u0079\u0015\u0003\u0002\u0002\u0002\u007a\u007d\u0005\u0018\u000d\u0002\u007b\u007d\u0005\u001a\u000e\u0002\u007c\u007a\u0003\u0002\u0002\u0002\u007c\u007b\u0003\u0002\u0002\u0002\u007d\u0017\u0003\u0002\u0002\u0002\u007e\u0083\u0009\u0002\u0002\u0002\u007f\u0080\u0007\u000e\u0002\u0002\u0080\u0082\u0007\u000f\u0002\u0002\u0081\u007f\u0003\u0002\u0002\u0002\u0082\u0085\u0003\u0002\u0002\u0002\u0083\u0081\u0003\u0002\u0002\u0002\u0083\u0084\u0003\u0002\u0002\u0002\u0084\u00cf\u0003\u0002\u0002\u0002\u0085\u0083\u0003\u0002\u0002\u0002\u0086\u008b\u0009\u0003\u0002\u0002\u0087\u0088\u0007\u000e\u0002\u0002\u0088\u008a\u0007\u000f\u0002\u0002\u0089\u0087\u0003\u0002\u0002\u0002\u008a\u008d\u0003\u0002\u0002\u0002\u008b\u0089\u0003\u0002\u0002\u0002\u008b\u008c\u0003\u0002\u0002\u0002\u008c\u00cf\u0003\u0002\u0002\u0002\u008d\u008b\u0003\u0002\u0002\u0002\u008e\u0093\u0009\u0004\u0002\u0002\u008f\u0090\u0007\u000e\u0002\u0002\u0090\u0092\u0007\u000f\u0002\u0002\u0091\u008f\u0003\u0002\u0002\u0002\u0092\u0095\u0003\u0002\u0002\u0002\u0093\u0091\u0003\u0002\u0002\u0002\u0093\u0094\u0003\u0002\u0002\u0002\u0094\u00cf\u0003\u0002\u0002\u0002\u0095\u0093\u0003\u0002\u0002\u0002\u0096\u009b\u0009\u0005\u0002\u0002\u0097\u0098\u0007\u000e\u0002\u0002\u0098\u009a\u0007\u000f\u0002\u0002\u0099\u0097\u0003\u0002\u0002\u0002\u009a\u009d\u0003\u0002\u0002\u0002\u009b\u0099\u0003\u0002\u0002\u0002\u009b\u009c\u0003\u0002\u0002\u0002\u009c\u00cf\u0003\u0002\u0002\u0002\u009d\u009b\u0003\u0002\u0002\u0002\u009e\u00a3\u0009\u0006\u0002\u0002\u009f\u00a0\u0007\u000e\u0002\u0002\u00a0\u00a2\u0007\u000f\u0002\u0002\u00a1\u009f\u0003\u0002\u0002\u0002\u00a2\u00a5\u0003\u0002\u0002\u0002\u00a3\u00a1\u0003\u0002\u0002\u0002\u00a3\u00a4\u0003\u0002\u0002\u0002\u00a4\u00cf\u0003\u0002\u0002\u0002\u00a5\u00a3\u0003\u0002\u0002\u0002\u00a6\u00ab\u0009\u0007\u0002\u0002\u00a7\u00a8\u0007\u000e\u0002\u0002\u00a8\u00aa\u0007\u000f\u0002\u0002\u00a9\u00a7\u0003\u0002\u0002\u0002\u00aa\u00ad\u0003\u0002\u0002\u0002\u00ab\u00a9\u0003\u0002\u0002\u0002\u00ab\u00ac\u0003\u0002\u0002\u0002\u00ac\u00cf\u0003\u0002\u0002\u0002\u00ad\u00ab\u0003\u0002\u0002\u0002\u00ae\u00b3\u0009\u0008\u0002\u0002\u00af\u00b0\u0007\u000e\u0002\u0002\u00b0\u00b2\u0007\u000f\u0002\u0002\u00b1\u00af\u0003\u0002\u0002\u0002\u00b2\u00b5\u0003\u0002\u0002\u0002\u00b3\u00b1\u0003\u0002\u0002\u0002\u00b3\u00b4\u0003\u0002\u0002\u0002\u00b4\u00cf\u0003\u0002\u0002\u0002\u00b5\u00b3\u0003\u0002\u0002\u0002\u00b6\u00bb\u0009\u0009\u0002\u0002\u00b7\u00b8\u0007\u000e\u0002\u0002\u00b8\u00ba\u0007\u000f\u0002\u0002\u00b9\u00b7\u0003\u0002\u0002\u0002\u00ba\u00bd\u0003\u0002\u0002\u0002\u00bb\u00b9\u0003\u0002\u0002\u0002\u00bb\u00bc\u0003\u0002\u0002\u0002\u00bc\u00cf\u0003\u0002\u0002\u0002\u00bd\u00bb\u0003\u0002\u0002\u0002\u00be\u00c3\u0009\u000a\u0002\u0002\u00bf\u00c0\u0007\u000e\u0002\u0002\u00c0\u00c2\u0007\u000f\u0002\u0002\u00c1\u00bf\u0003\u0002\u0002\u0002\u00c2\u00c5\u0003\u0002\u0002\u0002\u00c3\u00c1\u0003\u0002\u0002\u0002\u00c3\u00c4\u0003\u0002\u0002\u0002\u00c4\u00cf\u0003\u0002\u0002\u0002\u00c5\u00c3\u0003\u0002\u0002\u0002\u00c6\u00cb\u0009\u000b\u0002\u0002\u00c7\u00c8\u0007\u000e\u0002\u0002\u00c8\u00ca\u0007\u000f\u0002\u0002\u00c9\u00c7\u0003\u0002\u0002\u0002\u00ca\u00cd\u0003\u0002\u0002\u0002\u00cb\u00c9\u0003\u0002\u0002\u0002\u00cb\u00cc\u0003\u0002\u0002\u0002\u00cc\u00cf\u0003\u0002\u0002\u0002\u00cd\u00cb\u0003\u0002\u0002\u0002\u00ce\u007e\u0003\u0002\u0002\u0002\u00ce\u0086\u0003\u0002\u0002\u0002\u00ce\u008e\u0003\u0002\u0002\u0002\u00ce\u0096\u0003\u0002\u0002\u0002\u00ce\u009e\u0003\u0002\u0002\u0002\u00ce\u00a6\u0003\u0002\u0002\u0002\u00ce\u00ae\u0003\u0002\u0002\u0002\u00ce\u00b6\u0003\u0002\u0002\u0002\u00ce\u00be\u0003\u0002\u0002\u0002\u00ce\u00c6\u0003\u0002\u0002\u0002\u00cf\u0019\u0003\u0002\u0002\u0002\u00d0\u00d5\u0005\u001c\u000f\u0002\u00d1\u00d2\u0007\u000e\u0002\u0002\u00d2\u00d4\u0007\u000f\u0002\u0002\u00d3\u00d1\u0003\u0002\u0002\u0002\u00d4\u00d7\u0003\u0002\u0002\u0002\u00d5\u00d3\u0003\u0002\u0002\u0002\u00d5\u00d6\u0003\u0002\u0002\u0002\u00d6\u001b\u0003\u0002\u0002\u0002\u00d7\u00d5\u0003\u0002\u0002\u0002\u00d8\u00db\u0007\u0043\u0002\u0002\u00d9\u00da\u0007\u0022\u0002\u0002\u00da\u00dc\u0007\u0043\u0002\u0002\u00db\u00d9\u0003\u0002\u0002\u0002\u00dc\u00dd\u0003\u0002\u0002\u0002\u00dd\u00db\u0003\u0002\u0002\u0002\u00dd\u00de\u0003\u0002\u0002\u0002\u00de\u001d\u0003\u0002\u0002\u0002\u00df\u00e3\u0007\u0003\u0002\u0002\u00e0\u00e2\u0005\u0020\u0011\u0002\u00e1\u00e0\u0003\u0002\u0002\u0002\u00e2\u00e5\u0003\u0002\u0002\u0002\u00e3\u00e1\u0003\u0002\u0002\u0002\u00e3\u00e4\u0003\u0002\u0002\u0002\u00e4\u00e6\u0003\u0002\u0002\u0002\u00e5\u00e3\u0003\u0002\u0002\u0002\u00e6\u00e7\u0007\u0004\u0002\u0002\u00e7\u001f\u0003\u0002\u0002\u0002\u00e8\u00f1\u0005\u001e\u0010\u0002\u00e9\u00f1\u0005\u0022\u0012\u0002\u00ea\u00f1\u0005\u0024\u0013\u0002\u00eb\u00f1\u0005\u0026\u0014\u0002\u00ec\u00f1\u0005\u002c\u0017\u0002\u00ed\u00f1\u0005\u0028\u0015\u0002\u00ee\u00f1\u0005\u002a\u0016\u0002\u00ef\u00f1\u0005\u0034\u001b\u0002\u00f0\u00e8\u0003\u0002\u0002\u0002\u00f0\u00e9\u0003\u0002\u0002\u0002\u00f0\u00ea\u0003\u0002\u0002\u0002\u00f0\u00eb\u0003\u0002\u0002\u0002\u00f0\u00ec\u0003\u0002\u0002\u0002\u00f0\u00ed\u0003\u0002\u0002\u0002\u00f0\u00ee\u0003\u0002\u0002\u0002\u00f0\u00ef\u0003\u0002\u0002\u0002\u00f1\u0021\u0003\u0002\u0002\u0002\u00f2\u00f3\u0005\u0030\u0019\u0002\u00f3\u00f4\u0007\u0023\u0002\u0002\u00f4\u00f5\u0005\u0034\u001b\u0002\u00f5\u0023\u0003\u0002\u0002\u0002\u00f6\u00f7\u0007\u0033\u0002\u0002\u00f7\u00f8\u0007\u0006\u0002\u0002\u00f8\u00f9\u0005\u0034\u001b\u0002\u00f9\u00fa\u0007\u0008\u0002\u0002\u00fa\u0025\u0003\u0002\u0002\u0002\u00fb\u00fc\u0007\u0034\u0002\u0002\u00fc\u00fd\u0007\u0006\u0002\u0002\u00fd\u00fe\u0005\u0034\u001b\u0002\u00fe\u00ff\u0007\u0008\u0002\u0002\u00ff\u0027\u0003\u0002\u0002\u0002\u0100\u0101\u0007\u002e\u0002\u0002\u0101\u0104\u0005\u0034\u001b\u0002\u0102\u0104\u0007\u002e\u0002\u0002\u0103\u0100\u0003\u0002\u0002\u0002\u0103\u0102\u0003\u0002\u0002\u0002\u0104\u0029\u0003\u0002\u0002\u0002\u0105\u0107\u0007\u002c\u0002\u0002\u0106\u0108\u0007\u0006\u0002\u0002\u0107\u0106\u0003\u0002\u0002\u0002\u0107\u0108\u0003\u0002\u0002\u0002\u0108\u0109\u0003\u0002\u0002\u0002\u0109\u010b\u0005\u0034\u001b\u0002\u010a\u010c\u0007\u0008\u0002\u0002\u010b\u010a\u0003\u0002\u0002\u0002\u010b\u010c\u0003\u0002\u0002\u0002\u010c\u010d\u0003\u0002\u0002\u0002\u010d\u0110\u0005\u0020\u0011\u0002\u010e\u010f\u0007\u002d\u0002\u0002\u010f\u0111\u0005\u0020\u0011\u0002\u0110\u010e\u0003\u0002\u0002\u0002\u0110\u0111\u0003\u0002\u0002\u0002\u0111\u002b\u0003\u0002\u0002\u0002\u0112\u0114\u0007\u002f\u0002\u0002\u0113\u0115\u0007\u0006\u0002\u0002\u0114\u0113\u0003\u0002\u0002\u0002\u0114\u0115\u0003\u0002\u0002\u0002\u0115\u0116\u0003\u0002\u0002\u0002\u0116\u0118\u0005\u002e\u0018\u0002\u0117\u0119\u0007\u0008\u0002\u0002\u0118\u0117\u0003\u0002\u0002\u0002\u0118\u0119\u0003\u0002\u0002\u0002\u0119\u011a\u0003\u0002\u0002\u0002\u011a\u011b\u0005\u0020\u0011\u0002\u011b\u002d\u0003\u0002\u0002\u0002\u011c\u011d\u0005\u0036\u001c\u0002\u011d\u011e\u0007\u000a\u0002\u0002\u011e\u0120\u0005\u0034\u001b\u0002\u011f\u0121\u0007\u0030\u0002\u0002\u0120\u011f\u0003\u0002\u0002\u0002\u0120\u0121\u0003\u0002\u0002\u0002\u0121\u0122\u0003\u0002\u0002\u0002\u0122\u0123\u0009\u000c\u0002\u0002\u0123\u0124\u0005\u0034\u001b\u0002\u0124\u002f\u0003\u0002\u0002\u0002\u0125\u0126\u0007\u0043\u0002\u0002\u0126\u0031\u0003\u0002\u0002\u0002\u0127\u012d\u0005\u0034\u001b\u0002\u0128\u0129\u0005\u0030\u0019\u0002\u0129\u012a\u0007\u000b\u0002\u0002\u012a\u012b\u0005\u0034\u001b\u0002\u012b\u012d\u0003\u0002\u0002\u0002\u012c\u0127\u0003\u0002\u0002\u0002\u012c\u0128\u0003\u0002\u0002\u0002\u012d\u0033\u0003\u0002\u0002\u0002\u012e\u012f\u0008\u001b\u0001\u0002\u012f\u0130\u0009\u000d\u0002\u0002\u0130\u0161\u0005\u0034\u001b\u0015\u0131\u0132\u0007\u0006\u0002\u0002\u0132\u0133\u0005\u0034\u001b\u0002\u0133\u0134\u0007\u0008\u0002\u0002\u0134\u0161\u0003\u0002\u0002\u0002\u0135\u0161\u0005\u0036\u001c\u0002\u0136\u0137\u0007\u0026\u0002\u0002\u0137\u0139\u0007\u0006\u0002\u0002\u0138\u013a\u0005\u0032\u001a\u0002\u0139\u0138\u0003\u0002\u0002\u0002\u0139\u013a\u0003\u0002\u0002\u0002\u013a\u013f\u0003\u0002\u0002\u0002\u013b\u013c\u0007\u0007\u0002\u0002\u013c\u013e\u0005\u0032\u001a\u0002\u013d\u013b\u0003\u0002\u0002\u0002\u013e\u0141\u0003\u0002\u0002\u0002\u013f\u013d\u0003\u0002\u0002\u0002\u013f\u0140\u0003\u0002\u0002\u0002\u0140\u0142\u0003\u0002\u0002\u0002\u0141\u013f\u0003\u0002\u0002\u0002\u0142\u0161\u0007\u0008\u0002\u0002\u0143\u0144\u0005\u0006\u0004\u0002\u0144\u0146\u0007\u0006\u0002\u0002\u0145\u0147\u0005\u0032\u001a\u0002\u0146\u0145\u0003\u0002\u0002\u0002\u0146\u0147\u0003\u0002\u0002\u0002\u0147\u014c\u0003\u0002\u0002\u0002\u0148\u0149\u0007\u0007\u0002\u0002\u0149\u014b\u0005\u0032\u001a\u0002\u014a\u0148\u0003\u0002\u0002\u0002\u014b\u014e\u0003\u0002\u0002\u0002\u014c\u014a\u0003\u0002\u0002\u0002\u014c\u014d\u0003\u0002\u0002\u0002\u014d\u014f\u0003\u0002\u0002\u0002\u014e\u014c\u0003\u0002\u0002\u0002\u014f\u0150\u0007\u0008\u0002\u0002\u0150\u0161\u0003\u0002\u0002\u0002\u0151\u0152\u0005\u0012\u000a\u0002\u0152\u0154\u0007\u0006\u0002\u0002\u0153\u0155\u0005\u0032\u001a\u0002\u0154\u0153\u0003\u0002\u0002\u0002\u0154\u0155\u0003\u0002\u0002\u0002\u0155\u015a\u0003\u0002\u0002\u0002\u0156\u0157\u0007\u0007\u0002\u0002\u0157\u0159\u0005\u0032\u001a\u0002\u0158\u0156\u0003\u0002\u0002\u0002\u0159\u015c\u0003\u0002\u0002\u0002\u015a\u0158\u0003\u0002\u0002\u0002\u015a\u015b\u0003\u0002\u0002\u0002\u015b\u015d\u0003\u0002\u0002\u0002\u015c\u015a\u0003\u0002\u0002\u0002\u015d\u015e\u0007\u0008\u0002\u0002\u015e\u0161\u0003\u0002\u0002\u0002\u015f\u0161\u0009\u000e\u0002\u0002\u0160\u012e\u0003\u0002\u0002\u0002\u0160\u0131\u0003\u0002\u0002\u0002\u0160\u0135\u0003\u0002\u0002\u0002\u0160\u0136\u0003\u0002\u0002\u0002\u0160\u0143\u0003\u0002\u0002\u0002\u0160\u0151\u0003\u0002\u0002\u0002\u0160\u015f\u0003\u0002\u0002\u0002\u0161\u0198\u0003\u0002\u0002\u0002\u0162\u0163\u000c\u000e\u0002\u0002\u0163\u0164\u0007\u003a\u0002\u0002\u0164\u0197\u0005\u0034\u001b\u000f\u0165\u0166\u000c\u000d\u0002\u0002\u0166\u0167\u0007\u003b\u0002\u0002\u0167\u0197\u0005\u0034\u001b\u000e\u0168\u0169\u000c\u000c\u0002\u0002\u0169\u016a\u0007\u003e\u0002\u0002\u016a\u0197\u0005\u0034\u001b\u000d\u016b\u016c\u000c\u000b\u0002\u0002\u016c\u016d\u0007\u003f\u0002\u0002\u016d\u0197\u0005\u0034\u001b\u000c\u016e\u016f\u000c\u000a\u0002\u0002\u016f\u0170\u0007\u003c\u0002\u0002\u0170\u0197\u0005\u0034\u001b\u000b\u0171\u0172\u000c\u0009\u0002\u0002\u0172\u0173\u0007\u003d\u0002\u0002\u0173\u0197\u0005\u0034\u001b\u000a\u0174\u0175\u000c\u0008\u0002\u0002\u0175\u0176\u0007\u0028\u0002\u0002\u0176\u0177\u0005\u0034\u001b\u0002\u0177\u0178\u0007\u000a\u0002\u0002\u0178\u0179\u0005\u0034\u001b\u0009\u0179\u0197\u0003\u0002\u0002\u0002\u017a\u017b\u000c\u0007\u0002\u0002\u017b\u017c\u0007\u0037\u0002\u0002\u017c\u0197\u0005\u0034\u001b\u0008\u017d\u017e\u000c\u0006\u0002\u0002\u017e\u017f\u0007\u0038\u0002\u0002\u017f\u0197\u0005\u0034\u001b\u0007\u0180\u0181\u000c\u0005\u0002\u0002\u0181\u0182\u0007\u0035\u0002\u0002\u0182\u0197\u0005\u0034\u001b\u0006\u0183\u0184\u000c\u0004\u0002\u0002\u0184\u0185\u0007\u0036\u0002\u0002\u0185\u0197\u0005\u0034\u001b\u0005\u0186\u0187\u000c\u0010\u0002\u0002\u0187\u0188\u0007\u0027\u0002\u0002\u0188\u0189\u0005\u0012\u000a\u0002\u0189\u018b\u0007\u0006\u0002\u0002\u018a\u018c\u0005\u0032\u001a\u0002\u018b\u018a\u0003\u0002\u0002\u0002\u018b\u018c\u0003\u0002\u0002\u0002\u018c\u0191\u0003\u0002\u0002\u0002\u018d\u018e\u0007\u0007\u0002\u0002\u018e\u0190\u0005\u0032\u001a\u0002\u018f\u018d\u0003\u0002\u0002\u0002\u0190\u0193\u0003\u0002\u0002\u0002\u0191\u018f\u0003\u0002\u0002\u0002\u0191\u0192\u0003\u0002\u0002\u0002\u0192\u0194\u0003\u0002\u0002\u0002\u0193\u0191\u0003\u0002\u0002\u0002\u0194\u0195\u0007\u0008\u0002\u0002\u0195\u0197\u0003\u0002\u0002\u0002\u0196\u0162\u0003\u0002\u0002\u0002\u0196\u0165\u0003\u0002\u0002\u0002\u0196\u0168\u0003\u0002\u0002\u0002\u0196\u016b\u0003\u0002\u0002\u0002\u0196\u016e\u0003\u0002\u0002\u0002\u0196\u0171\u0003\u0002\u0002\u0002\u0196\u0174\u0003\u0002\u0002\u0002\u0196\u017a\u0003\u0002\u0002\u0002\u0196\u017d\u0003\u0002\u0002\u0002\u0196\u0180\u0003\u0002\u0002\u0002\u0196\u0183\u0003\u0002\u0002\u0002\u0196\u0186\u0003\u0002\u0002\u0002\u0197\u019a\u0003\u0002\u0002\u0002\u0198\u0196\u0003\u0002\u0002\u0002\u0198\u0199\u0003\u0002\u0002\u0002\u0199\u0035\u0003\u0002\u0002\u0002\u019a\u0198\u0003\u0002\u0002\u0002\u019b\u019c\u0007\u0043\u0002\u0002\u019c\u0037\u0003\u0002\u0002\u0002\u002e\u0045\u0047\u004c\u0055\u0058\u0067\u006a\u006f\u0078\u007c\u0083\u008b\u0093\u009b\u00a3\u00ab\u00b3\u00bb\u00c3\u00cb\u00ce\u00d5\u00dd\u00e3\u00f0\u0103\u0107\u010b\u0110\u0114\u0118\u0120\u012c\u0139\u013f\u0146\u014c\u0154\u015a\u0160\u018b\u0191\u0196\u0198"

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
    private val T__21 = Tokens.T__21.id
    private val T__22 = Tokens.T__22.id
    private val T__23 = Tokens.T__23.id
    private val T__24 = Tokens.T__24.id
    private val T__25 = Tokens.T__25.id
    private val T__26 = Tokens.T__26.id
    private val T__27 = Tokens.T__27.id
    private val T__28 = Tokens.T__28.id
    private val T__29 = Tokens.T__29.id
    private val T__30 = Tokens.T__30.id
    private val T__31 = Tokens.T__31.id
    private val T__32 = Tokens.T__32.id
    private val T__33 = Tokens.T__33.id
    private val T__34 = Tokens.T__34.id
    private val T__35 = Tokens.T__35.id
    private val T__36 = Tokens.T__36.id
    private val T__37 = Tokens.T__37.id
    private val CLASS = Tokens.CLASS.id
    private val FUNC = Tokens.FUNC.id
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
			this.state = 54
			classDeclaration()
			this.state = 55
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
			this.state = 57
			match(CLASS) as Token
			this.state = 58
			className()
			this.state = 59
			match(T__0) as Token
			this.state = 60
			classBody()
			this.state = 61
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
		fun ID() : TerminalNode? = getToken(CASCParser.Tokens.ID.id, 0)
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
			this.state = 63
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

	open class ClassBodyContext : ParserRuleContext {
	    override var ruleIndex: Int
	        get() = Rules.RULE_classBody.id
	        set(value) { throw RuntimeException() }
		fun findFunction() : List<FunctionContext> = getRuleContexts(solver.getType("FunctionContext"))
		fun findFunction(i: Int) : FunctionContext? = getRuleContext(solver.getType("FunctionContext"),i)
		fun findConstructor() : List<ConstructorContext> = getRuleContexts(solver.getType("ConstructorContext"))
		fun findConstructor(i: Int) : ConstructorContext? = getRuleContext(solver.getType("ConstructorContext"),i)
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
			this.state = 69
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__2 || _la==T__6) {
				if (true){
				this.state = 67
				errorHandler.sync(this)
				when (_input!!.LA(1)) {
				T__6  ->  /*LL1AltBlock*/{if (true){
				this.state = 65
				function()
				}}
				T__2  ->  /*LL1AltBlock*/{if (true){
				this.state = 66
				constructor()
				}}
				else -> throw NoViableAltException(this)
				}
				}
				this.state = 71
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
		enterRule(_localctx, 8, Rules.RULE_constructor.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 72
			constructorDeclaration()
			this.state = 74
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__0) {
				if (true){
				this.state = 73
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
		enterRule(_localctx, 10, Rules.RULE_constructorDeclaration.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			if (true){
			this.state = 76
			match(T__2) as Token
			}
			this.state = 77
			match(T__3) as Token
			this.state = 86
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==ID) {
				if (true){
				this.state = 78
				parameter()
				this.state = 83
				errorHandler.sync(this);
				_la = _input!!.LA(1)
				while (_la==T__4) {
					if (true){
					if (true){
					this.state = 79
					match(T__4) as Token
					this.state = 80
					parameter()
					}
					}
					this.state = 85
					errorHandler.sync(this)
					_la = _input!!.LA(1)
				}
				}
			}

			this.state = 88
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
		enterRule(_localctx, 12, Rules.RULE_function.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 90
			functionDeclaration()
			this.state = 91
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
		enterRule(_localctx, 14, Rules.RULE_functionDeclaration.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 93
			match(T__6) as Token
			this.state = 94
			functionName()
			this.state = 95
			match(T__3) as Token
			this.state = 104
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==ID) {
				if (true){
				this.state = 96
				parameter()
				this.state = 101
				errorHandler.sync(this);
				_la = _input!!.LA(1)
				while (_la==T__4) {
					if (true){
					if (true){
					this.state = 97
					match(T__4) as Token
					this.state = 98
					parameter()
					}
					}
					this.state = 103
					errorHandler.sync(this)
					_la = _input!!.LA(1)
				}
				}
			}

			this.state = 106
			match(T__5) as Token
			this.state = 109
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__7) {
				if (true){
				this.state = 107
				match(T__7) as Token
				this.state = 108
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
		enterRule(_localctx, 16, Rules.RULE_functionName.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 111
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
		enterRule(_localctx, 18, Rules.RULE_parameter.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 113
			match(ID) as Token
			this.state = 114
			match(T__7) as Token
			this.state = 115
			type()
			this.state = 118
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__8) {
				if (true){
				this.state = 116
				match(T__8) as Token
				this.state = 117
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
		enterRule(_localctx, 20, Rules.RULE_type.id)
		try {
			this.state = 122
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__9 , T__10 , T__13 , T__14 , T__15 , T__16 , T__17 , T__18 , T__19 , T__20 , T__21 , T__22 , T__23 , T__24 , T__25 , T__26 , T__27 , T__28 , T__29 , T__30  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 120
			primitiveType()
			}}
			ID  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 121
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
		enterRule(_localctx, 22, Rules.RULE_primitiveType.id)
		var _la: Int
		try {
			this.state = 204
			errorHandler.sync(this)
			when (_input!!.LA(1)) {
			T__9 , T__10  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 124
			_la = _input!!.LA(1)
			if ( !(_la==T__9 || _la==T__10) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 129
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 125
				match(T__11) as Token
				this.state = 126
				match(T__12) as Token
				}
				}
				this.state = 131
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__13 , T__14  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 132
			_la = _input!!.LA(1)
			if ( !(_la==T__13 || _la==T__14) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 137
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 133
				match(T__11) as Token
				this.state = 134
				match(T__12) as Token
				}
				}
				this.state = 139
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__15 , T__16  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 140
			_la = _input!!.LA(1)
			if ( !(_la==T__15 || _la==T__16) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 145
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 141
				match(T__11) as Token
				this.state = 142
				match(T__12) as Token
				}
				}
				this.state = 147
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__17 , T__18  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 4)
			if (true){
			this.state = 148
			_la = _input!!.LA(1)
			if ( !(_la==T__17 || _la==T__18) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 153
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 149
				match(T__11) as Token
				this.state = 150
				match(T__12) as Token
				}
				}
				this.state = 155
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__19 , T__20  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 5)
			if (true){
			this.state = 156
			_la = _input!!.LA(1)
			if ( !(_la==T__19 || _la==T__20) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 161
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 157
				match(T__11) as Token
				this.state = 158
				match(T__12) as Token
				}
				}
				this.state = 163
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__21 , T__22  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 6)
			if (true){
			this.state = 164
			_la = _input!!.LA(1)
			if ( !(_la==T__21 || _la==T__22) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 169
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 165
				match(T__11) as Token
				this.state = 166
				match(T__12) as Token
				}
				}
				this.state = 171
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__23 , T__24  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 7)
			if (true){
			this.state = 172
			_la = _input!!.LA(1)
			if ( !(_la==T__23 || _la==T__24) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 177
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 173
				match(T__11) as Token
				this.state = 174
				match(T__12) as Token
				}
				}
				this.state = 179
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__25 , T__26  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 8)
			if (true){
			this.state = 180
			_la = _input!!.LA(1)
			if ( !(_la==T__25 || _la==T__26) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 185
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 181
				match(T__11) as Token
				this.state = 182
				match(T__12) as Token
				}
				}
				this.state = 187
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__27 , T__28  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 9)
			if (true){
			this.state = 188
			_la = _input!!.LA(1)
			if ( !(_la==T__27 || _la==T__28) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 193
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 189
				match(T__11) as Token
				this.state = 190
				match(T__12) as Token
				}
				}
				this.state = 195
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			}}
			T__29 , T__30  ->  /*LL1AltBlock*/{
			enterOuterAlt(_localctx, 10)
			if (true){
			this.state = 196
			_la = _input!!.LA(1)
			if ( !(_la==T__29 || _la==T__30) ) {
				errorHandler.recoverInline(this)
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 201
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 197
				match(T__11) as Token
				this.state = 198
				match(T__12) as Token
				}
				}
				this.state = 203
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
		enterRule(_localctx, 24, Rules.RULE_classType.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 206
			qualifiedName()
			this.state = 211
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__11) {
				if (true){
				if (true){
				this.state = 207
				match(T__11) as Token
				this.state = 208
				match(T__12) as Token
				}
				}
				this.state = 213
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
		enterRule(_localctx, 26, Rules.RULE_qualifiedName.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 214
			match(ID) as Token
			this.state = 217 
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			do {
				if (true){
				if (true){
				this.state = 215
				match(T__31) as Token
				this.state = 216
				match(ID) as Token
				}
				}
				this.state = 219 
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			} while ( _la==T__31 )
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
		enterRule(_localctx, 28, Rules.RULE_block.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 221
			match(T__0) as Token
			this.state = 225
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while ((((_la) and 0x3f.inv()) == 0 && ((1L shl _la) and ((1L shl T__0) or (1L shl T__3) or (1L shl T__33) or (1L shl T__34) or (1L shl T__35) or (1L shl IF) or (1L shl RETURN) or (1L shl FOR) or (1L shl PRINT) or (1L shl PRINTLN) or (1L shl NUMBER) or (1L shl STRING))) != 0L) || _la==BOOL || _la==ID) {
				if (true){
				if (true){
				this.state = 222
				statement()
				}
				}
				this.state = 227
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 228
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
		enterRule(_localctx, 30, Rules.RULE_statement.id)
		try {
			this.state = 238
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,24,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 230
			block()
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 231
			variableDeclaration()
			}}
			3 -> {
			enterOuterAlt(_localctx, 3)
			if (true){
			this.state = 232
			printStatement()
			}}
			4 -> {
			enterOuterAlt(_localctx, 4)
			if (true){
			this.state = 233
			printlnStatement()
			}}
			5 -> {
			enterOuterAlt(_localctx, 5)
			if (true){
			this.state = 234
			forStatement()
			}}
			6 -> {
			enterOuterAlt(_localctx, 6)
			if (true){
			this.state = 235
			returnStatement()
			}}
			7 -> {
			enterOuterAlt(_localctx, 7)
			if (true){
			this.state = 236
			ifStatement()
			}}
			8 -> {
			enterOuterAlt(_localctx, 8)
			if (true){
			this.state = 237
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
		enterRule(_localctx, 32, Rules.RULE_variableDeclaration.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 240
			name()
			this.state = 241
			match(T__32) as Token
			this.state = 242
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
		enterRule(_localctx, 34, Rules.RULE_printStatement.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 244
			match(PRINT) as Token
			this.state = 245
			match(T__3) as Token
			this.state = 246
			expression(0)
			this.state = 247
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
		enterRule(_localctx, 36, Rules.RULE_printlnStatement.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 249
			match(PRINTLN) as Token
			this.state = 250
			match(T__3) as Token
			this.state = 251
			expression(0)
			this.state = 252
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
		enterRule(_localctx, 38, Rules.RULE_returnStatement.id)
		try {
			this.state = 257
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,25,context) ) {
			1 -> {_localctx = ReturnWithValueContext(_localctx)
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 254
			match(RETURN) as Token
			this.state = 255
			expression(0)
			}}
			2 -> {_localctx = ReturnVoidContext(_localctx)
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 256
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
		enterRule(_localctx, 40, Rules.RULE_ifStatement.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 259
			match(IF) as Token
			this.state = 261
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,26,context) ) {
			1   -> if (true){
			this.state = 260
			match(T__3) as Token
			}
			}
			this.state = 263
			(_localctx as IfStatementContext).condition = expression(0)
			this.state = 265
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__5) {
				if (true){
				this.state = 264
				match(T__5) as Token
				}
			}

			this.state = 267
			(_localctx as IfStatementContext).trueStatement = statement()
			this.state = 270
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,28,context) ) {
			1   -> if (true){
			this.state = 268
			match(ELSE) as Token
			this.state = 269
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
		enterRule(_localctx, 42, Rules.RULE_forStatement.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 272
			match(FOR) as Token
			this.state = 274
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__3) {
				if (true){
				this.state = 273
				match(T__3) as Token
				}
			}

			this.state = 276
			forRangedExpression()
			this.state = 278
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==T__5) {
				if (true){
				this.state = 277
				match(T__5) as Token
				}
			}

			this.state = 280
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
		enterRule(_localctx, 44, Rules.RULE_forRangedExpression.id)
		var _la: Int
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 282
			(_localctx as ForRangedExpressionContext).iterator = varReference()
			this.state = 283
			match(T__7) as Token
			this.state = 284
			(_localctx as ForRangedExpressionContext).startExpr = expression(0)
			this.state = 286
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (_la==DOWN) {
				if (true){
				this.state = 285
				(_localctx as ForRangedExpressionContext).down = match(DOWN) as Token
				}
			}

			this.state = 288
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
			this.state = 289
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
		enterRule(_localctx, 46, Rules.RULE_name.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 291
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
		enterRule(_localctx, 48, Rules.RULE_argument.id)
		try {
			this.state = 298
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,32,context) ) {
			1 -> {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 293
			expression(0)
			}}
			2 -> {
			enterOuterAlt(_localctx, 2)
			if (true){
			this.state = 294
			name()
			this.state = 295
			match(T__8) as Token
			this.state = 296
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
		public var condition: ExpressionContext? = null
		public var trueExpression: ExpressionContext? = null
		public var falseExpression: ExpressionContext? = null
		fun findExpression() : List<ExpressionContext> = getRuleContexts(solver.getType("ExpressionContext"))
		fun findExpression(i: Int) : ExpressionContext? = getRuleContext(solver.getType("ExpressionContext"),i)
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
		var _startState : Int = 50
		enterRecursionRule(_localctx, 50, Rules.RULE_expression.id, _p)
		var _la: Int
		try {
			var _alt: Int
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 350
			errorHandler.sync(this)
			when ( interpreter!!.adaptivePredict(_input!!,39,context) ) {
			1 -> {if (true){
			_localctx = NegativeExpressionContext(_localctx)
			context = _localctx
			_prevctx = _localctx

			this.state = 301
			(_localctx as NegativeExpressionContext).NEG = _input!!.LT(1)
			_la = _input!!.LA(1)
			if ( !(_la==T__33 || _la==T__34) ) {
				(_localctx as NegativeExpressionContext).NEG = errorHandler.recoverInline(this) as Token
			}
			else {
				if ( _input!!.LA(1)==Tokens.EOF.id ) isMatchedEOF = true
				errorHandler.reportMatch(this)
				consume()
			}
			this.state = 302
			expression(19)
			}}
			2 -> {if (true){
			_localctx = WrappedExpressionContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 303
			match(T__3) as Token
			this.state = 304
			expression(0)
			this.state = 305
			match(T__5) as Token
			}}
			3 -> {if (true){
			_localctx = VarRefContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 307
			varReference()
			}}
			4 -> {if (true){
			_localctx = SuperCallContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 308
			(_localctx as SuperCallContext).superCall = match(T__35) as Token
			this.state = 309
			match(T__3) as Token
			this.state = 311
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 4)) and 0x3f.inv()) == 0 && ((1L shl (_la - 4)) and ((1L shl (T__3 - 4)) or (1L shl (T__33 - 4)) or (1L shl (T__34 - 4)) or (1L shl (T__35 - 4)) or (1L shl (NUMBER - 4)) or (1L shl (STRING - 4)) or (1L shl (BOOL - 4)) or (1L shl (ID - 4)))) != 0L)) {
				if (true){
				this.state = 310
				argument()
				}
			}

			this.state = 317
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__4) {
				if (true){
				if (true){
				this.state = 313
				match(T__4) as Token
				this.state = 314
				argument()
				}
				}
				this.state = 319
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 320
			match(T__5) as Token
			}}
			5 -> {if (true){
			_localctx = ConstructorCallContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 321
			className()
			this.state = 322
			match(T__3) as Token
			this.state = 324
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 4)) and 0x3f.inv()) == 0 && ((1L shl (_la - 4)) and ((1L shl (T__3 - 4)) or (1L shl (T__33 - 4)) or (1L shl (T__34 - 4)) or (1L shl (T__35 - 4)) or (1L shl (NUMBER - 4)) or (1L shl (STRING - 4)) or (1L shl (BOOL - 4)) or (1L shl (ID - 4)))) != 0L)) {
				if (true){
				this.state = 323
				argument()
				}
			}

			this.state = 330
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__4) {
				if (true){
				if (true){
				this.state = 326
				match(T__4) as Token
				this.state = 327
				argument()
				}
				}
				this.state = 332
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 333
			match(T__5) as Token
			}}
			6 -> {if (true){
			_localctx = FunctionCallContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 335
			functionName()
			this.state = 336
			match(T__3) as Token
			this.state = 338
			errorHandler.sync(this)
			_la = _input!!.LA(1)
			if (((((_la - 4)) and 0x3f.inv()) == 0 && ((1L shl (_la - 4)) and ((1L shl (T__3 - 4)) or (1L shl (T__33 - 4)) or (1L shl (T__34 - 4)) or (1L shl (T__35 - 4)) or (1L shl (NUMBER - 4)) or (1L shl (STRING - 4)) or (1L shl (BOOL - 4)) or (1L shl (ID - 4)))) != 0L)) {
				if (true){
				this.state = 337
				argument()
				}
			}

			this.state = 344
			errorHandler.sync(this);
			_la = _input!!.LA(1)
			while (_la==T__4) {
				if (true){
				if (true){
				this.state = 340
				match(T__4) as Token
				this.state = 341
				argument()
				}
				}
				this.state = 346
				errorHandler.sync(this)
				_la = _input!!.LA(1)
			}
			this.state = 347
			match(T__5) as Token
			}}
			7 -> {if (true){
			_localctx = ValueContext(_localctx)
			context = _localctx
			_prevctx = _localctx
			this.state = 349
			_la = _input!!.LA(1)
			if ( !(((((_la - 62)) and 0x3f.inv()) == 0 && ((1L shl (_la - 62)) and ((1L shl (NUMBER - 62)) or (1L shl (STRING - 62)) or (1L shl (BOOL - 62)))) != 0L)) ) {
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
			this.state = 406
			errorHandler.sync(this)
			_alt = interpreter!!.adaptivePredict(_input!!,43,context)
			while ( _alt!=2 && _alt!=INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent()
					    _prevctx = _localctx
					if (true){
					this.state = 404
					errorHandler.sync(this)
					when ( interpreter!!.adaptivePredict(_input!!,42,context) ) {
					1 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 352
					if (!(precpred(context!!, 12))) throw FailedPredicateException(this, "precpred(context!!, 12)")
					this.state = 353
					(_localctx as ConditionalExpressionContext).cmp = match(GREATER) as Token
					this.state = 354
					expression(13)
					}}
					2 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 355
					if (!(precpred(context!!, 11))) throw FailedPredicateException(this, "precpred(context!!, 11)")
					this.state = 356
					(_localctx as ConditionalExpressionContext).cmp = match(LESS) as Token
					this.state = 357
					expression(12)
					}}
					3 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 358
					if (!(precpred(context!!, 10))) throw FailedPredicateException(this, "precpred(context!!, 10)")
					this.state = 359
					(_localctx as ConditionalExpressionContext).cmp = match(EQ) as Token
					this.state = 360
					expression(11)
					}}
					4 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 361
					if (!(precpred(context!!, 9))) throw FailedPredicateException(this, "precpred(context!!, 9)")
					this.state = 362
					(_localctx as ConditionalExpressionContext).cmp = match(NOT_EQ) as Token
					this.state = 363
					expression(10)
					}}
					5 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 364
					if (!(precpred(context!!, 8))) throw FailedPredicateException(this, "precpred(context!!, 8)")
					this.state = 365
					(_localctx as ConditionalExpressionContext).cmp = match(GREATER_EQ) as Token
					this.state = 366
					expression(9)
					}}
					6 -> {if (true){
					_localctx = ConditionalExpressionContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 367
					if (!(precpred(context!!, 7))) throw FailedPredicateException(this, "precpred(context!!, 7)")
					this.state = 368
					(_localctx as ConditionalExpressionContext).cmp = match(LESS_EQ) as Token
					this.state = 369
					expression(8)
					}}
					7 -> {if (true){
					_localctx = IfExpressionContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as IfExpressionContext).condition = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 370
					if (!(precpred(context!!, 6))) throw FailedPredicateException(this, "precpred(context!!, 6)")
					this.state = 371
					match(T__37) as Token
					this.state = 372
					(_localctx as IfExpressionContext).trueExpression = expression(0)
					this.state = 373
					match(T__7) as Token
					this.state = 374
					(_localctx as IfExpressionContext).falseExpression = expression(7)
					}}
					8 -> {if (true){
					_localctx = MultiplyContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 376
					if (!(precpred(context!!, 5))) throw FailedPredicateException(this, "precpred(context!!, 5)")
					this.state = 377
					match(STAR) as Token
					this.state = 378
					expression(6)
					}}
					9 -> {if (true){
					_localctx = DivideContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 379
					if (!(precpred(context!!, 4))) throw FailedPredicateException(this, "precpred(context!!, 4)")
					this.state = 380
					match(SLASH) as Token
					this.state = 381
					expression(5)
					}}
					10 -> {if (true){
					_localctx = AddContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 382
					if (!(precpred(context!!, 3))) throw FailedPredicateException(this, "precpred(context!!, 3)")
					this.state = 383
					match(PLUS) as Token
					this.state = 384
					expression(4)
					}}
					11 -> {if (true){
					_localctx = SubtractContext(ExpressionContext(_parentctx, _parentState))
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 385
					if (!(precpred(context!!, 2))) throw FailedPredicateException(this, "precpred(context!!, 2)")
					this.state = 386
					match(MINUS) as Token
					this.state = 387
					expression(3)
					}}
					12 -> {if (true){
					_localctx = FunctionCallContext(ExpressionContext(_parentctx, _parentState))
					(_localctx as FunctionCallContext).owner = _prevctx
					pushNewRecursionContext(_localctx, _startState, Rules.RULE_expression.id)
					this.state = 388
					if (!(precpred(context!!, 14))) throw FailedPredicateException(this, "precpred(context!!, 14)")
					this.state = 389
					match(T__36) as Token
					this.state = 390
					functionName()
					this.state = 391
					match(T__3) as Token
					this.state = 393
					errorHandler.sync(this)
					_la = _input!!.LA(1)
					if (((((_la - 4)) and 0x3f.inv()) == 0 && ((1L shl (_la - 4)) and ((1L shl (T__3 - 4)) or (1L shl (T__33 - 4)) or (1L shl (T__34 - 4)) or (1L shl (T__35 - 4)) or (1L shl (NUMBER - 4)) or (1L shl (STRING - 4)) or (1L shl (BOOL - 4)) or (1L shl (ID - 4)))) != 0L)) {
						if (true){
						this.state = 392
						argument()
						}
					}

					this.state = 399
					errorHandler.sync(this);
					_la = _input!!.LA(1)
					while (_la==T__4) {
						if (true){
						if (true){
						this.state = 395
						match(T__4) as Token
						this.state = 396
						argument()
						}
						}
						this.state = 401
						errorHandler.sync(this)
						_la = _input!!.LA(1)
					}
					this.state = 402
					match(T__5) as Token
					}}
					}
					} 
				}
				this.state = 408
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
		enterRule(_localctx, 52, Rules.RULE_varReference.id)
		try {
			enterOuterAlt(_localctx, 1)
			if (true){
			this.state = 409
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
		25 -> return expression_sempred(_localctx as ExpressionContext, predIndex)
		}
		return true
	}
	private fun expression_sempred( _localctx : ExpressionContext, predIndex: Int) : Boolean {
		when (predIndex) {
		    0 -> return precpred(context!!, 12)
		    1 -> return precpred(context!!, 11)
		    2 -> return precpred(context!!, 10)
		    3 -> return precpred(context!!, 9)
		    4 -> return precpred(context!!, 8)
		    5 -> return precpred(context!!, 7)
		    6 -> return precpred(context!!, 6)
		    7 -> return precpred(context!!, 5)
		    8 -> return precpred(context!!, 4)
		    9 -> return precpred(context!!, 3)
		    10 -> return precpred(context!!, 2)
		    11 -> return precpred(context!!, 14)
		}
		return true
	}

}