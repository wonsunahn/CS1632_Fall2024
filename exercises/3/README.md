- [CS 1632 - Software Quality Assurance](#cs-1632---software-quality-assurance)
  * [Description](#description)
  * [Prerequisites](#prerequisites)
  * [Task 1: Write test cases](#task-1-write-test-cases)
    + [Tips for writing assertions for each test](#tips-for-writing-assertions-for-each-test)
    + [Tips for selecting the best locator string](#tips-for-selecting-the-best-locator-string)
    + [Other tips when using Selenium IDE](#other-tips-when-using-selenium-ide)
  * [Task 2: Add test cases to test suite and save project](#task-2-add-test-cases-to-test-suite-and-save-project)
  * [Task 3: Export test suite to JUnit class](#task-3-export-test-suite-to-junit-class)
    + [Why export to a JUnit class?](#why-export-to-a-junit-class-)
    + [How to export to JUnit for Selenium IDE](#how-to-export-to-junit-for-selenium-ide)
    + [Running the JUnit class](#running-the-junit-class)
  * [Tips for JUnit + Selenium problem solving](#tips-for-junit--selenium-problem-solving)
    + [Conversion of relative URLs to absolute URLs](#conversion-of-relative-urls-to-absolute-urls)
    + [How to enforce uniform window sizes](#how-to-enforce-uniform-window-sizes)
    + [How to deal with race conditions](#how-to-deal-with-race-conditions)
- [Submission](#submission)
- [GradeScope Feedback](#gradescope-feedback)
- [Resources](#resources)
- [Extra Credit](#extra-credit)
  * [Description](#description-1)
  * [Submission](#submission-1)

# CS 1632 - Software Quality Assurance

* DUE: October 2 (Wednesday), 2024 before start of class

**GitHub Classroom Link:** https://classroom.github.com/a/-mFE2_xV

## Description

For this assignment, you and a partner will write a systems-level, automated
black-box tests for the Pitt website using the Selenium IDE.  Specifically,
we are going to test the URL:

https://www.pitt.edu/

## Prerequisites

1. Please install the [Chrome web browser](https://www.google.com/chrome/)
   or the [Firefox web browser](https://www.mozilla.org/en-US/firefox/new/).

1. Please install the web driver for the browser of your choice.  The
   Selenium people just recently announced a new tool called Selenium
Manager that can automatically download and install a web driver that
matches your current browser.  You can invoke Selenium Manager as
follows, assuming you are using Chrome.  If you want to use Firefox, you can
just replace "chrome" with "firefox" in the instructions.

   On Windows:
   
   ```
   selenium-manager\windows\selenium-manager.exe --browser chrome
   ```

   On MacOS:

   ```
   selenium-manager/macos/selenium-manager --browser chrome
   ```

   On Linux:

   ```
   selenium-manager/linux/selenium-manager --browser chrome
   ```

   On running this tool, you should see something similar to the below:

   ```
   > selenium-manager\windows\selenium-manager.exe --browser chrome
   INFO    Driver path: C:\Users\mrabb\.cache\selenium\chromedriver\win64\116.0.5845.96\chromedriver.exe
   INFO    Browser path: C:\Program Files\Google\Chrome\Application\chrome.exe
   ```

1. Please adding the Selenium IDE browser extension for your web browser by
   selecting "Chrome Download" or "Firefox Download" on the below website:

   https://www.selenium.dev/selenium-ide/

   Then, open Selenium IDE by clicking on the newly created browser extension with
the "Se" symbol.  You should see a pop up window that looks very similar to the
one shown on the lecture slides.

## Task 1: Write test cases

Implement the 6 test cases listed in the [testplan.md](testplan.md) document
using Selenium IDE, for the requirements listed in
[requirements.md](requirements.md).  Remember, each test must end with an
assertion to check the postconditions!

The list of available assertions and other commands are available at:

https://www.selenium.dev/selenium-ide/docs/en/api/commands

### Tips for writing assertions for each test

TEST-1-TITLE = This should be pretty straightforward.  Just make sure that when
you do **assert title**, the expected title string is in the "Target" box and
not the "Value" box.  If you read the Reference tab for **assert title**, the
title string is the first argument, and the first argument always goes into the
"Target" box, and the second argument goes into the "Value" box.  Sometimes
confusing, but that is how it always is.

TEST-2-LOGO-EXISTS - The important thing is when you use the **assert element
present** assertion, you use a locator string that indicates that the element
contains the alt text "University of Pittsburgh".  Otherwise, if you use any
random xpath locator indicating a position in the HTML page, you will be
checking that there exists an element in that position, but not that an element
with the alt text exists.  By the way, the logo with the alt text is the large
University of Pittsburgh logo at the top left.

TEST-3-LOGO-IMAGE - You will need to use **store attribute** followed by
**assert** for the postcondition check.  You will be storing the **src**
attribute value of the **img** element to a Selenium variable of your choosing
and asserting on the value of that variable.  Now the target argument for
**store attribute** does not directly take a locator string.  If you see the
Reference tab for the command, you will see that it takes \<locator
string\>@\<attribute name\> instead, where the attribute name in this case is
**src**.  Since the target argument is not a locator string, the target
selector button is disabled.  If you want to still use the target selector to
at least get the locator string part, you will have to do a workaround and
enter a command such as **assert text** or **click** which allows you to use
the target selector, fill in the locator string using it, and then revert back
to **store attribute**.

TEST-4-SCHOOLS-SCI - You will **assert text** to check that the 3rd school in
the list is "Computing & Information".  Now, for the locator passed to **assert
text**, you cannot use a locator for an element that contains the "Computing &
Information" text, unlike what we did for TEST-3-LOGO-EXISTS.  Using such a
locator string would give us no indication that the element is the 3rd item in
the list.  For that, you need an xpath locator that includes **li[3]**, or the
3rd **li** item in a list.

TEST-5-SCHOOLS-COUNT - The logic is that if **assert element present**
passes for the **li[16]** item and **assert element not present** passes for the
**li[17]** item, there must be exactly 16 lis and 16 schools.  Now, for
the **assert element not present** command, there is no such element so
obviously you would not be able to use the target selector tool to generate the
locator string.  You will have to copy the locator string from the **assert
element present** command and edit the locator to change li[16] to li[17].

TEST-6-SEARCH-CSC - Just like for TEST-2-LOGO-EXISTS, you will use an **assert
element present** assertion to check that "Student Organization Spotlight:
Computer Science Club (CSC)" is among the search results.  That means you need
to use a locator string that searches for that specific string in the search
results, and not a locator string that indicates a certain ranking in the
results (again, that would only check that the rank exists, not what we want).

### Tips for selecting the best locator string

When using the Selenium IDE target selector button, it tries to generate a
**locator string** as best it can using xpath, css selector, or id tag.  But it
is not that intelligent.  The problem is that Selenium IDE does not know
exactly what you are looking for in your testing scenario.  You may be looking
for an element at a particular position on the web page, or an element with a
certain text, or an element with a certain image source attribute.  Depending
on which it is, you will want to use a different locator string.  Now
regardless of which locator string you choose, your test case will pass as long
as that locator string finds the target element.  But you would not be testing
what you want to test.  For example, you might be checking a certain list item
exists at a certain relative position rather than checking a list item with a
certain text exists.

   That is why Selenium IDE gives you an option to choose alternative locator
strings other than its initial suggestion.  You will notice that there is a
small down arrow at the end of the target text box.  If you click on that
arrow, you will see alternative locator strings that point to the same element.
Good news is that Selenium IDE is smart enough that these locator strings all
uniquely identify the target element.  Bad news is that you have to be smart
enough to choose the best one.  If you become an expert Selenium programmer,
you will soon realize that even the alternative suggestions are sometimes
lacking and you have to come up with your own locator string and type it.

   Here is a list of all the different types of locators available in Selenium:

   https://www.selenium.dev/documentation/webdriver/elements/locators/   

   Here is an in-depth discussion on what are "good" locator strings:

   https://www.selenium.dev/documentation/test_practices/encouraged/locators/

   The above discussion states that using the ID attribute of the element is
often preferred, if it is available.  That is because the ID attribute always
uniquely identifies an HTML element (it is actually a [W3 Consortium
rule](https://www.w3.org/TR/2011/WD-html5-20110525/elements.html#the-id-attribute)).
Most often, the testing scenario requires you to simply uniquely identify an
element regardless of its position on the page, its text, or its attributes.
For example, if the test step is to click on the purchase button, then it
should not matter where that button is on the page, or whether the button text
actually says "purchase" or "buy", or what kind of image is used for the
button.  If there is an ID for that purchase button, it is best to use that ID.
That way, your test will not be fragile and break the moment you move the
button or change other properties of the button.

### Other tips when using Selenium IDE

Sometimes your test case will not work as expected.  Here are a few hints on how to debug a problem:

1. Check the **Log window** at the bottom of the Selenium IDE.  It will tell you
   which step failed for what reason (in red).

1. Select the test step that failed in the main test case window, and then
   select the **Reference tab** at the bottom pane of the IDE.  It will display
usage instructions for that command.  Remember always, the first argument goes
to the Target field and the second argument goes to the Value field, regardless
of command.

## Task 2: Add test cases to test suite and save project

1. Choose "Test Suites" from the left panel drop down menu.

1. There will already be a "Default Suite" there with possibly one or more tests.

1. Right click on "Default Suite", or click on the vertical-3-dot context menu button, and select "Rename" and rename to "PittEdu".

1. Right click on "PittEdu", or click on the vertical-3-dot context menu button,
and select "Add tests".  Make sure all tests are checked the press on the
"Select" button.

1. Click on the "Save project" button on the top right corner that looks like a
   floppy disk.  Save to file name "PittEdu.side" in the exercise root folder.

## Task 3: Export test suite to JUnit class

### Why export to a JUnit class?

There are many reasons why you would want to export to JUnit.

1. You may have a pre-existing testing framework in JUnit (or Python Pytest, or
   JavaScript Mocha, etc).  And you may want to merge the Selenium IDE testing
script to the language and framework of your choice.

1. Exporting to JUnit really gives you a good sense of what's happening under
   the covers (in terms of the actual calls to the web driver).  Also, if there
is a test case that is particularly hard to nail down just by using Selenium
IDE, you can touch it up in the form of exported Java code.  

1. Selenium IDE also gives the option to export your JUnit test directly to a
   Selenium Grid which can run the test cases in parallel.  This can allow you
to utilize a server farm to finish your testing very quickly, although we will
not explore this option today.

In the end, once you get familiar with the Selenium API and how locator strings
work, you will prefer coding in Java directly to the APIs.  At that stage,
Selenium IDE will feel more like extra baggage rather than a helping hand.  The
Selenium IDE scripting language is Turing complete, but it is primitive
compared to something like Java and the GUI coding interface, while helpful
initially, will start to feel clunky.  Moreover, the full range of Selenium
APIs are not represented in the scripting language (not to mention a few
defects in the subset that it does implement).

This part of the exercise will help you get more familiar with the Selenium API
and get you started on your journey to be a full Selenium programmer.

### How to export to JUnit for Selenium IDE

Once you are done writing your Selenium test suite, let's try exporting the test
suite in Selenium IDE to a Java JUnit test class.  

Follow these instructions:

1. Right click on "PittEdu", or click on the vertical-3-dot context menu
   button, and select "Export".

1. Select "Java JUnit" in the list of language options and optionally check
   "Include step descriptions as a separate comment" to generate more detailed
comments.  Leave other boxes unchecked.

1. Save the resulting file "PittEduTest.java" to the
   src/test/java/edu/pitt/cs test source directory.

1. Add the following line to the top of "PittEduTest.java":
   ```
   package edu.pitt.cs;
   ```

1. Also add the following line in the list of imports:
   ```
   import static org.hamcrest.MatcherAssert.assertThat;
   ```

   This gets rid of the annoying crossouts on assertThat calls in VSCode.

### Running the JUnit class

Before starting, let me warn you that your PittEduTest.java JUnit test class is
**may not work**, even after having made the above changes.  That is because
making Selenium work on websites sometimes requires some
[massaging](#tips-for-junit--selenium-problem-solving) and the Selenium IDE
code generation just takes you halfway there.  Moreover, Selenium IDE will
sometimes generate code which is flat out incorrect (one glaring example is
switching the locations of the expected and observed values in assertEquals
assertions).  Which is just fine because this is going to be a learning
opportunity for you to solve these problems and gain a deeper understanding of
Selenium Web Driver API.

With full expectation with things will fail, let's invoke the Maven test phase:

```
mvn test
```

Alternatively, you may invoke the Test Runner extension for JUnit on VSCode.

If things go properly, you will see the browser pop up repeatedly for each test
case, perform the actions, and close.  In the command line, you should see:

```
...
[INFO] Results:
[INFO]
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  44.361 s
[INFO] Finished at: 2024-09-14T18:51:20-04:00
[INFO] ------------------------------------------------------------------------
```

If you do see test failures, please read the failure message and the stack
trace for each failure to figure out what went wrong.  Then, modify each test
case that fails using tips listed in the next section.

## Tips for JUnit + Selenium problem solving

Here are a few tips for dealing with Selenium JUnit test failures.

### Conversion of relative URLs to absolute URLs

When you do assertions against URLs in JUnit, you may come across test failures
where the difference is the presence of "https://www.pitt.edu/" in the observed
URL whereas the expected URL does not contain that prefix.  Now if you inspect
that element on the web browser by right clicking on it and clicking "Inspect",
you will see that the actual URL attribute does not contain the
"https://www.pitt.edu" prefix.  It appears that the Selenium web driver is
internally attaching that prefix, converting all relative URLs to absolute
URLs.  Well, according to the Selenium maintainers, this is not a bug but a
feature:

https://github.com/seleniumhq/selenium-google-code-issue-archive/issues/1943

This is done supposedly for browser compatibility reasons according to a reply
in the GitHub issue.  So you need to modify the expected URLs in your
assertions to take into account that behavior.

### How to enforce uniform window sizes

Another common problem is that depending on the browser window size, certain
elements may disappear.  This is often done by web developers to tailor the
content to the window size so that it is not too crowded.  If Selenium web
driver does not explicitly set the size of the browser window, it will default
to a certain size that may vary depending on the testing platform.  This can
lead to unrepeatable testing.

One way to solve this is to uniformly set the window size at the @Before
setUp() method so that all your test cases are tested on the same dimensions
(and remove all calls to setSize in your test cases):

   ```
   driver.manage().window().setSize(new Dimension(1200, 800));
   ```

### How to deal with race conditions

One big headache with Selenium is that there is an inherent **race
condition** in the way it works.  There are three components to this
distributed system: the web browser that renders the web page and runs
JavaScript, the web server that sends web data to the web browser
intermittently, and the web driver that sends commands to the web browser to
control its actions.  These three components will not synchronize with each
other unless you tell them to and events (such as page loads from web
server, DOM element rendering by the web browser, and commands from the web
driver) can happen in arbitrary order.  For example, your web browser may
not have finished rendering a button before your web driver sends a command
to click on it.  This leads to nondeterminism and unreproducible testing.

So why am I getting these race conditions on the JUnit test all of a sudden,
when they did not occur with Selenium IDE?  That is likely not because the
Selenium IDE did not have race conditions.  It is that your JUnit test steps
execute much more quickly compared to Selenium IDE test steps and more easily
expose the existing race condition.  You will notice that if you insert a
Thread.sleep(1000) before steps with race conditions, the frequency of race
conditions suddenly decreases drastically.  But of course this is not the
proper way to resolve race conditions because 1) sleep is not a form of
synchronization that removes races (just makes them less likely) and 2) the
sleep introduces arbitrary wait time that slows down testing.

Fortunately, Selenium does provide you with a long list of synchronization APIs
that allow you to wait for the correct moment to perform a step.  Details about
the different types of wait APIs available on Selenium are described in:

https://www.selenium.dev/documentation/webdriver/waits/
   
1. Often, setting an **implicit wait** at the beginning is enough to solve most
race conditions.  It ensures that the web driver implicitly waits for the given
amount of time for a target element to be rendered when sending any command,
before flagging a failure.  Once you set an implicit wait on your web driver
(most often in the @Before setUp() method), it applies to all Selenium commands
targeting elements, so it is a quick (and dirty) way to resolve a lot of race
conditions.

1. Sometimes, you may have to synchronize on events other than an element
getting rendered.  For that, you will have to do an **explicit wait** on that
event.  Here is an exhaustive list of events that you can wait on:

   https://www.selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/ExpectedConditions.html

We are going to practice using explicit waits to solve race conditions in our
two test cases: TEST-4-SCHOOLS-SCI and TEST-6-SEARCH-CSC.

TEST-4-SCHOOLS-SCI: A race condition may cause you to get an empty string on
the 3rd element in the school list when comparing with "Computing &
Information".  In that case, you will need to insert an explicit wait using the
**wait for element visible** command on Selenium IDE to wait for the "Colleges
& Schools" list header to appear, right before performing that assertion, to
make sure that the list has correctly rendered.  When exported to JUnit code,
the Selenium IDE command will get translated to the following snippet of code:

```
    // 4 | waitForElementVisible | id=block-collegesschools-menu | 30000
    {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("block-collegesschools-menu")));
    }
```

You may need to import java.time.Duration for the above to compile:

```
import java.time.Duration;
```


TEST-6-SEARCH-CSC: A race condition may cause you to get an "element not
interactable" exception when clicking on the search box in order to type
"computer science club".  That is because there is a delay between clicking on
the search icon and the search box appearing, during which you are attempting
the click.  To avoid this, you need to insert an explicit wait using the **wait
for element editable** command on Selenium IDE targeting the search box so that
it is ready for editing before clicking on it.  This translates to the below
JUnit code:

```
    // 4 | waitForElementEditable | id=gsc-i-id1 | 30000
    {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
      wait.until(ExpectedConditions.elementToBeClickable(By.id("gsc-i-id1")));
    }
```

TEST-6-SEARCH-CSC: A race condition may prevent you from finding the "Student
Organization Spotlight: Computer Science Club (CSC)" item in the search list.
The item appears eventually --- it is just that there is a slight delay before
the search list populates and you are attempting to find the item concurrently.
To prevent this from happening, insert an explicit wait using the **wait for
element visible** command on Selenium IDE targeting the 10th element in the
search list so that the list is fully visible before we attempt to search our
item.  This translates to the below JUnit code:

```
    // 8 | waitForElementVisible | xpath=//div[10]/div/div/div/a | 30000
    {
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
      wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[10]/div/div/div/a")));
    }
```

# Submission

Submit the repository created by GitHub Classroom to GradeScope at the
**Exercise 3 GitHub** link.  Make sure the files "PittEdu.side" and
"PittEduTest.java" are in your submission.  Once you submit, GradeScope will
run the autograder to grade you and give feedback.  If you get deductions, fix
your code based on the feedback and resubmit.  Repeat until you don't get
deductions.

If you have insurmountable difficulties that you could not resolve by either
asking your classmates or your instructor, please complete the reflection
section in the ReportTemplate.docx file, export it to ReportTemplate.pdf, and
then submit the file as part of your repository.  If you do not write a
reflection even when you have deductions on the autograder, I can only assume
that you did not put any effort into it.

# GradeScope Feedback

The GradeScope autograder simply runs your 6 tests against www.pitt.edu and
deducts 5 points per test failure.

You may be curious how I was able to run the tests on the GradeScope cloud
runners when they most likely don't have displays to render the Chrome browser.
The Chrome web driver, as well as other web drivers, can be run in "headless"
mode.  That is, the tests can be performed inside the web engine without having
to actually display the page.  This is common practice since in a work setting,
testers will be running tests on server machines or even on the cloud in Docker
images like I did.  If you need to do this in the future, you can achieve this
by passing additional options when creating the Chrome web driver:

```
options.addArguments("--headless");			// Enable running without a display
options.addArguments("--disable-dev-shm-usage");	// Disable /dev/shm which is limited to 64MB in Docker and use /tmp/ instead to store shared memory files
```

I add the above options by replacing the setUp() method in PittEduTest.java
with my own version that looks like the following:

```
@Before
public void setUp() {
    ChromeOptions options = new ChromeOptions();
    options.addArguments("--headless");			// Enable running without a display
    options.addArguments("--disable-notifications");
    options.addArguments("--disable-dev-shm-usage");	// Disable /dev/shm which is limited to 64MB in Docker and use /tmp/ instead to store shared memory files
    options.addArguments("--no-sandbox");		// A quick and dirty way to run Selenium as root, bypassing the OS security model
    driver = new ChromeDriver(options);
    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    driver.manage().window().setSize(new Dimension(1200, 800));
    js = (JavascriptExecutor) driver;
    vars = new HashMap<String, Object>();
}
```

# Resources

These links are the same ones posted at the end of the slides:

* Selenium IDE Getting Started:
https://www.selenium.dev/selenium-ide/docs/en/introduction/getting-started

* Selenium IDE Command Reference:  
https://www.selenium.dev/selenium-ide/docs/en/api/commands

* Selenium WebDriver Tutorial:
https://www.selenium.dev/documentation/webdriver/

* Official W3C XPath specification:
https://www.w3.org/TR/xpath/

* Unofficial XPath tutorial:
https://www.w3schools.com/xml/xpath_intro.asp.

* Official W3C CSS selector specification:
https://www.w3.org/TR/selectors/

* Unofficial CSS selector tutorial:
https://www.w3schools.com/cssref/css_selectors.asp

# Extra Credit

DUE: October 21 (Monday), 2024 before start of class

## Description

This extra credit is going to be 0.2 points out of 100 points for the entire
course, for anyone who is able to do this.

Previously, the suggested method for testing TEST-5-SCHOOLS-COUNT was to use
"assert element present" for the 16th item, followed by a "assert element not
present" for the 17th item.  

Admittedly, this is clunky.  It would be much cleaner if we could count the
number elements directly and verify that it is 16.

The Selenium IDE command "store xpath count" allows you to count the number of
elements that matches an xpath and store it inside a Selenium variable.  You
can later verify the value of the variable using the "assert" command.  Now,
you will not be able to acquire that xpath using the target selector button in
the IDE.  You will have to inspect the element on your web browser and come up
with a pattern than can match all 16 items in that list.  On both the Chrome
and Firefox browsers, when you right click on an HTML element to bring up the
context menu, there is an "Inspect" menu.  Clicking on the "Inspect" menu
brings up Inspector view.  When you right click on the highlighted element
again, there is a "Copy" menu on the context menu.  This allows you to copy the
XPath of the given element.

You will have to do a little bit of your own research on xpaths to figure out
what actual XPath to pass to the "store xpath count" command.  You can read the
[official W3C specification](https://www.w3.org/TR/xpath/).

But just like most specifications, it is focused on exact specification rather
than readability.  You will find this unofficial tutorial using examples more
digestible: https://www.w3schools.com/xml/xpath_intro.asp.

## Submission

Please do an extra submission for the extra credit.  When you are done, submit
at the **Exercise 3 Extra Credit** link.  You should get a full score on the
autograder and have used "store xpath count" to get credit.  Make sure the
files "PittEdu.side" and "PittEduTest.java" are in your submission.
