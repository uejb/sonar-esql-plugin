package com.exxeta.iss.sonar.esql.tree.impl.lexical;

import java.util.Iterator;
import java.util.List;

import com.exxeta.iss.sonar.esql.api.tree.Tree;
import com.exxeta.iss.sonar.esql.api.tree.lexical.SyntaxToken;
import com.exxeta.iss.sonar.esql.api.tree.lexical.SyntaxTrivia;
import com.exxeta.iss.sonar.esql.api.visitors.DoubleDispatchVisitor;
import com.exxeta.iss.sonar.esql.tree.impl.EsqlTree;
import com.sonar.sslr.api.TokenType;

public class InternalSyntaxToken extends EsqlTree implements SyntaxToken {

	  private List<SyntaxTrivia> trivias;
	  private int startIndex;
	  private final int line;
	  private final int column;
	  private final String value;
	  private final boolean isEOF;
	  private int endLine;
	  private int endColumn;

	  public InternalSyntaxToken(int line, int column, String value, List<SyntaxTrivia> trivias, int startIndex, boolean isEOF) {
	    this.value = value;
	    this.line = line;
	    this.column = column;
	    this.trivias = trivias;
	    this.startIndex = startIndex;
	    this.isEOF = isEOF;
	    calculateEndOffsets();
	  }

	  private void calculateEndOffsets() {
	    String[] lines = value.split("\r\n|\n|\r", -1);
	    endColumn = column + value.length();
	    endLine = line + lines.length - 1;

	    if (endLine != line) {
	      endColumn = lines[lines.length - 1].length();
	    }
	  }

	  @Override
	  public int endLine() {
	    return endLine;
	  }

	  @Override
	  public int endColumn() {
	    return endColumn;
	  }

	  public int toIndex() {
	    return startIndex + value.length();
	  }

	  @Override
	  public String text() {
	    return value;
	  }

	  @Override
	  public List<SyntaxTrivia> trivias() {
	    return trivias;
	  }

	  /**
	   * @deprecated Use {@link SyntaxToken#line()} instead.
	   */
	  @Deprecated
	  @Override
	  public int getLine() {
	    return line();
	  }

	  @Override
	  public int line() {
	    return line;
	  }

	  @Override
	  public int column() {
	    return column;
	  }

	  public int startIndex() {
	    return startIndex;
	  }

	  @Override
	  public Kind getKind() {
	    return Kind.TOKEN;
	  }

	  @Override
	  public boolean isLeaf() {
	    return true;
	  }

	  public boolean isEOF() {
	    return isEOF;
	  }

	  @Override
	  public Iterator<Tree> childrenIterator() {
	    throw new UnsupportedOperationException();
	  }

	  @Override
	  public void accept(DoubleDispatchVisitor visitor) {
	    visitor.visitToken(this);
	  }

	  public boolean is(TokenType type) {
	    return this.text().equals(type.getValue());
	  }

	  @Override
	  public SyntaxToken getFirstToken() {
	    return this;
	  }

	  @Override
	  public SyntaxToken getLastToken() {
	    return this;
	  }

	  @Override
	  public String toString() {
	    return value;
	  }

	}
