package ca.ulaval.glo4003.interfaces.domain;

import static com.google.common.truth.Truth.assertThat;

import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class StringCodeGeneratorTest {
  private static final String KEYWORD = "TEST";

  private StringCodeGenerator stringCodeGenerator;

  @Before
  public void setUp() {
    stringCodeGenerator = new StringCodeGenerator();
  }

  @Test
  public void whenBuildingCode_thenReturnDifferentCodes() {
    String firstCode = stringCodeGenerator.buildCode(KEYWORD);
    String secondCode = stringCodeGenerator.buildCode(KEYWORD);

    assertThat(firstCode).isNotEqualTo(secondCode);
  }

  @Test
  public void whenBuildingCode_thenReturnCodeWithKeyword() {
    String code = stringCodeGenerator.buildCode(KEYWORD);

    assertThat(code).contains(KEYWORD);
  }

  @Test
  public void whenBuildingCode_thenReturnTwoPartedCode() {
    String separator = "-";

    String code = stringCodeGenerator.buildCode(KEYWORD);
    List<String> codeParts = Arrays.asList(code.split(separator));

    assertThat(codeParts).hasSize(2);
  }

  @Test
  public void whenBuildingCode_thenReturnCodeWithSixAlphanumericalCharactersAsSecondPart() {
    String separator = "-";
    String alphanumericalRegex = "[A-Z0-9]+";

    String code = stringCodeGenerator.buildCode(KEYWORD);
    String secondPart = code.split(separator)[1];

    assertThat(secondPart).hasLength(6);
    assertThat(secondPart).matches(alphanumericalRegex);
  }
}
