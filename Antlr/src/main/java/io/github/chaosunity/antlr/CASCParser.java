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
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		CLASS=32, FUNC=33, VARIABLE=34, IF=35, ELSE=36, RETURN=37, PRINT=38, PRINTLN=39, 
		NEG=40, POS=41, PLUS=42, MINUS=43, STAR=44, SLASH=45, EQUALS=46, GREATER=47, 
		LESS=48, GREATER_EQ=49, LESS_EQ=50, EQ=51, NOT_EQ=52, NUMBER=53, STRING=54, 
		ID=55, TRUE=56, FALSE=57, QUALIFIED_NAME=58, WS=59;
	public static final int
		RULE_compilationUnit = 0, RULE_classDeclaration = 1, RULE_className = 2, 
		RULE_classBody = 3, RULE_function = 4, RULE_functionDeclaration = 5, RULE_functionName = 6, 
		RULE_functionParameter = 7, RULE_functionParameterDefaultValue = 8, RULE_type = 9, 
		RULE_primitiveType = 10, RULE_classType = 11, RULE_block = 12, RULE_statement = 13, 
		RULE_variableDeclaration = 14, RULE_printStatement = 15, RULE_printlnStatement = 16, 
		RULE_returnStatement = 17, RULE_functionCall = 18, RULE_ifStatement = 19, 
		RULE_name = 20, RULE_argument = 21, RULE_expressionList = 22, RULE_expression = 23, 
		RULE_varReference = 24, RULE_value = 25;
	private static String[] makeRuleNames() {
		return new String[] {
			"compilationUnit", "classDeclaration", "className", "classBody", "function", 
			"functionDeclaration", "functionName", "functionParameter", "functionParameterDefaultValue", 
			"type", "primitiveType", "classType", "block", "statement", "variableDeclaration", 
			"printStatement", "printlnStatement", "returnStatement", "functionCall", 
			"ifStatement", "name", "argument", "expressionList", "expression", "varReference", 
			"value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'('", "','", "')'", "':'", "'='", "'boolean'", "'\u5E03\u6797'", 
			"'['", "']'", "'string'", "'\u5B57\u4E32'", "'char'", "'\u5B57\u5143'", 
			"'byte'", "'\u4F4D\u5143'", "'short'", "'\u77ED\u6574\u6578'", "'int'", 
			"'\u6574\u6578'", "'long'", "'\u9577\u6574\u6578'", "'float'", "'\u6D6E\u9EDE\u6578'", 
			"'double'", "'\u500D\u6D6E\u9EDE\u6578'", "'void'", "'\u7A7A'", "'\u8CE6'", 
			"'?'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, "CLASS", "FUNC", "VARIABLE", 
			"IF", "ELSE", "RETURN", "PRINT", "PRINTLN", "NEG", "POS", "PLUS", "MINUS", 
			"STAR", "SLASH", "EQUALS", "GREATER", "LESS", "GREATER_EQ", "LESS_EQ", 
			"EQ", "NOT_EQ", "NUMBER", "STRING", "ID", "TRUE", "FALSE", "QUALIFIED_NAME", 
			"WS"
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
			setState(52);
			classDeclaration();
			setState(53);
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
			setState(55);
			match(CLASS);
			setState(56);
			className();
			setState(57);
			match(T__0);
			setState(58);
			classBody();
			setState(59);
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
			setState(61);
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
			setState(66);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==FUNC) {
				{
				{
				setState(63);
				function();
				}
				}
				setState(68);
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
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
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
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			functionDeclaration();
			setState(70);
			block();
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
		public List<FunctionParameterContext> functionParameter() {
			return getRuleContexts(FunctionParameterContext.class);
		}
		public FunctionParameterContext functionParameter(int i) {
			return getRuleContext(FunctionParameterContext.class,i);
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
			setState(72);
			match(FUNC);
			setState(73);
			functionName();
			setState(74);
			match(T__2);
			setState(83);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(75);
				functionParameter();
				setState(80);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__3) {
					{
					{
					setState(76);
					match(T__3);
					setState(77);
					functionParameter();
					}
					}
					setState(82);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(85);
			match(T__4);
			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(86);
				match(T__5);
				setState(87);
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
			setState(90);
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

	public static class FunctionParameterContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public FunctionParameterDefaultValueContext functionParameterDefaultValue() {
			return getRuleContext(FunctionParameterDefaultValueContext.class,0);
		}
		public FunctionParameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameter; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionParameter(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParameterContext functionParameter() throws RecognitionException {
		FunctionParameterContext _localctx = new FunctionParameterContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_functionParameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(92);
			match(ID);
			setState(93);
			match(T__5);
			setState(94);
			type();
			setState(96);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(95);
				functionParameterDefaultValue();
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

	public static class FunctionParameterDefaultValueContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public FunctionParameterDefaultValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functionParameterDefaultValue; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitFunctionParameterDefaultValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctionParameterDefaultValueContext functionParameterDefaultValue() throws RecognitionException {
		FunctionParameterDefaultValueContext _localctx = new FunctionParameterDefaultValueContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_functionParameterDefaultValue);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(T__6);
			setState(99);
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
			setState(103);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
			case T__8:
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
			case T__28:
				enterOuterAlt(_localctx, 1);
				{
				setState(101);
				primitiveType();
				}
				break;
			case QUALIFIED_NAME:
				enterOuterAlt(_localctx, 2);
				{
				setState(102);
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
			setState(185);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
			case T__8:
				enterOuterAlt(_localctx, 1);
				{
				setState(105);
				_la = _input.LA(1);
				if ( !(_la==T__7 || _la==T__8) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(110);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(106);
					match(T__9);
					setState(107);
					match(T__10);
					}
					}
					setState(112);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__11:
			case T__12:
				enterOuterAlt(_localctx, 2);
				{
				setState(113);
				_la = _input.LA(1);
				if ( !(_la==T__11 || _la==T__12) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(118);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(114);
					match(T__9);
					setState(115);
					match(T__10);
					}
					}
					setState(120);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__13:
			case T__14:
				enterOuterAlt(_localctx, 3);
				{
				setState(121);
				_la = _input.LA(1);
				if ( !(_la==T__13 || _la==T__14) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(126);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(122);
					match(T__9);
					setState(123);
					match(T__10);
					}
					}
					setState(128);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__15:
			case T__16:
				enterOuterAlt(_localctx, 4);
				{
				setState(129);
				_la = _input.LA(1);
				if ( !(_la==T__15 || _la==T__16) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(134);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(130);
					match(T__9);
					setState(131);
					match(T__10);
					}
					}
					setState(136);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__17:
			case T__18:
				enterOuterAlt(_localctx, 5);
				{
				setState(137);
				_la = _input.LA(1);
				if ( !(_la==T__17 || _la==T__18) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(142);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(138);
					match(T__9);
					setState(139);
					match(T__10);
					}
					}
					setState(144);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__19:
			case T__20:
				enterOuterAlt(_localctx, 6);
				{
				setState(145);
				_la = _input.LA(1);
				if ( !(_la==T__19 || _la==T__20) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(150);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(146);
					match(T__9);
					setState(147);
					match(T__10);
					}
					}
					setState(152);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__21:
			case T__22:
				enterOuterAlt(_localctx, 7);
				{
				setState(153);
				_la = _input.LA(1);
				if ( !(_la==T__21 || _la==T__22) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(158);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(154);
					match(T__9);
					setState(155);
					match(T__10);
					}
					}
					setState(160);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__23:
			case T__24:
				enterOuterAlt(_localctx, 8);
				{
				setState(161);
				_la = _input.LA(1);
				if ( !(_la==T__23 || _la==T__24) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(166);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(162);
					match(T__9);
					setState(163);
					match(T__10);
					}
					}
					setState(168);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__25:
			case T__26:
				enterOuterAlt(_localctx, 9);
				{
				setState(169);
				_la = _input.LA(1);
				if ( !(_la==T__25 || _la==T__26) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(174);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(170);
					match(T__9);
					setState(171);
					match(T__10);
					}
					}
					setState(176);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__27:
			case T__28:
				enterOuterAlt(_localctx, 10);
				{
				setState(177);
				_la = _input.LA(1);
				if ( !(_la==T__27 || _la==T__28) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__9) {
					{
					{
					setState(178);
					match(T__9);
					setState(179);
					match(T__10);
					}
					}
					setState(184);
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
			setState(187);
			match(QUALIFIED_NAME);
			setState(192);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__9) {
				{
				{
				setState(188);
				match(T__9);
				setState(189);
				match(T__10);
				}
				}
				setState(194);
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

	public static class BlockContext extends ParserRuleContext {
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			match(T__0);
			setState(199);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__0) | (1L << T__2) | (1L << VARIABLE) | (1L << IF) | (1L << RETURN) | (1L << PRINT) | (1L << PRINTLN) | (1L << NEG) | (1L << POS) | (1L << NUMBER) | (1L << STRING) | (1L << ID) | (1L << TRUE) | (1L << FALSE))) != 0)) {
				{
				{
				setState(196);
				statement();
				}
				}
				setState(201);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(202);
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

	public static class StatementContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
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
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public IfStatementContext ifStatement() {
			return getRuleContext(IfStatementContext.class,0);
		}
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_statement);
		try {
			setState(211);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(204);
				block();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(205);
				variableDeclaration();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(206);
				printStatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(207);
				printlnStatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(208);
				functionCall();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(209);
				returnStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(210);
				ifStatement();
				}
				break;
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
		enterRule(_localctx, 28, RULE_variableDeclaration);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(213);
			match(VARIABLE);
			setState(214);
			name();
			setState(215);
			_la = _input.LA(1);
			if ( !(_la==T__6 || _la==T__29) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(216);
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
		enterRule(_localctx, 30, RULE_printStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(PRINT);
			setState(219);
			match(T__2);
			setState(220);
			expression(0);
			setState(221);
			match(T__4);
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
		enterRule(_localctx, 32, RULE_printlnStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			match(PRINTLN);
			setState(224);
			match(T__2);
			setState(225);
			expression(0);
			setState(226);
			match(T__4);
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

	public static class ReturnStatementContext extends ParserRuleContext {
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
	 
		public ReturnStatementContext() { }
		public void copyFrom(ReturnStatementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class ReturnVoidContext extends ReturnStatementContext {
		public TerminalNode RETURN() { return getToken(CASCParser.RETURN, 0); }
		public ReturnVoidContext(ReturnStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitReturnVoid(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ReturnWithValueContext extends ReturnStatementContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RETURN() { return getToken(CASCParser.RETURN, 0); }
		public ReturnWithValueContext(ReturnStatementContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitReturnWithValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_returnStatement);
		int _la;
		try {
			setState(233);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				_localctx = new ReturnWithValueContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(229);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==RETURN) {
					{
					setState(228);
					match(RETURN);
					}
				}

				setState(231);
				expression(0);
				}
				break;
			case 2:
				_localctx = new ReturnVoidContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(232);
				match(RETURN);
				}
				break;
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
		public Token mod;
		public FunctionNameContext functionName() {
			return getRuleContext(FunctionNameContext.class,0);
		}
		public List<ArgumentContext> argument() {
			return getRuleContexts(ArgumentContext.class);
		}
		public ArgumentContext argument(int i) {
			return getRuleContext(ArgumentContext.class,i);
		}
		public TerminalNode NEG() { return getToken(CASCParser.NEG, 0); }
		public TerminalNode POS() { return getToken(CASCParser.POS, 0); }
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
		enterRule(_localctx, 36, RULE_functionCall);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(236);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NEG || _la==POS) {
				{
				setState(235);
				((FunctionCallContext)_localctx).mod = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==NEG || _la==POS) ) {
					((FunctionCallContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(238);
			functionName();
			setState(239);
			match(T__2);
			setState(241);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << NEG) | (1L << POS) | (1L << NUMBER) | (1L << STRING) | (1L << ID) | (1L << TRUE) | (1L << FALSE))) != 0)) {
				{
				setState(240);
				argument();
				}
			}

			setState(247);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(243);
				match(T__3);
				setState(244);
				argument();
				}
				}
				setState(249);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(250);
			match(T__4);
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

	public static class IfStatementContext extends ParserRuleContext {
		public BlockContext trueStatement;
		public BlockContext falseStatement;
		public TerminalNode IF() { return getToken(CASCParser.IF, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public TerminalNode ELSE() { return getToken(CASCParser.ELSE, 0); }
		public IfStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitIfStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStatementContext ifStatement() throws RecognitionException {
		IfStatementContext _localctx = new IfStatementContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_ifStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(252);
			match(IF);
			setState(254);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				{
				setState(253);
				match(T__2);
				}
				break;
			}
			setState(256);
			expression(0);
			setState(258);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(257);
				match(T__4);
				}
			}

			setState(260);
			((IfStatementContext)_localctx).trueStatement = block();
			setState(263);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ELSE) {
				{
				setState(261);
				match(ELSE);
				setState(262);
				((IfStatementContext)_localctx).falseStatement = block();
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
		enterRule(_localctx, 40, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
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

	public static class ArgumentContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ArgumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argument; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitArgument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentContext argument() throws RecognitionException {
		ArgumentContext _localctx = new ArgumentContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_argument);
		try {
			setState(272);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,28,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(267);
				expression(0);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(268);
				name();
				setState(269);
				match(T__6);
				setState(270);
				expression(0);
				}
				break;
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
		enterRule(_localctx, 44, RULE_expressionList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(275);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__2) | (1L << NEG) | (1L << POS) | (1L << NUMBER) | (1L << STRING) | (1L << ID) | (1L << TRUE) | (1L << FALSE))) != 0)) {
				{
				setState(274);
				expression(0);
				}
			}

			setState(281);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__3) {
				{
				{
				setState(277);
				match(T__3);
				setState(278);
				expression(0);
				}
				}
				setState(283);
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
		public Token mod;
		public VarReferenceContext varReference() {
			return getRuleContext(VarReferenceContext.class,0);
		}
		public TerminalNode NEG() { return getToken(CASCParser.NEG, 0); }
		public TerminalNode POS() { return getToken(CASCParser.POS, 0); }
		public VarRefContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitVarRef(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValContext extends ExpressionContext {
		public Token mod;
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public TerminalNode NEG() { return getToken(CASCParser.NEG, 0); }
		public TerminalNode POS() { return getToken(CASCParser.POS, 0); }
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
	public static class IfExprContext extends ExpressionContext {
		public ExpressionContext condition;
		public ExpressionContext trueExpression;
		public ExpressionContext falseExpression;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public IfExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitIfExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ConditionalExpressionContext extends ExpressionContext {
		public Token cmp;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode GREATER() { return getToken(CASCParser.GREATER, 0); }
		public TerminalNode LESS() { return getToken(CASCParser.LESS, 0); }
		public TerminalNode EQ() { return getToken(CASCParser.EQ, 0); }
		public TerminalNode NOT_EQ() { return getToken(CASCParser.NOT_EQ, 0); }
		public TerminalNode GREATER_EQ() { return getToken(CASCParser.GREATER_EQ, 0); }
		public TerminalNode LESS_EQ() { return getToken(CASCParser.LESS_EQ, 0); }
		public ConditionalExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitConditionalExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ModAddContext extends ExpressionContext {
		public Token mod;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode PLUS() { return getToken(CASCParser.PLUS, 0); }
		public TerminalNode NEG() { return getToken(CASCParser.NEG, 0); }
		public TerminalNode POS() { return getToken(CASCParser.POS, 0); }
		public ModAddContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitModAdd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class FuncCallContext extends ExpressionContext {
		public Token mod;
		public FunctionCallContext functionCall() {
			return getRuleContext(FunctionCallContext.class,0);
		}
		public TerminalNode NEG() { return getToken(CASCParser.NEG, 0); }
		public TerminalNode POS() { return getToken(CASCParser.POS, 0); }
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
	public static class ModDivideContext extends ExpressionContext {
		public Token mod;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode SLASH() { return getToken(CASCParser.SLASH, 0); }
		public TerminalNode NEG() { return getToken(CASCParser.NEG, 0); }
		public TerminalNode POS() { return getToken(CASCParser.POS, 0); }
		public ModDivideContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitModDivide(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ModMultiplyContext extends ExpressionContext {
		public Token mod;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode STAR() { return getToken(CASCParser.STAR, 0); }
		public TerminalNode NEG() { return getToken(CASCParser.NEG, 0); }
		public TerminalNode POS() { return getToken(CASCParser.POS, 0); }
		public ModMultiplyContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitModMultiply(this);
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
	public static class ModSubtractContext extends ExpressionContext {
		public Token mod;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MINUS() { return getToken(CASCParser.MINUS, 0); }
		public TerminalNode NEG() { return getToken(CASCParser.NEG, 0); }
		public TerminalNode POS() { return getToken(CASCParser.POS, 0); }
		public ModSubtractContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof CASCVisitor ) return ((CASCVisitor<? extends T>)visitor).visitModSubtract(this);
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
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(333);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
			case 1:
				{
				_localctx = new VarRefContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(286);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
				case 1:
					{
					setState(285);
					((VarRefContext)_localctx).mod = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==NEG || _la==POS) ) {
						((VarRefContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				setState(288);
				varReference();
				}
				break;
			case 2:
				{
				_localctx = new ValContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(290);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NEG || _la==POS) {
					{
					setState(289);
					((ValContext)_localctx).mod = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==NEG || _la==POS) ) {
						((ValContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(292);
				value();
				}
				break;
			case 3:
				{
				_localctx = new FuncCallContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(294);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
				case 1:
					{
					setState(293);
					((FuncCallContext)_localctx).mod = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==NEG || _la==POS) ) {
						((FuncCallContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
					break;
				}
				setState(296);
				functionCall();
				}
				break;
			case 4:
				{
				_localctx = new ModMultiplyContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(298);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NEG || _la==POS) {
					{
					setState(297);
					((ModMultiplyContext)_localctx).mod = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==NEG || _la==POS) ) {
						((ModMultiplyContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(300);
				match(T__2);
				setState(301);
				expression(0);
				setState(302);
				match(STAR);
				setState(303);
				expression(0);
				setState(304);
				match(T__4);
				}
				break;
			case 5:
				{
				_localctx = new ModDivideContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(307);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NEG || _la==POS) {
					{
					setState(306);
					((ModDivideContext)_localctx).mod = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==NEG || _la==POS) ) {
						((ModDivideContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(309);
				match(T__2);
				setState(310);
				expression(0);
				setState(311);
				match(SLASH);
				setState(312);
				expression(0);
				setState(313);
				match(T__4);
				}
				break;
			case 6:
				{
				_localctx = new ModAddContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NEG || _la==POS) {
					{
					setState(315);
					((ModAddContext)_localctx).mod = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==NEG || _la==POS) ) {
						((ModAddContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(318);
				match(T__2);
				setState(319);
				expression(0);
				setState(320);
				match(PLUS);
				setState(321);
				expression(0);
				setState(322);
				match(T__4);
				}
				break;
			case 7:
				{
				_localctx = new ModSubtractContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(325);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==NEG || _la==POS) {
					{
					setState(324);
					((ModSubtractContext)_localctx).mod = _input.LT(1);
					_la = _input.LA(1);
					if ( !(_la==NEG || _la==POS) ) {
						((ModSubtractContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
					}
					else {
						if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
						_errHandler.reportMatch(this);
						consume();
					}
					}
				}

				setState(327);
				match(T__2);
				setState(328);
				expression(0);
				setState(329);
				match(MINUS);
				setState(330);
				expression(0);
				setState(331);
				match(T__4);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(373);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(371);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
					case 1:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(335);
						if (!(precpred(_ctx, 18))) throw new FailedPredicateException(this, "precpred(_ctx, 18)");
						setState(336);
						((ConditionalExpressionContext)_localctx).cmp = match(GREATER);
						setState(337);
						expression(19);
						}
						break;
					case 2:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(338);
						if (!(precpred(_ctx, 17))) throw new FailedPredicateException(this, "precpred(_ctx, 17)");
						setState(339);
						((ConditionalExpressionContext)_localctx).cmp = match(LESS);
						setState(340);
						expression(18);
						}
						break;
					case 3:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(341);
						if (!(precpred(_ctx, 16))) throw new FailedPredicateException(this, "precpred(_ctx, 16)");
						setState(342);
						((ConditionalExpressionContext)_localctx).cmp = match(EQ);
						setState(343);
						expression(17);
						}
						break;
					case 4:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(344);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(345);
						((ConditionalExpressionContext)_localctx).cmp = match(NOT_EQ);
						setState(346);
						expression(16);
						}
						break;
					case 5:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(347);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(348);
						((ConditionalExpressionContext)_localctx).cmp = match(GREATER_EQ);
						setState(349);
						expression(15);
						}
						break;
					case 6:
						{
						_localctx = new ConditionalExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(350);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(351);
						((ConditionalExpressionContext)_localctx).cmp = match(LESS_EQ);
						setState(352);
						expression(14);
						}
						break;
					case 7:
						{
						_localctx = new IfExprContext(new ExpressionContext(_parentctx, _parentState));
						((IfExprContext)_localctx).condition = _prevctx;
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(353);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(354);
						match(T__30);
						setState(355);
						((IfExprContext)_localctx).trueExpression = expression(0);
						setState(356);
						match(T__5);
						setState(357);
						((IfExprContext)_localctx).falseExpression = expression(10);
						}
						break;
					case 8:
						{
						_localctx = new MultiplyContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(359);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(360);
						match(STAR);
						setState(361);
						expression(8);
						}
						break;
					case 9:
						{
						_localctx = new DivideContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(362);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(363);
						match(SLASH);
						setState(364);
						expression(6);
						}
						break;
					case 10:
						{
						_localctx = new AddContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(365);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(366);
						match(PLUS);
						setState(367);
						expression(4);
						}
						break;
					case 11:
						{
						_localctx = new SubtractContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(368);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(369);
						match(MINUS);
						setState(370);
						expression(2);
						}
						break;
					}
					} 
				}
				setState(375);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,40,_ctx);
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
		public Token mod;
		public TerminalNode ID() { return getToken(CASCParser.ID, 0); }
		public TerminalNode NEG() { return getToken(CASCParser.NEG, 0); }
		public TerminalNode POS() { return getToken(CASCParser.POS, 0); }
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
		enterRule(_localctx, 48, RULE_varReference);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(377);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NEG || _la==POS) {
				{
				setState(376);
				((VarReferenceContext)_localctx).mod = _input.LT(1);
				_la = _input.LA(1);
				if ( !(_la==NEG || _la==POS) ) {
					((VarReferenceContext)_localctx).mod = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(379);
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
		public TerminalNode FALSE() { return getToken(CASCParser.FALSE, 0); }
		public TerminalNode TRUE() { return getToken(CASCParser.TRUE, 0); }
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
		enterRule(_localctx, 50, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(381);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << NUMBER) | (1L << STRING) | (1L << TRUE) | (1L << FALSE))) != 0)) ) {
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
		case 23:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 18);
		case 1:
			return precpred(_ctx, 17);
		case 2:
			return precpred(_ctx, 16);
		case 3:
			return precpred(_ctx, 15);
		case 4:
			return precpred(_ctx, 14);
		case 5:
			return precpred(_ctx, 13);
		case 6:
			return precpred(_ctx, 9);
		case 7:
			return precpred(_ctx, 7);
		case 8:
			return precpred(_ctx, 5);
		case 9:
			return precpred(_ctx, 3);
		case 10:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3=\u0182\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\7"+
		"\5C\n\5\f\5\16\5F\13\5\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\7\7\7Q\n\7\f"+
		"\7\16\7T\13\7\5\7V\n\7\3\7\3\7\3\7\5\7[\n\7\3\b\3\b\3\t\3\t\3\t\3\t\5"+
		"\tc\n\t\3\n\3\n\3\n\3\13\3\13\5\13j\n\13\3\f\3\f\3\f\7\fo\n\f\f\f\16\f"+
		"r\13\f\3\f\3\f\3\f\7\fw\n\f\f\f\16\fz\13\f\3\f\3\f\3\f\7\f\177\n\f\f\f"+
		"\16\f\u0082\13\f\3\f\3\f\3\f\7\f\u0087\n\f\f\f\16\f\u008a\13\f\3\f\3\f"+
		"\3\f\7\f\u008f\n\f\f\f\16\f\u0092\13\f\3\f\3\f\3\f\7\f\u0097\n\f\f\f\16"+
		"\f\u009a\13\f\3\f\3\f\3\f\7\f\u009f\n\f\f\f\16\f\u00a2\13\f\3\f\3\f\3"+
		"\f\7\f\u00a7\n\f\f\f\16\f\u00aa\13\f\3\f\3\f\3\f\7\f\u00af\n\f\f\f\16"+
		"\f\u00b2\13\f\3\f\3\f\3\f\7\f\u00b7\n\f\f\f\16\f\u00ba\13\f\5\f\u00bc"+
		"\n\f\3\r\3\r\3\r\7\r\u00c1\n\r\f\r\16\r\u00c4\13\r\3\16\3\16\7\16\u00c8"+
		"\n\16\f\16\16\16\u00cb\13\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\5\17\u00d6\n\17\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21"+
		"\3\22\3\22\3\22\3\22\3\22\3\23\5\23\u00e8\n\23\3\23\3\23\5\23\u00ec\n"+
		"\23\3\24\5\24\u00ef\n\24\3\24\3\24\3\24\5\24\u00f4\n\24\3\24\3\24\7\24"+
		"\u00f8\n\24\f\24\16\24\u00fb\13\24\3\24\3\24\3\25\3\25\5\25\u0101\n\25"+
		"\3\25\3\25\5\25\u0105\n\25\3\25\3\25\3\25\5\25\u010a\n\25\3\26\3\26\3"+
		"\27\3\27\3\27\3\27\3\27\5\27\u0113\n\27\3\30\5\30\u0116\n\30\3\30\3\30"+
		"\7\30\u011a\n\30\f\30\16\30\u011d\13\30\3\31\3\31\5\31\u0121\n\31\3\31"+
		"\3\31\5\31\u0125\n\31\3\31\3\31\5\31\u0129\n\31\3\31\3\31\5\31\u012d\n"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u0136\n\31\3\31\3\31\3\31"+
		"\3\31\3\31\3\31\3\31\5\31\u013f\n\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31"+
		"\5\31\u0148\n\31\3\31\3\31\3\31\3\31\3\31\3\31\5\31\u0150\n\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\31\3\31\3\31\3\31\3\31\3\31\7\31\u0176\n\31\f\31\16\31\u0179\13"+
		"\31\3\32\5\32\u017c\n\32\3\32\3\32\3\33\3\33\3\33\2\3\60\34\2\4\6\b\n"+
		"\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\2\17\3\2\n\13\3\2\16\17"+
		"\3\2\20\21\3\2\22\23\3\2\24\25\3\2\26\27\3\2\30\31\3\2\32\33\3\2\34\35"+
		"\3\2\36\37\4\2\t\t  \3\2*+\4\2\678:;\2\u01ac\2\66\3\2\2\2\49\3\2\2\2\6"+
		"?\3\2\2\2\bD\3\2\2\2\nG\3\2\2\2\fJ\3\2\2\2\16\\\3\2\2\2\20^\3\2\2\2\22"+
		"d\3\2\2\2\24i\3\2\2\2\26\u00bb\3\2\2\2\30\u00bd\3\2\2\2\32\u00c5\3\2\2"+
		"\2\34\u00d5\3\2\2\2\36\u00d7\3\2\2\2 \u00dc\3\2\2\2\"\u00e1\3\2\2\2$\u00eb"+
		"\3\2\2\2&\u00ee\3\2\2\2(\u00fe\3\2\2\2*\u010b\3\2\2\2,\u0112\3\2\2\2."+
		"\u0115\3\2\2\2\60\u014f\3\2\2\2\62\u017b\3\2\2\2\64\u017f\3\2\2\2\66\67"+
		"\5\4\3\2\678\7\2\2\38\3\3\2\2\29:\7\"\2\2:;\5\6\4\2;<\7\3\2\2<=\5\b\5"+
		"\2=>\7\4\2\2>\5\3\2\2\2?@\79\2\2@\7\3\2\2\2AC\5\n\6\2BA\3\2\2\2CF\3\2"+
		"\2\2DB\3\2\2\2DE\3\2\2\2E\t\3\2\2\2FD\3\2\2\2GH\5\f\7\2HI\5\32\16\2I\13"+
		"\3\2\2\2JK\7#\2\2KL\5\16\b\2LU\7\5\2\2MR\5\20\t\2NO\7\6\2\2OQ\5\20\t\2"+
		"PN\3\2\2\2QT\3\2\2\2RP\3\2\2\2RS\3\2\2\2SV\3\2\2\2TR\3\2\2\2UM\3\2\2\2"+
		"UV\3\2\2\2VW\3\2\2\2WZ\7\7\2\2XY\7\b\2\2Y[\5\24\13\2ZX\3\2\2\2Z[\3\2\2"+
		"\2[\r\3\2\2\2\\]\79\2\2]\17\3\2\2\2^_\79\2\2_`\7\b\2\2`b\5\24\13\2ac\5"+
		"\22\n\2ba\3\2\2\2bc\3\2\2\2c\21\3\2\2\2de\7\t\2\2ef\5\60\31\2f\23\3\2"+
		"\2\2gj\5\26\f\2hj\5\30\r\2ig\3\2\2\2ih\3\2\2\2j\25\3\2\2\2kp\t\2\2\2l"+
		"m\7\f\2\2mo\7\r\2\2nl\3\2\2\2or\3\2\2\2pn\3\2\2\2pq\3\2\2\2q\u00bc\3\2"+
		"\2\2rp\3\2\2\2sx\t\3\2\2tu\7\f\2\2uw\7\r\2\2vt\3\2\2\2wz\3\2\2\2xv\3\2"+
		"\2\2xy\3\2\2\2y\u00bc\3\2\2\2zx\3\2\2\2{\u0080\t\4\2\2|}\7\f\2\2}\177"+
		"\7\r\2\2~|\3\2\2\2\177\u0082\3\2\2\2\u0080~\3\2\2\2\u0080\u0081\3\2\2"+
		"\2\u0081\u00bc\3\2\2\2\u0082\u0080\3\2\2\2\u0083\u0088\t\5\2\2\u0084\u0085"+
		"\7\f\2\2\u0085\u0087\7\r\2\2\u0086\u0084\3\2\2\2\u0087\u008a\3\2\2\2\u0088"+
		"\u0086\3\2\2\2\u0088\u0089\3\2\2\2\u0089\u00bc\3\2\2\2\u008a\u0088\3\2"+
		"\2\2\u008b\u0090\t\6\2\2\u008c\u008d\7\f\2\2\u008d\u008f\7\r\2\2\u008e"+
		"\u008c\3\2\2\2\u008f\u0092\3\2\2\2\u0090\u008e\3\2\2\2\u0090\u0091\3\2"+
		"\2\2\u0091\u00bc\3\2\2\2\u0092\u0090\3\2\2\2\u0093\u0098\t\7\2\2\u0094"+
		"\u0095\7\f\2\2\u0095\u0097\7\r\2\2\u0096\u0094\3\2\2\2\u0097\u009a\3\2"+
		"\2\2\u0098\u0096\3\2\2\2\u0098\u0099\3\2\2\2\u0099\u00bc\3\2\2\2\u009a"+
		"\u0098\3\2\2\2\u009b\u00a0\t\b\2\2\u009c\u009d\7\f\2\2\u009d\u009f\7\r"+
		"\2\2\u009e\u009c\3\2\2\2\u009f\u00a2\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0"+
		"\u00a1\3\2\2\2\u00a1\u00bc\3\2\2\2\u00a2\u00a0\3\2\2\2\u00a3\u00a8\t\t"+
		"\2\2\u00a4\u00a5\7\f\2\2\u00a5\u00a7\7\r\2\2\u00a6\u00a4\3\2\2\2\u00a7"+
		"\u00aa\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00bc\3\2"+
		"\2\2\u00aa\u00a8\3\2\2\2\u00ab\u00b0\t\n\2\2\u00ac\u00ad\7\f\2\2\u00ad"+
		"\u00af\7\r\2\2\u00ae\u00ac\3\2\2\2\u00af\u00b2\3\2\2\2\u00b0\u00ae\3\2"+
		"\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00bc\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3"+
		"\u00b8\t\13\2\2\u00b4\u00b5\7\f\2\2\u00b5\u00b7\7\r\2\2\u00b6\u00b4\3"+
		"\2\2\2\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9"+
		"\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bbk\3\2\2\2\u00bbs\3\2\2\2\u00bb"+
		"{\3\2\2\2\u00bb\u0083\3\2\2\2\u00bb\u008b\3\2\2\2\u00bb\u0093\3\2\2\2"+
		"\u00bb\u009b\3\2\2\2\u00bb\u00a3\3\2\2\2\u00bb\u00ab\3\2\2\2\u00bb\u00b3"+
		"\3\2\2\2\u00bc\27\3\2\2\2\u00bd\u00c2\7<\2\2\u00be\u00bf\7\f\2\2\u00bf"+
		"\u00c1\7\r\2\2\u00c0\u00be\3\2\2\2\u00c1\u00c4\3\2\2\2\u00c2\u00c0\3\2"+
		"\2\2\u00c2\u00c3\3\2\2\2\u00c3\31\3\2\2\2\u00c4\u00c2\3\2\2\2\u00c5\u00c9"+
		"\7\3\2\2\u00c6\u00c8\5\34\17\2\u00c7\u00c6\3\2\2\2\u00c8\u00cb\3\2\2\2"+
		"\u00c9\u00c7\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00cc\3\2\2\2\u00cb\u00c9"+
		"\3\2\2\2\u00cc\u00cd\7\4\2\2\u00cd\33\3\2\2\2\u00ce\u00d6\5\32\16\2\u00cf"+
		"\u00d6\5\36\20\2\u00d0\u00d6\5 \21\2\u00d1\u00d6\5\"\22\2\u00d2\u00d6"+
		"\5&\24\2\u00d3\u00d6\5$\23\2\u00d4\u00d6\5(\25\2\u00d5\u00ce\3\2\2\2\u00d5"+
		"\u00cf\3\2\2\2\u00d5\u00d0\3\2\2\2\u00d5\u00d1\3\2\2\2\u00d5\u00d2\3\2"+
		"\2\2\u00d5\u00d3\3\2\2\2\u00d5\u00d4\3\2\2\2\u00d6\35\3\2\2\2\u00d7\u00d8"+
		"\7$\2\2\u00d8\u00d9\5*\26\2\u00d9\u00da\t\f\2\2\u00da\u00db\5\60\31\2"+
		"\u00db\37\3\2\2\2\u00dc\u00dd\7(\2\2\u00dd\u00de\7\5\2\2\u00de\u00df\5"+
		"\60\31\2\u00df\u00e0\7\7\2\2\u00e0!\3\2\2\2\u00e1\u00e2\7)\2\2\u00e2\u00e3"+
		"\7\5\2\2\u00e3\u00e4\5\60\31\2\u00e4\u00e5\7\7\2\2\u00e5#\3\2\2\2\u00e6"+
		"\u00e8\7\'\2\2\u00e7\u00e6\3\2\2\2\u00e7\u00e8\3\2\2\2\u00e8\u00e9\3\2"+
		"\2\2\u00e9\u00ec\5\60\31\2\u00ea\u00ec\7\'\2\2\u00eb\u00e7\3\2\2\2\u00eb"+
		"\u00ea\3\2\2\2\u00ec%\3\2\2\2\u00ed\u00ef\t\r\2\2\u00ee\u00ed\3\2\2\2"+
		"\u00ee\u00ef\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f1\5\16\b\2\u00f1\u00f3"+
		"\7\5\2\2\u00f2\u00f4\5,\27\2\u00f3\u00f2\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4"+
		"\u00f9\3\2\2\2\u00f5\u00f6\7\6\2\2\u00f6\u00f8\5,\27\2\u00f7\u00f5\3\2"+
		"\2\2\u00f8\u00fb\3\2\2\2\u00f9\u00f7\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa"+
		"\u00fc\3\2\2\2\u00fb\u00f9\3\2\2\2\u00fc\u00fd\7\7\2\2\u00fd\'\3\2\2\2"+
		"\u00fe\u0100\7%\2\2\u00ff\u0101\7\5\2\2\u0100\u00ff\3\2\2\2\u0100\u0101"+
		"\3\2\2\2\u0101\u0102\3\2\2\2\u0102\u0104\5\60\31\2\u0103\u0105\7\7\2\2"+
		"\u0104\u0103\3\2\2\2\u0104\u0105\3\2\2\2\u0105\u0106\3\2\2\2\u0106\u0109"+
		"\5\32\16\2\u0107\u0108\7&\2\2\u0108\u010a\5\32\16\2\u0109\u0107\3\2\2"+
		"\2\u0109\u010a\3\2\2\2\u010a)\3\2\2\2\u010b\u010c\79\2\2\u010c+\3\2\2"+
		"\2\u010d\u0113\5\60\31\2\u010e\u010f\5*\26\2\u010f\u0110\7\t\2\2\u0110"+
		"\u0111\5\60\31\2\u0111\u0113\3\2\2\2\u0112\u010d\3\2\2\2\u0112\u010e\3"+
		"\2\2\2\u0113-\3\2\2\2\u0114\u0116\5\60\31\2\u0115\u0114\3\2\2\2\u0115"+
		"\u0116\3\2\2\2\u0116\u011b\3\2\2\2\u0117\u0118\7\6\2\2\u0118\u011a\5\60"+
		"\31\2\u0119\u0117\3\2\2\2\u011a\u011d\3\2\2\2\u011b\u0119\3\2\2\2\u011b"+
		"\u011c\3\2\2\2\u011c/\3\2\2\2\u011d\u011b\3\2\2\2\u011e\u0120\b\31\1\2"+
		"\u011f\u0121\t\r\2\2\u0120\u011f\3\2\2\2\u0120\u0121\3\2\2\2\u0121\u0122"+
		"\3\2\2\2\u0122\u0150\5\62\32\2\u0123\u0125\t\r\2\2\u0124\u0123\3\2\2\2"+
		"\u0124\u0125\3\2\2\2\u0125\u0126\3\2\2\2\u0126\u0150\5\64\33\2\u0127\u0129"+
		"\t\r\2\2\u0128\u0127\3\2\2\2\u0128\u0129\3\2\2\2\u0129\u012a\3\2\2\2\u012a"+
		"\u0150\5&\24\2\u012b\u012d\t\r\2\2\u012c\u012b\3\2\2\2\u012c\u012d\3\2"+
		"\2\2\u012d\u012e\3\2\2\2\u012e\u012f\7\5\2\2\u012f\u0130\5\60\31\2\u0130"+
		"\u0131\7.\2\2\u0131\u0132\5\60\31\2\u0132\u0133\7\7\2\2\u0133\u0150\3"+
		"\2\2\2\u0134\u0136\t\r\2\2\u0135\u0134\3\2\2\2\u0135\u0136\3\2\2\2\u0136"+
		"\u0137\3\2\2\2\u0137\u0138\7\5\2\2\u0138\u0139\5\60\31\2\u0139\u013a\7"+
		"/\2\2\u013a\u013b\5\60\31\2\u013b\u013c\7\7\2\2\u013c\u0150\3\2\2\2\u013d"+
		"\u013f\t\r\2\2\u013e\u013d\3\2\2\2\u013e\u013f\3\2\2\2\u013f\u0140\3\2"+
		"\2\2\u0140\u0141\7\5\2\2\u0141\u0142\5\60\31\2\u0142\u0143\7,\2\2\u0143"+
		"\u0144\5\60\31\2\u0144\u0145\7\7\2\2\u0145\u0150\3\2\2\2\u0146\u0148\t"+
		"\r\2\2\u0147\u0146\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u0149\3\2\2\2\u0149"+
		"\u014a\7\5\2\2\u014a\u014b\5\60\31\2\u014b\u014c\7-\2\2\u014c\u014d\5"+
		"\60\31\2\u014d\u014e\7\7\2\2\u014e\u0150\3\2\2\2\u014f\u011e\3\2\2\2\u014f"+
		"\u0124\3\2\2\2\u014f\u0128\3\2\2\2\u014f\u012c\3\2\2\2\u014f\u0135\3\2"+
		"\2\2\u014f\u013e\3\2\2\2\u014f\u0147\3\2\2\2\u0150\u0177\3\2\2\2\u0151"+
		"\u0152\f\24\2\2\u0152\u0153\7\61\2\2\u0153\u0176\5\60\31\25\u0154\u0155"+
		"\f\23\2\2\u0155\u0156\7\62\2\2\u0156\u0176\5\60\31\24\u0157\u0158\f\22"+
		"\2\2\u0158\u0159\7\65\2\2\u0159\u0176\5\60\31\23\u015a\u015b\f\21\2\2"+
		"\u015b\u015c\7\66\2\2\u015c\u0176\5\60\31\22\u015d\u015e\f\20\2\2\u015e"+
		"\u015f\7\63\2\2\u015f\u0176\5\60\31\21\u0160\u0161\f\17\2\2\u0161\u0162"+
		"\7\64\2\2\u0162\u0176\5\60\31\20\u0163\u0164\f\13\2\2\u0164\u0165\7!\2"+
		"\2\u0165\u0166\5\60\31\2\u0166\u0167\7\b\2\2\u0167\u0168\5\60\31\f\u0168"+
		"\u0176\3\2\2\2\u0169\u016a\f\t\2\2\u016a\u016b\7.\2\2\u016b\u0176\5\60"+
		"\31\n\u016c\u016d\f\7\2\2\u016d\u016e\7/\2\2\u016e\u0176\5\60\31\b\u016f"+
		"\u0170\f\5\2\2\u0170\u0171\7,\2\2\u0171\u0176\5\60\31\6\u0172\u0173\f"+
		"\3\2\2\u0173\u0174\7-\2\2\u0174\u0176\5\60\31\4\u0175\u0151\3\2\2\2\u0175"+
		"\u0154\3\2\2\2\u0175\u0157\3\2\2\2\u0175\u015a\3\2\2\2\u0175\u015d\3\2"+
		"\2\2\u0175\u0160\3\2\2\2\u0175\u0163\3\2\2\2\u0175\u0169\3\2\2\2\u0175"+
		"\u016c\3\2\2\2\u0175\u016f\3\2\2\2\u0175\u0172\3\2\2\2\u0176\u0179\3\2"+
		"\2\2\u0177\u0175\3\2\2\2\u0177\u0178\3\2\2\2\u0178\61\3\2\2\2\u0179\u0177"+
		"\3\2\2\2\u017a\u017c\t\r\2\2\u017b\u017a\3\2\2\2\u017b\u017c\3\2\2\2\u017c"+
		"\u017d\3\2\2\2\u017d\u017e\79\2\2\u017e\63\3\2\2\2\u017f\u0180\t\16\2"+
		"\2\u0180\65\3\2\2\2,DRUZbipx\u0080\u0088\u0090\u0098\u00a0\u00a8\u00b0"+
		"\u00b8\u00bb\u00c2\u00c9\u00d5\u00e7\u00eb\u00ee\u00f3\u00f9\u0100\u0104"+
		"\u0109\u0112\u0115\u011b\u0120\u0124\u0128\u012c\u0135\u013e\u0147\u014f"+
		"\u0175\u0177\u017b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}