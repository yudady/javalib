# guava   java駝峰轉換


com.google.guava
CaseFormat. UPPER_UNDERSCORE .to(CaseFormat .LOWER_CAMEL, "CONSTANT_NAME" );
CONSTANT_NAME  ->     constantName

CaseFormat.LOWER_CAMEL.to(CaseFormat. UPPER_UNDERSCORE , "constantName" );
constantName -> CONSTANT_NAME


Joiner.on(",").join(Arrays.asList(1, 5, 7)); // returns "1,5,7"

Splitter.on(',')
       .trimResults()
       .omitEmptyStrings()
       .split("foo,bar,,   qux");
returns an Iterable<String> containing "foo", "bar", "qux". A Splitter may be set to split on any Pattern, char, String, or CharMatcher.
