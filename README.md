
# blockchainexperiment

The purpose of this application is to demonstrate how a very basic implementation of a blockchain system works.

## Documentation

[Requirements analysis](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/requirements_analysis.md)

[Working hours report](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/working_hours_report.md)

[Architecture](https://github.com/joonakauranen/ot-harjoitustyo/blob/master/dokumentaatio/architecture.md)

## Testing

Run the tests with the following command:

mvn test

Create a test coverage report with:

mvn jacoco:report

After running the above command find the report from _target/site/jacoco/index.html_ and open in browser.

## Checkstyle

Run style checks as defined in  [checkstyle.xml](https://github.com/mluukkai/OtmTodoApp/blob/master/checkstyle.xml) with the following command:

mvn jxr:jxr checkstyle:checkstyle

To open the report navigate to _target/site/_ and open _checkstyle.html_ in your browser
