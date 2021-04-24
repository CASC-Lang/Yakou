// Generated from CASC.g4 by ANTLR 4.7.2

package io.github.chaosunity.antlr;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CASCLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, CLASS=19, FUNC=20, VARIABLE=21, PRINT=22, PRINTLN=23, EQUALS=24, 
		NUMBER=25, STRING=26, ID=27, QUALIFIED_NAME=28, WS=29;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "T__10", "T__11", "T__12", "T__13", "T__14", "T__15", "T__16", 
			"T__17", "CHAR", "DIGIT", "UNICODE", "CLASS", "FUNC", "VARIABLE", "PRINT", 
			"PRINTLN", "EQUALS", "NUMBER", "STRING", "ID", "QUALIFIED_NAME", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'{'", "'}'", "'('", "')'", "'='", "'boolean'", "'['", "']'", "'string'", 
			"'char'", "'byte'", "'short'", "'int'", "'long'", "'float'", "'double'", 
			"'void'", "','"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "CLASS", "FUNC", "VARIABLE", 
			"PRINT", "PRINTLN", "EQUALS", "NUMBER", "STRING", "ID", "QUALIFIED_NAME", 
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


	public CASCLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CASC.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\37\u00e6\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31"+
		"\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t"+
		" \4!\t!\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\n\3\n\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\16"+
		"\3\16\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21"+
		"\3\21\3\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\23\3\23\3\24\5\24"+
		"\u008f\n\24\3\25\3\25\3\26\3\26\3\27\3\27\3\27\3\27\3\27\3\27\3\27\5\27"+
		"\u009c\n\27\3\30\3\30\3\30\3\30\3\30\3\30\5\30\u00a4\n\30\3\31\3\31\3"+
		"\31\3\31\3\31\5\31\u00ab\n\31\3\32\3\32\3\32\3\32\3\32\3\32\3\32\5\32"+
		"\u00b4\n\32\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\3\33\5\33\u00c0"+
		"\n\33\3\34\3\34\3\35\6\35\u00c5\n\35\r\35\16\35\u00c6\3\36\3\36\7\36\u00cb"+
		"\n\36\f\36\16\36\u00ce\13\36\3\36\3\36\3\37\3\37\3\37\6\37\u00d5\n\37"+
		"\r\37\16\37\u00d6\3 \3 \3 \6 \u00dc\n \r \16 \u00dd\3!\6!\u00e1\n!\r!"+
		"\16!\u00e2\3!\3!\2\2\"\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27"+
		"\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\2)\2+\2-\25/\26\61\27\63\30\65"+
		"\31\67\329\33;\34=\35?\36A\37\3\2\6\4\2C\\c|\4\2??\u8ce8\u8ce8\3\2\62"+
		";\5\2\13\f\17\17\"\"\2\u00ee\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3"+
		"\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2"+
		"\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37"+
		"\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\3C\3\2\2\2\5E\3\2\2\2\7G\3\2\2\2\tI\3"+
		"\2\2\2\13K\3\2\2\2\rM\3\2\2\2\17U\3\2\2\2\21W\3\2\2\2\23Y\3\2\2\2\25`"+
		"\3\2\2\2\27e\3\2\2\2\31j\3\2\2\2\33p\3\2\2\2\35t\3\2\2\2\37y\3\2\2\2!"+
		"\177\3\2\2\2#\u0086\3\2\2\2%\u008b\3\2\2\2\'\u008e\3\2\2\2)\u0090\3\2"+
		"\2\2+\u0092\3\2\2\2-\u009b\3\2\2\2/\u00a3\3\2\2\2\61\u00aa\3\2\2\2\63"+
		"\u00b3\3\2\2\2\65\u00bf\3\2\2\2\67\u00c1\3\2\2\29\u00c4\3\2\2\2;\u00c8"+
		"\3\2\2\2=\u00d4\3\2\2\2?\u00d8\3\2\2\2A\u00e0\3\2\2\2CD\7}\2\2D\4\3\2"+
		"\2\2EF\7\177\2\2F\6\3\2\2\2GH\7*\2\2H\b\3\2\2\2IJ\7+\2\2J\n\3\2\2\2KL"+
		"\7?\2\2L\f\3\2\2\2MN\7d\2\2NO\7q\2\2OP\7q\2\2PQ\7n\2\2QR\7g\2\2RS\7c\2"+
		"\2ST\7p\2\2T\16\3\2\2\2UV\7]\2\2V\20\3\2\2\2WX\7_\2\2X\22\3\2\2\2YZ\7"+
		"u\2\2Z[\7v\2\2[\\\7t\2\2\\]\7k\2\2]^\7p\2\2^_\7i\2\2_\24\3\2\2\2`a\7e"+
		"\2\2ab\7j\2\2bc\7c\2\2cd\7t\2\2d\26\3\2\2\2ef\7d\2\2fg\7{\2\2gh\7v\2\2"+
		"hi\7g\2\2i\30\3\2\2\2jk\7u\2\2kl\7j\2\2lm\7q\2\2mn\7t\2\2no\7v\2\2o\32"+
		"\3\2\2\2pq\7k\2\2qr\7p\2\2rs\7v\2\2s\34\3\2\2\2tu\7n\2\2uv\7q\2\2vw\7"+
		"p\2\2wx\7i\2\2x\36\3\2\2\2yz\7h\2\2z{\7n\2\2{|\7q\2\2|}\7c\2\2}~\7v\2"+
		"\2~ \3\2\2\2\177\u0080\7f\2\2\u0080\u0081\7q\2\2\u0081\u0082\7w\2\2\u0082"+
		"\u0083\7d\2\2\u0083\u0084\7n\2\2\u0084\u0085\7g\2\2\u0085\"\3\2\2\2\u0086"+
		"\u0087\7x\2\2\u0087\u0088\7q\2\2\u0088\u0089\7k\2\2\u0089\u008a\7f\2\2"+
		"\u008a$\3\2\2\2\u008b\u008c\7.\2\2\u008c&\3\2\2\2\u008d\u008f\t\2\2\2"+
		"\u008e\u008d\3\2\2\2\u008f(\3\2\2\2\u0090\u0091\4\62;\2\u0091*\3\2\2\2"+
		"\u0092\u0093\4\u0082\1\2\u0093,\3\2\2\2\u0094\u0095\7e\2\2\u0095\u0096"+
		"\7n\2\2\u0096\u0097\7c\2\2\u0097\u0098\7u\2\2\u0098\u009c\7u\2\2\u0099"+
		"\u009a\7\u9860\2\2\u009a\u009c\7\u5227\2\2\u009b\u0094\3\2\2\2\u009b\u0099"+
		"\3\2\2\2\u009c.\3\2\2\2\u009d\u009e\7h\2\2\u009e\u009f\7w\2\2\u009f\u00a0"+
		"\7p\2\2\u00a0\u00a4\7e\2\2\u00a1\u00a2\7\u51ff\2\2\u00a2\u00a4\7\u5f11"+
		"\2\2\u00a3\u009d\3\2\2\2\u00a3\u00a1\3\2\2\2\u00a4\60\3\2\2\2\u00a5\u00a6"+
		"\7x\2\2\u00a6\u00a7\7c\2\2\u00a7\u00ab\7t\2\2\u00a8\u00a9\7\u8b8c\2\2"+
		"\u00a9\u00ab\7\u657a\2\2\u00aa\u00a5\3\2\2\2\u00aa\u00a8\3\2\2\2\u00ab"+
		"\62\3\2\2\2\u00ac\u00ad\7r\2\2\u00ad\u00ae\7t\2\2\u00ae\u00af\7k\2\2\u00af"+
		"\u00b0\7p\2\2\u00b0\u00b4\7v\2\2\u00b1\u00b2\7\u5372\2\2\u00b2\u00b4\7"+
		"\u51fc\2\2\u00b3\u00ac\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b4\64\3\2\2\2\u00b5"+
		"\u00b6\7r\2\2\u00b6\u00b7\7t\2\2\u00b7\u00b8\7k\2\2\u00b8\u00b9\7p\2\2"+
		"\u00b9\u00ba\7v\2\2\u00ba\u00bb\7n\2\2\u00bb\u00c0\7p\2\2\u00bc\u00bd"+
		"\7\u5372\2\2\u00bd\u00be\7\u51fc\2\2\u00be\u00c0\7\u884e\2\2\u00bf\u00b5"+
		"\3\2\2\2\u00bf\u00bc\3\2\2\2\u00c0\66\3\2\2\2\u00c1\u00c2\t\3\2\2\u00c2"+
		"8\3\2\2\2\u00c3\u00c5\t\4\2\2\u00c4\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2"+
		"\u00c6\u00c4\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7:\3\2\2\2\u00c8\u00cc\7"+
		"$\2\2\u00c9\u00cb\13\2\2\2\u00ca\u00c9\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc"+
		"\u00ca\3\2\2\2\u00cc\u00cd\3\2\2\2\u00cd\u00cf\3\2\2\2\u00ce\u00cc\3\2"+
		"\2\2\u00cf\u00d0\7$\2\2\u00d0<\3\2\2\2\u00d1\u00d5\5\'\24\2\u00d2\u00d5"+
		"\5)\25\2\u00d3\u00d5\5+\26\2\u00d4\u00d1\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4"+
		"\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d4\3\2\2\2\u00d6\u00d7\3\2"+
		"\2\2\u00d7>\3\2\2\2\u00d8\u00db\5=\37\2\u00d9\u00da\7\60\2\2\u00da\u00dc"+
		"\5=\37\2\u00db\u00d9\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00db\3\2\2\2\u00dd"+
		"\u00de\3\2\2\2\u00de@\3\2\2\2\u00df\u00e1\t\5\2\2\u00e0\u00df\3\2\2\2"+
		"\u00e1\u00e2\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4"+
		"\3\2\2\2\u00e4\u00e5\b!\2\2\u00e5B\3\2\2\2\17\2\u008e\u009b\u00a3\u00aa"+
		"\u00b3\u00bf\u00c6\u00cc\u00d4\u00d6\u00dd\u00e2\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}