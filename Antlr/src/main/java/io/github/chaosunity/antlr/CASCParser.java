// Generated from CASC.g4 by ANTLR 4.7.2

package io.github.chaosunity.antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CASCParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, CLASS=31, 
		FUNC=32, VARIABLE=33, PRINT=34, PRINTLN=35, PLUS=36, MINUS=37, STAR=38, 
		SLASH=39, EQUALS=40, NUMBER=41, STRING=42, ID=43, QUALIFIED_NAME=44, WS=45;
	public static final int
		RULE_compilationUnit = 0, RULE_classDeclaration = 1, RULE_className = 2, 
		RULE_classBody = 3, RULE_function = 4, RULE_functionDeclaration = 5, RULE_functionName = 6, 
		RULE_functionArgument = 7, RULE_functionParamdefaultValue = 8, RULE_type = 9, 
		RULE_primitiveType = 10, RULE_classType = 11, RULE_blockStatement = 12, 
		RULE_variableDeclaration = 13, RULE_printStatement = 14, RULE_printlnStatement = 15, 
		RULE_functionCall = 16, RULE_name = 17, RULE_expressionList = 18, RULE_expression = 19, 
		RULE_varReference = 20, RULE_value = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "classDeclaration", "className", "classBody", "function", 
			"functionDeclaration", "functionName", "functionArgument", "functionParamdefaultValue", 
			"type", "primitiveType", "classType", "blockStatement", "variableDeclaration", 
			"printStatement", "printlnStatement", "functionCall", "name", "expressionList", 
			"expression", "varReference", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'('", "')'", "':'", "'='", "'boolean'", "'\u5E03\u6797'", 
			"'['", "']'", "'string'", "'\u5B57\u4E32'", "'char'", "'\u5B57\u5143'", 
			"'byte'", "'\u4F4D\u5143'", "'short'", "'\u77ED\u6574\u6578'", "'int'", 
			"'\u6574\u6578'", "'long'", "'\u9577\u6574\u6578'", "'float'", "'\u6D6E\u9EDE\u6578'", 
			"'double'", "'\u500D\u6D6E\u9EDE\u6578'", "'void'", "'\u7A7A'", "'\u8CE6'", 
			"','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "CLASS", "FUNC", "VARIABLE", 
			"PRINT", "PRINTLN", "PLUS", "MINUS", "STAR", "SLASH", "EQUALS", "NUMBER", 
			"STRING", "ID", "QUALIFIED_NAME", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "CASC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public CASCParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class CompilationUnitContext extends ParserRuleContext {
		public ClassDeclarationContext classDeclaration() {
			return getRuleContext(ClassDeclarationContext.class,0);
		}
		public TerminalNode EOF() { return getToken(CASCParser.EOF, 0); }
		public CompilationUnitContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compilationUnit; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitCompilationUnit(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompilationUnitContext compilationUnit() throws RecognitionException {
		CompilationUnitContext _localctx = new CompilationUnitContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_compilationUnit);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(44);
			classDeclaration();
			setState(45);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassDeclarationContext extends ParserRuleContext {
		public TerminalNode CLASS() { return getToken(CASCParser.CLASS, 0); }
		public ClassNameContext className() {
			return getRuleContext(ClassNameContext.class,0);
		}
		public ClassBodyContext classBody() {
			return getRuleContext(ClassBodyContext.class,0);
		}
		public ClassDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitClassDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassDeclarationContext classDeclaration() throws RecognitionException {
		ClassDeclarationContext _localctx = new ClassDeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_classDeclaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(CLASS);
			setState(48);
			className();
			setState(49);
			match(T__0);
			setState(50);
			classBody();
			setState(51);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public ClassNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_className; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitClassName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassNameContext className() throws RecognitionException {
		ClassNameContext _localctx = new ClassNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_className);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassBodyContext extends ParserRuleContext {
		public List<FunctionContext> function() {
			return getRuleContexts(FunctionContext.class);
		}
		public FunctionContext function(int i) {
			return getRuleContext(FunctionContext.class,i);
		}
		public ClassBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBody; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitClassBody(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassBodyContext classBody() throws RecognitionException {
		ClassBodyContext _localctx = new ClassBodyContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_classBody);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FUNC) {
				{
				{
				setState(55);
				function();
				}
				}
				setState(60);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionContext extends ParserRuleContext {
		public FunctionDeclarationContext functionDeclaration() {
			return getRuleContext(FunctionDeclarationContext.class,0);
		}
		public List<BlockStatementContext> blockStatement() {
			return getRuleContexts(BlockStatementContext.class);
		}
		public BlockStatementContext blockStatement(int i) {
			return getRuleContext(BlockStatementContext.class,i);
		}
		public FunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_function; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionContext function() throws RecognitionException {
		FunctionContext _localctx = new FunctionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_function);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			functionDeclaration();
			setState(62);
			match(T__0);
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << VARIABLE) | (1L << PRINT) | (1L << PRINTLN) | (1L << ID))) != 0)) {
				{
				{
				setState(63);
				blockStatement();
				}
				}
				setState(68);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(69);
			match(T__1);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionDeclarationContext extends ParserRuleContext {
		public TerminalNode FUNC() { return getToken(CASCParser.FUNC, 0); }
		public FunctionNameContext functionName() {
			return getRuleContext(FunctionNameContext.class,0);
		}
		public List<FunctionArgumentContext> functionArgument() {
			return getRuleContexts(FunctionArgumentContext.class);
		}
		public FunctionArgumentContext functionArgument(int i) {
			return getRuleContext(FunctionArgumentContext.class,i);
		}
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FunctionDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionDeclarationContext functionDeclaration() throws RecognitionException {
		FunctionDeclarationContext _localctx = new FunctionDeclarationContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_functionDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(71);
			match(FUNC);
			setState(72);
			functionName();
			setState(73);
			match(T__2);
			setState(77);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID) {
				{
				{
				setState(74);
				functionArgument();
				}
				}
				setState(79);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(80);
			match(T__3);
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(81);
				match(T__4);
				setState(82);
				type();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionNameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public FunctionNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionNameContext functionName() throws RecognitionException {
		FunctionNameContext _localctx = new FunctionNameContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_functionName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionArgumentContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FunctionParamdefaultValueContext functionParamdefaultValue() {
			return getRuleContext(FunctionParamdefaultValueContext.class,0);
		}
		public FunctionArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionArgument; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionArgumentContext functionArgument() throws RecognitionException {
		FunctionArgumentContext _localctx = new FunctionArgumentContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_functionArgument);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(ID);
			setState(88);
			match(T__4);
			setState(89);
			type();
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(90);
				functionParamdefaultValue();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionParamdefaultValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunctionParamdefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParamdefaultValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionParamdefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParamdefaultValueContext functionParamdefaultValue() throws RecognitionException {
		FunctionParamdefaultValueContext _localctx = new FunctionParamdefaultValueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_functionParamdefaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(T__5);
			setState(94);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TypeContext extends ParserRuleContext {
		public PrimitiveTypeContext primitiveType() {
			return getRuleContext(PrimitiveTypeContext.class,0);
		}
		public ClassTypeContext classType() {
			return getRuleContext(ClassTypeContext.class,0);
		}
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_type);
		try {
			setState(98);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__7:
			case T__10:
			case T__11:
			case T__12:
			case T__13:
			case T__14:
			case T__15:
			case T__16:
			case T__17:
			case T__18:
			case T__19:
			case T__20:
			case T__21:
			case T__22:
			case T__23:
			case T__24:
			case T__25:
			case T__26:
			case T__27:
				enterOuterAlt(_localctx, 1);
				{
				setState(96);
				primitiveType();
				}
				break;
			case QUALIFIED_NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(97);
				classType();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrimitiveTypeContext extends ParserRuleContext {
		public PrimitiveTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primitiveType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitPrimitiveType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimitiveTypeContext primitiveType() throws RecognitionException {
		PrimitiveTypeContext _localctx = new PrimitiveTypeContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_primitiveType);
		int _la;
		try {
			setState(180);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
			case T__7:
				enterOuterAlt(_localctx, 1);
				{
				setState(100);
				_la = _input.LA(1);
				if ( !(_la==T__6 || _la==T__7) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(105);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(101);
					match(T__8);
					setState(102);
					match(T__9);
					}
					}
					setState(107);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__10:
			case T__11:
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				_la = _input.LA(1);
				if ( !(_la==T__10 || _la==T__11) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(113);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(109);
					match(T__8);
					setState(110);
					match(T__9);
					}
					}
					setState(115);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__12:
			case T__13:
				enterOuterAlt(_localctx, 3);
				{
				setState(116);
				_la = _input.LA(1);
				if ( !(_la==T__12 || _la==T__13) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(121);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(117);
					match(T__8);
					setState(118);
					match(T__9);
					}
					}
					setState(123);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__14:
			case T__15:
				enterOuterAlt(_localctx, 4);
				{
				setState(124);
				_la = _input.LA(1);
				if ( !(_la==T__14 || _la==T__15) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(129);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(125);
					match(T__8);
					setState(126);
					match(T__9);
					}
					}
					setState(131);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__16:
			case T__17:
				enterOuterAlt(_localctx, 5);
				{
				setState(132);
				_la = _input.LA(1);
				if ( !(_la==T__16 || _la==T__17) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(133);
					match(T__8);
					setState(134);
					match(T__9);
					}
					}
					setState(139);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__18:
			case T__19:
				enterOuterAlt(_localctx, 6);
				{
				setState(140);
				_la = _input.LA(1);
				if ( !(_la==T__18 || _la==T__19) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(145);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(141);
					match(T__8);
					setState(142);
					match(T__9);
					}
					}
					setState(147);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__20:
			case T__21:
				enterOuterAlt(_localctx, 7);
				{
				setState(148);
				_la = _input.LA(1);
				if ( !(_la==T__20 || _la==T__21) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(149);
					match(T__8);
					setState(150);
					match(T__9);
					}
					}
					setState(155);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__22:
			case T__23:
				enterOuterAlt(_localctx, 8);
				{
				setState(156);
				_la = _input.LA(1);
				if ( !(_la==T__22 || _la==T__23) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(161);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(157);
					match(T__8);
					setState(158);
					match(T__9);
					}
					}
					setState(163);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__24:
			case T__25:
				enterOuterAlt(_localctx, 9);
				{
				setState(164);
				_la = _input.LA(1);
				if ( !(_la==T__24 || _la==T__25) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(169);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(165);
					match(T__8);
					setState(166);
					match(T__9);
					}
					}
					setState(171);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__26:
			case T__27:
				enterOuterAlt(_localctx, 10);
				{
				setState(172);
				_la = _input.LA(1);
				if ( !(_la==T__26 || _la==T__27) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__8) {
					{
					{
					setState(173);
					match(T__8);
					setState(174);
					match(T__9);
					}
					}
					setState(179);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClassTypeContext extends ParserRuleContext {
		public TerminalNode QUALIFIED_NAME() { return getToken(CASCParser.QUALIFIED_NAME, 0); }
		public ClassTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classType; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitClassType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClassTypeContext classType() throws RecognitionException {
		ClassTypeContext _localctx = new ClassTypeContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_classType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			match(QUALIFIED_NAME);
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8) {
				{
				{
				setState(183);
				match(T__8);
				setState(184);
				match(T__9);
				}
				}
				setState(189);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockStatementContext extends ParserRuleContext {
		public VariableDeclarationContext variableDeclaration() {
			return getRuleContext(VariableDeclarationContext.class,0);
		}
		public PrintStatementContext printStatement() {
			return getRuleContext(PrintStatementContext.class,0);
		}
		public PrintlnStatementContext printlnStatement() {
			return getRuleContext(PrintlnStatementContext.class,0);
		}
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public BlockStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitBlockStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockStatementContext blockStatement() throws RecognitionException {
		BlockStatementContext _localctx = new BlockStatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_blockStatement);
		try {
			setState(194);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case VARIABLE:
				enterOuterAlt(_localctx, 1);
				{
				setState(190);
				variableDeclaration();
				}
				break;
			case PRINT:
				enterOuterAlt(_localctx, 2);
				{
				setState(191);
				printStatement();
				}
				break;
			case PRINTLN:
				enterOuterAlt(_localctx, 3);
				{
				setState(192);
				printlnStatement();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 4);
				{
				setState(193);
				functionCall();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VariableDeclarationContext extends ParserRuleContext {
		public TerminalNode VARIABLE() { return getToken(CASCParser.VARIABLE, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public VariableDeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variableDeclaration; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitVariableDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableDeclarationContext variableDeclaration() throws RecognitionException {
		VariableDeclarationContext _localctx = new VariableDeclarationContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(196);
			match(VARIABLE);
			setState(197);
			name();
			setState(198);
			_la = _input.LA(1);
			if ( !(_la==T__5 || _la==T__28) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(199);
			expression(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintStatementContext extends ParserRuleContext {
		public TerminalNode PRINT() { return getToken(CASCParser.PRINT, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrintStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitPrintStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintStatementContext printStatement() throws RecognitionException {
		PrintStatementContext _localctx = new PrintStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_printStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(PRINT);
			setState(202);
			match(T__2);
			setState(203);
			expression(0);
			setState(204);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrintlnStatementContext extends ParserRuleContext {
		public TerminalNode PRINTLN() { return getToken(CASCParser.PRINTLN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public PrintlnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_printlnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitPrintlnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrintlnStatementContext printlnStatement() throws RecognitionException {
		PrintlnStatementContext _localctx = new PrintlnStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_printlnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(PRINTLN);
			setState(207);
			match(T__2);
			setState(208);
			expression(0);
			setState(209);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctionCallContext extends ParserRuleContext {
		public FunctionNameContext functionName() {
			return getRuleContext(FunctionNameContext.class,0);
		}
		public ExpressionListContext expressionList() {
			return getRuleContext(ExpressionListContext.class,0);
		}
		public FunctionCallContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionCall; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionCall(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionCallContext functionCall() throws RecognitionException {
		FunctionCallContext _localctx = new FunctionCallContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_functionCall);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(211);
			functionName();
			setState(212);
			match(T__2);
			setState(213);
			expressionList();
			setState(214);
			match(T__3);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(216);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionListContext extends ParserRuleContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public ExpressionListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitExpressionList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionListContext expressionList() throws RecognitionException {
		ExpressionListContext _localctx = new ExpressionListContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			expression(0);
			setState(223);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__29) {
				{
				{
				setState(219);
				match(T__29);
				setState(220);
				expression(0);
				}
				}
				setState(225);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class VarRefContext extends ExpressionContext {
		public VarReferenceContext varReference() {
			return getRuleContext(VarReferenceContext.class,0);
		}
		public VarRefContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitVarRef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValContext extends ExpressionContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ValContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitVal(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AddContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(CASCParser.PLUS, 0); }
		public AddContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncCallContext extends ExpressionContext {
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public FuncCallContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFuncCall(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class DivideContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode SLASH() { return getToken(CASCParser.SLASH, 0); }
		public DivideContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitDivide(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiplyContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode STAR() { return getToken(CASCParser.STAR, 0); }
		public MultiplyContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitMultiply(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class SubtractContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public SubtractContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitSubtract(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(254);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				{
				_localctx = new VarRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(227);
				varReference();
				}
				break;
			case 2:
				{
				_localctx = new ValContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(228);
				value();
				}
				break;
			case 3:
				{
				_localctx = new FuncCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(229);
				functionCall();
				}
				break;
			case 4:
				{
				_localctx = new MultiplyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(230);
				match(T__2);
				setState(231);
				expression(0);
				setState(232);
				match(STAR);
				setState(233);
				expression(0);
				setState(234);
				match(T__3);
				}
				break;
			case 5:
				{
				_localctx = new DivideContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236);
				match(T__2);
				setState(237);
				expression(0);
				setState(238);
				match(SLASH);
				setState(239);
				expression(0);
				setState(240);
				match(T__3);
				}
				break;
			case 6:
				{
				_localctx = new AddContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(242);
				match(T__2);
				setState(243);
				expression(0);
				setState(244);
				match(PLUS);
				setState(245);
				expression(0);
				setState(246);
				match(T__3);
				}
				break;
			case 7:
				{
				_localctx = new SubtractContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(248);
				match(T__2);
				setState(249);
				expression(0);
				setState(250);
				match(MINUS);
				setState(251);
				expression(0);
				setState(252);
				match(T__3);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(270);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(268);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
					case 1:
						{
						_localctx = new MultiplyContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(256);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(257);
						match(STAR);
						setState(258);
						expression(8);
						}
						break;
					case 2:
						{
						_localctx = new DivideContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(259);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(260);
						match(SLASH);
						setState(261);
						expression(6);
						}
						break;
					case 3:
						{
						_localctx = new AddContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(262);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(263);
						match(PLUS);
						setState(264);
						expression(4);
						}
						break;
					case 4:
						{
						_localctx = new SubtractContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(265);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(266);
						match(MINUS);
						setState(267);
						expression(2);
						}
						break;
					}
					} 
				}
				setState(272);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,22,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class VarReferenceContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public VarReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varReference; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitVarReference(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VarReferenceContext varReference() throws RecognitionException {
		VarReferenceContext _localctx = new VarReferenceContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_varReference);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(273);
			match(ID);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(CASCParser.NUMBER, 0); }
		public TerminalNode STRING() { return getToken(CASCParser.STRING, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(275);
			_la = _input.LA(1);
			if ( !(_la==NUMBER || _la==STRING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 19:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 7);
		case 1:
			return precpred(_ctx, 5);
		case 2:
			return precpred(_ctx, 3);
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3/\u0118\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\3\2\3\2\3\3\3\3"+
		"\3\3\3\3\3\3\3\3\3\4\3\4\3\5\7\5;\n\5\f\5\16\5>\13\5\3\6\3\6\3\6\7\6C"+
		"\n\6\f\6\16\6F\13\6\3\6\3\6\3\7\3\7\3\7\3\7\7\7N\n\7\f\7\16\7Q\13\7\3"+
		"\7\3\7\3\7\5\7V\n\7\3\b\3\b\3\t\3\t\3\t\3\t\5\t^\n\t\3\n\3\n\3\n\3\13"+
		"\3\13\5\13e\n\13\3\f\3\f\3\f\7\fj\n\f\f\f\16\fm\13\f\3\f\3\f\3\f\7\fr"+
		"\n\f\f\f\16\fu\13\f\3\f\3\f\3\f\7\fz\n\f\f\f\16\f}\13\f\3\f\3\f\3\f\7"+
		"\f\u0082\n\f\f\f\16\f\u0085\13\f\3\f\3\f\3\f\7\f\u008a\n\f\f\f\16\f\u008d"+
		"\13\f\3\f\3\f\3\f\7\f\u0092\n\f\f\f\16\f\u0095\13\f\3\f\3\f\3\f\7\f\u009a"+
		"\n\f\f\f\16\f\u009d\13\f\3\f\3\f\3\f\7\f\u00a2\n\f\f\f\16\f\u00a5\13\f"+
		"\3\f\3\f\3\f\7\f\u00aa\n\f\f\f\16\f\u00ad\13\f\3\f\3\f\3\f\7\f\u00b2\n"+
		"\f\f\f\16\f\u00b5\13\f\5\f\u00b7\n\f\3\r\3\r\3\r\7\r\u00bc\n\r\f\r\16"+
		"\r\u00bf\13\r\3\16\3\16\3\16\3\16\5\16\u00c5\n\16\3\17\3\17\3\17\3\17"+
		"\3\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\3\24\7\24\u00e0\n\24\f\24\16\24\u00e3\13"+
		"\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3"+
		"\25\5\25\u0101\n\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\25"+
		"\3\25\3\25\7\25\u010f\n\25\f\25\16\25\u0112\13\25\3\26\3\26\3\27\3\27"+
		"\3\27\2\3(\30\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&(*,\2\16\3\2"+
		"\t\n\3\2\r\16\3\2\17\20\3\2\21\22\3\2\23\24\3\2\25\26\3\2\27\30\3\2\31"+
		"\32\3\2\33\34\3\2\35\36\4\2\b\b\37\37\3\2+,\2\u0129\2.\3\2\2\2\4\61\3"+
		"\2\2\2\6\67\3\2\2\2\b<\3\2\2\2\n?\3\2\2\2\fI\3\2\2\2\16W\3\2\2\2\20Y\3"+
		"\2\2\2\22_\3\2\2\2\24d\3\2\2\2\26\u00b6\3\2\2\2\30\u00b8\3\2\2\2\32\u00c4"+
		"\3\2\2\2\34\u00c6\3\2\2\2\36\u00cb\3\2\2\2 \u00d0\3\2\2\2\"\u00d5\3\2"+
		"\2\2$\u00da\3\2\2\2&\u00dc\3\2\2\2(\u0100\3\2\2\2*\u0113\3\2\2\2,\u0115"+
		"\3\2\2\2./\5\4\3\2/\60\7\2\2\3\60\3\3\2\2\2\61\62\7!\2\2\62\63\5\6\4\2"+
		"\63\64\7\3\2\2\64\65\5\b\5\2\65\66\7\4\2\2\66\5\3\2\2\2\678\7-\2\28\7"+
		"\3\2\2\29;\5\n\6\2:9\3\2\2\2;>\3\2\2\2<:\3\2\2\2<=\3\2\2\2=\t\3\2\2\2"+
		"><\3\2\2\2?@\5\f\7\2@D\7\3\2\2AC\5\32\16\2BA\3\2\2\2CF\3\2\2\2DB\3\2\2"+
		"\2DE\3\2\2\2EG\3\2\2\2FD\3\2\2\2GH\7\4\2\2H\13\3\2\2\2IJ\7\"\2\2JK\5\16"+
		"\b\2KO\7\5\2\2LN\5\20\t\2ML\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2PR\3"+
		"\2\2\2QO\3\2\2\2RU\7\6\2\2ST\7\7\2\2TV\5\24\13\2US\3\2\2\2UV\3\2\2\2V"+
		"\r\3\2\2\2WX\7-\2\2X\17\3\2\2\2YZ\7-\2\2Z[\7\7\2\2[]\5\24\13\2\\^\5\22"+
		"\n\2]\\\3\2\2\2]^\3\2\2\2^\21\3\2\2\2_`\7\b\2\2`a\5(\25\2a\23\3\2\2\2"+
		"be\5\26\f\2ce\5\30\r\2db\3\2\2\2dc\3\2\2\2e\25\3\2\2\2fk\t\2\2\2gh\7\13"+
		"\2\2hj\7\f\2\2ig\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2l\u00b7\3\2\2\2"+
		"mk\3\2\2\2ns\t\3\2\2op\7\13\2\2pr\7\f\2\2qo\3\2\2\2ru\3\2\2\2sq\3\2\2"+
		"\2st\3\2\2\2t\u00b7\3\2\2\2us\3\2\2\2v{\t\4\2\2wx\7\13\2\2xz\7\f\2\2y"+
		"w\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2|\u00b7\3\2\2\2}{\3\2\2\2~\u0083"+
		"\t\5\2\2\177\u0080\7\13\2\2\u0080\u0082\7\f\2\2\u0081\177\3\2\2\2\u0082"+
		"\u0085\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u00b7\3\2"+
		"\2\2\u0085\u0083\3\2\2\2\u0086\u008b\t\6\2\2\u0087\u0088\7\13\2\2\u0088"+
		"\u008a\7\f\2\2\u0089\u0087\3\2\2\2\u008a\u008d\3\2\2\2\u008b\u0089\3\2"+
		"\2\2\u008b\u008c\3\2\2\2\u008c\u00b7\3\2\2\2\u008d\u008b\3\2\2\2\u008e"+
		"\u0093\t\7\2\2\u008f\u0090\7\13\2\2\u0090\u0092\7\f\2\2\u0091\u008f\3"+
		"\2\2\2\u0092\u0095\3\2\2\2\u0093\u0091\3\2\2\2\u0093\u0094\3\2\2\2\u0094"+
		"\u00b7\3\2\2\2\u0095\u0093\3\2\2\2\u0096\u009b\t\b\2\2\u0097\u0098\7\13"+
		"\2\2\u0098\u009a\7\f\2\2\u0099\u0097\3\2\2\2\u009a\u009d\3\2\2\2\u009b"+
		"\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c\u00b7\3\2\2\2\u009d\u009b\3\2"+
		"\2\2\u009e\u00a3\t\t\2\2\u009f\u00a0\7\13\2\2\u00a0\u00a2\7\f\2\2\u00a1"+
		"\u009f\3\2\2\2\u00a2\u00a5\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a3\u00a4\3\2"+
		"\2\2\u00a4\u00b7\3\2\2\2\u00a5\u00a3\3\2\2\2\u00a6\u00ab\t\n\2\2\u00a7"+
		"\u00a8\7\13\2\2\u00a8\u00aa\7\f\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ad\3"+
		"\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00b7\3\2\2\2\u00ad"+
		"\u00ab\3\2\2\2\u00ae\u00b3\t\13\2\2\u00af\u00b0\7\13\2\2\u00b0\u00b2\7"+
		"\f\2\2\u00b1\u00af\3\2\2\2\u00b2\u00b5\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3"+
		"\u00b4\3\2\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6f\3\2\2\2"+
		"\u00b6n\3\2\2\2\u00b6v\3\2\2\2\u00b6~\3\2\2\2\u00b6\u0086\3\2\2\2\u00b6"+
		"\u008e\3\2\2\2\u00b6\u0096\3\2\2\2\u00b6\u009e\3\2\2\2\u00b6\u00a6\3\2"+
		"\2\2\u00b6\u00ae\3\2\2\2\u00b7\27\3\2\2\2\u00b8\u00bd\7.\2\2\u00b9\u00ba"+
		"\7\13\2\2\u00ba\u00bc\7\f\2\2\u00bb\u00b9\3\2\2\2\u00bc\u00bf\3\2\2\2"+
		"\u00bd\u00bb\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\31\3\2\2\2\u00bf\u00bd"+
		"\3\2\2\2\u00c0\u00c5\5\34\17\2\u00c1\u00c5\5\36\20\2\u00c2\u00c5\5 \21"+
		"\2\u00c3\u00c5\5\"\22\2\u00c4\u00c0\3\2\2\2\u00c4\u00c1\3\2\2\2\u00c4"+
		"\u00c2\3\2\2\2\u00c4\u00c3\3\2\2\2\u00c5\33\3\2\2\2\u00c6\u00c7\7#\2\2"+
		"\u00c7\u00c8\5$\23\2\u00c8\u00c9\t\f\2\2\u00c9\u00ca\5(\25\2\u00ca\35"+
		"\3\2\2\2\u00cb\u00cc\7$\2\2\u00cc\u00cd\7\5\2\2\u00cd\u00ce\5(\25\2\u00ce"+
		"\u00cf\7\6\2\2\u00cf\37\3\2\2\2\u00d0\u00d1\7%\2\2\u00d1\u00d2\7\5\2\2"+
		"\u00d2\u00d3\5(\25\2\u00d3\u00d4\7\6\2\2\u00d4!\3\2\2\2\u00d5\u00d6\5"+
		"\16\b\2\u00d6\u00d7\7\5\2\2\u00d7\u00d8\5&\24\2\u00d8\u00d9\7\6\2\2\u00d9"+
		"#\3\2\2\2\u00da\u00db\7-\2\2\u00db%\3\2\2\2\u00dc\u00e1\5(\25\2\u00dd"+
		"\u00de\7 \2\2\u00de\u00e0\5(\25\2\u00df\u00dd\3\2\2\2\u00e0\u00e3\3\2"+
		"\2\2\u00e1\u00df\3\2\2\2\u00e1\u00e2\3\2\2\2\u00e2\'\3\2\2\2\u00e3\u00e1"+
		"\3\2\2\2\u00e4\u00e5\b\25\1\2\u00e5\u0101\5*\26\2\u00e6\u0101\5,\27\2"+
		"\u00e7\u0101\5\"\22\2\u00e8\u00e9\7\5\2\2\u00e9\u00ea\5(\25\2\u00ea\u00eb"+
		"\7(\2\2\u00eb\u00ec\5(\25\2\u00ec\u00ed\7\6\2\2\u00ed\u0101\3\2\2\2\u00ee"+
		"\u00ef\7\5\2\2\u00ef\u00f0\5(\25\2\u00f0\u00f1\7)\2\2\u00f1\u00f2\5(\25"+
		"\2\u00f2\u00f3\7\6\2\2\u00f3\u0101\3\2\2\2\u00f4\u00f5\7\5\2\2\u00f5\u00f6"+
		"\5(\25\2\u00f6\u00f7\7&\2\2\u00f7\u00f8\5(\25\2\u00f8\u00f9\7\6\2\2\u00f9"+
		"\u0101\3\2\2\2\u00fa\u00fb\7\5\2\2\u00fb\u00fc\5(\25\2\u00fc\u00fd\7\'"+
		"\2\2\u00fd\u00fe\5(\25\2\u00fe\u00ff\7\6\2\2\u00ff\u0101\3\2\2\2\u0100"+
		"\u00e4\3\2\2\2\u0100\u00e6\3\2\2\2\u0100\u00e7\3\2\2\2\u0100\u00e8\3\2"+
		"\2\2\u0100\u00ee\3\2\2\2\u0100\u00f4\3\2\2\2\u0100\u00fa\3\2\2\2\u0101"+
		"\u0110\3\2\2\2\u0102\u0103\f\t\2\2\u0103\u0104\7(\2\2\u0104\u010f\5(\25"+
		"\n\u0105\u0106\f\7\2\2\u0106\u0107\7)\2\2\u0107\u010f\5(\25\b\u0108\u0109"+
		"\f\5\2\2\u0109\u010a\7&\2\2\u010a\u010f\5(\25\6\u010b\u010c\f\3\2\2\u010c"+
		"\u010d\7\'\2\2\u010d\u010f\5(\25\4\u010e\u0102\3\2\2\2\u010e\u0105\3\2"+
		"\2\2\u010e\u0108\3\2\2\2\u010e\u010b\3\2\2\2\u010f\u0112\3\2\2\2\u0110"+
		"\u010e\3\2\2\2\u0110\u0111\3\2\2\2\u0111)\3\2\2\2\u0112\u0110\3\2\2\2"+
		"\u0113\u0114\7-\2\2\u0114+\3\2\2\2\u0115\u0116\t\r\2\2\u0116-\3\2\2\2"+
		"\31<DOU]dks{\u0083\u008b\u0093\u009b\u00a3\u00ab\u00b3\u00b6\u00bd\u00c4"+
		"\u00e1\u0100\u010e\u0110";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}