# guava   java駝峰轉換

```
		<dependency>
			<groupId>com.google.guava</groupId>
			<artifactId>guava</artifactId>
			<version>18.0</version>
		</dependency>
```




```
import java.util.Date;

import com.google.common.base.CaseFormat;

public class CaseFormatDemo {
	
	
	public static void main(String[] args) {

		String UPPER_UNDERSCORE_TO = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, "CONSTANT_NAME"); // returns "constantName"
		System.out.println(UPPER_UNDERSCORE_TO);
		
		
		String LOWER_CAMEL_TO = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "constantName");
		System.out.println(LOWER_CAMEL_TO);
		
		
		System.out.println(new Date().getTime());
		//141,46610,79546
		//141,46616,12844
		
	}
}
```


---

```
CaseFormat. UPPER_UNDERSCORE .to(CaseFormat .LOWER_CAMEL, "CONSTANT_NAME" );
// CONSTANT_NAME  ->     constantName

CaseFormat.LOWER_CAMEL.to(CaseFormat. UPPER_UNDERSCORE , "constantName" );
// constantName -> CONSTANT_NAME


Joiner.on(",").join(Arrays.asList(1, 5, 7)); // returns "1,5,7"

Splitter.on(',')
       .trimResults()
       .omitEmptyStrings()
       .split("foo,bar,,   qux");
returns an Iterable<String> containing "foo", "bar", "qux". A Splitter may be set to split on any Pattern, char, String, or CharMatcher.


```
