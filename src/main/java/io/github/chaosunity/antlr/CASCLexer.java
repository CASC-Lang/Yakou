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
		T__0=1, T__1=2, VARIABLE=3, PRINT=4, EQUALS=5, NUMBER=6, STRING=7, ID=8, 
		WS=9;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "CHAR", "DIGIT", "UNICODE", "VARIABLE", "PRINT", "EQUALS", 
			"NUMBER", "STRING", "ID", "WS"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, "VARIABLE", "PRINT", "EQUALS", "NUMBER", "STRING", 
			"ID", "WS"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\13T\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\3\2\3\2\3\3\3\3\3\4\5\4!\n\4\3\5\3\5\3\6\3\6\3\7\3"+
		"\7\3\7\3\7\3\7\5\7,\n\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\5\b\65\n\b\3\t\3\t"+
		"\3\n\6\n:\n\n\r\n\16\n;\3\13\3\13\7\13@\n\13\f\13\16\13C\13\13\3\13\3"+
		"\13\3\f\3\f\3\f\6\fJ\n\f\r\f\16\fK\3\r\6\rO\n\r\r\r\16\rP\3\r\3\r\2\2"+
		"\16\3\3\5\4\7\2\t\2\13\2\r\5\17\6\21\7\23\b\25\t\27\n\31\13\3\2\6\4\2"+
		"C\\c|\4\2??\u8ce8\u8ce8\3\2\62;\5\2\13\f\17\17\"\"\2X\2\3\3\2\2\2\2\5"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\2\27\3\2\2\2\2\31\3\2\2\2\3\33\3\2\2\2\5\35\3\2\2\2\7 \3\2\2\2\t\""+
		"\3\2\2\2\13$\3\2\2\2\r+\3\2\2\2\17\64\3\2\2\2\21\66\3\2\2\2\239\3\2\2"+
		"\2\25=\3\2\2\2\27I\3\2\2\2\31N\3\2\2\2\33\34\7*\2\2\34\4\3\2\2\2\35\36"+
		"\7+\2\2\36\6\3\2\2\2\37!\t\2\2\2 \37\3\2\2\2!\b\3\2\2\2\"#\4\62;\2#\n"+
		"\3\2\2\2$%\4\u0082\1\2%\f\3\2\2\2&\'\7x\2\2\'(\7c\2\2(,\7t\2\2)*\7\u8b8c"+
		"\2\2*,\7\u657a\2\2+&\3\2\2\2+)\3\2\2\2,\16\3\2\2\2-.\7r\2\2./\7t\2\2/"+
		"\60\7k\2\2\60\61\7p\2\2\61\65\7v\2\2\62\63\7\u5372\2\2\63\65\7\u51fc\2"+
		"\2\64-\3\2\2\2\64\62\3\2\2\2\65\20\3\2\2\2\66\67\t\3\2\2\67\22\3\2\2\2"+
		"8:\t\4\2\298\3\2\2\2:;\3\2\2\2;9\3\2\2\2;<\3\2\2\2<\24\3\2\2\2=A\7$\2"+
		"\2>@\13\2\2\2?>\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2BD\3\2\2\2CA\3\2"+
		"\2\2DE\7$\2\2E\26\3\2\2\2FJ\5\7\4\2GJ\5\t\5\2HJ\5\13\6\2IF\3\2\2\2IG\3"+
		"\2\2\2IH\3\2\2\2JK\3\2\2\2KI\3\2\2\2KL\3\2\2\2L\30\3\2\2\2MO\t\5\2\2N"+
		"M\3\2\2\2OP\3\2\2\2PN\3\2\2\2PQ\3\2\2\2QR\3\2\2\2RS\b\r\2\2S\32\3\2\2"+
		"\2\13\2 +\64;AIKP\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}